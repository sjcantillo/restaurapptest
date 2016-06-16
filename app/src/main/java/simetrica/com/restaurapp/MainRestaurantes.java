package simetrica.com.restaurapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.desandroid.framework.ada.exceptions.AdaFrameworkException;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import simetrica.com.restaurapp.simetrica.com.modelo.OpenTable;
import simetrica.com.restaurapp.simetrica.com.modelo.Restaurante;
import simetrica.com.restaurapp.simetrica.com.simetrica.com.persistencia.ApplicationDataContext;
import simetrica.com.restaurapp.simetrica.com.util.MyRestFulGP;
import simetrica.com.restaurapp.simetrica.com.util.RestaurantesAdapter;

public class MainRestaurantes extends AppCompatActivity {

    private List<Restaurante> restaurantes;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    public long page;
    public long perPage =10;
    private Context context;
    private ApplicationDataContext appDataContext;
    private RestaurantesAdapter adapter;
    private boolean loading = true;
    SwipeRefreshLayout swipeRefreshLayout;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private final String HTTP_EVENT = "http://opentable.herokuapp.com/api/restaurants";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_restaurantes);
       // toolbar= (Toolbar) findViewById(R.id.appbar);
        if (Build.VERSION.SDK_INT >= 21){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        }
        //setSupportActionBar(toolbar);
        context=this.getBaseContext();
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        SharedPreferences pref = getSharedPreferences("ParametrosBasicos", Context.MODE_PRIVATE);
        boolean primerAcceso = pref.getBoolean("acceso", true);
        page= pref.getLong("currentPage",(long) 1);
        if(primerAcceso)
        {
            //llamar el proceso que me trae los datois
            traerDatosWS();
            SharedPreferences.Editor edit = pref.edit();
            edit.putBoolean("acceso",false);
            edit.putLong("currentPage", (long)1);
            edit.commit();
        }
        traerDatosWS();
        llenarRecyclerView();
        adapter=new RestaurantesAdapter(restaurantes);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView idTxtView =(TextView) v.findViewById(R.id.id);
                String idRestaurante =idTxtView.getText().toString();
                List<Restaurante> restauratns;
                ApplicationDataContext dataContextAux = getApplicationDataContext();
                try {
                    restauratns=(List<Restaurante>)dataContextAux.restauntantesDAO.search(true,"id_r = ? ", new String[]{idRestaurante}, "id_r", null,null,null,1);
                     if(restauratns!=null)
                     {
                        Restaurante item =restauratns.get(0);

                         Intent intent = new Intent(MainRestaurantes.this, RestauranteA.class);
                         intent.putExtra("parametro", item);
                         startActivity(intent);

                     }
                } catch (AdaFrameworkException e) {
                    e.printStackTrace();
                }
            }
        });
        final  LinearLayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data
                            swipeRefreshLayout.setRefreshing(true);
                            page=page+1;

                            SharedPreferences pref = getSharedPreferences("ParametrosBasicos", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = pref.edit();
                            edit.putBoolean("acceso",false);
                            edit.putLong("currentPage", page);
                            edit.commit();
                            traerDatosWS();


                        }
                    }
                }
            }
        });
        recyclerView.setAdapter(adapter);


    }
    public Context getContext() {
        return context;
    }

    private ApplicationDataContext getApplicationDataContext() {
        if (appDataContext == null) {
            try {
                appDataContext = new ApplicationDataContext(getContext());
            } catch (Exception e) {
                Log.e("Androcode", "Error inicializando ApplicationDataContext: " + e.getMessage());
            }
        }
        return appDataContext;
    }
    private boolean hasFooter() {
        return false;
    }
    public void llenarRecyclerView()
    {
        restaurantes = new ArrayList<Restaurante>();
        appDataContext = getApplicationDataContext();
        try {
            appDataContext.restauntantesDAO.fill("state , name");

            for (int i = 0; i < appDataContext.restauntantesDAO.size(); i++) {

                restaurantes.add(appDataContext.restauntantesDAO.get(i));
            }




        } catch (AdaFrameworkException e) {
            e.printStackTrace();
        }



    }

    public void traerDatosWS()
    {
        String ruta = this.HTTP_EVENT;

        new MyAsyncTask(context).
                execute("GET", "" + perPage, ""+page,ruta);
    }

class MyAsyncTask extends AsyncTask<String, Void, String> {


    private Context context;
    private ProgressDialog progressDialog;

    /**
     * Constructor de clase
     */
    public MyAsyncTask(Context context) {
        this.context = context;

    }

    /**
     * Realiza la tarea en segundo plano
     *
     * @param params[0] Comando GET/POST
     *
     */
    @Override
    protected String doInBackground(String... params) {
        if (this.isCancelled()) {
            return null;
        }

        MyRestFulGP myRestFulGP = new MyRestFulGP();
        List<NameValuePair> parames = new ArrayList<NameValuePair>();
        parames.add(new BasicNameValuePair("city", "New York"));
        parames.add(new BasicNameValuePair("per_page", params[1]));
        parames.add(new BasicNameValuePair("page", params[2]));

        if (params[0].equals("GET")) {

            String jsonResult = null;
            try {
                jsonResult = myRestFulGP.addEventGet(parames, params[3]);
                Log.i("jsonResultDatos", jsonResult);
                Gson gsonR = new Gson();
                OpenTable resultado=new OpenTable();
                if (Build.VERSION.SDK_INT >= 23) {
                    JSONObject jsonRootObject = new JSONObject(jsonResult);
                    JSONArray jsonArray = jsonRootObject.optJSONArray("restaurants");
                    List<Restaurante> restautans= new ArrayList<Restaurante>();
                    for(int i=0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Restaurante objeto = new Restaurante(Long.parseLong(jsonObject.optString("id").toString()),
                                jsonObject.optString("address").toString() ,
                                jsonObject.optString("city").toString() ,
                                jsonObject.optString("country").toString() ,
                                jsonObject.optString("image_url").toString() ,
                                jsonObject.optString("name").toString()  ,
                                Double.parseDouble(jsonObject.optString("lat").toString()),
                                Double.parseDouble(jsonObject.optString("lng").toString()),
                                jsonObject.optString("phone").toString(),
                                jsonObject.optString("postal_code").toString() ,
                                jsonObject.optString("reserve_url").toString() ,
                                jsonObject.optString("state").toString() ,
                                Double.parseDouble(jsonObject.optString("price").toString()) ,
                                jsonObject.optString("mobile_reserve_url").toString());
                       restautans.add(objeto);
                    }
                    resultado.setRestaurants(restautans);
                }else
                {
                    resultado = (OpenTable) gsonR.fromJson(jsonResult, OpenTable.class);
                }

                ApplicationDataContext dataContext = getApplicationDataContext();
                ApplicationDataContext dataContextAux = getApplicationDataContext();


                if (dataContext != null) {
                    for(int i=0; i < resultado.getRestaurants().size(); i++)
                         {

                             if(dataContextAux.restauntantesDAO.search(true,"id_r = ? ", new String[]{resultado.getRestaurants().get(i).getId()+""}, "id_r", null,null,null,1) ==null) {

                                 dataContext.restauntantesDAO.add(resultado.getRestaurants().get(i));
                             }

                        }
                    dataContext.restauntantesDAO.save();


                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (AdaFrameworkException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        return "Restaurantes actualizados";
    }

    /**
     * Antes de comenzar la tarea muestra el progressDialog
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();





    }

    /**
     * Cuando se termina de ejecutar, cierra el progressDialog y retorna el resultado a la interfaz
     **/
    @Override
    protected void onPostExecute(String resul) {
        llenarRecyclerView();
        adapter.clear();
        adapter.addAll(restaurantes);
        if(page>1)
        swipeRefreshLayout.setRefreshing(false);

        loading=true;
        /*adapter.clear();
        adapter.addAll(llenarLista(context));*/



    }


}
}
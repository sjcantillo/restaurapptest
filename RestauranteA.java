package simetrica.com.restaurapp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

import simetrica.com.restaurapp.simetrica.com.modelo.Restaurante;

public class RestauranteA extends AppCompatActivity {

    Bitmap bitmap;
    ProgressDialog pDialog;
    ImageView imgRes;
    EditText edtName ;
    EditText edtdireccion;
    EditText edtciudad ;
    EditText edtEstado;
    EditText edtTelefono;
    EditText edtPostal ;
    Restaurante rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurante);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rest = (Restaurante)getIntent().getExtras().getSerializable("parametro");
        new LoadImage().execute(rest.getImage_url(),rest.getAddress());
        edtName = (EditText) this.findViewById(R.id.input_name);
        edtdireccion = (EditText) this.findViewById(R.id.input_direccion);
        edtciudad = (EditText) this.findViewById(R.id.input_ciudad);
        edtEstado = (EditText) this.findViewById(R.id.input_estado);
        edtTelefono = (EditText) this.findViewById(R.id.input_Telefono);
        edtPostal = (EditText) this.findViewById(R.id.input_postal);
        imgRes = (ImageView) this.findViewById(R.id.imagen);

        Log.i("DemoRecView", "Pulsado el elemento " + rest.getAddress());



    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        String address;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RestauranteA.this);
            pDialog.setMessage("Cargando Imagen ....");
            pDialog.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
                address=args[1];

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                imgRes.setImageBitmap(image);
                edtName.setText(rest.getName());
                edtdireccion.setText(address);
                edtciudad.setText(rest.getCity());
                edtEstado.setText(rest.getState());
                edtTelefono.setText(rest.getPhone());
                edtPostal.setText(rest.getPostal_code());
                pDialog.dismiss();

            }else{

                pDialog.dismiss();
                Toast.makeText(RestauranteA.this, "Imagen no se encuentra disponible", Toast.LENGTH_SHORT).show();

            }
        }
    }
}

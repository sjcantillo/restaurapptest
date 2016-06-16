package simetrica.com.restaurapp.simetrica.com.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import simetrica.com.restaurapp.R;
import simetrica.com.restaurapp.simetrica.com.modelo.Restaurante;

/**
 * Created by rytscc on 15/06/2016.
 */
public class RestaurantesAdapter extends RecyclerView.Adapter<RestauranteViewHolder>  implements View.OnClickListener  {
    private List<Restaurante> data;
    private View.OnClickListener listener;


    public RestaurantesAdapter(@NonNull List<Restaurante> data) {
        this.data = data;

    }
    @Override
    public RestauranteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_restaurante, parent, false);
        row.setOnClickListener(this);
        return new RestauranteViewHolder(row);
    }

    @Override
    public void onBindViewHolder(RestauranteViewHolder holder, int position) {
        Restaurante restaurant = data.get(position);
        holder.getNameTextView().setText(restaurant.getName().toString());
        holder.getAddressTextView().setText(restaurant.getAddress());
        holder.getId().setText("" + restaurant.getId());
        holder.getStateTextView().setText(restaurant.getState());

    }
    public void clear(){
        data.clear();
        notifyDataSetChanged();
    }
    public void addAll(List<Restaurante> lista){
        data.addAll(lista);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }
}

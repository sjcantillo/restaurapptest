package simetrica.com.restaurapp.simetrica.com.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import simetrica.com.restaurapp.R;

/**
 * Created by rytscc on 15/06/2016.
 */
public class RestauranteViewHolder extends RecyclerView.ViewHolder {
    private TextView id;
    private TextView nameTextView;
    private TextView addressTextView;
    private TextView stateTextView;
    public RestauranteViewHolder(View itemView) {
        super(itemView);
        id = (TextView) itemView.findViewById(R.id.id);
        nameTextView = (TextView) itemView.findViewById(R.id.name);
        addressTextView = (TextView) itemView.findViewById(R.id.address);
        stateTextView= (TextView) itemView.findViewById(R.id.state);
    }

    public TextView getNameTextView() {
        return nameTextView;
    }

    public TextView getAddressTextView() {
        return addressTextView;
    }

    public TextView getStateTextView() {
        return stateTextView;
    }

    public TextView getId() {
        return id;
    }

    public void setId(TextView id) {
        this.id = id;
    }


}

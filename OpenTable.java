package simetrica.com.restaurapp.simetrica.com.modelo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rytscc on 15/06/2016.
 */
public class OpenTable implements Serializable {

    private long  total_entries;
    private long  per_page;
    private long  current_page;
    private List<Restaurante> restaurants;

    public OpenTable() {
    super();
    }

    public OpenTable(long total_entries, long per_page, long current_page, List<Restaurante> restaurants) {
        this.total_entries = total_entries;
        this.per_page = per_page;
        this.current_page = current_page;
        this.restaurants = restaurants;
    }

    public long getTotal_entries() {
        return total_entries;
    }

    public void setTotal_entries(long total_entries) {
        this.total_entries = total_entries;
    }

    public long getPer_page() {
        return per_page;
    }

    public void setPer_page(long per_page) {
        this.per_page = per_page;
    }

    public long getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(long current_page) {
        this.current_page = current_page;
    }

    public List<Restaurante> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurante> restaurants) {
        this.restaurants = restaurants;
    }
}

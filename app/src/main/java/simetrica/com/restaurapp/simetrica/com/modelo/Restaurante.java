package simetrica.com.restaurapp.simetrica.com.modelo;

import android.content.Entity;

import com.desandroid.framework.ada.annotations.Table;
import com.desandroid.framework.ada.annotations.TableField;

import java.io.Serializable;

/**
 * Created by silvio on 15/06/2016.
 */
@Table(name = "Restaurante")
public class Restaurante extends com.desandroid.framework.ada.Entity implements Serializable   {

    @TableField(name = "id_r", datatype = DATATYPE_LONG, required = true)
    private long id;
    @TableField(name = "address", datatype = DATATYPE_TEXT, required = true)
    private String address;
    @TableField(name = "city", datatype = DATATYPE_TEXT, required = true)
    private String city;
    @TableField(name = "country", datatype = DATATYPE_TEXT, required = true)
    private String country;
    @TableField(name = "image_url", datatype = DATATYPE_TEXT, required = true)
    private String image_url;
    @TableField(name = "name", datatype = DATATYPE_TEXT, required = true)
    private String name;
    @TableField(name = "lat", datatype = DATATYPE_DOUBLE, required = true)
    private double  lat;
    @TableField(name = "lng", datatype = DATATYPE_DOUBLE, required = true)
    private double  lng;
    @TableField(name = "phone", datatype = DATATYPE_TEXT, required = true)
    private String phone;
    @TableField(name = "postal_code", datatype = DATATYPE_TEXT, required = true)
    private String postal_code;
    @TableField(name = "reserve_url", datatype = DATATYPE_TEXT, required = true)
    private String reserve_url;
    @TableField(name = "price", datatype = DATATYPE_DOUBLE, required = true)
    private double price;
    @TableField(name = "state", datatype = DATATYPE_TEXT, required = true)
    private String state;
    @TableField(name = "mobile_reserve_url", datatype = DATATYPE_TEXT, required = true)
    private String mobile_reserve_url;

    public Restaurante() {
        super();
    }

    public Restaurante(long id, String address, String city, String country, String image_url, String name, double lat, double lng, String phone, String postal_code, String reserve_url, String state, double price, String mobile_reserve_url) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.country = country;
        this.image_url = image_url;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.phone = phone;
        this.postal_code = postal_code;
        this.reserve_url = reserve_url;
        this.state = state;
        this.price = price;
        this.mobile_reserve_url = mobile_reserve_url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getReserve_url() {
        return reserve_url;
    }

    public void setReserve_url(String reserve_url) {
        this.reserve_url = reserve_url;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMobile_reserve_url() {
        return mobile_reserve_url;
    }

    public void setMobile_reserve_url(String mobile_reserve_url) {
        this.mobile_reserve_url = mobile_reserve_url;
    }
}

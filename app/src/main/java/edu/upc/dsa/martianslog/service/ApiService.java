package edu.upc.dsa.martianslog.service;

import edu.upc.dsa.martianslog.models.Product;


import java.util.List;

import edu.upc.dsa.martianslog.models.ProfileUser;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService
{
    public static final String API_URL="http://10.0.2.2:8080/dsaApp/";
    @POST("user/login")
    Call<LoginUsuari> loginUser(@Body LoginUsuari user);

    @GET("user/getUser/{username}")
    Call<ProfileUser> getUser(@Path("username") String username);

    @POST("user/register")
    Call<RegisterUsuari> addUser(@Body RegisterUsuari usuari);

    @GET("store/getStoreProducts")
    Call<List<Product>> getStoreProducts();

    @POST("store/addProduct")
    Call<Product> addProduct(@Body Product product);

    @GET("/store/getProduct/{id}")
    Call<Product> getProduct(@Path("id") String id);

    @DELETE("/store/deleteProduct/{id}")
    Call<Void> deleteProduct(@Path("id") String id);

    @POST("/store/buyProduct/{username}/{idProduct}")
    Call<List<Product>> buyProduct(@Path("username") String username, @Path("idProduct") String idProduct);
}

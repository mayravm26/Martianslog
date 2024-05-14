package edu.upc.dsa.martianslog.service;

import edu.upc.dsa.martianslog.models.Usuari;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService
{
    @POST("user/login")
    Call<LoginUsuari> loginUser(@Body LoginUsuari user);

    @POST("user/register")
    Call<Usuari> addUser(@Body RegisterUsuari usuari);

}

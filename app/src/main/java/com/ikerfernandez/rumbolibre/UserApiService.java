package com.ikerfernandez.rumbolibre;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApiService {
    @POST("/api/usuarios/login")
    Call<Usuario> login(@Body Usuario usuario);

    @POST("/api/usuarios/registro")
    Call<Usuario> register(@Body Usuario usuario);

    @GET("/api/usuarios/{id}")
    Call<Usuario> getUser(@Path("id") Long id);
}


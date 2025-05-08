package com.ikerfernandez.rumbolibre;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiService {
    @POST("/api/usuarios/login")
    Call<Usuario> login(@Body Usuario usuario);

    @POST("/api/usuarios/registro")
    Call<Usuario> register(@Body Usuario usuario);
}


package com.ikerfernandez.rumbolibre;
import android.util.Log;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface SupabaseApiService {
    @GET("Vuelos?select=*")
    Call<List<Vuelo>> getAllVuelos(
            @Header("apikey") String apiKey,
            @Header("Authorization") String authHeader
    );

    //Filtrar por origen
    @GET("Vuelos")
    Call<List<Vuelo>> getVuelosByOrigen(
            @Header("apikey") String apiKey,
            @Header("Authorization") String authHeader,
            @Query("origen") String origen
    );
}
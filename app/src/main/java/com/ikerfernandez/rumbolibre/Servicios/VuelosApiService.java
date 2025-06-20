package com.ikerfernandez.rumbolibre.Servicios;

import com.ikerfernandez.rumbolibre.Modelos.Vuelo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VuelosApiService {
    @GET("vuelos/get")
    Call<List<Vuelo>> getAllVuelos();

    @GET("vuelos/buscar")
    Call<List<Vuelo>> buscarVuelos(
            @Query("ciudadOrigen") String ciudadOrigen,
            @Query("ciudadDestino") String ciudadDestino,
            @Query("aerolinea") String aerolinea
    );

    @GET("vuelos/random")
    Call<Vuelo> getVueloRandom();
}
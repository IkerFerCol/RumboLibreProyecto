package com.ikerfernandez.rumbolibre;

import com.ikerfernandez.rumbolibre.Vuelo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface VuelosApiService {
    @GET("vuelos/get")
    Call<List<Vuelo>> getAllVuelos();
}
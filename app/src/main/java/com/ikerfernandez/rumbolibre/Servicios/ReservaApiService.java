package com.ikerfernandez.rumbolibre.Servicios;

import com.ikerfernandez.rumbolibre.Modelos.Reserva;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReservaApiService {
    @POST("/api/reserva")
    Call<Reserva> crearReserva(@Body Reserva reserva);

    @GET("/api/reserva")
    Call<List<Reserva>> getReservas();

    @GET("api/reserva/usuario/{nombreUsuario}")
    Call<List<Reserva>> obtenerReservasPorUsuario(@Path("nombreUsuario") String nombreUsuario);


    @DELETE("/api/reserva/{id}")
    Call<Void> eliminarReserva(@Path("id") Long id);


}


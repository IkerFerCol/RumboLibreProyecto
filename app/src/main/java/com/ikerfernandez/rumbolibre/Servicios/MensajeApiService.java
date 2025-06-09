package com.ikerfernandez.rumbolibre.Servicios;

import com.ikerfernandez.rumbolibre.Modelos.Mensaje;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MensajeApiService {

    @GET("/api/mensaje")
    Call<List<Mensaje>> getMensajes();

    @POST("/api/mensaje")
    Call<Mensaje> enviarMensaje(@Body Mensaje mensaje);

}

package com.ikerfernandez.rumbolibre;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface SupabaseApiService {
    @GET("Vuelo?select=*")
    Call<List<Vuelo>> getAllVuelos(
            @Header("apikey") String apiKey,
            @Header("Authorization") String authHeader
    );

    //Filtrar por origen
    @GET("Vuelo")
    Call<List<Vuelo>> getVuelosByOrigen(
            @Header("apikey") String apiKey,
            @Header("Authorization") String authHeader,
            @Query("origen") String origen
    );
}
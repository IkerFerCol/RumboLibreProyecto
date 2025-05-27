package com.ikerfernandez.rumbolibre;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FavoritosApiService {

    @GET("api/favoritos/{usuarioId}")
    Call<List<Vuelo>> getFavoritos(@Query("idUsuario") Long idUsuario);

    @POST("api/favoritos/{usuarioId}/agregar/{vueloId}")
    Call<Void> addFavorito(@Query("idUsuario") Long idUsuario, @Query("idVuelo") Long idVuelo);

    @DELETE("api/favoritos/{usuarioId}/eliminar/{vueloId}")
    Call<Void> deleteFavoritos(@Query("idUsuario") Long idUsuario, @Query("idVuelo") Long idVuelo);
}

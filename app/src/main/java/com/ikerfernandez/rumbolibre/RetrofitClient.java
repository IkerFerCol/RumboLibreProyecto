package com.ikerfernandez.rumbolibre;

import com.ikerfernandez.rumbolibre.Servicios.MensajeApiService;
import com.ikerfernandez.rumbolibre.Servicios.ReservaApiService;
import com.ikerfernandez.rumbolibre.Servicios.UserApiService;
import com.ikerfernandez.rumbolibre.Servicios.VuelosApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
//    private static final String BASE_URL = "http://192.168.0.191:8080";
    private static final String BASE_URL = "https://rumbolibreapi.onrender.com";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static VuelosApiService getApiService() {
        return getClient().create(VuelosApiService.class);
    }

    public static UserApiService getUserApiService() {
        return getClient().create(UserApiService.class);
    }

    public static MensajeApiService getMensajeApiService() {
        return getClient().create(MensajeApiService.class);
    }

    public static ReservaApiService getReservaApiService() {
        return getClient().create(ReservaApiService.class);
    }
}
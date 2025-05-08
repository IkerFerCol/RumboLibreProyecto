package com.ikerfernandez.rumbolibre;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://192.168.0.191:8080";
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
}
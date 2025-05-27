package com.ikerfernandez.rumbolibre;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritosFragment extends Fragment {

    private RecyclerView recyclerView;
    private VueloAdapter adapter;
    private List<Vuelo> listaFavoritos = new ArrayList<>();
    private FavoritosApiService favoritosApiService;
    private Long idUsuario;

    public FavoritosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_favoritos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerFavoritos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        idUsuario = (long) obtenerIdUsuario();
        favoritosApiService = RetrofitClient.getClient().create(FavoritosApiService.class);

        cargarVuelosFavoritos();
    }

    private int obtenerIdUsuario() {
        SharedPreferences prefs = requireContext().getSharedPreferences("usuario", Context.MODE_PRIVATE);
        return prefs.getInt("id", -1);
    }

    private void cargarVuelosFavoritos() {
        Call<List<Vuelo>> call = favoritosApiService.getFavoritos(idUsuario);
        call.enqueue(new Callback<List<Vuelo>>() {
            @Override
            public void onResponse(Call<List<Vuelo>> call, Response<List<Vuelo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaFavoritos = response.body();
                    adapter = new VueloAdapter(listaFavoritos, getContext());
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Error al obtener favoritos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Vuelo>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de red", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


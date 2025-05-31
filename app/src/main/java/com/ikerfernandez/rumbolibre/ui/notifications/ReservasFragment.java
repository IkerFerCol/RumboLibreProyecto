package com.ikerfernandez.rumbolibre.ui.notifications;

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

import com.ikerfernandez.rumbolibre.R;
import com.ikerfernandez.rumbolibre.Reserva;
import com.ikerfernandez.rumbolibre.ReservaApiService;
import com.ikerfernandez.rumbolibre.ReservasAdapter;
import com.ikerfernandez.rumbolibre.RetrofitClient;
import com.ikerfernandez.rumbolibre.Vuelo;
import com.ikerfernandez.rumbolibre.VuelosApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservasFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReservasAdapter adapter;
    private List<Vuelo> listaVuelos = new ArrayList<>();
    private List<Reserva> listaReservas = new ArrayList<>();
    private ReservaApiService reservaApi;
    private String nombreUsuario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.activity_reservas, container, false);
        recyclerView = vista.findViewById(R.id.recyclerViewReservas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SharedPreferences prefs = requireContext().getSharedPreferences("datos_usuario", Context.MODE_PRIVATE);
        nombreUsuario = prefs.getString("usuarioNombre", "Invitado");

        reservaApi = RetrofitClient.getClient().create(ReservaApiService.class);
        cargarReservas();

        return vista;
    }

    private void cargarReservas() {
        Call<List<Reserva>> call = reservaApi.obtenerReservasPorUsuario(nombreUsuario);
        call.enqueue(new Callback<List<Reserva>>() {
            @Override
            public void onResponse(Call<List<Reserva>> call, Response<List<Reserva>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaReservas = response.body();
                    VuelosApiService vuelosApi = RetrofitClient.getClient().create(VuelosApiService.class);
                    Call<List<Vuelo>> callVuelos = vuelosApi.getAllVuelos();
                    callVuelos.enqueue(new Callback<>() {
                        @Override
                        public void onResponse(Call<List<Vuelo>> call, Response<List<Vuelo>> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                listaVuelos = response.body();
                                adapter = new ReservasAdapter(getContext(), listaReservas, listaVuelos, reservaApi);
                                recyclerView.setAdapter(adapter);
                            } else {
                                Toast.makeText(getContext(), "Error al cargar vuelos", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<List<Vuelo>> call, Throwable t) {
                            Toast.makeText(getContext(), "Error de red al cargar vuelos", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "No se pudieron cargar las reservas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Reserva>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de red", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

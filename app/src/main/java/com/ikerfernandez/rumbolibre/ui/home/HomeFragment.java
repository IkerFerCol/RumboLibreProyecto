package com.ikerfernandez.rumbolibre.ui.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.textfield.TextInputEditText;
import com.ikerfernandez.rumbolibre.MainActivity;
import com.ikerfernandez.rumbolibre.R;
import com.ikerfernandez.rumbolibre.RetrofitClient;
import com.ikerfernandez.rumbolibre.SettingsActivity;
import com.ikerfernandez.rumbolibre.VuelosApiService;
import com.ikerfernandez.rumbolibre.Vuelo;
import com.ikerfernandez.rumbolibre.VueloAdapter;
import com.ikerfernandez.rumbolibre.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private List<Vuelo> listaVuelos = new ArrayList<>();
    private VueloAdapter vueloAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Log.d("AQUI", "AQUI LLEGA");
        binding.recyclerViewVuelos.setLayoutManager(new LinearLayoutManager(getContext()));
        vueloAdapter = new VueloAdapter(listaVuelos, requireContext());
        binding.recyclerViewVuelos.setAdapter(vueloAdapter);

        fetchVuelos();




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void mostrarDialogoBusqueda() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_search, null);
        builder.setView(dialogView)
                .setTitle("Buscar vuelos");

        final TextInputEditText etOrigen = dialogView.findViewById(R.id.etOrigen);
        final TextInputEditText etDestino = dialogView.findViewById(R.id.etDestino);
        final TextInputEditText etAerolinea = dialogView.findViewById(R.id.etAerolinea);
        Button btnBuscar = dialogView.findViewById(R.id.btnBuscar);

        final AlertDialog dialog = builder.create();

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String origen = etOrigen.getText().toString();
                String destino = etDestino.getText().toString();
                String aerolinea = etAerolinea.getText().toString();

                buscarVuelos(origen, destino, aerolinea);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void buscarVuelos(String origen, String destino, String aerolinea) {
        VuelosApiService apiService = RetrofitClient.getClient().create(VuelosApiService.class);
        Call<List<Vuelo>> call = apiService.buscarVuelos(
                origen.isEmpty() ? null : origen,
                destino.isEmpty() ? null : destino,
                aerolinea.isEmpty() ? null : aerolinea
        );

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Vuelo>> call, Response<List<Vuelo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Vuelo> vuelosEncontrados = response.body();
                    if (vuelosEncontrados.isEmpty()) {
                        Toast.makeText(getContext(), "No se encontraron vuelos", Toast.LENGTH_SHORT).show();
                    } else {
                        vueloAdapter.actualizarLista(vuelosEncontrados);
                    }
                } else {
                    Toast.makeText(getContext(), "Error en la búsqueda", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Vuelo>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void fetchVuelos() {
        VuelosApiService apiService = RetrofitClient.getApiService();

        Call<List<Vuelo>> call = apiService.getAllVuelos();
        call.enqueue(new Callback<List<Vuelo>>() {
            @Override
            public void onResponse(Call<List<Vuelo>> call, Response<List<Vuelo>> response) {
                if (response.isSuccessful()) {
                    List<Vuelo> vuelos = response.body();
                    listaVuelos.clear();
                    listaVuelos.addAll(vuelos);
                    vueloAdapter.notifyDataSetChanged();

                    // Logs para depuración
                    Log.d("API_SUCCESS", "Vuelos obtenidos: " + listaVuelos.size());
                    for (Vuelo vuelo : vuelos) {
                        Log.d("VUELO", "Origen: " + vuelo.getCiudadOrigen() +
                                " | Destino: " + vuelo.getCiudadDestino());
                    }
                } else {
                    Log.e("API_ERROR", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Vuelo>> call, Throwable t) {
                Log.e("API_FAIL", "Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }
        if (item.getItemId() == R.id.action_buscar_vuelos) {
            mostrarDialogoBusqueda();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
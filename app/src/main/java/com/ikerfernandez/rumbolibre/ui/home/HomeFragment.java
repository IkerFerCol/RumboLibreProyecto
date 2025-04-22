package com.ikerfernandez.rumbolibre.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

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
        vueloAdapter = new VueloAdapter(listaVuelos);
        binding.recyclerViewVuelos.setAdapter(vueloAdapter);

        fetchVuelos();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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
        return super.onOptionsItemSelected(item);
    }
}
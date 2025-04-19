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
import android.widget.ArrayAdapter;

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
import com.ikerfernandez.rumbolibre.SupabaseApiService;
import com.ikerfernandez.rumbolibre.Vuelo;
import com.ikerfernandez.rumbolibre.VueloAdapter;
import com.ikerfernandez.rumbolibre.databinding.FragmentHomeBinding;
import com.ikerfernandez.rumbolibre.databinding.LvVuelosRowBinding;

import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;

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


    private void fetchVuelos(){
        SupabaseApiService apiService = RetrofitClient.getApiService();
        String apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZnYnluaW5tc3h1eGlzbWlsZ3VyIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDM1MTk4ODksImV4cCI6MjA1OTA5NTg4OX0.Pzpigdd2WunRIzElAEr_6YXdBitHxtS0NcW_8lwa8tU";
        String authHeader = "Bearer " + apiKey;

        Call<List<Vuelo>> call = apiService.getAllVuelos(apiKey, authHeader);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Vuelo>> call, Response<List<Vuelo>> response) {
                if (response.isSuccessful()) {
                    List<Vuelo> vuelos = response.body();
                    Log.d("VUELOS_SIZE", "Tamaño de la lista de vuelos: " + vuelos.size()); // Añadir este log
                    listaVuelos.clear();
                    listaVuelos.addAll(vuelos);
                    for (Vuelo vuelo : vuelos) {
                        Log.d("VUELO", "Origen " + vuelo.getCiudadOrigen() + " | Destino: " + vuelo.getCiudadDestino() + " | Precio: " + vuelo.getPrecio());
                    }
                    vueloAdapter.notifyDataSetChanged();
                } else {
                    Log.e("API_ERROR", "Código: " + response.code());
                }
                Log.d("API_SUCCESS", "Vuelos obtenidos: " + listaVuelos.size());
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
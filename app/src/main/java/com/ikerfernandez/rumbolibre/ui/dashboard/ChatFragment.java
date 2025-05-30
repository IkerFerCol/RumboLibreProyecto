package com.ikerfernandez.rumbolibre.ui.dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ikerfernandez.rumbolibre.Mensaje;
import com.ikerfernandez.rumbolibre.MensajeAdapter;
import com.ikerfernandez.rumbolibre.MensajeApiService;
import com.ikerfernandez.rumbolibre.R;
import com.ikerfernandez.rumbolibre.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatFragment extends Fragment {

    private RecyclerView recyclerMensajes;
    private EditText editContenido;
    private Button btnEnviar;
    private MensajeAdapter adapter;
    private List<Mensaje> listaMensajes = new ArrayList<>();
    private MensajeApiService mensajeApiService;
    private String nombreUsuario;

    public ChatFragment() {
        // Constructor vacío requerido
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_chat, container, false); // Usamos el mismo layout
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mensajeApiService = RetrofitClient.getMensajeApiService();

        SharedPreferences prefs = requireActivity().getSharedPreferences("datos_usuario", Context.MODE_PRIVATE);
        nombreUsuario = prefs.getString("usuarioNombre", "Invitado");
        Log.d("MENSAJE", "Nombre de usuario: " + nombreUsuario);

        recyclerMensajes = view.findViewById(R.id.recyclerMensajes);
        editContenido = view.findViewById(R.id.editContenido);
        btnEnviar = view.findViewById(R.id.btnEnviar);

        adapter = new MensajeAdapter(listaMensajes);
        recyclerMensajes.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerMensajes.setAdapter(adapter);

        cargarMensajes();

        btnEnviar.setOnClickListener(v -> {
            String contenido = editContenido.getText().toString().trim();
            if (!contenido.isEmpty()) {
                Mensaje nuevoMensaje = new Mensaje();
                nuevoMensaje.setAutor(nombreUsuario);
                nuevoMensaje.setContenido(contenido);

                mensajeApiService.enviarMensaje(nuevoMensaje).enqueue(new Callback<Mensaje>() {
                    @Override
                    public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                        if (response.isSuccessful()) {
                            editContenido.setText("");
                            cargarMensajes();
                        } else {
                            Log.e("MENSAJE", "Error al enviar: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Mensaje> call, Throwable t) {
                        Toast.makeText(getContext(), "Error al enviar", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void cargarMensajes() {
        mensajeApiService.getMensajes().enqueue(new Callback<List<Mensaje>>() {
            @Override
            public void onResponse(Call<List<Mensaje>> call, Response<List<Mensaje>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaMensajes.clear();
                    listaMensajes.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    recyclerMensajes.scrollToPosition(listaMensajes.size() - 1);
                }
            }

            @Override
            public void onFailure(Call<List<Mensaje>> call, Throwable t) {
                Toast.makeText(getContext(), "Error al cargar mensajes", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

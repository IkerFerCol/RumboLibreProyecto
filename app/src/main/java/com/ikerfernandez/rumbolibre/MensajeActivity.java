package com.ikerfernandez.rumbolibre;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ikerfernandez.rumbolibre.Adapters.MensajeAdapter;
import com.ikerfernandez.rumbolibre.Modelos.Mensaje;
import com.ikerfernandez.rumbolibre.Servicios.MensajeApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MensajeActivity extends AppCompatActivity {

    private RecyclerView recyclerMensajes;
    private EditText editContenido;
    private Button btnEnviar;
    private MensajeAdapter adapter;
    private List<Mensaje> listaMensajes = new ArrayList<>();
    private MensajeApiService mensajeApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MENSAJE", "LLEGA 1");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mensajeApiService = RetrofitClient.getMensajeApiService();

        SharedPreferences prefs = getSharedPreferences("datos_usuario", MODE_PRIVATE);
        String nombreUsuario = prefs.getString("usuarioNombre", "Invitado");
        Log.d("MENSAJE", "Nombre de usuario recibido: " + nombreUsuario);


        recyclerMensajes = findViewById(R.id.recyclerMensajes);
        editContenido = findViewById(R.id.editContenido);
        btnEnviar = findViewById(R.id.btnEnviar);


        adapter = new MensajeAdapter(listaMensajes);
        recyclerMensajes.setLayoutManager(new LinearLayoutManager(this));
        recyclerMensajes.setAdapter(adapter);

        cargarMensajes();

        Log.d("MENSAJE", "LLEGA 1");

        btnEnviar.setOnClickListener(v -> {
            Log.d("MENSAJE", "LLEGA");
            String contenido = editContenido.getText().toString().trim();
            if (!contenido.isEmpty()) {
                Log.d("MENSAJE", "LLEGA 2");
                Mensaje nuevoMensaje = new Mensaje();
                nuevoMensaje.setAutor(nombreUsuario);
                nuevoMensaje.setContenido(contenido);

                mensajeApiService.enviarMensaje(nuevoMensaje).enqueue(new Callback<Mensaje>() {
                    @Override
                    public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                        if (response.isSuccessful()) {
                            editContenido.setText("");
                            cargarMensajes();
                            Log.d("MENSAJE", "LLEGA 3 - Enviado correctamente");
                        } else {
                            Log.e("MENSAJE", "Error en respuesta: " + response.code());
                            Log.e("MENSAJE", "Mensaje: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Mensaje> call, Throwable t) {
                        Toast.makeText(MensajeActivity.this, "Error al enviar", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

//    ### Recupera todos los mensajes del backend y actualiza el RecyclerView con la nueva lista ###
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
                Toast.makeText(MensajeActivity.this, "Error al cargar mensajes", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

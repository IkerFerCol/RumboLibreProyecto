package com.ikerfernandez.rumbolibre;

import static android.content.Intent.getIntent;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VueloDetailActivity extends AppCompatActivity {


    String nombreUsuario;
    Vuelo vuelo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vuelo_details);

        Button btnReservar = findViewById(R.id.btn_reservar);
        vuelo = (Vuelo) getIntent().getSerializableExtra("vuelo");


        SharedPreferences prefs = getSharedPreferences("datos_usuario", MODE_PRIVATE);
        nombreUsuario = prefs.getString("usuarioNombre", "Invitado");

        Vuelo vuelo = (Vuelo) getIntent().getSerializableExtra("vuelo");

        TextView tvOrigen = findViewById(R.id.tv_ciudad_origen);
        TextView tvDestino = findViewById(R.id.tv_ciudad_destino);
        TextView tvAerolinea = findViewById(R.id.tv_aerolinea);
        TextView tvFechaInicioIda = findViewById(R.id.tv_fecha_inicio_ida);
        TextView tvFechaFinIda = findViewById(R.id.tv_fecha_fin_ida);
        TextView tvHoraInicioIda = findViewById(R.id.tv_hora_inicio_ida);
        TextView tvHoraFinIda = findViewById(R.id.tv_hora_fin_ida);
        TextView tvTiempoIda = findViewById(R.id.tv_tiempo_ida);
        TextView tvFechaInicioVuelta = findViewById(R.id.tv_fecha_inicio_vuelta);
        TextView tvFechaFinVuelta = findViewById(R.id.tv_fecha_fin_vuelta);
        TextView tvHoraInicioVuelta = findViewById(R.id.tv_hora_inicio_vuelta);
        TextView tvHoraFinVuelta = findViewById(R.id.tv_hora_fin_vuelta);
        TextView tvTiempoVuelta = findViewById(R.id.tv_tiempo_vuelta);
        TextView tvPrecio = findViewById(R.id.tv_precio);

        tvOrigen.setText(vuelo.getCiudadOrigen());
        tvDestino.setText(vuelo.getCiudadDestino());
        tvAerolinea.setText(vuelo.getAerolinea());
        tvFechaInicioIda.setText(vuelo.getFechaInicioIda());
        tvFechaFinIda.setText(vuelo.getFechaFinIda());
        tvHoraInicioIda.setText(vuelo.getHoraInicioIda());
        tvHoraFinIda.setText(vuelo.getHoraFinIda());
        tvTiempoIda.setText(vuelo.getTiempoIda());
        tvFechaInicioVuelta.setText(vuelo.getFechaInicioVuelta());
        tvFechaFinVuelta.setText(vuelo.getFechaFinVuelta());
        tvHoraInicioVuelta.setText(vuelo.getHoraInicioVuelta());
        tvHoraFinVuelta.setText(vuelo.getHoraFinVuelta());
        tvTiempoVuelta.setText(vuelo.getTiempoVuelta());
        tvPrecio.setText("Precio: " + String.format("%.2f€", vuelo.getPrecio()));


        btnReservar.setOnClickListener(v -> {
            Log.d("USER", nombreUsuario);
            if (nombreUsuario.equals("Invitado")) {
                Toast.makeText(this, "Debes iniciar sesión para reservar un vuelo", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(this)
                    .setTitle("Confirmar reserva")
                    .setMessage("¿Estás seguro de que quieres reservar este vuelo?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        hacerReserva();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });

    }

    private void hacerReserva() {
        Long vueloId = vuelo.getId();

        Reserva reserva = new Reserva(vueloId, nombreUsuario);

        ReservaApiService reservaApi = RetrofitClient.getClient().create(ReservaApiService.class);
        Call<Reserva> call = reservaApi.crearReserva(reserva);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Reserva> call, Response<Reserva> response) {
                if (response.isSuccessful()) {
                    mostrarNotificacion("Reserva confirmada", "Tu vuelo ha sido reservado correctamente.");
                } else {
                    Toast.makeText(getApplicationContext(), "Error al reservar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Reserva> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarNotificacion(String titulo, String contenido) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String canalId = "canal_reservas";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel(canalId, "Reservas", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(canal);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, canalId)
                .setSmallIcon(R.drawable.baseline_airplane_ticket_24)
                .setContentTitle(titulo)
                .setContentText(contenido)
                .setAutoCancel(true);

        notificationManager.notify(1, builder.build());
    }


}

package com.ikerfernandez.rumbolibre;

import static android.content.Intent.getIntent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

public class VueloDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vuelo_details);



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
    }


}

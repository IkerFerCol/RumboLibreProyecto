package com.ikerfernandez.rumbolibre;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.ikerfernandez.rumbolibre.Fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

//        ### Habilita el botón de "retroceso" en la barra superior (ActionBar) ###
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        ### Cambia el título de la barra a "Configuración" ###
            getSupportActionBar().setTitle("Configuración");
        }

//        ### Carga el SettingsFragment dentro del contenedor definido en el layout ###
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_container, new SettingsFragment())
                .commit();
    }

//    ### Maneja los clics sobre elementos del menú, especialmente el botón de retroceso de la barra superior ###
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
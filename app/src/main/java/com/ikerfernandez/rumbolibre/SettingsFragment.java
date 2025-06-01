package com.ikerfernandez.rumbolibre;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends PreferenceFragmentCompat

        implements SharedPreferences.OnSharedPreferenceChangeListener {
    private SharedPreferences sharedPreferences;
    private UserApiService userApiService;


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        userApiService = RetrofitClient.getClient().create(UserApiService.class);
        sharedPreferences = getPreferenceManager().getSharedPreferences();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("modo_oscuro")) {
            boolean darkMode = sharedPreferences.getBoolean("modo_oscuro", false);
            AppCompatDelegate.setDefaultNightMode(darkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            requireActivity().recreate();
        }
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        String key = preference.getKey();

        if ("version".equals(key)) {
            mostrarDialogo("Versión", "Versión de la app: 1.0.0");
        }

        if ("acercade".equals(key)) {
            mostrarDialogo("Acerca de", "RumboLibre es una innovadora aplicación diseñada para facilitar la gestión de vuelos y la organización de tus viajes. Con una interfaz intuitiva y fácil de usar, RumboLibre te permite buscar, comparar y reservar vuelos de manera rápida y eficiente, todo desde la comodidad de tu dispositivo móvil.    \n" +
                    "\n" +
                    "Características Principales\n" +
                    "   \n" +
                    "\n" +
                    "Búsqueda de Vuelos: Encuentra vuelos de diferentes aerolíneas y compara precios en tiempo real. Utiliza filtros para ajustar tus preferencias de búsqueda, como origen, destino, fechas y aerolíneas.    \n" +
                    "\n" +
                    "Interfaz Amigable: Diseñada pensando en el usuario, la aplicación ofrece una experiencia fluida y agradable. Navega sin esfuerzo entre las diferentes secciones y encuentra lo que necesitas en cuestión de segundos.    \n" +
                    "\n" +
                    "Modo Oscuro: Para aquellos que prefieren una experiencia visual más suave, RumboLibre incluye un modo oscuro que reduce la fatiga visual y mejora la legibilidad en condiciones de poca luz.    ");
            return true;
        }

        if ("contacto".equals(key)) {
            mostrarDialogo("Contacto", "Si tienes preguntas, sugerencias o comentarios sobre la aplicación, no dudes en ponerte en contacto con nosotros. Tu opinión es muy valiosa y nos ayuda a mejorar continuamente.    \n" +
                    "\n" +
                    "Correo Electrónico: ikerfc09@gmail.com\n" +
                    "Teléfono: +34 601 750 764");
        }

        if ("cerrar_sesion".equals(key)) {
            startActivity(new Intent(getActivity(), AuthActivity.class));
        }

        return super.onPreferenceTreeClick(preference);
    }

    private void mostrarDialogo(String titulo, String mensaje) {
        new AlertDialog.Builder(requireContext())
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Cerrar", null)
                .show();
    }


}

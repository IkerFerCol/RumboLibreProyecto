package com.ikerfernandez.rumbolibre.Fragments;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.ikerfernandez.rumbolibre.R;
import com.ikerfernandez.rumbolibre.RetrofitClient;
import com.ikerfernandez.rumbolibre.Servicios.UserApiService;

public class SettingsFragment extends PreferenceFragmentCompat

        implements SharedPreferences.OnSharedPreferenceChangeListener {
    private SharedPreferences sharedPreferences;
    private UserApiService userApiService;


//    ### Carga las preferencias desde root_preferences.xml y configura el servicio de API y preferencias compartidas ###
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        userApiService = RetrofitClient.getClient().create(UserApiService.class);
        sharedPreferences = getPreferenceManager().getSharedPreferences();

    }

//    ### Registra un listener para detectar cambios en las preferencias cuando el fragmento esté activo ###
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

//    ### Elimina el listener cuando el fragmento deja de estar activo ###
    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

//    ### Se ejecuta cuando cambia alguna preferencia ###
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("modo_oscuro")) {
            boolean darkMode = sharedPreferences.getBoolean("modo_oscuro", false);
            AppCompatDelegate.setDefaultNightMode(darkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            requireActivity().recreate();
        }
    }

//    ### Detecta clics en las opciones del menú de ajustes ###
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

            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.action_settingsFragment3_to_authFragment);
            return true;
        }


        return super.onPreferenceTreeClick(preference);
    }

// ### Muestra un cuadro de diálogo con un título y mensaje determinado ###
    private void mostrarDialogo(String titulo, String mensaje) {
        new AlertDialog.Builder(requireContext())
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Cerrar", null)
                .show();
    }


}

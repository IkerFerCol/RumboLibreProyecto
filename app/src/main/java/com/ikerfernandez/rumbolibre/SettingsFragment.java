package com.ikerfernandez.rumbolibre;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceFragmentCompat;

import java.util.Locale;

public class SettingsFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
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
        } else if (key.equals("idioma")) {
            String idioma = sharedPreferences.getString("idioma", "es");
            cambiarIdioma(idioma);
        }
    }

    private void cambiarIdioma(String codIdioma){
        Locale locale = new Locale(codIdioma);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        requireActivity().getResources().updateConfiguration(config, requireActivity().getResources().getDisplayMetrics());
        requireActivity().recreate();
    }
}

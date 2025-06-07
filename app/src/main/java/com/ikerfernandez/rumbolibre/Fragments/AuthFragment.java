package com.ikerfernandez.rumbolibre.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.ikerfernandez.rumbolibre.MainActivity;
import com.ikerfernandez.rumbolibre.R;

public class AuthFragment extends Fragment {

    private Button btnRegistro, btnLogin, btnInvitado;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_auth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnRegistro = view.findViewById(R.id.btnRegister);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnInvitado = view.findViewById(R.id.btnGuest);

        btnRegistro.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_authFragment_to_registerFragment);
        });

        btnLogin.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_authFragment_to_loginFragment);
        });

        btnInvitado.setOnClickListener(v -> {
            SharedPreferences prefs = requireActivity().getSharedPreferences("datos_usuario", Context.MODE_PRIVATE);
            prefs.edit().putString("usuarioNombre", "Invitado").apply();

            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_authFragment_to_navigation_home);
        });
    }
}

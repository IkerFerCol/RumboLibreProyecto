package com.ikerfernandez.rumbolibre.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.ikerfernandez.rumbolibre.R;
import com.ikerfernandez.rumbolibre.RetrofitClient;
import com.ikerfernandez.rumbolibre.Servicios.UserApiService;
import com.ikerfernandez.rumbolibre.Modelos.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    private EditText edtNombre, edtEmail, edtContrasena;
    private Button btnRegistrar, btnGoToLogin;
    private UserApiService userApiService;

// ### Infla el layout XML activity_register como la vista del fragmento ###
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_register, container, false);
    }

//    ### Inicializa los campos de texto, botones y el servicio de la API ###
    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtNombre = view.findViewById(R.id.etUsername);
        edtEmail = view.findViewById(R.id.etEmail);
        edtContrasena = view.findViewById(R.id.etPassword);
        btnRegistrar = view.findViewById(R.id.btnSubmitRegister);

        userApiService = RetrofitClient.getClient().create(UserApiService.class);

        btnRegistrar.setOnClickListener(v -> {
            String nombre = edtNombre.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String contrasena = edtContrasena.getText().toString().trim();

            if (nombre.isEmpty() || email.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(getContext(), "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario usuario = new Usuario();
            usuario.setNombreUsuario(nombre);
            usuario.setEmail(email);
            usuario.setContrasena(contrasena);

            userApiService.register(usuario).enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "Usuario registrado", Toast.LENGTH_SHORT).show();

                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(R.id.action_registerFragment_to_loginFragment);

                    } else {
                        Toast.makeText(getContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Toast.makeText(getContext(), "Fallo: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}

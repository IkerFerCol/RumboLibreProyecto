package com.ikerfernandez.rumbolibre.Fragments;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.ikerfernandez.rumbolibre.R;
import com.ikerfernandez.rumbolibre.RetrofitClient;
import com.ikerfernandez.rumbolibre.Servicios.UserApiService;
import com.ikerfernandez.rumbolibre.Modelos.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private EditText edtEmail, edtContrasena;
    private Button btnLogin;
    private UserApiService userApiService;



//    ### Carga la interfaz desde el layout activity_login.xml, que incluye los campos de email, contraseña y el botón de login ###
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_login, container, false);
    }

//    ### Inicializa componentes (EditText y Button) y el servicio UserApiService para la API ###
    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtEmail = view.findViewById(R.id.etLoginEmail);
        edtContrasena = view.findViewById(R.id.etLoginPassword);
        btnLogin = view.findViewById(R.id.btnSubmitLogin);

        userApiService = RetrofitClient.getClient().create(UserApiService.class);

        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String contrasena = edtContrasena.getText().toString().trim();

            if (email.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(getContext(), "Introduce email y contraseña", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setContrasena(contrasena);

            userApiService.login(usuario).enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(getContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                        SharedPreferences prefs = requireActivity().getSharedPreferences("datos_usuario", Context.MODE_PRIVATE);
                        prefs.edit().putString("usuarioNombre", response.body().getNombreUsuario()).apply();

                        Log.d("USUARIO", response.body().getNombreUsuario());

                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(R.id.action_loginFragment_to_navigation_home);
                    } else {
                        Toast.makeText(getContext(), "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}

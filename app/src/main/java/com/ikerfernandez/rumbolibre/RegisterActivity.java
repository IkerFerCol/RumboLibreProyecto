package com.ikerfernandez.rumbolibre;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtNombre, edtEmail, edtContrasena;
    private Button btnRegistrar;
    private UserApiService userApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtNombre = findViewById(R.id.etUsername);
        edtEmail = findViewById(R.id.etEmail);
        edtContrasena = findViewById(R.id.etPassword);
        btnRegistrar = findViewById(R.id.btnSubmitRegister);

        userApiService = RetrofitClient.getClient().create(UserApiService.class);

        btnRegistrar.setOnClickListener(v -> {
            String nombre = edtNombre.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String contrasena = edtContrasena.getText().toString().trim();

            if (nombre.isEmpty() || email.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(RegisterActivity.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Error al registrar", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Fallo: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}


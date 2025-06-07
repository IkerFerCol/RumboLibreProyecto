package com.ikerfernandez.rumbolibre;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class AuthActivity extends AppCompatActivity {
    private Button btnRegistro, btnLogin, btnInvitado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        btnRegistro = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        btnInvitado = findViewById(R.id.btnGuest);

        btnRegistro.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));

        btnLogin.setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class)));

        btnInvitado.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("datos_usuario", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("usuarioNombre", "Invitado");
            editor.apply();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }
}


package com.ikerfernandez.rumbolibre;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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

        btnInvitado.setOnClickListener(v ->
                startActivity(new Intent(this, MainActivity.class)));
    }
}


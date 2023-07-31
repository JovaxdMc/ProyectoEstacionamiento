package com.example.proyectoestacionamiento;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CargaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga);

        // Esperar 5 segundos (5000 milisegundos) y luego iniciar la siguiente actividad
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(CargaActivity.this, Login.class);
                startActivity(intent);
                finish(); // Finalizar la actividad actual para que no se pueda volver atrás
            }
        }, 5000); // 5000 milisegundos = 5 segundos
    }
}

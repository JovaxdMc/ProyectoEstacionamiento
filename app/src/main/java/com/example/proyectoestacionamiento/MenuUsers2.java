package com.example.proyectoestacionamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MenuUsers2 extends AppCompatActivity {

    ImageView btnPanico,btnQr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_users2);
        btnPanico=(ImageView) findViewById(R.id.btnPanico);
        btnQr=(ImageView) findViewById(R.id.btnQr);

        btnQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuUsers2.this, codigosQR.class);
                startActivity(intent);

            }
        });

        btnPanico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numeroTelefono = "7752383016"; // Reemplaza esto con el número que desees llamar
                Intent intentLlamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numeroTelefono));
                startActivity(intentLlamada);

            }
        });

    }
}
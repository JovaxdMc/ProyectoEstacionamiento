package com.example.proyectoestacionamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuUsers2 extends AppCompatActivity {
    private PrefManager prefManager;
    ImageView btnPanico,btnQr;
    TextView usrsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_users2);
        btnPanico=(ImageView) findViewById(R.id.btnPanico);
        btnQr=(ImageView) findViewById(R.id.btnQr);
        usrsData=(TextView) findViewById(R.id.usrDatos);

        prefManager = new PrefManager(this);

        // Obtener los datos del usuario desde las preferencias
        String username = prefManager.getUsername();
        String rol = prefManager.getRol();
        String nombre = prefManager.getNombre();
        String apellidos = prefManager.getApellidos();

        // Mostrar los datos en la interfaz de usuario
        usrsData.setText("Bienvenido "+nombre+" "+apellidos);
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
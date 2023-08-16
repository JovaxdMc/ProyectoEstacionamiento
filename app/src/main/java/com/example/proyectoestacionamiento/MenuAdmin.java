package com.example.proyectoestacionamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MenuAdmin extends AppCompatActivity {


    LinearLayout marco01,marco02,marco03,marco04,marco05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        marco04=(LinearLayout) findViewById(R.id.marco04);
        marco01=(LinearLayout) findViewById(R.id.marco01);
        marco02=(LinearLayout) findViewById(R.id.marco02);
        marco03=(LinearLayout) findViewById(R.id.marco03);
        marco05=(LinearLayout) findViewById(R.id.marco05);


        marco04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuAdmin.this, espacioEstacionamiento.class);
                startActivity(intent);

            }
        });
        marco05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuAdmin.this, Alarm2.class);
                startActivity(intent);

            }
        });


        marco01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuAdmin.this, Ventiladores.class);
                startActivity(intent);

            }
        });
        marco02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuAdmin.this, Temperatura.class);
                startActivity(intent);

            }
        });
    }




}
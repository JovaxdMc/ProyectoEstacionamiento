package com.example.proyectoestacionamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MenuAdmin extends AppCompatActivity {


    LinearLayout marco01,marco02,marco03,marco04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        marco04=(LinearLayout) findViewById(R.id.marco04);


        marco04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuAdmin.this, espacioEstacionamiento.class);
                startActivity(intent);

            }
        });
    }




}
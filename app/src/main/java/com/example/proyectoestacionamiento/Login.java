package com.example.proyectoestacionamiento;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
public class Login extends AppCompatActivity {

    Button B_Ingresar;
    EditText E_Usu, E_Cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        E_Usu = findViewById(R.id.E_Usu);
        E_Cont = findViewById(R.id.E_Cont);
        B_Ingresar = findViewById(R.id.B_Ingresar);

    }

}

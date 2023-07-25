package com.example.proyectoestacionamiento;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.Eusu);
        editTextPassword = findViewById(R.id.Econt);
        Button buttonIngresar = findViewById(R.id.Bingresar);

        buttonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos de usuario y contraseña ingresados por el usuario
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // Validar que los campos no estén vacíos
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    // Realizar la solicitud HTTP para el inicio de sesión
                    LoginTask loginTask = new LoginTask(new LoginTask.OnLoginResultListener() {
                        @Override
                        public void onLoginResult(String result) {
                            // Procesar la respuesta del servidor (inicio de sesión exitoso o fallido)
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                String status = jsonObject.getString("status");
                                String message = jsonObject.getString("message");
                                String rol = jsonObject.getString("rol");

                                if (status.equals("success")) {
                                    Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                                    // Redirigir al usuario según el rol
                                    if (rol.equalsIgnoreCase("Admin")) {
                                        Intent intent = new Intent(Login.this, MenuAdmin.class);
                                        startActivity(intent);
                                    } else if (rol.equalsIgnoreCase("Usuario")) {
                                        Intent intent = new Intent(Login.this, Menu.class);
                                        startActivity(intent);
                                    }
                                    // Finalizar la actividad actual (opcional, dependiendo de la lógica de la app)
                                    finish();
                                } else {
                                    Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    loginTask.execute(username, password);
                }
            }
        });
    }
}
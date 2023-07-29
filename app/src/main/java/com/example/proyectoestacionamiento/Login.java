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
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.Eusu);
        editTextPassword = findViewById(R.id.Econt);
        Button buttonIngresar = findViewById(R.id.Bingresar);

        prefManager = new PrefManager(this);

        buttonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    LoginTask loginTask = new LoginTask(new LoginTask.OnLoginResultListener() {
                        @Override
                        public void onLoginResult(String result) {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                String status = jsonObject.getString("status");
                                String message = jsonObject.getString("message");
                                String rol = jsonObject.getString("rol");
                                String id_u = jsonObject.getString("id_usuario");
                                String nombre = jsonObject.getString("nombre");
                                String apellidos = jsonObject.getString("apellidos");

                                if (status.equals("success")) {
                                    // Guardar los datos del usuario en SharedPreferences
                                    prefManager.saveUserData(id_u, username, rol, nombre, apellidos);

                                    Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                                    // Redirigir al usuario según el rol
                                    if (rol.equalsIgnoreCase("Admin")) {
                                        Intent intent = new Intent(Login.this, MenuAdmin.class);
                                        startActivity(intent);
                                    } else if (rol.equalsIgnoreCase("Usuario")) {
                                        Intent intent = new Intent(Login.this, MenuUsers2.class);
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

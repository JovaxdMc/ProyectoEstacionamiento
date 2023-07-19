package com.example.proyectoestacionamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectoestacionamiento.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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

        B_Ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarUsuario();
            }
        });
    }

    private void buscarUsuario() {
        String username = E_Usu.getText().toString().trim();
        String password = E_Cont.getText().toString().trim();

        if (!username.isEmpty() && !password.isEmpty()) {
            String encodedUsername = URLEncoder.encode(username);
            String encodedPassword = URLEncoder.encode(password);

            String url = "https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/Login/Buscar.php?id_usuario=" + encodedUsername;
            new LoginTask().execute(url, encodedPassword);
        } else {
            Toast.makeText(this, "Por favor ingrese un nombre de usuario y contraseña", Toast.LENGTH_SHORT).show();
        }
    }

    private class LoginTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            String password = params[1];

            try {
                URL loginUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) loginUrl.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();

                return stringBuilder.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null; // Agregamos este return para el caso de que ocurra una excepción.
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);

                    if (jsonObject.has("username") && jsonObject.has("password")) {
                        // Se encontró el usuario en la base de datos
                        // Redirigir a la actividad MainActivity
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Cierra la actividad actual para que el usuario no pueda volver atrás
                    } else {
                        // Usuario no encontrado en la base de datos
                        Toast.makeText(Login.this, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    // Error al analizar los datos
                    Toast.makeText(Login.this, "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Error en la conexión o respuesta nula
                Toast.makeText(Login.this, "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

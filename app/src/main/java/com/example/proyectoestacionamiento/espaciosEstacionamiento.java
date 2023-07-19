package com.example.proyectoestacionamiento;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;


public class espaciosEstacionamiento extends AppCompatActivity {

    private LinearLayout parkingLayout;
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espacios_estacionamiento);

        parkingLayout = findViewById(R.id.parkingLayout);

        // Realizar la solicitud HTTP al URL especificado
        Request request = new Request.Builder()
                .url("https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/Lugares/lugares.php?endpoint=espacios")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Manejar error en caso de fallo de la solicitud HTTP
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();

                    // Procesar los datos de respuesta y actualizar la interfaz de usuario
                    runOnUiThread(() -> {
                        try {
                            JSONArray jsonArray = new JSONArray(responseData);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int idEspacio = jsonObject.getInt("id_espacio");
                                String estado = jsonObject.getString("estado");

                                // Crear un nuevo botón para cada espacio de estacionamiento
                                Button button = new Button(espaciosEstacionamiento.this);
                                button.setText("Espacio " + idEspacio);

                                // Personalizar el aspecto del botón según el estado del espacio
                                if (estado.equals("ocupado")) {
                                    button.setBackgroundColor(Color.RED);
                                } else {
                                    button.setBackgroundColor(Color.GREEN);
                                }

                                // Agregar el botón al diseño del estacionamiento
                                parkingLayout.addView(button);

                                // Agregar un listener de clic al botón si deseas manejar eventos
                                button.setOnClickListener(v -> {
                                    // Acciones al hacer clic en el botón
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    // Manejar error en caso de respuesta no exitosa
                    Log.e("API Error", "Response code: " + response.code());
                }
            }
        });
    }
}

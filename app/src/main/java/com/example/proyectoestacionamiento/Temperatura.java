package com.example.proyectoestacionamiento;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class Temperatura extends AppCompatActivity {
    EditText txtnumer;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperatura);

        txtnumer = findViewById(R.id.txtnumer);
        handler = new Handler();

        // Ejecutar la obtención de temperatura automáticamente al entrar
        obtenerTemperaturaTermometro();

        // Ejecutar la obtención de temperatura automáticamente cada 5 segundos
        handler.postDelayed(obtenerTemperaturaRunnable, 5000);
    }

    private Runnable obtenerTemperaturaRunnable = new Runnable() {
        @Override
        public void run() {
            obtenerTemperaturaTermometro();

            // Ejecutar nuevamente el Runnable después de 5 segundos
            handler.postDelayed(this, 5000);
        }
    };

    private void obtenerTemperaturaTermometro() {
        // URL de la API para obtener los datos del termómetro
        String url = "https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/Actuadores/termometro.php";

        // Crear una nueva solicitud POST con Volley
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Procesar la respuesta JSON y actualizar la vista con la temperatura
                        try {
                            JSONObject datosJson = new JSONObject(response);
                            double temperatura = datosJson.getDouble("temperatura");

                            // Actualizar la vista con la temperatura obtenida
                            txtnumer.setText(String.valueOf(temperatura) + " °C");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error en caso de que la solicitud no se pueda completar
                        Toast.makeText(getApplicationContext(), "Error al obtener los datos del termómetro", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("obtener_datos", ""); // Agregar el parámetro para indicar que deseas obtener los datos

                return params;
            }
        };

        // Agregar la solicitud a la cola de Volley para ejecutarla
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Detener el ciclo de obtención de temperatura al cerrar la actividad
        handler.removeCallbacks(obtenerTemperaturaRunnable);
    }
}

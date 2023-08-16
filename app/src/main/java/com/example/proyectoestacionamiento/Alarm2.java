package com.example.proyectoestacionamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Alarm2 extends AppCompatActivity {
    LinearLayout btnOn, btnOf;
    TextView textOn, textOf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm2);
        btnOn = findViewById(R.id.btnOn);
        btnOf = findViewById(R.id.btnOf);
        textOn = findViewById(R.id.textOn);
        textOf = findViewById(R.id.textOf);


        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Modificar2("https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/Actuadores/Alarma.php");
            }
        });

        btnOf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Modificar3("https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/Actuadores/Alarma.php");
            }
        });

    }
    public void Modificar2(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Registro actualizado", Toast.LENGTH_LONG).show();
                        // Después de actualizar, obtener y mostrar los nuevos datos
                        obtenerEstadoAlarma();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("estado", "On"); // Cambiar "On" por el valor deseado

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void Modificar3(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Registro actualizado", Toast.LENGTH_LONG).show();
                        // Después de actualizar, obtener y mostrar los nuevos datos
                        obtenerEstadoAlarma();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("estado", "Off"); // Cambiar "Off" por el valor deseado

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void obtenerEstadoAlarma() {
        // URL de la API para obtener los datos del ventilador
        String url = "https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/Actuadores/Alarma.php";

        // Crear una nueva solicitud POST con Volley
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Procesar la respuesta JSON y actualizar las vistas con los datos del ventilador
                        try {
                            JSONObject datosJson = new JSONObject(response);
                            String estado = datosJson.getString("estado");

                            // Actualizar las vistas con los datos obtenidos


                            if (estado.equals("On")) {
                                textOn.setTextColor(getResources().getColor(R.color.colorOn)); // Cambia R.color.colorOn por el color deseado
                                textOf.setTextColor(getResources().getColor(R.color.colorOff)); // Cambia R.color.colorDefault por el color deseado
                            } else {
                                textOn.setTextColor(getResources().getColor(R.color.colorOff)); // Cambia R.color.colorDefault por el color deseado
                                textOf.setTextColor(getResources().getColor(R.color.colorOn)); // Cambia R.color.colorOff por el color deseado
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error en caso de que la solicitud no se pueda completar
                        Toast.makeText(getApplicationContext(), "Error al obtener los datos del ventilador", Toast.LENGTH_LONG).show();
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
}
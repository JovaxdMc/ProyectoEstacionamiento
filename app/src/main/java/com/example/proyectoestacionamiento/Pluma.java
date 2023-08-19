package com.example.proyectoestacionamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
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

public class Pluma extends AppCompatActivity {
    LinearLayout btnSi, btnNo;
    TextView textSi, textNo;

    private Handler handler = new Handler();
    private Runnable updateRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pluma);
        btnNo = findViewById(R.id.btnNo);
        btnSi = findViewById(R.id.btnSi);
        textNo = findViewById(R.id.textNo);
        textSi = findViewById(R.id.textSi);

        btnSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Modificar2("https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/Actuadores/pluma.php");
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Modificar3("https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/Actuadores/pluma.php");
            }
        });

        updateRunnable = new Runnable() {
            @Override
            public void run() {
                obtenerEstadoPluma();
                handler.postDelayed(this, 3000); // Schedule the runnable again after 3000 milliseconds (3 seconds)
            }
        };

        handler.post(updateRunnable); // Start the initial runnable
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateRunnable); // Stop the periodic updates
    }

    public void Modificar2(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Registro actualizado", Toast.LENGTH_LONG).show();
                        // Después de actualizar, obtener y mostrar los nuevos datos
                        obtenerEstadoPluma();
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
                params.put("bloqueado", "Si"); // Cambiar "On" por el valor deseado
                // params.put("estado", "Cerrado");

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
                        obtenerEstadoPluma();
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
                params.put("bloqueado", "No"); // Cambiar "Off" por el valor deseado
                // params.put("estado", "Abierto");

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void obtenerEstadoPluma() {
        String url = "https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/Actuadores/pluma.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject datosJson = new JSONObject(response);
                            String estadoPluma = datosJson.getString("bloqueado");
                            if (estadoPluma.equals("Si")) {
                                textSi.setTextColor(getResources().getColor(R.color.colorOn));
                                textNo.setTextColor(getResources().getColor(R.color.colorOff));
                            } else {
                                textSi.setTextColor(getResources().getColor(R.color.colorOff));
                                textNo.setTextColor(getResources().getColor(R.color.colorOn));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error al obtener los datos de la pluma", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("obtener_datos", "");
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}

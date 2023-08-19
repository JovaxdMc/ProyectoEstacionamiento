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

public class Alarm2 extends AppCompatActivity {
    LinearLayout btnOn, btnOf;
    TextView textOn, textOf;

    private Handler handler = new Handler();
    private Runnable updateRunnable;

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

        updateRunnable = new Runnable() {
            @Override
            public void run() {
                obtenerEstadoAlarma();
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
                params.put("estado", "On"); // Change "On" to the desired value
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
                params.put("estado", "Off"); // Change "Off" to the desired value
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void obtenerEstadoAlarma() {
        String url = "https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/Actuadores/Alarma.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject datosJson = new JSONObject(response);
                            String estado = datosJson.getString("estado");
                            if (estado.equals("On")) {
                                textOn.setTextColor(getResources().getColor(R.color.colorOn));
                                textOf.setTextColor(getResources().getColor(R.color.colorOff));
                            } else {
                                textOn.setTextColor(getResources().getColor(R.color.colorOff));
                                textOf.setTextColor(getResources().getColor(R.color.colorOn));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error al obtener los datos del ventilador", Toast.LENGTH_LONG).show();
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

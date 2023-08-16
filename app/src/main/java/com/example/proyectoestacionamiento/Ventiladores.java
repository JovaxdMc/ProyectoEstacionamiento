package com.example.proyectoestacionamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Ventiladores extends AppCompatActivity {

    LinearLayout btnOn, btnOf;
    EditText Vel;
    TextView textOn, textOf;
    Button Guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventiladores);

        // Inicializar vistas
        btnOn = findViewById(R.id.btnOn);
        btnOf = findViewById(R.id.btnOf);
        Vel = findViewById(R.id.Vel);
        Guardar = findViewById(R.id.Guardar);
        textOn = findViewById(R.id.textOn);
        textOf = findViewById(R.id.textOf);

        obtenerEstadoVentilador();

        // Aplicar filtro de entrada para el campo de velocidad
        Vel.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
            String newValue = dest.toString().substring(0, dstart) + source.toString() + dest.toString().substring(dend);
            if (newValue.isEmpty() || newValue.equals("-")) {
                return null;
            }
            try {
                if (newValue.equals("0")) {
                    return null;  // Permitir solo cero individual
                }
                if (newValue.startsWith("00")) {
                    return "";  // Evitar múltiples ceros iniciales
                }
                int input = Integer.parseInt(newValue);
                return (input >= 0 && input <= 100) ? null : "";
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return "";
            }
        }});

        // Configurar botones y su comportamiento
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Modificar("https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/Actuadores/ventiladores.php");
            }
        });

        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Modificar2("https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/Actuadores/ventiladores.php");
            }
        });

        btnOf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Modificar3("https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/Actuadores/ventiladores.php");
            }
        });


    }

    public void Modificar(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Registro actualizado", Toast.LENGTH_LONG).show();
                        // Después de actualizar, obtener y mostrar los nuevos datos
                        obtenerEstadoVentilador();
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
                params.put("velocidad", Vel.getText().toString());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void Modificar2(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Registro actualizado", Toast.LENGTH_LONG).show();
                        // Después de actualizar, obtener y mostrar los nuevos datos
                        obtenerEstadoVentilador();
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
                        obtenerEstadoVentilador();
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

    private void obtenerEstadoVentilador() {
        // URL de la API para obtener los datos del ventilador
        String url = "https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/Actuadores/ventiladores.php";

        // Crear una nueva solicitud POST con Volley
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Procesar la respuesta JSON y actualizar las vistas con los datos del ventilador
                        try {
                            JSONObject datosJson = new JSONObject(response);
                            int velocidad = datosJson.getInt("velocidad");
                            String estado = datosJson.getString("estado");

                            // Actualizar las vistas con los datos obtenidos
                            Vel.setText(String.valueOf(velocidad));

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

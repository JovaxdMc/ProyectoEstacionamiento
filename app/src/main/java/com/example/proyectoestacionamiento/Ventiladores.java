package com.example.proyectoestacionamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class Ventiladores extends AppCompatActivity {

    LinearLayout btnOn,btnOf,Diez,Treinta,cincuenta,ochenta,cien;
    EditText Vel;
    TextView textOn,textOf;
    Button Guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventiladores);

        btnOn = findViewById(R.id.btnOn);
        btnOf = findViewById(R.id.btnOf);
        Vel = findViewById(R.id.Vel);
        Guardar = findViewById(R.id.Guardar);
        textOn = findViewById(R.id.textOn);
        textOf = findViewById(R.id.textOf);

        Vel.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
            String newValue = dest.toString().substring(0, dstart) + source.toString() + dest.toString().substring(dend);
            if (newValue.isEmpty() || newValue.equals("-")) {
                return null;
            }
            try {
                if (newValue.equals("0")) {
                    return null;  // Allow single zero
                }
                if (newValue.startsWith("00")) {
                    return "";  // Prevent multiple leading zeros
                }
                int input = Integer.parseInt(newValue);
                return (input >= 0 && input <= 100) ? null : "";
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return "";
            }
        }});

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
                params.put("estado", textOn.getText().toString());


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
                params.put("estado", textOf.getText().toString());


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }



}

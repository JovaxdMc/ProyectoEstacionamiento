package com.example.proyectoestacionamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MenuUsers2 extends AppCompatActivity {
    private PrefManager prefManager;
    private ImageView btnPanico, btnQr;
    private TextView usrsData, lugares;
    private final Handler handler = new Handler();
    private final Runnable updateTask = new Runnable() {
        @Override
        public void run() {
            obtenerNumeroEspaciosDisponibles();
            handler.postDelayed(this, 3000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_users2);
        btnPanico = (ImageView) findViewById(R.id.btnPanico);
        btnQr = (ImageView) findViewById(R.id.btnQr);
        usrsData = (TextView) findViewById(R.id.usrDatos);
        lugares = (TextView) findViewById(R.id.cantidadLugares);

        obtenerNumeroEspaciosDisponibles();
        handler.post(updateTask);

        prefManager = new PrefManager(this);
        String username = prefManager.getUsername();
        String rol = prefManager.getRol();
        String nombre = prefManager.getNombre();
        String apellidos = prefManager.getApellidos();

        usrsData.setText("Bienvenido " + nombre + " " + apellidos);
        btnQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuUsers2.this, codigosQR.class);
                startActivity(intent);
            }
        });

        btnPanico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numeroTelefono = "7752383016";
                Intent intentLlamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numeroTelefono));
                startActivity(intentLlamada);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateTask);
    }

    private void obtenerNumeroEspaciosDisponibles() {
        String url = "https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/Lugares/lugares.php?accion=contar_espacios";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String espaciosDisponibles = response.getString("disponibles");
                            lugares.setText(espaciosDisponibles);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        lugares.setText("Error al obtener el número de espacios disponibles");
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}

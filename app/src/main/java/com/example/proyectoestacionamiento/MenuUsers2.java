package com.example.proyectoestacionamiento;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import android.Manifest;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.telephony.SmsManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MenuUsers2 extends AppCompatActivity {
    private PrefManager prefManager;
    ImageView btnPanico,btnQr,btnllamadaPanico;
    TextView usrsData,lugares;
    TextView btnSalir;
    private Handler handler;
    private Runnable runnable;
    private static final int CODIGO_PETICION_PERMISO_LLAMADA = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_users2);
        btnPanico=(ImageView) findViewById(R.id.btnPanico);
        btnQr=(ImageView) findViewById(R.id.btnQr);
        btnllamadaPanico=(ImageView) findViewById(R.id.btnllamadaPanico);
        usrsData=(TextView) findViewById(R.id.usrDatos);
        btnSalir=(TextView) findViewById(R.id.btnSalir);
        lugares=(TextView) findViewById(R.id.cantidadLugares);
        obtenerNumeroEspaciosDisponibles();

        prefManager = new PrefManager(this);

        // Obtener los datos del usuario desde las preferencias
        String username = prefManager.getUsername();
        String rol = prefManager.getRol();
        String nombre = prefManager.getNombre();
        String apellidos = prefManager.getApellidos();

        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                // Coloca aquí el código que deseas ejecutar cada 3 segundos
                // Por ejemplo, puedes llamar a tu función aquí
                obtenerNumeroEspaciosDisponibles();
                handler.postDelayed(this, 3000); // Ejecuta nuevamente el Runnable después de 3 segundos
            }
        };

        // Inicia la ejecución inicial del Runnable
        handler.postDelayed(runnable, 3000);
        // Mostrar los datos en la interfaz de usuario
        usrsData.setText("Bienvenido "+nombre+" "+apellidos);
        btnQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuUsers2.this, codigosQR.class);
                startActivity(intent);

            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuUsers2.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the activity stack
                startActivity(intent);
            }
        });


        btnllamadaPanico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numeroTelefono = "7752383016"; // Reemplaza esto con el número de teléfono deseado
                Intent intentLlamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numeroTelefono));

                // Verifica el permiso CALL_PHONE antes de realizar la llamada
                if (ContextCompat.checkSelfPermission(MenuUsers2.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(intentLlamada);
                } else {
                    // Solicita el permiso CALL_PHONE
                    ActivityCompat.requestPermissions(MenuUsers2.this, new String[]{Manifest.permission.CALL_PHONE}, CODIGO_PETICION_PERMISO_LLAMADA);
                }
            }
        });



        btnPanico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = "7752383016"; // Reemplaza con el número de teléfono

                // Obtiene la ubicación actual y envía el SMS
                sendLocationSMS(phoneNumber);

                // Trigger the alarm modification
                Modificar2("https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/Actuadores/Alarma.php");
            }
        });


    }
    private void sendLocationSMS(String phoneNumber) {
        String coordinates = getCurrentCoordinates();

        // Create a Google Maps link using the coordinates
        String mapLink = "http://maps.google.com/maps?q=" + coordinates;

        // Create the SMS message with the Google Maps link
        String message = "¡Necesito ayuda! Actualmente estoy en: " + mapLink;

        // Get the default SMS manager
        SmsManager smsManager = SmsManager.getDefault();

        // Send the SMS to the phone number
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
    }

    private String getCurrentCoordinates() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = null;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } else {
            // Solicita permiso de ubicación si no está concedido
            // Debes agregar código para manejar la solicitud de permiso y la devolución de llamada
        }

        // Si se encontró la ubicación, crea las coordenadas
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            return latitude + "," + longitude;
        }

        // Si no se pudo obtener la ubicación, devuelve un valor por defecto
        return "Ubicación no disponible";
    }




    private void obtenerNumeroEspaciosDisponibles() {
        // URL de la API
        String url = "https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/Lugares/lugares.php?accion=contar_espacios";

        // Crear una nueva solicitud GET con Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Procesar la respuesta JSON y actualizar el TextView con el número de espacios disponibles
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
                        // Manejar el error en caso de que la solicitud no se pueda completar
                        lugares.setText("Error al obtener el número de espacios disponibles");
                    }
                });

        // Agregar la solicitud a la cola de Volley para ejecutarla
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
    public void Modificar2(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Alarma encendida", Toast.LENGTH_LONG).show();
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







}
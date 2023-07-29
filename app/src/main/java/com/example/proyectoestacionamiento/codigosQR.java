package com.example.proyectoestacionamiento;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONException;
import org.json.JSONObject;

public class codigosQR extends AppCompatActivity {
    private PrefManager prefManager;
    private EditText idEditText;
    private Button generateButton;
    private ImageView qrImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigos_qr);

        idEditText = findViewById(R.id.idEditText);
        generateButton = findViewById(R.id.generateButton);
        qrImageView = findViewById(R.id.qrImageView);

        prefManager = new PrefManager(this);
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateQRCode();
            }
        });
    }

    private void generateQRCode() {
        String id = idEditText.getText().toString();


        // Obtener los datos del usuario desde las preferencias
        String id_u = prefManager.getUserId();
        System.out.println("idusr: "+id_u);
        retrieveDataFromAPI(id_u);
    }

    private void retrieveDataFromAPI(String id) {
        // Aquí debes modificar la URL para apuntar a tu API y la ruta adecuada para obtener los datos del usuario
        String apiUrl = "https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/usuarios/usuario.php?operacion=buscar&id_usuario="+id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Procesa la respuesta JSON y genera el código QR
                        try {
                            String data = processApiResponse(response);
                            System.out.println(data);
                            generateQRCodeBitmap(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Maneja el error de la solicitud HTTP
                        error.printStackTrace();
                    }
                });

        // Agrega la solicitud a la cola de Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private String processApiResponse(JSONObject jsonObject) throws JSONException {
        // Verifica si la propiedad "usuario" está presente en el objeto JSON
        if (jsonObject.has("usuario")) {
            // Obtiene el objeto "usuario" del JSON
            JSONObject usuarioObject = jsonObject.getJSONObject("usuario");

            // Obtén los campos específicos del usuario del objeto "usuario"
            String id = usuarioObject.getString("id_usuario");
            String nombre = usuarioObject.getString("nombre");
            String apellidos = usuarioObject.getString("apellidos");
            String username = usuarioObject.getString("username");
            String password = usuarioObject.getString("password");
            String rol = usuarioObject.getString("rol");
            // ... Obtén los demás campos que necesites

            return "ID: " + id + "\nNombre: " + nombre + "\nApellidos: " + apellidos + "\nUsername: " + username + "\nRol: " + rol;
        } else {
            return "No se encontró el objeto de usuario en la respuesta JSON";
        }
    }

    private void generateQRCodeBitmap(String data) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);

            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap qrBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    qrBitmap.setPixel(x, y, bitMatrix.get(x, y) ? getResources().getColor(android.R.color.black) : getResources().getColor(android.R.color.white));
                }
            }

            qrImageView.setImageBitmap(qrBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}

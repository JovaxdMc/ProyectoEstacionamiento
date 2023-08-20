package com.example.proyectoestacionamiento;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Users extends AppCompatActivity {

    private TableLayout idTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        idTable = findViewById(R.id.idTable); // Replace with your TableLayout's ID

        // Declarar la URL del archivo PHP
        String url = "https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/usuarios/cargar.php";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject user = response.getJSONObject(i);
                                String idUsuario = user.getString("id_usuario");
                                String nombre = user.getString("nombre");
                                String apellidos = user.getString("apellidos");
                                String password = user.getString("password");
                                String username = user.getString("username");
                                String rol = user.getString("rol");

                                // Crear una nueva fila
                                TableRow tableRow = new TableRow(Users.this);

                                // Crear las celdas y agregarlas a la fila
                                TextView textViewIdUsuario = new TextView(Users.this);
                                textViewIdUsuario.setText(idUsuario);
                                tableRow.addView(textViewIdUsuario);

                                TextView textViewNombre = new TextView(Users.this);
                                textViewNombre.setText(nombre);
                                tableRow.addView(textViewNombre);

                                TextView textViewApellidos = new TextView(Users.this);
                                textViewApellidos.setText(apellidos);
                                tableRow.addView(textViewApellidos);

                                TextView textViewPassword = new TextView(Users.this);
                                textViewPassword.setText(password);
                                tableRow.addView(textViewPassword);


                                TextView textViewUsername = new TextView(Users.this);
                                textViewUsername.setText(username);
                                tableRow.addView(textViewUsername);

                                TextView textViewRol = new TextView(Users.this);
                                textViewRol.setText(rol);
                                tableRow.addView(textViewRol);

                                // Agregar la fila a la tabla
                                idTable.addView(tableRow);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(Users.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Users.this, "Error de conexión: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(request);
    }
}

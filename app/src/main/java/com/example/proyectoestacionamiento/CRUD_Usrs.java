package com.example.proyectoestacionamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CRUD_Usrs extends AppCompatActivity {

    EditText id_usuario, ed_Nombre, ed_Apellido, ed_Password, ed_user;
    Spinner spn_rol;
    Button boton_Agregar, boton_Eliminar, boton_Modificar, boton_Buscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_usrs);

        /*EditText*/
        id_usuario = findViewById(R.id.id_usuario);
        ed_Nombre = findViewById(R.id.ed_Nombre);
        ed_Apellido = findViewById(R.id.ed_Apellido);
        ed_Password = findViewById(R.id.ed_Password);
        ed_user = findViewById(R.id.ed_user);

        /*Spinner*/
        spn_rol = findViewById(R.id.spn_rol);

        /*Boton*/
        boton_Agregar = findViewById(R.id.boton_Agregar);
        boton_Eliminar = findViewById(R.id.boton_Eliminar);
        boton_Modificar = findViewById(R.id.boton_Modificar);
        boton_Buscar = findViewById(R.id.boton_Buscar);

        boton_Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Insertar("https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/usuarios/insertar.php");
            }
        });

        boton_Eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Eliminar("https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/usuarios/eliminar.php?id_usuario=" + id_usuario.getText().toString());
            }
        });

        boton_Modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Modificar("https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/usuarios/actualizar.php");
            }
        });

        boton_Buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Buscar("https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/usuarios/Buscar.php?Id=" + id_usuario.getText().toString());
            }
        });
    }

    /*Insertar*/
    public void Insertar(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Registro insertado", Toast.LENGTH_LONG).show();
                        id_usuario.setText("");
                        ed_Nombre.setText("");
                        ed_Apellido.setText("");
                        ed_Password.setText("");
                        // Obtén el valor seleccionado del Spinner
                        String selectedRol = spn_rol.getSelectedItem().toString();
                        // Puedes mostrar un mensaje para verificar que se haya obtenido correctamente
                        Toast.makeText(CRUD_Usrs.this, "Rol seleccionado: " + selectedRol, Toast.LENGTH_SHORT).show();
                        ed_user.setText("");
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
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id_usuario", id_usuario.getText().toString());
                parametros.put("nombre", ed_Nombre.getText().toString());
                parametros.put("apellidos", ed_Apellido.getText().toString());
                parametros.put("password", ed_Password.getText().toString());
                // Obtén el valor seleccionado del Spinner
                String selectedRol = spn_rol.getSelectedItem().toString();
                parametros.put("rol", selectedRol);
                parametros.put("username", ed_user.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    /*Modificar*/

    public void Modificar(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Registro actualizado", Toast.LENGTH_LONG).show();
                        id_usuario.setText("");
                        ed_Nombre.setText("");
                        ed_Apellido.setText("");
                        ed_Password.setText("");
                        // No se usa ed_rol porque fue reemplazado por el Spinner spn_rol
                        ed_user.setText("");
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
                params.put("id_usuario", id_usuario.getText().toString());
                params.put("nombre", ed_Nombre.getText().toString());
                params.put("apellidos", ed_Apellido.getText().toString());
                params.put("password", ed_Password.getText().toString());
                // Obtén el valor seleccionado del Spinner
                String selectedRol = spn_rol.getSelectedItem().toString();
                params.put("rol", selectedRol);
                params.put("username", ed_user.getText().toString());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    /*Eliminar*/

    public void Eliminar(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Registro eliminado", Toast.LENGTH_LONG).show();
                        id_usuario.setText("");
                        ed_Nombre.setText("");
                        ed_Apellido.setText("");
                        ed_Password.setText("");
                        // No se usa ed_rol porque fue reemplazado por el Spinner spn_rol
                        ed_user.setText("");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error al eliminar el registro", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_usuario", id_usuario.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    /*Buscar*/

    public void Buscar(String url) {
        JsonArrayRequest stringArray = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        id_usuario.setText(jsonObject.getString("id_usuario"));
                        ed_user.setText(jsonObject.getString("username"));
                        ed_Nombre.setText(jsonObject.getString("nombre"));
                        ed_Apellido.setText(jsonObject.getString("apellidos"));
                        ed_Password.setText(jsonObject.getString("password"));
                        // No se usa ed_rol porque fue reemplazado por el Spinner spn_rol
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error al realizar la consulta", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringArray);
    }

}
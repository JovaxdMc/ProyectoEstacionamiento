package com.example.proyectoestacionamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {

    EditText id_usuario , ed_Nombre,ed_Apellido,ed_Password,ed_rol;
    Button boton_Agregar,boton_Eliminar,boton_Modificar,boton_Buscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*EditText*/
        id_usuario=(EditText) findViewById(R.id.id_usuario);
        ed_Nombre=(EditText) findViewById(R.id.ed_Nombre);
        ed_Apellido=(EditText) findViewById(R.id.ed_Apellido);
        ed_Password=(EditText) findViewById(R.id.ed_Password);
        ed_rol=(EditText) findViewById(R.id.ed_rol);

        /*Boton*/
        boton_Agregar=(Button)findViewById(R.id.boton_Agregar);
        boton_Eliminar=(Button)findViewById(R.id.boton_Eliminar);
        boton_Modificar=(Button)findViewById(R.id.boton_Modificar);
        boton_Buscar=(Button)findViewById(R.id.boton_Buscar);


        boton_Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Insertar("https://davidgon.000webhostapp.com/Estacionamiento/Usuario/insertar.php");

            }
        });

        boton_Eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Eliminar("https://davidgon.000webhostapp.com/Estacionamiento/Usuario/eliminar.php?Id="+id_usuario.getText().toString());
            }
        });

        boton_Modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Modificar("https://davidgon.000webhostapp.com/Estacionamiento/Usuario/actualizar.php");

            }
        });

        boton_Buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Buscar("https://davidgon.000webhostapp.com/Estacionamiento/Usuario/Buscar.php?Id="+id_usuario.getText().toString());
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
                        ed_rol.setText("");

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
                parametros.put("Id", id_usuario.getText().toString());
                parametros.put("Nombre", ed_Nombre.getText().toString());
                parametros.put("Apellido", ed_Apellido.getText().toString());
                parametros.put("Password", ed_Password.getText().toString());
                parametros.put("Rol", ed_rol.getText().toString());
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
                        ed_rol.setText("");
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
                params.put ("Id", id_usuario.getText().toString());
                params.put("Nombre", ed_Nombre.getText().toString());
                params.put("Apellido", ed_Apellido.getText().toString());
                params.put ("Password", ed_Password.getText().toString());
                params.put("Rol", ed_rol.getText().toString());

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
                        ed_rol.setText("");
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
                params.put("Id", id_usuario.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    /*Buscar*/
    public void Buscar (String url){
        JsonArrayRequest stringArray=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        id_usuario.setText(jsonObject.getString("Id"));
                        ed_Nombre.setText(jsonObject.getString("Nombre"));
                        ed_Apellido.setText(jsonObject.getString("Apellido"));
                        ed_Password.setText(jsonObject.getString("Password"));
                        ed_rol.setText(jsonObject.getString("Rol"));

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
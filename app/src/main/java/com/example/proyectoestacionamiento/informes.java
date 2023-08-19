package com.example.proyectoestacionamiento;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class informes extends AppCompatActivity {

    Spinner spinnerDay;
    Spinner spinnerMonth;
    Spinner spinnerYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informes);

        spinnerDay = findViewById(R.id.spinnerDay);
        spinnerMonth = findViewById(R.id.spinnerMonth);
        spinnerYear = findViewById(R.id.spinnerYear);

        ArrayAdapter<CharSequence> adapterDay = ArrayAdapter.createFromResource(this, R.array.days_of_week, android.R.layout.simple_spinner_item);
        adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapterDay);

        ArrayAdapter<CharSequence> adapterMonth = ArrayAdapter.createFromResource(this, R.array.months, android.R.layout.simple_spinner_item);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapterMonth);

        List<String> years = new ArrayList<>();
        for (int i = 2019; i <= Calendar.getInstance().get(Calendar.YEAR); i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapterYear = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapterYear);

        Button btnFilter = findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedDay = spinnerDay.getSelectedItem().toString();
                String selectedMonth = spinnerMonth.getSelectedItem().toString();
                String selectedYear = spinnerYear.getSelectedItem().toString();

                // Convierte los días y meses a valores numéricos si es necesario
                int dayIndex = spinnerDay.getSelectedItemPosition() + 1; // Por ejemplo, 1 para Lunes
                int monthIndex = spinnerMonth.getSelectedItemPosition() + 1; // Por ejemplo, 1 para Enero

                // Construye la URL de tu API con los parámetros de filtro
                String url = "https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/api.php?accion=por_mes&mes=" + monthIndex + "&anio=" + selectedYear;

                // Realiza la llamada a la API usando Volley
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesa la respuesta y actualiza el gráfico
                                // Aquí deberías parsear los datos de la respuesta y usarlos para actualizar tu gráfico
                                try {
                                    JSONArray jsonArray = response.getJSONArray("informes");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject informe = jsonArray.getJSONObject(i);
                                        // Procesa cada informe y actualiza el gráfico
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }

                        });

                // Agrega la solicitud a la cola de Volley para ejecutarla
                RequestQueue queue = Volley.newRequestQueue(informes.this);
                queue.add(request);
            }
        });

    }
}

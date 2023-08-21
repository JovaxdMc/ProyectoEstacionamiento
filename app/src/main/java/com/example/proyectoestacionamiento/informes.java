package com.example.proyectoestacionamiento;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class informes extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Spinner spinnerMonth, spinnerYear;
    private Button btnFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informes);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        spinnerMonth = findViewById(R.id.spinnerMonth);
        spinnerYear = findViewById(R.id.spinnerYear);
        btnFilter = findViewById(R.id.btnFilter);

        btnFilter.setOnClickListener(view -> {
            String selectedMonth = (String) spinnerMonth.getSelectedItem();
           String selectedYear = (String) spinnerYear.getSelectedItem();

            // Construir la URL base
            String url = "https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/Lugares/informes.php?accion=obtener_eventos";

            if (selectedMonth != "") {
                url += "&month=" + selectedMonth;
            }
            if (selectedYear != "") {
                url += "&year=" + selectedYear;
            }
            System.out.println(url);
            // Realizar la solicitud HTTP
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> {
                        Toast.makeText(informes.this, "Error al cargar los datos.", Toast.LENGTH_SHORT).show();
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String jsonData = response.body().string();
                        System.out.println(jsonData);
                        try {
                            JSONArray jsonArray = new JSONArray(jsonData);
                            HashMap<String, List<Integer>> dataMap = new HashMap<>();

                            // Agregar duraciones a las fechas correspondientes en el HashMap
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String fecha = jsonObject.getString("hora_entrada").split(" ")[0]; // Extraer solo la fecha
                                int duracion = jsonObject.getInt("duracion");

                                if (dataMap.containsKey(fecha)) {
                                    dataMap.get(fecha).add(duracion);
                                } else {
                                    List<Integer> duraciones = new ArrayList<>();
                                    duraciones.add(duracion);
                                    dataMap.put(fecha, duraciones);
                                }
                            }

                            List<ReportItem> reportItems = new ArrayList<>();
                            for (Map.Entry<String, List<Integer>> entry : dataMap.entrySet()) {
                                String fecha = entry.getKey();
                                List<Integer> duraciones = entry.getValue();
                                int totalDuracion = 0;
                                for (Integer duracion : duraciones) {
                                    totalDuracion += duracion;
                                }
                                int promedioDuracion = totalDuracion / duraciones.size();

                                ReportItem item = new ReportItem(fecha, promedioDuracion);
                                reportItems.add(item);
                            }

                            runOnUiThread(() -> {
                                ReportAdapter reportAdapter = new ReportAdapter(reportItems);
                                recyclerView.setAdapter(reportAdapter);
                                Toast.makeText(informes.this, "Datos cargados correctamente.", Toast.LENGTH_SHORT).show();
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        });

        llenarSpinners();
    }

    public class ReportItem {
        private String fecha;
        private int promedioDuracion;

        public ReportItem(String fecha, int promedioDuracion) {
            this.fecha = fecha;
            this.promedioDuracion = promedioDuracion;
        }

        public String getFecha() {
            return fecha;
        }

        public int getPromedioDuracion() {
            return promedioDuracion;
        }
    }

    public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {
        private List<ReportItem> reportItems;

        public ReportAdapter(List<ReportItem> reportItems) {
            this.reportItems = reportItems;
        }

        @NonNull
        @Override
        public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report, parent, false);
            return new ReportViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
            ReportItem item = reportItems.get(position);
            holder.textFecha.setText(item.getFecha());
            holder.textPromedioDuracion.setText(String.valueOf(item.getPromedioDuracion()));
        }

        @Override
        public int getItemCount() {
            return reportItems.size();
        }

        public class ReportViewHolder extends RecyclerView.ViewHolder {
            TextView textFecha;
            TextView textPromedioDuracion;

            public ReportViewHolder(View itemView) {
                super(itemView);
                textFecha = itemView.findViewById(R.id.textFecha);
                textPromedioDuracion = itemView.findViewById(R.id.textPromedioDuracion);
            }
        }
    }

    private void llenarSpinners() {
        String url = "https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/Lugares/informes.php?accion=llenar_spiners";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    System.out.println(jsonData);
                    try {
                        JSONObject jsonObject = new JSONObject(jsonData);
                        JSONArray jsonArrayMeses = jsonObject.getJSONArray("meses");
                        JSONArray jsonArrayAnios = jsonObject.getJSONArray("anios");

                        final ArrayList<String> meses = new ArrayList<>();
                        final ArrayList<String> anios = new ArrayList<>();

                        for (int i = 0; i < jsonArrayMeses.length(); i++) {
                            String mes = jsonArrayMeses.getString(i);
                            meses.add(mes);
                        }

                        for (int i = 0; i < jsonArrayAnios.length(); i++) {
                            String anio = jsonArrayAnios.getString(i);
                            anios.add(anio);
                        }

                        runOnUiThread(() -> {
                            ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(informes.this, android.R.layout.simple_spinner_item, meses);
                            ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(informes.this, android.R.layout.simple_spinner_item, anios);

                            monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            spinnerMonth.setAdapter(monthAdapter);
                            spinnerYear.setAdapter(yearAdapter);
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}

package com.example.proyectoestacionamiento;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class espacioEstacionamiento extends AppCompatActivity {

    private GridView gridViewParking;
    private OkHttpClient client = new OkHttpClient();
    private final Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            actualizarDatos();
            handler.postDelayed(this, 3000); // Actualizar cada 3 segundos
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espacios_estacionamiento);

        gridViewParking = findViewById(R.id.gridViewParking);

        Button btnActualizar = findViewById(R.id.btn_actualizar);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarDatos();
            }
        });

        // Iniciar la actualización periódica
        handler.postDelayed(runnable, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    private void actualizarDatos() {
        Request request = new Request.Builder()
                .url("https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/Lugares/lugares.php?accion=ver_todos")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();

                    runOnUiThread(() -> {
                        try {
                            JSONArray jsonArray = new JSONArray(responseData);
                            GridViewAdapter adapter = new GridViewAdapter(jsonArray);
                            gridViewParking.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    Log.e("API Error", "Response code: " + response.code());
                }
            }
        });
    }

    private class GridViewAdapter extends BaseAdapter {
        private JSONArray jsonArray;

        public GridViewAdapter(JSONArray jsonArray) {
            this.jsonArray = jsonArray;
        }

        @Override
        public int getCount() {
            return jsonArray.length();
        }

        @Override
        public JSONObject getItem(int position) {
            try {
                return jsonArray.getJSONObject(position);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View gridViewItem;
            if (convertView == null) {
                gridViewItem = getLayoutInflater().inflate(R.layout.grid_item_layout, parent, false);
            } else {
                gridViewItem = convertView;
            }

            ImageView imageView = gridViewItem.findViewById(R.id.imageViewParking);
            TextView textView = gridViewItem.findViewById(R.id.textViewPlaceNumber);

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(position);
                String estado = jsonObject.getString("estado");
                int idEspacio = jsonObject.getInt("id_espacio");

                if (estado.equals("ocupado")) {
                    imageView.setImageResource(R.drawable.espacio_ocupado);
                } else {
                    imageView.setImageResource(R.drawable.espacio_libre);
                }

                textView.setText("P-" + idEspacio);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return gridViewItem;
        }
    }
}

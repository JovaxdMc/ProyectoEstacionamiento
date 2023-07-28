package com.example.proyectoestacionamiento;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espacios_estacionamiento);

        gridViewParking = findViewById(R.id.gridViewParking);

        // Realizar la solicitud HTTP al URL especificado
        Request request = new Request.Builder()
                .url("https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/Lugares/lugares.php?endpoint=espacios")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Manejar error en caso de fallo de la solicitud HTTP
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();

                    // Procesar los datos de respuesta y actualizar la interfaz de usuario
                    runOnUiThread(() -> {
                        try {
                            JSONArray jsonArray = new JSONArray(responseData);

                            // Crear un adaptador personalizado para cargar las imágenes en el GridView
                            GridViewAdapter adapter = new GridViewAdapter(jsonArray);
                            gridViewParking.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    // Manejar error en caso de respuesta no exitosa
                    Log.e("API Error", "Response code: " + response.code());
                }
            }
        });
    }

    // Adaptador personalizado para cargar las imágenes en el GridView
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
                // Inflar el diseño personalizado para cada elemento del GridView
                gridViewItem = getLayoutInflater().inflate(R.layout.grid_item_layout, parent, false);
            } else {
                gridViewItem = convertView;
            }

            // Obtener el ImageView y
            // el TextView del diseño personalizado
            ImageView imageView = gridViewItem.findViewById(R.id.imageViewParking);
            TextView textView = gridViewItem.findViewById(R.id.textViewPlaceNumber);

            // Obtener el estado del espacio y cargar la imagen correspondiente
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(position);
                String estado = jsonObject.getString("estado");
                int idEspacio = jsonObject.getInt("id_espacio");

                if (estado.equals("ocupado")) {
                    imageView.setImageResource(R.drawable.espacio_ocupado); // Reemplaza 'espacio_ocupado' con el nombre de tu recurso de imagen/icono para espacio ocupado
                } else {
                    imageView.setImageResource(R.drawable.espacio_libre); // Reemplaza 'espacio_libre' con el nombre de tu recurso de imagen/icono para espacio libre
                }

                // Mostrar el número de lugar debajo de la imagen
                textView.setText("P-" + idEspacio); // Aquí también puedes personalizar el formato del número de lugar según tus necesidades

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return gridViewItem;
        }
    }
}

package com.example.proyectoestacionamiento;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginTask extends AsyncTask<String, Void, String> {

    public interface OnLoginResultListener {
        void onLoginResult(String result);
    }

    private OnLoginResultListener listener;

    public LoginTask(OnLoginResultListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String urlString = "https://estacionamientohmagdl.000webhostapp.com/Estacionamiento/Login/login.php";
        String username = params[0];
        String password = params[1];
        String response = "";

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Construir el cuerpo de la solicitud
            String body = "username=" + username + "&password=" + password;
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(body.getBytes());
            outputStream.flush();
            outputStream.close();

            // Leer la respuesta del servidor
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response += line;
            }
            reader.close();

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Respuesta: "+response);
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        if (listener != null) {
            listener.onLoginResult(result);
        }
    }
}
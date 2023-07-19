package com.example.proyectoestacionamiento;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class codigosQR extends AppCompatActivity {
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

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateQRCode();
            }
        });
    }

    private void generateQRCode() {
        String id = idEditText.getText().toString();
        String data = retrieveDataFromDatabase(id);

        if (data != null) {
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

    private String retrieveDataFromDatabase(String id) {
        String url = "jdbc:mysql://localhost:3306/id21048854_estacionamiento";
        String username = "id21048854_adm1420014414";
        String password = "Yihh123456.";

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM usuarios WHERE id = ?";
            statement = conn.prepareStatement(query);
            statement.setString(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Aquí debes obtener los campos específicos de tu base de datos
                String campo1 = resultSet.getString("nombre");
                String campo2 = resultSet.getString("apellidos");
                String campo3 = resultSet.getString("username");
                String campo4 = resultSet.getString("rol");
                // ... Obtén los demás campos que necesites

                return "ID: " + id + "\nCampo1: " + campo1 + "\nCampo2: " + campo2 + "\nCampo3: " + campo3 + "\nCampo4: " + campo4;
            } else {
                return "No se encontraron datos para el ID: " + id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al obtener los datos de la base de datos";
        } finally {
            // Cerrar los recursos
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

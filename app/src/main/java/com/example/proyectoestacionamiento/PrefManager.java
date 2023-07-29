package com.example.proyectoestacionamiento;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    private static final String PREF_NAME = "UserPref";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ROL = "rol";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_APELLIDOS = "apellidos";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveUserData(String userId, String username, String rol, String nombre, String apellidos) {
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_ROL, rol);
        editor.putString(KEY_NOMBRE, nombre);
        editor.putString(KEY_APELLIDOS, apellidos);
        editor.apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, "");
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }

    public String getRol() {
        return sharedPreferences.getString(KEY_ROL, "");
    }

    public String getNombre() {
        return sharedPreferences.getString(KEY_NOMBRE, "");
    }

    public String getApellidos() {
        return sharedPreferences.getString(KEY_APELLIDOS, "");
    }
}

package com.example.kasirdigital.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.kasirdigital.models.Pengguna;

public class SessionManager {
    private static final String PREFS_NAME = "kasir_digital_prefs";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_NAMA = "nama";
    private static final String KEY_ROLE = "role";
    private static final String KEY_LOGIN = "is_login";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void createLoginSession(Pengguna pengguna) {
        editor.putInt(KEY_USER_ID, pengguna.getId());
        editor.putString(KEY_USERNAME, pengguna.getUsername());
        editor.putString(KEY_NAMA, pengguna.getNama());
        editor.putString(KEY_ROLE, pengguna.getRole());
        editor.putBoolean(KEY_LOGIN, true);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_LOGIN, false);
    }

    public int getUserId() {
        return prefs.getInt(KEY_USER_ID, -1);
    }

    public String getUsername() {
        return prefs.getString(KEY_USERNAME, "");
    }

    public String getNama() {
        return prefs.getString(KEY_NAMA, "");
    }

    public String getRole() {
        return prefs.getString(KEY_ROLE, "");
    }

    public void logout() {
        editor.clear();
        editor.commit();
    }
}

package com.example.kasirdigital.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kasirdigital.R;
import com.example.kasirdigital.database.DatabaseHelper;
import com.example.kasirdigital.helpers.SessionManager;
import com.example.kasirdigital.models.Pengguna;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        // Cek jika sudah login
        if (sessionManager.isLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        Pengguna pengguna = dbHelper.loginPengguna(username, password);
        if (pengguna != null) {
            sessionManager.createLoginSession(pengguna);
            Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Username atau Password salah", Toast.LENGTH_SHORT).show();
            etPassword.setText("");
        }
    }
}

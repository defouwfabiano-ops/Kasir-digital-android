package com.example.kasirdigital.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kasirdigital.R;
import com.example.kasirdigital.database.DatabaseHelper;
import com.example.kasirdigital.helpers.SessionManager;
import com.example.kasirdigital.models.Transaksi;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView tvSelamat, tvTotalPenjualan, tvJumlahTransaksi, tvProdukTerlaris;
    private Button btnPenjualan, btnInventori, btnLaporan, btnLogout;
    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        tvSelamat = findViewById(R.id.tv_selamat);
        tvTotalPenjualan = findViewById(R.id.tv_total_penjualan);
        tvJumlahTransaksi = findViewById(R.id.tv_jumlah_transaksi);
        tvProdukTerlaris = findViewById(R.id.tv_produk_terlaris);
        btnPenjualan = findViewById(R.id.btn_penjualan);
        btnInventori = findViewById(R.id.btn_inventori);
        btnLaporan = findViewById(R.id.btn_laporan);
        btnLogout = findViewById(R.id.btn_logout);

        // Set greeting
        tvSelamat.setText("Selamat Datang, " + sessionManager.getNama() + "!");

        // Load dashboard data
        loadDashboard();

        // Set button listeners
        btnPenjualan.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, PenjualanActivity.class)));
        btnInventori.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, InventoriActivity.class)));
        btnLaporan.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LaporanActivity.class)));
        btnLogout.setOnClickListener(v -> logout());
    }

    private void loadDashboard() {
        List<Transaksi> transaksiList = dbHelper.getAllTransaksi();
        DecimalFormat df = new DecimalFormat("#,###");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String todayDate = sdf.format(new Date());

        double totalPenjualanHariIni = 0;
        int jumlahTransaksiHariIni = 0;

        for (Transaksi transaksi : transaksiList) {
            String transaksiDate = sdf.format(new Date(transaksi.getTanggal()));
            if (transaksiDate.equals(todayDate)) {
                totalPenjualanHariIni += transaksi.getTotalHarga();
                jumlahTransaksiHariIni++;
            }
        }

        tvTotalPenjualan.setText("Rp " + df.format(totalPenjualanHariIni));
        tvJumlahTransaksi.setText(String.valueOf(jumlahTransaksiHariIni));
        tvProdukTerlaris.setText("-"); // Fitur advanced bisa dikembangkan
    }

    private void logout() {
        sessionManager.logout();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}

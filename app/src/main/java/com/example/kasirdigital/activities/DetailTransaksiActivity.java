package com.example.kasirdigital.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kasirdigital.R;
import com.example.kasirdigital.database.DatabaseHelper;
import com.example.kasirdigital.models.Transaksi;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailTransaksiActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private int transaksiId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi);

        dbHelper = new DatabaseHelper(this);
        transaksiId = getIntent().getIntExtra("transaksi_id", -1);

        if (transaksiId != -1) {
            loadDetailTransaksi();
        }
    }

    private void loadDetailTransaksi() {
        Transaksi transaksi = dbHelper.getTransaksiById(transaksiId);
        if (transaksi != null) {
            showDetailTransaksi(transaksi);
        }
    }

    private void showDetailTransaksi(Transaksi transaksi) {
        DecimalFormat df = new DecimalFormat("#,###");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());

        TextView tvNomorTransaksi = findViewById(R.id.tv_detail_nomor_transaksi);
        TextView tvTanggal = findViewById(R.id.tv_detail_tanggal);
        TextView tvPenjual = findViewById(R.id.tv_detail_penjual);
        TextView tvTotalHarga = findViewById(R.id.tv_detail_total_harga);
        TextView tvDiskon = findViewById(R.id.tv_detail_diskon);
        TextView tvJumlahBayar = findViewById(R.id.tv_detail_jumlah_bayar);
        TextView tvKembalian = findViewById(R.id.tv_detail_kembalian);
        TextView tvMetode = findViewById(R.id.tv_detail_metode);
        TextView tvJumlahItem = findViewById(R.id.tv_detail_jumlah_item);

        tvNomorTransaksi.setText(transaksi.getNomorTransaksi());
        tvTanggal.setText(sdf.format(new Date(transaksi.getTanggal())));
        tvPenjual.setText(transaksi.getNamaPenjual());
        tvTotalHarga.setText("Rp " + df.format(transaksi.getTotalHarga()));
        tvDiskon.setText("Rp " + df.format(transaksi.getDiskon()));
        tvJumlahBayar.setText("Rp " + df.format(transaksi.getJumlahBayar()));
        tvKembalian.setText("Rp " + df.format(transaksi.getKembalian()));
        tvMetode.setText(transaksi.getMetodePembayaran());
        tvJumlahItem.setText(transaksi.getJumlahItem() + " item");
    }
}

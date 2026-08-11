package com.example.kasirdigital.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasirdigital.R;
import com.example.kasirdigital.adapters.TransaksiAdapter;
import com.example.kasirdigital.database.DatabaseHelper;
import com.example.kasirdigital.models.Transaksi;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LaporanActivity extends AppCompatActivity implements TransaksiAdapter.OnTransaksiClickListener {
    private RecyclerView rvTransaksi;
    private TextView tvTotalPenjualan, tvTotalTransaksi, tvRataPenjualan;
    private DatabaseHelper dbHelper;
    private TransaksiAdapter adapter;
    private List<Transaksi> transaksiList;
    private DecimalFormat df = new DecimalFormat("#,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);

        dbHelper = new DatabaseHelper(this);
        rvTransaksi = findViewById(R.id.rv_laporan_transaksi);
        tvTotalPenjualan = findViewById(R.id.tv_laporan_total_penjualan);
        tvTotalTransaksi = findViewById(R.id.tv_laporan_total_transaksi);
        tvRataPenjualan = findViewById(R.id.tv_laporan_rata_penjualan);

        transaksiList = new ArrayList<>();
        rvTransaksi.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransaksiAdapter(this, transaksiList, this);
        rvTransaksi.setAdapter(adapter);

        loadLaporan();
    }

    private void loadLaporan() {
        transaksiList.clear();
        List<Transaksi> allTransaksi = dbHelper.getAllTransaksi();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String todayDate = sdf.format(new Date());

        double totalPenjualan = 0;
        int jumlahTransaksi = 0;

        for (Transaksi transaksi : allTransaksi) {
            String transaksiDate = sdf.format(new Date(transaksi.getTanggal()));
            if (transaksiDate.equals(todayDate)) {
                transaksiList.add(transaksi);
                totalPenjualan += transaksi.getTotalHarga();
                jumlahTransaksi++;
            }
        }

        adapter.notifyDataSetChanged();

        // Update summary
        tvTotalPenjualan.setText("Rp " + df.format(totalPenjualan));
        tvTotalTransaksi.setText(String.valueOf(jumlahTransaksi));
        double rataPenjualan = jumlahTransaksi > 0 ? totalPenjualan / jumlahTransaksi : 0;
        tvRataPenjualan.setText("Rp " + df.format(rataPenjualan));
    }

    @Override
    public void onTransaksiClick(Transaksi transaksi) {
        // TODO: Navigate to detail transaksi
    }
}

package com.example.kasirdigital.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasirdigital.R;
import com.example.kasirdigital.adapters.KeranjangAdapter;
import com.example.kasirdigital.adapters.ProdukAdapter;
import com.example.kasirdigital.database.DatabaseHelper;
import com.example.kasirdigital.helpers.SessionManager;
import com.example.kasirdigital.models.KeranjangItem;
import com.example.kasirdigital.models.Produk;
import com.example.kasirdigital.models.Transaksi;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PenjualanActivity extends AppCompatActivity implements
        ProdukAdapter.OnProdukClickListener,
        KeranjangAdapter.OnItemActionListener {

    private RecyclerView rvProduk, rvKeranjang;
    private TextView tvTotalHarga, tvJumlahItem;
    private Button btnBayar, btnBatalkan;
    private EditText etCari;

    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;
    private ProdukAdapter produkAdapter;
    private KeranjangAdapter keranjangAdapter;
    private List<Produk> produkList;
    private List<KeranjangItem> keranjangList;
    private DecimalFormat df = new DecimalFormat("#,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        // Initialize views
        rvProduk = findViewById(R.id.rv_produk);
        rvKeranjang = findViewById(R.id.rv_keranjang);
        tvTotalHarga = findViewById(R.id.tv_total_harga);
        tvJumlahItem = findViewById(R.id.tv_jumlah_item);
        btnBayar = findViewById(R.id.btn_bayar);
        btnBatalkan = findViewById(R.id.btn_batalkan);
        etCari = findViewById(R.id.et_cari_produk);

        // Initialize lists
        produkList = new ArrayList<>();
        keranjangList = new ArrayList<>();

        // Setup RecyclerViews
        rvProduk.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvKeranjang.setLayoutManager(new LinearLayoutManager(this));

        produkAdapter = new ProdukAdapter(this, produkList, this);
        keranjangAdapter = new KeranjangAdapter(this, keranjangList, this);

        rvProduk.setAdapter(produkAdapter);
        rvKeranjang.setAdapter(keranjangAdapter);

        // Load produk
        loadProduk();

        // Button listeners
        btnBayar.setOnClickListener(v -> showBayarDialog());
        btnBatalkan.setOnClickListener(v -> resetKeranjang());
    }

    private void loadProduk() {
        produkList.clear();
        produkList.addAll(dbHelper.getAllProduk());
        produkAdapter.notifyDataSetChanged();
    }

    @Override
    public void onProdukClick(Produk produk) {
        if (produk.getStok() <= 0) {
            Toast.makeText(this, "Stok habis!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cek apakah produk sudah ada di keranjang
        boolean found = false;
        for (KeranjangItem item : keranjangList) {
            if (item.getProdukId() == produk.getId()) {
                if (item.getQuantity() < produk.getStok()) {
                    item.setQuantity(item.getQuantity() + 1);
                    keranjangAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, "Stok tidak cukup!", Toast.LENGTH_SHORT).show();
                }
                found = true;
                break;
            }
        }

        if (!found) {
            keranjangList.add(new KeranjangItem(produk.getId(), produk.getNama(), produk.getHarga(), 1));
            keranjangAdapter.notifyDataSetChanged();
        }

        updateTotal();
    }

    @Override
    public void onItemRemoved(int position) {
        keranjangList.remove(position);
        keranjangAdapter.notifyDataSetChanged();
        updateTotal();
    }

    @Override
    public void onQuantityChanged(int position, int quantity) {
        updateTotal();
    }

    private void updateTotal() {
        double total = 0;
        int jumlahItem = 0;
        for (KeranjangItem item : keranjangList) {
            total += item.getSubtotal();
            jumlahItem += item.getQuantity();
        }
        tvTotalHarga.setText("Rp " + df.format(total));
        tvJumlahItem.setText(jumlahItem + " item");
    }

    private void showBayarDialog() {
        if (keranjangList.isEmpty()) {
            Toast.makeText(this, "Keranjang kosong!", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pembayaran");

        // Calculate total
        double totalHarga = 0;
        for (KeranjangItem item : keranjangList) {
            totalHarga += item.getSubtotal();
        }

        final EditText etJumlahBayar = new EditText(this);
        etJumlahBayar.setHint("Masukkan jumlah bayar");
        etJumlahBayar.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        builder.setView(etJumlahBayar);

        builder.setPositiveButton("Proses", (dialog, which) -> {
            String jumlahBayarStr = etJumlahBayar.getText().toString().trim();
            if (jumlahBayarStr.isEmpty()) {
                Toast.makeText(PenjualanActivity.this, "Jumlah bayar tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            double jumlahBayar = Double.parseDouble(jumlahBayarStr);
            if (jumlahBayar < totalHarga) {
                Toast.makeText(PenjualanActivity.this, "Jumlah bayar kurang!", Toast.LENGTH_SHORT).show();
                return;
            }

            double kembalian = jumlahBayar - totalHarga;

            // Save transaksi
            Transaksi transaksi = new Transaksi();
            transaksi.setNomorTransaksi(generateNomorTransaksi());
            transaksi.setTanggal(System.currentTimeMillis());
            transaksi.setTotalHarga(totalHarga);
            transaksi.setDiskon(0);
            transaksi.setJumlahBayar(jumlahBayar);
            transaksi.setKembalian(kembalian);
            transaksi.setMetodePembayaran("Tunai");
            transaksi.setNamaPenjual(sessionManager.getNama());
            transaksi.setJumlahItem(getTotalJumlahItem());

            long transaksiId = dbHelper.tambahTransaksi(transaksi);

            // Update stok
            for (KeranjangItem item : keranjangList) {
                Produk produk = dbHelper.getProdukById(item.getProdukId());
                if (produk != null) {
                    int stokBaru = produk.getStok() - item.getQuantity();
                    dbHelper.updateStok(item.getProdukId(), stokBaru);
                }
            }

            // Show success message
            showStruk(transaksi, kembalian);
            resetKeranjang();
            loadProduk();
        });

        builder.setNegativeButton("Batal", null);
        builder.show();
    }

    private void showStruk(Transaksi transaksi, double kembalian) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Struk Transaksi");
        builder.setMessage("Nomor: " + transaksi.getNomorTransaksi() + "\n" +
                "Kasir: " + transaksi.getNamaPenjual() + "\n" +
                "Total: Rp " + df.format(transaksi.getTotalHarga()) + "\n" +
                "Bayar: Rp " + df.format(transaksi.getJumlahBayar()) + "\n" +
                "Kembalian: Rp " + df.format(kembalian));
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    private void resetKeranjang() {
        keranjangList.clear();
        keranjangAdapter.notifyDataSetChanged();
        updateTotal();
    }

    private int getTotalJumlahItem() {
        int total = 0;
        for (KeranjangItem item : keranjangList) {
            total += item.getQuantity();
        }
        return total;
    }

    private String generateNomorTransaksi() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        return "TRX-" + sdf.format(new Date());
    }
}

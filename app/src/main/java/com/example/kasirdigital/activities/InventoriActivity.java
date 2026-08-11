package com.example.kasirdigital.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasirdigital.R;
import com.example.kasirdigital.adapters.ProdukAdapter;
import com.example.kasirdigital.database.DatabaseHelper;
import com.example.kasirdigital.models.Produk;

import java.util.ArrayList;
import java.util.List;

public class InventoriActivity extends AppCompatActivity implements ProdukAdapter.OnProdukClickListener {
    private RecyclerView rvInventori;
    private Button btnTambahProduk;
    private DatabaseHelper dbHelper;
    private ProdukAdapter adapter;
    private List<Produk> produkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventori);

        dbHelper = new DatabaseHelper(this);
        rvInventori = findViewById(R.id.rv_inventori);
        btnTambahProduk = findViewById(R.id.btn_tambah_produk);

        produkList = new ArrayList<>();
        rvInventori.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProdukAdapter(this, produkList, this);
        rvInventori.setAdapter(adapter);

        loadProduk();

        btnTambahProduk.setOnClickListener(v -> showTambahProdukDialog());
    }

    private void loadProduk() {
        produkList.clear();
        produkList.addAll(dbHelper.getAllProduk());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onProdukClick(Produk produk) {
        showEditProdukDialog(produk);
    }

    private void showTambahProdukDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Produk Baru");

        // Create layout untuk input
        EditText etNama = new EditText(this);
        etNama.setHint("Nama Produk");
        EditText etHarga = new EditText(this);
        etHarga.setHint("Harga");
        etHarga.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        EditText etStok = new EditText(this);
        etStok.setHint("Stok");
        etStok.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        EditText etKategori = new EditText(this);
        etKategori.setHint("Kategori");
        EditText etBarcode = new EditText(this);
        etBarcode.setHint("Barcode (Optional)");

        builder.setView(etNama);
        builder.setView(etHarga);
        builder.setView(etStok);
        builder.setView(etKategori);
        builder.setView(etBarcode);

        builder.setPositiveButton("Simpan", (dialog, which) -> {
            String nama = etNama.getText().toString().trim();
            String hargaStr = etHarga.getText().toString().trim();
            String stokStr = etStok.getText().toString().trim();
            String kategori = etKategori.getText().toString().trim();
            String barcode = etBarcode.getText().toString().trim();

            if (nama.isEmpty() || hargaStr.isEmpty() || stokStr.isEmpty()) {
                Toast.makeText(this, "Isi semua field yang required", Toast.LENGTH_SHORT).show();
                return;
            }

            double harga = Double.parseDouble(hargaStr);
            int stok = Integer.parseInt(stokStr);

            Produk produk = new Produk(0, nama, harga, stok, kategori, barcode);
            dbHelper.tambahProduk(produk);
            Toast.makeText(this, "Produk berhasil ditambah", Toast.LENGTH_SHORT).show();
            loadProduk();
        });

        builder.setNegativeButton("Batal", null);
        builder.show();
    }

    private void showEditProdukDialog(Produk produk) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Produk");

        EditText etNama = new EditText(this);
        etNama.setText(produk.getNama());
        EditText etHarga = new EditText(this);
        etHarga.setText(String.valueOf(produk.getHarga()));
        etHarga.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        EditText etStok = new EditText(this);
        etStok.setText(String.valueOf(produk.getStok()));
        etStok.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);

        builder.setView(etNama);
        builder.setView(etHarga);
        builder.setView(etStok);

        builder.setPositiveButton("Update", (dialog, which) -> {
            produk.setNama(etNama.getText().toString().trim());
            produk.setHarga(Double.parseDouble(etHarga.getText().toString().trim()));
            produk.setStok(Integer.parseInt(etStok.getText().toString().trim()));
            dbHelper.updateProduk(produk);
            Toast.makeText(this, "Produk berhasil diupdate", Toast.LENGTH_SHORT).show();
            loadProduk();
        });

        builder.setNegativeButton("Hapus", (dialog, which) -> {
            AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(this);
            deleteBuilder.setTitle("Konfirmasi");
            deleteBuilder.setMessage("Yakin ingin menghapus produk ini?");
            deleteBuilder.setPositiveButton("Ya", (d, w) -> {
                dbHelper.deleteProduk(produk.getId());
                Toast.makeText(this, "Produk berhasil dihapus", Toast.LENGTH_SHORT).show();
                loadProduk();
            });
            deleteBuilder.setNegativeButton("Tidak", null);
            deleteBuilder.show();
        });

        builder.show();
    }
}

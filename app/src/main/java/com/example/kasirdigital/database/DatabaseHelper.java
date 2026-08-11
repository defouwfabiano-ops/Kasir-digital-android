package com.example.kasirdigital.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kasirdigital.models.Produk;
import com.example.kasirdigital.models.Transaksi;
import com.example.kasirdigital.models.Pengguna;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "kasir_digital.db";
    private static final int DATABASE_VERSION = 1;

    // Tabel Produk
    private static final String TABLE_PRODUK = "produk";
    private static final String COLUMN_PRODUK_ID = "id";
    private static final String COLUMN_PRODUK_NAMA = "nama";
    private static final String COLUMN_PRODUK_HARGA = "harga";
    private static final String COLUMN_PRODUK_STOK = "stok";
    private static final String COLUMN_PRODUK_KATEGORI = "kategori";
    private static final String COLUMN_PRODUK_BARCODE = "barcode";

    // Tabel Transaksi
    private static final String TABLE_TRANSAKSI = "transaksi";
    private static final String COLUMN_TRANSAKSI_ID = "id";
    private static final String COLUMN_TRANSAKSI_NOMOR = "nomor_transaksi";
    private static final String COLUMN_TRANSAKSI_TANGGAL = "tanggal";
    private static final String COLUMN_TRANSAKSI_TOTAL = "total_harga";
    private static final String COLUMN_TRANSAKSI_DISKON = "diskon";
    private static final String COLUMN_TRANSAKSI_BAYAR = "jumlah_bayar";
    private static final String COLUMN_TRANSAKSI_KEMBALIAN = "kembalian";
    private static final String COLUMN_TRANSAKSI_METODE = "metode_pembayaran";
    private static final String COLUMN_TRANSAKSI_PENJUAL = "nama_penjual";
    private static final String COLUMN_TRANSAKSI_JUMLAH_ITEM = "jumlah_item";

    // Tabel Pengguna
    private static final String TABLE_PENGGUNA = "pengguna";
    private static final String COLUMN_PENGGUNA_ID = "id";
    private static final String COLUMN_PENGGUNA_USERNAME = "username";
    private static final String COLUMN_PENGGUNA_PASSWORD = "password";
    private static final String COLUMN_PENGGUNA_NAMA = "nama";
    private static final String COLUMN_PENGGUNA_ROLE = "role";
    private static final String COLUMN_PENGGUNA_AKTIF = "aktif";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table produk
        String CREATE_PRODUK_TABLE = "CREATE TABLE " + TABLE_PRODUK + "(" +
                COLUMN_PRODUK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_PRODUK_NAMA + " TEXT NOT NULL," +
                COLUMN_PRODUK_HARGA + " REAL NOT NULL," +
                COLUMN_PRODUK_STOK + " INTEGER NOT NULL," +
                COLUMN_PRODUK_KATEGORI + " TEXT," +
                COLUMN_PRODUK_BARCODE + " TEXT UNIQUE" +
                ")";
        db.execSQL(CREATE_PRODUK_TABLE);

        // Create table transaksi
        String CREATE_TRANSAKSI_TABLE = "CREATE TABLE " + TABLE_TRANSAKSI + "(" +
                COLUMN_TRANSAKSI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TRANSAKSI_NOMOR + " TEXT UNIQUE NOT NULL," +
                COLUMN_TRANSAKSI_TANGGAL + " LONG NOT NULL," +
                COLUMN_TRANSAKSI_TOTAL + " REAL NOT NULL," +
                COLUMN_TRANSAKSI_DISKON + " REAL DEFAULT 0," +
                COLUMN_TRANSAKSI_BAYAR + " REAL NOT NULL," +
                COLUMN_TRANSAKSI_KEMBALIAN + " REAL DEFAULT 0," +
                COLUMN_TRANSAKSI_METODE + " TEXT," +
                COLUMN_TRANSAKSI_PENJUAL + " TEXT," +
                COLUMN_TRANSAKSI_JUMLAH_ITEM + " INTEGER NOT NULL" +
                ")";
        db.execSQL(CREATE_TRANSAKSI_TABLE);

        // Create table pengguna
        String CREATE_PENGGUNA_TABLE = "CREATE TABLE " + TABLE_PENGGUNA + "(" +
                COLUMN_PENGGUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_PENGGUNA_USERNAME + " TEXT UNIQUE NOT NULL," +
                COLUMN_PENGGUNA_PASSWORD + " TEXT NOT NULL," +
                COLUMN_PENGGUNA_NAMA + " TEXT NOT NULL," +
                COLUMN_PENGGUNA_ROLE + " TEXT NOT NULL," +
                COLUMN_PENGGUNA_AKTIF + " INTEGER DEFAULT 1" +
                ")";
        db.execSQL(CREATE_PENGGUNA_TABLE);

        // Insert default users
        insertDefaultUsers(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSAKSI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PENGGUNA);
        onCreate(db);
    }

    // ==================== PRODUK ====================
    public long tambahProduk(Produk produk) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUK_NAMA, produk.getNama());
        values.put(COLUMN_PRODUK_HARGA, produk.getHarga());
        values.put(COLUMN_PRODUK_STOK, produk.getStok());
        values.put(COLUMN_PRODUK_KATEGORI, produk.getKategori());
        values.put(COLUMN_PRODUK_BARCODE, produk.getBarcode());
        return db.insert(TABLE_PRODUK, null, values);
    }

    public List<Produk> getAllProduk() {
        List<Produk> produkList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUK, null);

        if (cursor.moveToFirst()) {
            do {
                Produk produk = new Produk();
                produk.setId(cursor.getInt(0));
                produk.setNama(cursor.getString(1));
                produk.setHarga(cursor.getDouble(2));
                produk.setStok(cursor.getInt(3));
                produk.setKategori(cursor.getString(4));
                produk.setBarcode(cursor.getString(5));
                produkList.add(produk);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return produkList;
    }

    public Produk getProdukById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUK, null, COLUMN_PRODUK_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        Produk produk = null;
        if (cursor.moveToFirst()) {
            produk = new Produk();
            produk.setId(cursor.getInt(0));
            produk.setNama(cursor.getString(1));
            produk.setHarga(cursor.getDouble(2));
            produk.setStok(cursor.getInt(3));
            produk.setKategori(cursor.getString(4));
            produk.setBarcode(cursor.getString(5));
        }
        cursor.close();
        return produk;
    }

    public void updateProduk(Produk produk) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUK_NAMA, produk.getNama());
        values.put(COLUMN_PRODUK_HARGA, produk.getHarga());
        values.put(COLUMN_PRODUK_STOK, produk.getStok());
        values.put(COLUMN_PRODUK_KATEGORI, produk.getKategori());
        values.put(COLUMN_PRODUK_BARCODE, produk.getBarcode());
        db.update(TABLE_PRODUK, values, COLUMN_PRODUK_ID + "=?",
                new String[]{String.valueOf(produk.getId())});
    }

    public void deleteProduk(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUK, COLUMN_PRODUK_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void updateStok(int produkId, int stok) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUK_STOK, stok);
        db.update(TABLE_PRODUK, values, COLUMN_PRODUK_ID + "=?",
                new String[]{String.valueOf(produkId)});
    }

    // ==================== TRANSAKSI ====================
    public long tambahTransaksi(Transaksi transaksi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRANSAKSI_NOMOR, transaksi.getNomorTransaksi());
        values.put(COLUMN_TRANSAKSI_TANGGAL, transaksi.getTanggal());
        values.put(COLUMN_TRANSAKSI_TOTAL, transaksi.getTotalHarga());
        values.put(COLUMN_TRANSAKSI_DISKON, transaksi.getDiskon());
        values.put(COLUMN_TRANSAKSI_BAYAR, transaksi.getJumlahBayar());
        values.put(COLUMN_TRANSAKSI_KEMBALIAN, transaksi.getKembalian());
        values.put(COLUMN_TRANSAKSI_METODE, transaksi.getMetodePembayaran());
        values.put(COLUMN_TRANSAKSI_PENJUAL, transaksi.getNamaPenjual());
        values.put(COLUMN_TRANSAKSI_JUMLAH_ITEM, transaksi.getJumlahItem());
        return db.insert(TABLE_TRANSAKSI, null, values);
    }

    public List<Transaksi> getAllTransaksi() {
        List<Transaksi> transaksiList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TRANSAKSI + " ORDER BY " +
                COLUMN_TRANSAKSI_TANGGAL + " DESC", null);

        if (cursor.moveToFirst()) {
            do {
                Transaksi transaksi = new Transaksi();
                transaksi.setId(cursor.getInt(0));
                transaksi.setNomorTransaksi(cursor.getString(1));
                transaksi.setTanggal(cursor.getLong(2));
                transaksi.setTotalHarga(cursor.getDouble(3));
                transaksi.setDiskon(cursor.getDouble(4));
                transaksi.setJumlahBayar(cursor.getDouble(5));
                transaksi.setKembalian(cursor.getDouble(6));
                transaksi.setMetodePembayaran(cursor.getString(7));
                transaksi.setNamaPenjual(cursor.getString(8));
                transaksi.setJumlahItem(cursor.getInt(9));
                transaksiList.add(transaksi);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return transaksiList;
    }

    public Transaksi getTransaksiById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TRANSAKSI, null, COLUMN_TRANSAKSI_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        Transaksi transaksi = null;
        if (cursor.moveToFirst()) {
            transaksi = new Transaksi();
            transaksi.setId(cursor.getInt(0));
            transaksi.setNomorTransaksi(cursor.getString(1));
            transaksi.setTanggal(cursor.getLong(2));
            transaksi.setTotalHarga(cursor.getDouble(3));
            transaksi.setDiskon(cursor.getDouble(4));
            transaksi.setJumlahBayar(cursor.getDouble(5));
            transaksi.setKembalian(cursor.getDouble(6));
            transaksi.setMetodePembayaran(cursor.getString(7));
            transaksi.setNamaPenjual(cursor.getString(8));
            transaksi.setJumlahItem(cursor.getInt(9));
        }
        cursor.close();
        return transaksi;
    }

    // ==================== PENGGUNA ====================
    public long tambahPengguna(Pengguna pengguna) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PENGGUNA_USERNAME, pengguna.getUsername());
        values.put(COLUMN_PENGGUNA_PASSWORD, pengguna.getPassword());
        values.put(COLUMN_PENGGUNA_NAMA, pengguna.getNama());
        values.put(COLUMN_PENGGUNA_ROLE, pengguna.getRole());
        values.put(COLUMN_PENGGUNA_AKTIF, pengguna.isAktif() ? 1 : 0);
        return db.insert(TABLE_PENGGUNA, null, values);
    }

    public Pengguna loginPengguna(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PENGGUNA, null,
                COLUMN_PENGGUNA_USERNAME + "=? AND " + COLUMN_PENGGUNA_PASSWORD + "=? AND " +
                        COLUMN_PENGGUNA_AKTIF + "=1",
                new String[]{username, password}, null, null, null);
        Pengguna pengguna = null;
        if (cursor.moveToFirst()) {
            pengguna = new Pengguna();
            pengguna.setId(cursor.getInt(0));
            pengguna.setUsername(cursor.getString(1));
            pengguna.setPassword(cursor.getString(2));
            pengguna.setNama(cursor.getString(3));
            pengguna.setRole(cursor.getString(4));
            pengguna.setAktif(cursor.getInt(5) == 1);
        }
        cursor.close();
        return pengguna;
    }

    public List<Pengguna> getAllPengguna() {
        List<Pengguna> penggunaList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PENGGUNA, null);

        if (cursor.moveToFirst()) {
            do {
                Pengguna pengguna = new Pengguna();
                pengguna.setId(cursor.getInt(0));
                pengguna.setUsername(cursor.getString(1));
                pengguna.setPassword(cursor.getString(2));
                pengguna.setNama(cursor.getString(3));
                pengguna.setRole(cursor.getString(4));
                pengguna.setAktif(cursor.getInt(5) == 1);
                penggunaList.add(pengguna);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return penggunaList;
    }

    private void insertDefaultUsers(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PENGGUNA_USERNAME, "admin");
        values.put(COLUMN_PENGGUNA_PASSWORD, "admin");
        values.put(COLUMN_PENGGUNA_NAMA, "Administrator");
        values.put(COLUMN_PENGGUNA_ROLE, "admin");
        values.put(COLUMN_PENGGUNA_AKTIF, 1);
        db.insert(TABLE_PENGGUNA, null, values);

        values.clear();
        values.put(COLUMN_PENGGUNA_USERNAME, "kasir");
        values.put(COLUMN_PENGGUNA_PASSWORD, "kasir123");
        values.put(COLUMN_PENGGUNA_NAMA, "Kasir 1");
        values.put(COLUMN_PENGGUNA_ROLE, "kasir");
        values.put(COLUMN_PENGGUNA_AKTIF, 1);
        db.insert(TABLE_PENGGUNA, null, values);
    }
}

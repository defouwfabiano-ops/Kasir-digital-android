package com.example.kasirdigital.models;

public class Transaksi {
    private int id;
    private String nomorTransaksi;
    private long tanggal;
    private double totalHarga;
    private double diskon;
    private double jumlahBayar;
    private double kembalian;
    private String metodePembayaran;
    private String namaPenjual;
    private int jumlahItem;

    public Transaksi() {}

    public Transaksi(int id, String nomorTransaksi, long tanggal, double totalHarga,
                    double diskon, double jumlahBayar, double kembalian,
                    String metodePembayaran, String namaPenjual, int jumlahItem) {
        this.id = id;
        this.nomorTransaksi = nomorTransaksi;
        this.tanggal = tanggal;
        this.totalHarga = totalHarga;
        this.diskon = diskon;
        this.jumlahBayar = jumlahBayar;
        this.kembalian = kembalian;
        this.metodePembayaran = metodePembayaran;
        this.namaPenjual = namaPenjual;
        this.jumlahItem = jumlahItem;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNomorTransaksi() { return nomorTransaksi; }
    public void setNomorTransaksi(String nomorTransaksi) { this.nomorTransaksi = nomorTransaksi; }

    public long getTanggal() { return tanggal; }
    public void setTanggal(long tanggal) { this.tanggal = tanggal; }

    public double getTotalHarga() { return totalHarga; }
    public void setTotalHarga(double totalHarga) { this.totalHarga = totalHarga; }

    public double getDiskon() { return diskon; }
    public void setDiskon(double diskon) { this.diskon = diskon; }

    public double getJumlahBayar() { return jumlahBayar; }
    public void setJumlahBayar(double jumlahBayar) { this.jumlahBayar = jumlahBayar; }

    public double getKembalian() { return kembalian; }
    public void setKembalian(double kembalian) { this.kembalian = kembalian; }

    public String getMetodePembayaran() { return metodePembayaran; }
    public void setMetodePembayaran(String metodePembayaran) { this.metodePembayaran = metodePembayaran; }

    public String getNamaPenjual() { return namaPenjual; }
    public void setNamaPenjual(String namaPenjual) { this.namaPenjual = namaPenjual; }

    public int getJumlahItem() { return jumlahItem; }
    public void setJumlahItem(int jumlahItem) { this.jumlahItem = jumlahItem; }
}

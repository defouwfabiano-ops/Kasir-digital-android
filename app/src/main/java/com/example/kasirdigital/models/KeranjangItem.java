package com.example.kasirdigital.models;

public class KeranjangItem {
    private int produkId;
    private String namaProduk;
    private double harga;
    private int quantity;
    private double subtotal;

    public KeranjangItem() {}

    public KeranjangItem(int produkId, String namaProduk, double harga, int quantity) {
        this.produkId = produkId;
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.quantity = quantity;
        this.subtotal = harga * quantity;
    }

    public int getProdukId() { return produkId; }
    public void setProdukId(int produkId) { this.produkId = produkId; }

    public String getNamaProduk() { return namaProduk; }
    public void setNamaProduk(String namaProduk) { this.namaProduk = namaProduk; }

    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.subtotal = harga * quantity;
    }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}

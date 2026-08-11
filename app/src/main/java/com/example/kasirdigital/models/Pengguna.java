package com.example.kasirdigital.models;

public class Pengguna {
    private int id;
    private String username;
    private String password;
    private String nama;
    private String role;
    private boolean aktif;

    public Pengguna() {}

    public Pengguna(int id, String username, String password, String nama, String role, boolean aktif) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nama = nama;
        this.role = role;
        this.aktif = aktif;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isAktif() { return aktif; }
    public void setAktif(boolean aktif) { this.aktif = aktif; }
}

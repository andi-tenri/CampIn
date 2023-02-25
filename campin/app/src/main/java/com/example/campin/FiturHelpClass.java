package com.example.campin;

public class FiturHelpClass {

    int gambar;
    String nama, deskripsi;

    public FiturHelpClass(int gambar, String nama, String deskripsi) {
        this.gambar = gambar;
        this.nama = nama;
        this.deskripsi = deskripsi;
    }

    public int getGambar() {
        return gambar;
    }

    public String getNama() {
        return nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }
}

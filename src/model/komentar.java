package model;

import java.sql.Date;

public class Komentar {
    private int id_komentar;
    private int id_user;
    private String isi_komentar;
    private Date tanggal;
    private String nama_user; // For displaying user name in comments

    public Komentar(int id_komentar, int id_user, String isi_komentar, Date tanggal) {
        this.id_komentar = id_komentar;
        this.id_user = id_user;
        this.isi_komentar = isi_komentar;
        this.tanggal = tanggal;
    }

    // Getters and Setters
    public int getId_komentar() {
        return id_komentar;
    }

    public void setId_komentar(int id_komentar) {
        this.id_komentar = id_komentar;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getIsi_komentar() {
        return isi_komentar;
    }

    public void setIsi_komentar(String isi_komentar) {
        this.isi_komentar = isi_komentar;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }
} 
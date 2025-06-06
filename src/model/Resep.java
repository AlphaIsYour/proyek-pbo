package model;

public class Resep {
    private int id_resep;
    private int id_kategori;
    private String judul;
    private String bahan;
    private String alat;
    private String langkah;
    private String foto;
    private String nama_kategori; // To store kategori name from join query

    // Constructor
    public Resep(int id_resep, int id_kategori, String judul, String bahan, String alat, String langkah, String foto) {
        this.id_resep = id_resep;
        this.id_kategori = id_kategori;
        this.judul = judul;
        this.bahan = bahan;
        this.alat = alat;
        this.langkah = langkah;
        this.foto = foto;
    }

    // Getters and Setters
    public int getId_resep() {
        return id_resep;
    }

    public void setId_resep(int id_resep) {
        this.id_resep = id_resep;
    }

    public int getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(int id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getBahan() {
        return bahan;
    }

    public void setBahan(String bahan) {
        this.bahan = bahan;
    }

    public String getAlat() {
        return alat;
    }

    public void setAlat(String alat) {
        this.alat = alat;
    }

    public String getLangkah() {
        return langkah;
    }

    public void setLangkah(String langkah) {
        this.langkah = langkah;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }
} 
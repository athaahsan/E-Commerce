package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Produk {
    //private int idProduk;
    private String namaProduk;
    private int hargaProduk;
    private String deskripsiProduk;
    private int stok;
    private String imgPath;
    private String penjualProduk;
    private String imgPenjual;
    
    public Produk(String namaProduk, int hargaProduk, String deskripsiProduk, int stok, String imgPath, String penjualProduk, String imgPenjual){
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
        this.deskripsiProduk = deskripsiProduk;
        this.stok = stok;
        this.imgPath = imgPath;
        this.penjualProduk = penjualProduk;
        this.imgPenjual = imgPenjual;
    }

    public String getPenjualProduk() {
        return penjualProduk;
    }

    public void setPenjualProduk(String penjualProduk) {
        this.penjualProduk = penjualProduk;
    }

    public String getImgPenjual() {
        return imgPenjual;
    }

    public void setImgPenjual(String imgPenjual) {
        this.imgPenjual = imgPenjual;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public int getHargaProduk() {
        return hargaProduk;
    }

    public void setHargaProduk(int hargaProduk) {
        this.hargaProduk = hargaProduk;
    }

    public String getDeskripsiProduk() {
        return deskripsiProduk;
    }

    public void setDeskripsiProduk(String deskripsiProduk) {
        this.deskripsiProduk = deskripsiProduk;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
    
    static public int getProductCount() throws SQLException {
        Database db = new Database();
        String sql = "SELECT COUNT(*) AS banyak_barang FROM produk";
        ResultSet res = db.getData(sql);
        
        res.next();
        return res.getInt("banyak_barang");
    }
    
    static public void fetchProducts(Produk[] produkList, int offset) throws SQLException {
        Database db = new Database();
        String sql = String.format(
            "SELECT p.*, s.nama_toko AS nama_toko, s.image_path AS sImgPath FROM produk p INNER JOIN seller s ON p.penjual = s.email ORDER BY id_barang LIMIT 5 OFFSET %d;",
            offset*5
        );
        
        ResultSet res = db.getData(sql);
        int ptr = 0;
        while(res.next()) {
            produkList[ptr] = new Produk(
                res.getString("nama"),
                res. getInt("harga"),
                res.getString("deskripsi"),
                res.getInt("stok"),
                res.getString("image_path"),
                res.getString("nama_toko"),
                res.getString("sImgPath")
            );
            ptr++;
        }

        // Set the rest of items to null if number of products in database is insufficient
        while(ptr < 5) {
            produkList[ptr] = null;
            ptr++;
        }
        
    }
    
    static public void fetchSellerProduct(String seller, int offset) throws SQLException {
        Database db = new Database();
        String sql = String.format(
            "SELECT * FROM produk WHERE penjual='%s' LIMIT 5 OFFSET %d",
            seller, offset*5
        );
        ResultSet rs = db.getData(sql);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1.nhom2.de14.model;

/**
 *
 * @author LeThanhLoi
 */
public class KhachHang {
    private String maKH;
    private String tenKH;
    private String soCMND;
    private String gioiTinh;
    private String diaChi;
    private String quocTich;
    private String SDT;

    public KhachHang(String record){
        String [] values = new String[7];
        System.out.println(record);
        values = record.split("\t",7);
        maKH = values[0];
        tenKH = values[1];
        soCMND = values[2];
        gioiTinh = values[3];
        diaChi = values[4];
        quocTich = values[5];
        SDT = values[6];
    }
    
    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSoCMND() {
        return soCMND;
    }

    public void setSoCMND(String soCMND) {
        this.soCMND = soCMND;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getQuocTich() {
        return quocTich;
    }

    public void setQuocTich(String quocTich) {
        this.quocTich = quocTich;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }
    
    public String[] getValues(){
        String [] values = new String[7];
        
        values[0] = maKH;
        values[1] = tenKH;
        values[2] = soCMND;
        values[3] = gioiTinh;
        values[4] = diaChi;
        values[5] = quocTich;
        values[6] = SDT;
        
        return values;
    }
}

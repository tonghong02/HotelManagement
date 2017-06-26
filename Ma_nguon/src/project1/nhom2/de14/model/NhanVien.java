/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1.nhom2.de14.model;

import java.util.GregorianCalendar;
import project1.nhom2.de14.util.CalendarAdapter;

/**
 *
 * @author LeThanhLoi
 */
public class NhanVien {
    private String maNV;
    private String tenNV;
    private GregorianCalendar ngaySinh;
    private String gioiTinh;
    private String soCMND;
    private String diaChi;
    private String SDT;
    private String chucVu;

    public NhanVien(String record){
        String [] s = new String[8];
        s = record.split("\t", 8);
        maNV = s[0];
        tenNV = s[1];
        ngaySinh = new GregorianCalendar();
        ngaySinh.setTime(CalendarAdapter.StringToDate(s[2]));
        gioiTinh = s[3];
        soCMND = s[4];
        diaChi = s[5];
        SDT = s[7];
        chucVu = s[6];
    }
    
    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public GregorianCalendar getNgaySinh() {
        return ngaySinh;
    }
    public String getNgaySinhString(){
        return CalendarAdapter.formatCalendar(ngaySinh);
    }
    
    public void setNgaySinh(GregorianCalendar ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSoCMND() {
        return soCMND;
    }

    public void setSoCMND(String soCMND) {
        this.soCMND = soCMND;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }
 
    public String[] getValues(){
        String [] values = new String[8];
        
        values[0] = maNV;
        values[1] = tenNV;
        values[2] = getNgaySinhString();
        values[3] = gioiTinh;
        values[4] = soCMND;
        values[5] = diaChi;
        values[7] = SDT;
        values[6] = chucVu;
        
        return values;
    }
}

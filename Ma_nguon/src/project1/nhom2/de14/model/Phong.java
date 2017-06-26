package project1.nhom2.de14.model;


public class Phong {
    
    private String maP;
    private String loaiPhong;
    private double mucGia;
    private String trangThai;

    public Phong(String record){
        String [] values = new String[4];
        values = record.split("\t",6);
        maP = values[0];
        loaiPhong = values[1];
        mucGia = Double.parseDouble(values[2]);
        trangThai = values[3];
    }
    
    public String getMaP() {
        return maP;
    }

    public void setMaP(String maP) {
        this.maP = maP;
    }

    public String getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public double getMucGia() {
        return mucGia;
    }

    public void setMucGia(double mucGia) {
        this.mucGia = mucGia;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    public String [] getValues(){
        String [] values = new String[4];
        
        values[0] = (maP!=null)?maP:"";
        values[1] = (loaiPhong!=null)?loaiPhong:"";
        values[2] = Double.toString(mucGia);
        values[3] = (trangThai!=null)?trangThai:"";
        
        return values;
    }
}
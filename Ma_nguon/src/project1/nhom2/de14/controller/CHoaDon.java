/*
 * Project 1-T6-14h45-20161
 * Nhom 2-De 14
 * Phan Ngoc Lan
 * Le Thanh Loi
 * Tong Thi Hong
 */
package project1.nhom2.de14.controller;

import java.io.IOException;
import java.util.Vector;
import project1.nhom2.de14.model.ChiTietDatPhong;
import project1.nhom2.de14.model.ChiTietDichVu;
import project1.nhom2.de14.model.DatPhong;
import project1.nhom2.de14.model.DichVu;
import project1.nhom2.de14.model.HoaDon;
import project1.nhom2.de14.model.KhachHang;
import project1.nhom2.de14.model.Phong;
import project1.nhom2.de14.util.ConnectDB;
import project1.nhom2.de14.util.PrintPDF;
import project1.nhom2.de14.util.WriteRead;

/**
 *
 * @author WILL
 */
public class CHoaDon {
	private Vector<HoaDon> DS;
	private ConnectDB conn;
	
	public CHoaDon(String user, String pass){
		DS = new Vector<HoaDon>(100);
		conn = new ConnectDB();
		conn.connect("khachsan", UserInfo.username, UserInfo.password);
		
		String[] key = null;

		Vector<String> vec = conn.select("hoadon", key);
		for(String s : vec){
			DS.add(new HoaDon(s));
		}
	}
	
	public int searchIndex(String maHD) {
		int i = 0;
		for (HoaDon hd : DS) {
			if (hd.getMaHD().equals(maHD)) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public boolean add(String record){
		HoaDon hd = new HoaDon(record);
		String [] values = hd.getValues();
		
		if(!conn.insert("hoadon", values))
			return false;
		
		DS.add(hd);
		return true;
	}
	
	public boolean delete(String key){
		if(!conn.delete("hoadon", "MaHD", key))
			return false;
		
		int pos = searchIndex(key);
		DS.remove(pos);
		return true;
	}
	
	public boolean edit(String record, String key){
		HoaDon hd = new HoaDon(record);
		String [] values = hd.getValues();
		
		values[0] = "MaHD=" + "'" + values[0] + "'";
		values[1] = "MaDatPhong=" + "'" + values[1] + "'";
		values[2] = "TGThanhToan=" +"STR_TO_DATE(" + "'"+ values[2]+"',"+"'%d-%m-%Y')";
		values[3] = "TienPhong=" + values[3];
		values[4] = "TienDichVu=" + values[4];
		
		String pkey = "MaHD=" + "'" + key + "'";
		if (!conn.update("hoadon", values, pkey)) {
			return false;
		}

		int pos = searchIndex(key);
		DS.remove(pos);
		DS.insertElementAt(hd, pos);
		return true;
	}
	
	public Vector<String> addFile(String fileName){
		Vector<String> vec = WriteRead.read(fileName);
		Vector<String> vec2 = new Vector<String>(10);
		
		for (String s : vec){
			HoaDon hd = new HoaDon(s);
			String [] values = hd.getValues();
			if (!conn.insert("hoadon", values)) {
				continue;
			}
			vec2.add(s);
			DS.add(hd);
		}
		
		return vec2;
	}
	
	public Vector<HoaDon> getDS(){
		return DS;
	}
	
	public Object [][] getTableData(){
		if (DS == null) {
			return new Object[0][6];
		}
		Object[][] data = new Object[DS.size()][6];
		
		int i = 0;
		for(HoaDon hd : DS){
			data[i] = hd.getValues();
			i++;
		}
		
		return data;
	}

	public void printReceipt(String filePath, int index, String method, double discount, double tax){
		HoaDon hd = DS.get(index);
		
		CDatPhong cdp = new CDatPhong("","");
		DatPhong dp = cdp.getDS().get(cdp.searchIndex(hd.getMaDatHang()));
		
		CKhachHang ckh = new CKhachHang("","");
		KhachHang kh = ckh.getDS().get(ckh.searchIndex(dp.getMaKH()));
		
		Vector<Phong> rooms = new Vector<Phong>(10);
		CPhong cp = new CPhong("","");
		CChiTietDatPhong cctdp = new CChiTietDatPhong("","");
		for(ChiTietDatPhong ctdp : cctdp.getDS()){
			if(ctdp.getMaDatPhong().equals(dp.getMaDatPhong())){
				rooms.add(cp.getDS().get(cp.searchIndex(ctdp.getMaPhong())));
			}
		}
		
		CDichVu cdv = new CDichVu("","");
		CChiTietDichVu cctdv = new CChiTietDichVu("","");
		Vector<String[]> service = new Vector<String[]>(5);
		for(Phong p : rooms){
			for(ChiTietDichVu ctdv : cctdv.getDS()){
				if(ctdv.getMaPhong().equals(p.getMaP())){
					DichVu dv = cdv.getDS().get(cdv.searchIndex(ctdv.getMaDV()));
					service.add(new String[]{dv.getMaDV(),dv.getTenDV(),Integer.toString(ctdv.getSoLuong()),Double.toString(dv.getDonGia()),Double.toString(ctdv.getThanhTien())});
				}
			}
		}
		
		try{
			PrintPDF.printReceipt(filePath, hd, kh, rooms, service, method, discount, tax);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}

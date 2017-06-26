/*
 * Project 1-T6-14h45-20161
 * Nhom 2-De 14
 * Phan Ngoc Lan
 * Le Thanh Loi
 * Tong Thi Hong
 */
package project1.nhom2.de14.controller;

import java.util.Vector;
import project1.nhom2.de14.model.ChiTietDatPhong;
import project1.nhom2.de14.util.ConnectDB;
import project1.nhom2.de14.util.WriteRead;

/**
 *
 * @author WILL
 */
public class CChiTietDatPhong {
	private Vector<ChiTietDatPhong> DS;
	private ConnectDB conn;
	
	public CChiTietDatPhong(String user, String pass){
		DS = new Vector<ChiTietDatPhong>(100);
		conn = new ConnectDB();
		conn.connect("khachsan", UserInfo.username, UserInfo.password);

		String[] key = null;

		Vector<String> vec = conn.select("chitietdatphong", key);
		for (String s : vec) {
			DS.add(new ChiTietDatPhong(s));
		}
	}
	
	public int searchIndex(String maDP, String maP) {
		int i = 0;
		for (ChiTietDatPhong dp : DS) {
			if (dp.getMaDatPhong().equals(maDP) && dp.getMaPhong().equals(maP)) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public boolean add(String record){
		ChiTietDatPhong dp = new ChiTietDatPhong(record);
		String [] values = dp.getValues();
		
		if(!conn.insert("chitietdatphong", values)){
			return false;
		}
		
		DS.add(dp);
		return true;
	}
	
	public boolean delete(String key1, String key2){
		if(!conn.delete("chitietdatphong", "MaDatPhong", key1, "MaP", key2))
			return false;
		
		int pos = searchIndex(key1,key2);
		DS.remove(pos);
		return true;
	}
	
	public boolean edit(String record, String key1, String key2){
		ChiTietDatPhong dp = new ChiTietDatPhong(record);
		String [] values = dp.getValues();
		
		values[0] = "MaDatPhong=" + "'" + values[0] + "'";
		values[1] = "MaP=" + "'" + values[1] + "'";
		
		String pkey = "MaDatPhong=" + "'" + key1 + "'" + " AND " + "MaP=" + "'" + key2 + "'";
		if (!conn.update("chitietdatphong", values, pkey)) {
			return false;
		}

		int pos = searchIndex(key1,key2);
		DS.remove(pos);
		DS.insertElementAt(dp, pos);
		return true;
	}
	
	public Vector<String> addFile(String fileName){
		Vector<String> vec = WriteRead.read(fileName);
		Vector<String> vec2 = new Vector<String>(10);
		for (String s : vec){
			ChiTietDatPhong dp = new ChiTietDatPhong(s);
			String [] values = dp.getValues();
			if (!conn.insert("chitietdatphong", values)) {
				continue;
			}
			DS.add(dp);
			vec2.add(s);
		}
		
		return vec2;
	}
	
	public Vector<ChiTietDatPhong> getDS(){
		return DS;
	}
	
	public Object [][] getTableData(){
		if (DS == null) {
			return new Object[0][2];
		}
		Object[][] data = new Object[DS.size()][2];
		
		int i = 0;
		for(ChiTietDatPhong dp : DS){
			data[i] = dp.getValues();
			i++;
		}
		
		return data;
	}
}

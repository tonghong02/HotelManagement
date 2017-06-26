/*
 * Project 1-T6-14h45-20161
 * Nhom 2-De 14
 * Phan Ngoc Lan
 * Le Thanh Loi
 * Tong Thi Hong
 */
package project1.nhom2.de14.controller;

import java.util.Vector;
import project1.nhom2.de14.model.DichVu;
import project1.nhom2.de14.util.ConnectDB;
import project1.nhom2.de14.util.WriteRead;

/**
 *
 * @author WILL
 */
public class CDichVu {
	private Vector<DichVu> DS;
	private ConnectDB conn;
	
	public CDichVu(String user, String pass){
		DS = new Vector<DichVu>(100);
		conn = new ConnectDB();
		conn.connect("khachsan", UserInfo.username, UserInfo.password);

		String[] key = null;

		Vector<String> vec = conn.select("dichvu", key);
		for (String s : vec) {
			DS.add(new DichVu(s));
		}
	}
	
	public int searchIndex(String maDV) {
		int i = 0;
		for (DichVu dp : DS) {
			if (dp.getMaDV().equals(maDV)) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public boolean add(String record){
		DichVu dv = new DichVu(record);
		String [] values = dv.getValues();
		if(!conn.insert("dichvu", values)){
			return false;
		}
		
		DS.add(dv);
		return true;
	}
	
	public boolean delete(String key){
		if(!conn.delete("dichvu", "MaDv", key))
			return false;
		
		int pos = searchIndex(key);
		DS.remove(pos);
		return true;
	}
	
	public boolean edit(String record, String key){
		DichVu dv = new DichVu(record);
		String [] values = dv.getValues();
		
		values[0] = "MaDv=" + "'" + values[0] + "'";
		values[1] = "TenDV=" + "'" + values[1] + "'";
		values[2] = "DonGia=" + values[2];
		values[3] = "MaNVphutrach=" + "'" + values[3] + "'";
		
		String pkey = "MaDv=" + "'" + key + "'";
		if (!conn.update("dichvu", values, pkey)) {
			return false;
		}

		int pos = searchIndex(key);
		DS.remove(pos);
		DS.insertElementAt(dv, pos);
		return true;
	}
	
	public Vector<String> addFile(String fileName){
		Vector<String> vec = WriteRead.read(fileName);
		Vector<String> vec2 = new Vector<String>(10);
		
		for (String s : vec){
			DichVu dv = new DichVu(s);
			String [] values = dv.getValues();
			if (!conn.insert("dichvu", values)) {
				continue;
			}
			vec2.add(s);
			DS.add(dv);
		}
		
		return vec2;
	}
	
	public Vector<DichVu> getDS(){
		return DS;
	}
	
	public Object [][] getTableData(){
		if (DS == null) {
			return new Object[0][4];
		}
		Object[][] data = new Object[DS.size()][4];
		
		int i = 0;
		for(DichVu dv : DS){
			data[i] = dv.getValues();
			i++;
		}
		
		return data;
	}
}

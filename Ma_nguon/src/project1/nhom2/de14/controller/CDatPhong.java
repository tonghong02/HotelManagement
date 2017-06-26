/*
 * Project 1-T6-14h45-20161
 * Nhom 2-De 14
 * Phan Ngoc Lan
 * Le Thanh Loi
 * Tong Thi Hong
 */
package project1.nhom2.de14.controller;

import java.util.Vector;
import project1.nhom2.de14.model.DatPhong;
import project1.nhom2.de14.util.ConnectDB;
import project1.nhom2.de14.util.WriteRead;

/**
 *
 * @author WILL
 */
public class CDatPhong {
    
	private Vector<DatPhong> DS;
	private ConnectDB conn;
	
	public CDatPhong(String user, String pass){
		DS = new Vector<DatPhong>(100);
		conn = new ConnectDB();
		conn.connect("khachsan", UserInfo.username, UserInfo.password);

		String[] key = null;

		Vector<String> vec = conn.select("datphong", key);
		for (String s : vec) {
			System.out.println(s);
			DS.add(new DatPhong(s));
		}
	}
	
	public int searchIndex(String maDP) {
		int i = 0;
		for (DatPhong dp : DS) {
			if (dp.getMaDatPhong().equals(maDP)) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public boolean add(String record){
		DatPhong dp = new DatPhong(record);
		String [] values = dp.getValues();
		
		if(!conn.insert("datphong", values)){
			return false;
		}
		
		DS.add(dp);
		return true;
	}
	
	public boolean delete(String key){
		if(!conn.delete("datphong", "MaDP", key))
			return false;
		
		int pos = searchIndex(key);
		DS.remove(pos);
		return true;
	}
	
	public boolean edit(String record, String key){
		DatPhong dp = new DatPhong(record);
		String [] values = dp.getValues();
		
		values[0] = "MaDP=" + "'" + values[0] + "'";
		values[1] = "MaKH=" + "'" + values[1] + "'";
		values[2] = "ThoiGianNhan=" +"STR_TO_DATE(" + "'"+ values[2]+"',"+"'%d-%m-%Y')";
		values[3] = "ThoiGianTra=" +"STR_TO_DATE(" + "'"+ values[3]+"',"+"'%d-%m-%Y')";
		values[4] = "SoPhongDat=" + values[4];
		values[5] = "TienDatCoc=" + values[5];
		values[6] = "MaNV=" + "'" + values[6] + "'";
		
		String pkey = "MaDP=" + "'" + key + "'";
		if (!conn.update("datphong", values, pkey)) {
			return false;
		}

		int pos = searchIndex(key);
		DS.remove(pos);
		DS.insertElementAt(dp, pos);
		return true;
	}
	
	public Vector<String> addFile(String fileName){
		Vector<String> vec = WriteRead.read(fileName);
		Vector<String> vec2 = new Vector<String>(10);
		for (String s : vec){
			DatPhong dp = new DatPhong(s);
			String [] values = dp.getValues();
			if (!conn.insert("datphong", values)) {
				continue;
			}
			vec2.add(s);
			DS.add(dp);
		}
		
		return vec2;
	}
	
	public Vector<DatPhong> getDS(){
		return DS;
	}
	
	public Object [][] getTableData(){
		if (DS == null) {
			return new Object[0][7];
		}
		Object[][] data = new Object[DS.size()][7];
		
		int i = 0;
		for(DatPhong dp : DS){
			data[i] = dp.getValues();
			i++;
		}
		
		return data;
	}
}

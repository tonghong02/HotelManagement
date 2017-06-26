/*
 * Project 1-T6-14h45-20161
 * Nhom 2-De 14
 * Phan Ngoc Lan
 * Le Thanh Loi
 * Tong Thi Hong
 */
package project1.nhom2.de14.controller;

import java.util.Vector;
import project1.nhom2.de14.model.Phong;
import project1.nhom2.de14.util.*;

/**
 *
 * @author WILL
 */
public class CPhong {

	private Vector<Phong> DS;
	private ConnectDB conn;

	public CPhong(String user, String pass) {
		conn = new ConnectDB();
		conn.connect("khachsan", UserInfo.username, UserInfo.password);
		String[] key = null;
		DS = new Vector<Phong>(100);

		Vector<String> vec = conn.select("phong", key);
		for (String s : vec) {
			DS.add(new Phong(s));
		}
	}

	public boolean edit(String record, String key) {
		String[] values = new String[4];
		values = record.split("\t", 4);
		values[0] = "MP=" + "'" + values[0] + "'";
		values[1] = "LoaiPhong=" + "'" + values[1] + "'";
		values[2] = "MucGia=" + "'" + values[2] + "'";
		values[3] = "TrangThai=" + "'" + values[3] + "'";
		String pkey = "MP=" + "'" + key + "'";
		if (!conn.update("phong", values, pkey)) {
			return false;
		}

		int pos = searchIndex(key);
		DS.remove(pos);
		DS.insertElementAt(new Phong(record), pos);
		return true;
	}

	public boolean delete(String key) {
		if (!conn.delete("phong", "MP", key)) {
			return false;
		}

		int pos = searchIndex(key);
		DS.remove(pos);
		return true;
	}

	public boolean add(String record) {
		Phong phong = new Phong(record);
		String[] values = new String[4];
		values = record.split("\t", 4);

		if (!conn.insert("phong", values)) {
			return false;
		}

		DS.add(phong);
		return true;
	}

	public Vector<String> addFile(String fileName) {
		Vector<String> vec = WriteRead.read(fileName);
		Vector<String> vec2 = new Vector<String>(10);
		
		for (String s : vec) {
			Phong p = new Phong(s);
			String[] values = p.getValues();
			if (!conn.insert("phong", values)) {
				continue;
			}
			vec2.add(s);
			DS.add(p);
		}

		return vec2;
	}

	public void writeFile(String fileName) {
		Vector<String> vec = conn.select("phong", null);
		WriteRead.write(vec, fileName, fileName);
	}

	public int searchIndex(String maP) {
		int i = 0;
		for (Phong p : DS) {
			if (p.getMaP().equals(maP)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	public Vector<Phong> getDS() {
		return DS;
	}

	public Object[][] getTableData() {
		if (DS == null) {
			return new Object[0][4];
		}
		Object[][] data = new Object[DS.size()][4];

		int i = 0;
		for (Phong p : DS) {
			data[i] = p.getValues();
			i++;
		}

		return data;
	}

	public String[] getIds() {
		String[] ids = new String[DS.size()];
		for (int i = 0; i < DS.size(); i++) {
			ids[i] = DS.get(i).getMaP();
		}
		return ids;
	}
}

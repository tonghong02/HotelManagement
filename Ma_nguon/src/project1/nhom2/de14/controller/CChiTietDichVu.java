/*
 * Project 1-T6-14h45-20161
 * Nhom 2-De 14
 * Phan Ngoc Lan
 * Le Thanh Loi
 * Tong Thi Hong
 */
package project1.nhom2.de14.controller;

import java.util.Vector;
import project1.nhom2.de14.model.ChiTietDichVu;
import project1.nhom2.de14.util.ConnectDB;
import project1.nhom2.de14.util.WriteRead;

/**
 *
 * @author WILL
 */
public class CChiTietDichVu {

	private Vector<ChiTietDichVu> DS;
	private ConnectDB conn;

	public CChiTietDichVu(String user, String pass) {
		DS = new Vector<ChiTietDichVu>(100);
		conn = new ConnectDB();
		conn.connect("khachsan", UserInfo.username, UserInfo.password);
		String[] key = null;

		Vector<String> vec = conn.select("chitietdichvu", key);
		for (String s : vec) {
			DS.add(new ChiTietDichVu(s));
		}
	}

	public int searchIndex(String maDP, String maDV) {
		int i = 0;
		for (ChiTietDichVu dp : DS) {
			if (dp.getMaPhong().equals(maDP) && dp.getMaDV().equals(maDV)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	public boolean add(String record) {
		ChiTietDichVu dp = new ChiTietDichVu(record);
		String[] values = dp.getValues();

		if (!conn.insert("chitietdichvu", values)) {
			return false;
		}

		DS.add(dp);
		return true;
	}

	public boolean delete(String key1, String key2) {
		if (!conn.delete("chitietdichvu", "MaP", key1, "MaDV", key2)) {
			return false;
		}

		int pos = searchIndex(key1, key2);
		DS.remove(pos);
		return true;
	}

	public boolean edit(String record, String key1, String key2) {
		ChiTietDichVu dp = new ChiTietDichVu(record);
		String[] values = dp.getValues();

		values[0] = "MaP=" + "'" + values[0] + "'";
		values[1] = "MaDV=" + "'" + values[1] + "'";
		values[2] = "SoLuong=" + values[2];
		values[3] = "ThanhTien=" + values[3];

		String pkey = "MaP=" + "'" + key1 + "'" + " AND " + "MaDV=" + "'" + key2 + "'";
		if (!conn.update("chitietdichvu", values, pkey)) {
			return false;
		}

		int pos = searchIndex(key1, key2);
		DS.remove(pos);
		DS.insertElementAt(dp, pos);
		return true;
	}

	public Vector<String> addFile(String fileName) {
		Vector<String> vec = WriteRead.read(fileName);
		Vector<String> vec2 = new Vector<String>(10);
		for (String s : vec) {
			ChiTietDichVu dp = new ChiTietDichVu(s);
			String[] values = dp.getValues();
			if (!conn.insert("chitietdichvu", values)) {
				continue;
			}
			vec2.add(s);
			DS.add(dp);
		}

		return vec2;
	}

	public Vector<ChiTietDichVu> getDS() {
		return DS;
	}

	public Object[][] getTableData() {
		if (DS == null) {
			return new Object[0][4];
		}
		Object[][] data = new Object[DS.size()][4];

		int i = 0;
		for (ChiTietDichVu dp : DS) {
			data[i] = dp.getValues();
			i++;
		}

		return data;
	}
}

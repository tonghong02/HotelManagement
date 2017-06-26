/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1.nhom2.de14.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import project1.nhom2.de14.controller.UserInfo;

/**
 *
 * @author LeThanhLoi
 */
public class ConnectDB {

	private static final String className = "com.mysql.jdbc.Driver";
	private Connection connection;

	// kêt nối  database
	public boolean connect(String nameDatabase, String user, String pass) {
		try {
			Class.forName(className);
			String url = "jdbc:mysql://localhost:3306/" + nameDatabase;

			try {
				connection = DriverManager.getConnection(url, user, pass);
				System.out.println("Connect Success !!!");
				return true;
			} catch (SQLException ex) {
				String mssg = ex.getMessage();
				if (mssg.contains("Unknown database")) {
					createDatabase(user, pass);
					return connect(nameDatabase, user, pass);
				}
				return false;
			}
		} catch (ClassNotFoundException ex) {
			System.out.println("Class not found ");
			return false;
		}
	}

	public void createDatabase(String user, String pass) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", user, pass);
			Statement stmt = conn.createStatement();

			String q = "CREATE DATABASE IF NOT EXISTS `khachsan` /*!40100 DEFAULT CHARACTER SET utf8 */;";
			stmt.executeUpdate(q);

			conn.close();
			stmt.close();

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/khachsan", user, pass);
			stmt = conn.createStatement();
			q = "CREATE TABLE `phong` (\n"
					+ "  `MP` varchar(16) NOT NULL,\n"
					+ "  `LoaiPhong` varchar(64) DEFAULT NULL,\n"
					+ "  `MucGia` double DEFAULT NULL,\n"
					+ "  `TrangThai` varchar(64) DEFAULT NULL,\n"
					+ "  PRIMARY KEY (`MP`)\n"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
			stmt.execute(q);

			q = "CREATE TABLE `nhanvien` (\n"
					+ "  `MaNV` varchar(16) NOT NULL,\n"
					+ "  `TenNV` varchar(64) DEFAULT NULL,\n"
					+ "  `NgaySinh` date DEFAULT NULL,\n"
					+ "  `GioiTinh` varchar(8) DEFAULT NULL,\n"
					+ "  `SoCMND` varchar(32) DEFAULT NULL,\n"
					+ "  `DiaChi` varchar(128) DEFAULT NULL,\n"
					+ "  `ChucVu` varchar(64) DEFAULT NULL,\n"
					+ "  `SDT` varchar(16) DEFAULT NULL,\n"
					+ "  PRIMARY KEY (`MaNV`)\n"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
			stmt.execute(q);

			q = "CREATE TABLE `khachhang` (\n"
					+ "  `MaKH` varchar(16) NOT NULL,\n"
					+ "  `tenKH` varchar(64) DEFAULT NULL,\n"
					+ "  `soCMND` varchar(32) DEFAULT NULL,\n"
					+ "  `GioiTinh` varchar(8) DEFAULT NULL,\n"
					+ "  `DiaChi` varchar(128) DEFAULT NULL,\n"
					+ "  `QuocTich` varchar(32) DEFAULT NULL,\n"
					+ "  `SDT` varchar(32) DEFAULT NULL,\n"
					+ "  PRIMARY KEY (`MaKH`)\n"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
			stmt.execute(q);

			q = "CREATE TABLE `datphong` (\n"
					+ "  `MaDP` varchar(16) NOT NULL,\n"
					+ "  `MaKH` varchar(16) DEFAULT NULL,\n"
					+ "  `ThoiGianNhan` date DEFAULT NULL,\n"
					+ "  `ThoiGianTra` date DEFAULT NULL,\n"
					+ "  `SoPhongDat` int(11) DEFAULT NULL,\n"
					+ "  `TienDatCoc` double DEFAULT NULL,\n"
					+ "  `MaNV` varchar(16) DEFAULT NULL,\n"
					+ "  PRIMARY KEY (`MaDP`),\n"
					+ "  KEY `MaKH_idx` (`MaKH`),\n"
					+ "  KEY `fk2_idx` (`MaNV`),\n"
					+ "  CONSTRAINT `fk1` FOREIGN KEY (`MaKH`) REFERENCES `khachhang` (`MaKH`) ON UPDATE CASCADE,\n"
					+ "  CONSTRAINT `fk2` FOREIGN KEY (`MaNV`) REFERENCES `nhanvien` (`MaNV`) ON UPDATE CASCADE\n"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
			stmt.execute(q);

			q = "CREATE TABLE `chitietdatphong` (\n"
					+ "  `MaDatPhong` varchar(16) NOT NULL,\n"
					+ "  `MaP` varchar(16) NOT NULL,\n"
					+ "  PRIMARY KEY (`MaDatPhong`,`MaP`),\n"
					+ "  KEY `fk8_idx` (`MaP`),\n"
					+ "  CONSTRAINT `fk7` FOREIGN KEY (`MaDatPhong`) REFERENCES `datphong` (`MaDP`) ON DELETE CASCADE ON UPDATE CASCADE,\n"
					+ "  CONSTRAINT `fk8` FOREIGN KEY (`MaP`) REFERENCES `phong` (`MP`) ON DELETE CASCADE ON UPDATE CASCADE\n"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
			stmt.execute(q);

			q = "CREATE TABLE `dichvu` (\n"
					+ "  `MaDv` varchar(16) NOT NULL,\n"
					+ "  `TenDV` varchar(128) DEFAULT NULL,\n"
					+ "  `DonGia` double DEFAULT NULL,\n"
					+ "  `MaNVphutrach` varchar(16) DEFAULT NULL,\n"
					+ "  PRIMARY KEY (`MaDv`),\n"
					+ "  KEY `fk4_idx` (`MaNVphutrach`),\n"
					+ "  CONSTRAINT `fk4` FOREIGN KEY (`MaNVphutrach`) REFERENCES `nhanvien` (`MaNV`) ON UPDATE CASCADE\n"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
			stmt.execute(q);

			q = "CREATE TABLE `chitietdichvu` (\n"
					+ "  `MaP` varchar(16) NOT NULL,\n"
					+ "  `MaDV` varchar(16) NOT NULL,\n"
					+ "  `SoLuong` int(11) DEFAULT NULL,\n"
					+ "  `ThanhTien` double DEFAULT NULL,\n"
					+ "  PRIMARY KEY (`MaP`,`MaDV`),\n"
					+ "  KEY `fk5_idx` (`MaDV`),\n"
					+ "  CONSTRAINT `fk5` FOREIGN KEY (`MaDV`) REFERENCES `dichvu` (`MaDv`) ON UPDATE CASCADE,\n"
					+ "  CONSTRAINT `fk6` FOREIGN KEY (`MaP`) REFERENCES `phong` (`MP`) ON DELETE NO ACTION ON UPDATE CASCADE\n"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
			stmt.execute(q);

			q = "CREATE TABLE `hoadon` (\n"
					+ "  `MaHD` varchar(16) NOT NULL,\n"
					+ "  `MaDatPhong` varchar(16) DEFAULT NULL,\n"
					+ "  `TGThanhToan` date DEFAULT NULL,\n"
					+ "  `TienPhong` double DEFAULT NULL,\n"
					+ "  `TienDichVu` double DEFAULT NULL,\n"
					+ "  PRIMARY KEY (`MaHD`),\n"
					+ "  KEY `fk1_idx` (`MaDatPhong`),\n"
					+ "  CONSTRAINT `fk3` FOREIGN KEY (`MaDatPhong`) REFERENCES `datphong` (`MaDP`) ON UPDATE CASCADE\n"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
			stmt.execute(q);

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// lấy dữ liệu theo cột trong bảng
	public Vector<String> select(String table, String key[]) {
		ResultSet rs = null;
		Vector<String> vector = new Vector<String>(100);
		String sqlcommand = "select * " + " from " + table;
		PreparedStatement pst = null;
		if (key == null) {
			sqlcommand = "select * " + " from " + table;
		} else {

			for (int i = 0; i < key.length; i++) {
				sqlcommand = sqlcommand + key;
				if (i < key.length - 1) {
					sqlcommand += " AND ";
				}
			}
			sqlcommand = sqlcommand + " ;";
		}
		try {
			pst = connection.prepareStatement(sqlcommand);
			rs = pst.executeQuery();
			String str = new String();
			switch (table) {
				case "phong":
					while (rs.next()) {

						vector.add(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4));
					}
				case "nhanvien":
					while (rs.next()) {

						vector.add(rs.getString(1) + "\t" + rs.getString(2) + "\t" + CalendarAdapter.SQLDateToString(rs.getDate(3)) + "\t" + rs.getString(4)
								+ "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7) + "\t" + rs.getString(8));
					}
				case "khachhang":
					while (rs.next()) {

						vector.add(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t"
								+ rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7));
					}
				case "hoadon":
					while (rs.next()) {

						vector.add(rs.getString(1) + "\t" + rs.getString(2) + "\t" + CalendarAdapter.SQLDateToString(rs.getDate(3)) + "\t" + rs.getString(4) + "\t"
								+ rs.getString(5));
					}
				case "dichvu":
					while (rs.next()) {

						vector.add(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getDouble(3) + "\t" + rs.getString(4));
					}
				case "chitietdichvu":
					while (rs.next()) {

						vector.add(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4));
					}
				case "datphong":
					while (rs.next()) {

						vector.add(rs.getString(1) + "\t" + rs.getString(2) + "\t" + CalendarAdapter.SQLDateToString(rs.getDate(3)) + "\t" + CalendarAdapter.SQLDateToString(rs.getDate(4)) + "\t"
								+ rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7));
					}
				case "chitietdatphong":
					while (rs.next()) {

						vector.add(rs.getString(1) + "\t" + rs.getString(2));
					}
			}
		} catch (SQLException ex) {
			System.out.println("Select error !" + ex.toString());
		}

		return vector;
	}

	/// chon ra gia tri vector co 2 cot by LE THANH LOI 
	public Vector<String> select2colLOI(String sql) {
		ResultSet rs = null;
		Vector<String> vector = new Vector<String>(100);
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				vector.add(rs.getString(1) + "\t" + rs.getString(2));
			}

		} catch (SQLException ex) {
			Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return vector;

	}
/// chon ra gia tri co 1 cot by LE THANH LOI

	public String select1colLOI(String sql) {
		ResultSet rs = null;
		String str = new String();
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				str = (rs.getString(1));
			}

		} catch (SQLException ex) {
			Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return str;

	}

	/// Tim kiem thong tin cac bang by LE THANH LOI
	public Vector<String> selectSearch(String table, String col, String key) {
		ResultSet rs = null;
		Vector<String> vector = new Vector<String>(100);
		String sqlcommand = "select * from ";
		PreparedStatement pst = null;

		sqlcommand = "select * from " + table + " where " + col + " like '" + key + "%'" + " ;";
		System.out.println(sqlcommand);

		try {
			pst = connection.prepareStatement(sqlcommand);
			rs = pst.executeQuery();
			String str = new String();
			switch (table) {
				case "phong":
					while (rs.next()) {

						vector.add(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4));
					}
				case "nhanvien":
					while (rs.next()) {

						vector.add(rs.getString(1) + "\t" + rs.getString(2) + "\t" + CalendarAdapter.SQLDateToString(rs.getDate(3)) + "\t" + rs.getString(4)
								+ "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7) + "\t" + rs.getString(8));
					}
				case "khachhang":
					while (rs.next()) {

						vector.add(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t"
								+ rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7));
					}
				case "hoadon":
					while (rs.next()) {

						vector.add(rs.getString(1) + "\t" + rs.getString(2) + "\t" + CalendarAdapter.SQLDateToString(rs.getDate(3)) + "\t" + rs.getString(4) + "\t"
								+ rs.getString(5));
					}
				case "dichvu":
					while (rs.next()) {

						vector.add(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getDouble(3) + "\t" + rs.getString(4));
					}
				case "chitietdichvu":
					while (rs.next()) {

						vector.add(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4));
					}
				case "datphong":
					while (rs.next()) {

						vector.add(rs.getString(1) + "\t" + rs.getString(2) + "\t" + CalendarAdapter.SQLDateToString(rs.getDate(3)) + "\t" + CalendarAdapter.SQLDateToString(rs.getDate(4)) + "\t"
								+ rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7));
					}
				case "chitietdatphong":
					while (rs.next()) {

						vector.add(rs.getString(1) + "\t" + rs.getString(2));
					}
			}
		} catch (SQLException ex) {
			System.out.println("Select error !" + ex.toString());
		}

		return vector;
	}

	// In ra man hinh vector
	public void print(Vector<String> vector) {
		for (String s : vector) {
			System.out.println(s);
		}
	}

	//  insert values vao trong table
	public boolean insert(String table, String[] values) {

		try {

			String sqlcommand = "insert into " + table;
			PreparedStatement pst = null;
			System.out.println(sqlcommand);

			if (table.equals("phong")) {
				sqlcommand = sqlcommand + " values(" + "'" + values[0] + "','" + values[1] + "','" + values[2] + "','" + values[3] + "');";
			} else if (table.equals("nhanvien")) {
				if (values[2].equals("")) {
					values[2] = "null";
				} else {
					values[2] = "STR_TO_DATE(" + "'" + values[2] + "'," + "'%d-%m-%Y')";
				}
				sqlcommand = sqlcommand + " values(" + "'" + values[0] + "','" + values[1] + "'," + values[2] + ",'" + values[3] + "','" + values[4] + "','" + values[5] + "','" + values[6] + "','" + values[7] + "');";
			} else if (table.equals("khachhang")) {
				sqlcommand = sqlcommand + " values (" + "'" + values[0] + "','" + values[1] + "','" + values[2] + "','" + values[3] + "','" + values[4] + "','" + values[5] + "','" + values[6] + "');";
			} else if (table.equals("hoadon")) {
				if (values[2].equals("")) {
					values[2] = "null";
				} else {
					values[2] = "STR_TO_DATE(" + "'" + values[2] + "'," + "'%d-%m-%Y')";
				}
				sqlcommand = sqlcommand + " values (" + "'" + values[0] + "','" + values[1] + "'," + values[2] + "," + values[3] + "," + values[4] + ");";
			} else if (table.equals("dichvu")) {
				sqlcommand = sqlcommand + " values (" + "'" + values[0] + "','" + values[1] + "','" + values[2] + "','" + values[3] + "');";
			} else if (table.equals("datphong")) {
				if (values[2].equals("")) {
					values[2] = "null";
				} else {
					values[2] = "STR_TO_DATE(" + "'" + values[2] + "'," + "'%d-%m-%Y')";
				}
				if (values[3].equals("")) {
					values[3] = "null";
				} else {
					values[3] = "STR_TO_DATE(" + "'" + values[3] + "'," + "'%d-%m-%Y')";
				}
				sqlcommand = sqlcommand + " values (" + "'" + values[0] + "','" + values[1] + "'," + values[2] + "," + values[3] + ",'" + values[4] + "','"
						+ values[5] + "','" + values[6] + "');";
			} else if (table.equals("chitietdichvu")) {
				sqlcommand = sqlcommand + " values (" + "'" + values[0] + "','" + values[1] + "','" + values[2] + "','" + values[3] + "');";
			} else if (table.equals("chitietdatphong")) {
				sqlcommand = sqlcommand + " values (" + "'" + values[0] + "','" + values[1] + "');";
			}
			System.out.println(sqlcommand);
			pst = connection.prepareStatement(sqlcommand);
			pst.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
		return true;
	}

	//xoa column co gia tri la key trong bang table
	public boolean delete(String table, String column, String key) {
		try {

			String sqlcommand = " delete from " + table + " where " + column + " ='" + key + "';";
			PreparedStatement pst = null;
			System.out.println(sqlcommand);

			pst = connection.prepareStatement(sqlcommand);
			pst.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
		return true;
	}

	public boolean delete(String table, String column1, String key1, String column2, String key2) {
		try {

			String sqlcommand = " delete from " + table + " where " + column1 + " ='" + key1 + "' AND " + column2 + " ='" + key2 + "';";
			PreparedStatement pst = null;
			System.out.println(sqlcommand);

			pst = connection.prepareStatement(sqlcommand);
			pst.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
		return true;
	}

	//Update trong bang table nhung record co column= key
	public boolean update(String table, String[] values, String key) {
		try {

			String sqlcommand = " update " + table + " set ";
			PreparedStatement pst = null;

			for (int i = 0; i < values.length; i++) {
				sqlcommand += values[i];
				if (i < values.length - 1) {
					sqlcommand += ",";
				}
			}
			sqlcommand += " where " + key + ";";
			System.out.println(sqlcommand);

			pst = connection.prepareStatement(sqlcommand);
			pst.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
		return true;
	}

	public static boolean changePass(String user, String pass, String new_pass) {
		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName(className);
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/khachsan", user, pass);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();

			String q = "SET PASSWORD FOR " + "'" + user + "'" + " = " + "'" + new_pass + "';";
			stmt.executeUpdate(q);
			stmt.close();
			conn.commit();
			conn.close();
		} catch (SQLException | ClassNotFoundException e) {
			return false;
		}
		return true;
	}

	public static void deleteUser(String user) {
		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName(className);
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/khachsan", UserInfo.username, UserInfo.password);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();

			String q = "DROP USER IF EXISTS " + "'" + user + "';";
			stmt.executeUpdate(q);
			stmt.close();
			conn.commit();
			conn.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void createUser(String user, String pass, boolean admin) {
		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName(className);
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/khachsan", UserInfo.username, UserInfo.password);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();

			String q = "CREATE USER " + "'" + user + "'" + " IDENTIFIED BY " + "'" + pass + "';";
			stmt.executeUpdate(q);
			if (admin) {
				grantAdmin(stmt, user, pass);
			} else {
				grantStd(stmt, user, pass);
			}
			stmt.close();
			conn.commit();
			conn.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static boolean changePrivilige(String user, boolean admin) {
		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName(className);
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/khachsan", UserInfo.username, UserInfo.password);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();

			if (admin) {
				stmt.execute("GRANT ALL ON khachsan.nhanvien TO " + "'" + user + "'");
			} else {
				stmt.execute("REVOKE ALL ON khachsan.nhanvien FROM " + "'" + user + "'");
			}
			stmt.close();
			conn.commit();
			conn.close();
			return true;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void grantAdmin(Statement stmt, String user, String pass) throws SQLException {
		String q = "GRANT ALL ON khachsan.* TO " + "'" + user + "'";
		stmt.execute(q);
	}

	public static void grantStd(Statement stmt, String user, String pass) throws SQLException {
		String q = "GRANT ALL ON khachsan.phong TO " + "'" + user + "'";
		stmt.execute(q);
		q = "GRANT ALL ON khachsan.khachhang TO " + "'" + user + "'";
		stmt.execute(q);
		q = "GRANT ALL ON khachsan.dichvu TO " + "'" + user + "'";
		stmt.execute(q);
		q = "GRANT ALL ON khachsan.chitietdichvu TO " + "'" + user + "'";
		stmt.execute(q);
		q = "GRANT ALL ON khachsan.hoadon TO " + "'" + user + "'";
		stmt.execute(q);
		q = "GRANT ALL ON khachsan.datphong TO " + "'" + user + "'";
		stmt.execute(q);
		q = "GRANT ALL ON khachsan.chitietdatphong TO " + "'" + user + "'";
		stmt.execute(q);
	}

	public void close() {
		try {
			connection.close();
		} catch (SQLException ex) {
			Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

//        public Vector<String> TimKiem(String table, String key){
//            
//        }
	public static void main(String[] args) {
		ConnectDB con = new ConnectDB();
		con.connect("quanlykhachsan", "ngoclan", "serenity");
	}
}

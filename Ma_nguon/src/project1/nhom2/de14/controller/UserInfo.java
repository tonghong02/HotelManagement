/*
 * Project 1-T6-14h45-20161
 * Nhom 2-De 14
 * Phan Ngoc Lan
 * Le Thanh Loi
 * Tong Thi Hong
 */
package project1.nhom2.de14.controller;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 *
 * @author WILL
 */
public class UserInfo{
	public static String username;
	public static String password;
	public static String hotel_name = "";
	public static String phone_no = "";
	public static String fax_no = "";
	public static String address = "";
	public static String tax_code = "";
	private static boolean admin = false;
	
	public static void checkAdmin(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/khachsan", username, password);
			PreparedStatement pst = connection.prepareStatement("select * from nhanvien");
			pst.execute();
			pst.close();
			connection.close();
			admin = true;
		}catch(Exception e){
			
		}
	}
	
	public static boolean isAdmin(){
		return admin;
	}
	
	public static void reset(){
		username = password = "";
		admin = false;
	}
	
	public static void loadInfo(){
		File info = new File(".info");
		if(!info.exists()){
			return;
		}
		
		try{
			BufferedReader inp = new BufferedReader(new InputStreamReader(new FileInputStream(info),"UTF-8"));
			hotel_name = inp.readLine();
			phone_no = inp.readLine();
			fax_no = inp.readLine();
			address = inp.readLine();
			tax_code = inp.readLine();
			inp.close();
		}catch(IOException e){
			hotel_name = "";
			phone_no = "";
			fax_no ="";
			address ="";
			tax_code ="";
		}
	}
	
	public static boolean saveInfo(String hotel_name, String phone_no, String fax_no, String address, String tax_code){
		try{
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(".info"), "UTF-8"));
			
			out.write(hotel_name); out.newLine();
			UserInfo.hotel_name = hotel_name;
			out.write(phone_no); out.newLine();
			UserInfo.phone_no = phone_no;
			out.write(fax_no); out.newLine();
			UserInfo.fax_no = fax_no;
			out.write(address); out.newLine();
			UserInfo.address = address;
			out.write(tax_code); out.newLine();
			UserInfo.tax_code = tax_code;
			
			out.close();
			return true;
		}catch(IOException e){
			return false;
		}
	}
}

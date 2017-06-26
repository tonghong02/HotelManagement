/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1.nhom2.de14.view;

import java.awt.*;
import java.io.File;
import java.util.Date;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import project1.nhom2.de14.controller.UserInfo;
import project1.nhom2.de14.util.CalendarAdapter;
import project1.nhom2.de14.util.ConnectDB;
import project1.nhom2.de14.util.PrintPDF;

public class PanelBaoCao extends javax.swing.JPanel {

	/**
	 * Creates new form PanelBaoCao
	 */
	ConnectDB con = new ConnectDB();

	String[][] GioiTinh;
	String[][] GioiTinh1;
	String[][] Phongdat;
	String[][] Phongtrong;
	String[][] DichVu;
	String tongTienDV;
	String tongTienPhong;
	String tongDoanhThu;
	String[] dateS = new String[2];
	String[] dataLable = new String[10];

	public void loadDataLable() {
// nhan vien
		String sql1 = "select count(*)\n"
				+ "from nhanvien";
		String s1 = con.select1colLOI(sql1);
		lbTongSoNhanVien.setText(s1);
		// khach hang
		String sql2 = "select count(*)\n"
				+ "from khachhang";

		String s2 = con.select1colLOI(sql2);

		lbTongSoKhachHang.setText(s2);
		//dich vu
		String tongLoaiDichVu = con.select1colLOI("select count(MaDv) from dichvu;");
		lbTongSoDichVu.setText(tongLoaiDichVu);
		String tongDVDuocSuDung = con.select1colLOI("select count(distinct MaDV) from chitietdichvu ");
		lbTongSoDichVuduocsuDung.setText(tongDVDuocSuDung);

		// phong
		String sql3 = "SELECT count(*)\n"
				+ "from phong";
		String sql4 = "SELECT count(*)\n"
				+ "from phong\n"
				+ "where phong.TrangThai not like 'Trống'";
		String sql5 = "SELECT count(*)\n"
				+ "from phong\n"
				+ "where phong.TrangThai='Trống'";
		String s3 = con.select1colLOI(sql3);
		String s4 = con.select1colLOI(sql4);
		String s5 = con.select1colLOI(sql5);
		lbTongSoPhong.setText(s3);
		lbTongSoPhong1.setText(s4);
		lbTongSoPhong2.setText(s5);
		// doanh thu

	}

	public void loaddataTable() {
		// Nhan vien
		DefaultTableModel tableModel1 = new DefaultTableModel();
		Vector<String> colsName1 = new Vector<String>(10);
		colsName1.add("Giới tính ");
		colsName1.add("Số lượng");
		Vector<String> vector1 = new Vector<String>();

		tableModel1.setColumnIdentifiers(colsName1);

		String sql1 = "SELECT nhanvien.GioiTinh, count(nhanvien.GioiTinh)\n"
				+ "from nhanvien\n"
				+ "group by nhanvien.GioiTinh";
		vector1 = con.select2colLOI(sql1);
		GioiTinh = new String[vector1.size()][];
		for (int i = 0; i < vector1.size(); i++) {
			GioiTinh[i] = vector1.get(i).split("\t");

		}
		for (int i = 0; i < vector1.size(); i++) {
			Vector<String> vec1 = new Vector<>();
			String[][] str1 = new String[vector1.size()][];
			str1[i] = vector1.get(i).split("\t");
			for (int j = 0; j < str1[i].length; j++) {
				vec1.add(str1[i][j]);
			}
			tableModel1.addRow(vec1);
		}
		tbNhanVien.setModel(tableModel1);
		// khach hang
		DefaultTableModel tableModel2 = new DefaultTableModel();
		Vector<String> colsName2 = new Vector<String>(10);
		colsName2.add("Giới tính ");
		colsName2.add("Số lượng");
		Vector<String> vector2 = new Vector<String>();
		tableModel2.setColumnIdentifiers(colsName2);

		String sql2 = "SELECT khachhang.GioiTinh, count(khachhang.GioiTinh)\n"
				+ "from khachhang\n"
				+ "group by khachhang.GioiTinh\n"
				+ ";";

		vector2 = con.select2colLOI(sql2);
		GioiTinh1 = new String[vector2.size()][];
		for (int i = 0; i < vector2.size(); i++) {
			GioiTinh1[i] = vector2.get(i).split("\t");

		}
		for (int i = 0; i < vector2.size(); i++) {
			Vector<String> vec1 = new Vector<>();
			String[][] str1 = new String[vector2.size()][];
			str1[i] = vector2.get(i).split("\t");
			for (int j = 0; j < str1[i].length; j++) {
				vec1.add(str1[i][j]);
			}
			tableModel2.addRow(vec1);
		}
		tbKhachHang.setModel(tableModel2);
// dich vu
		DefaultTableModel tableModel3 = new DefaultTableModel();
		Vector<String> colsName3 = new Vector<String>(10);
		colsName3.add("Loại Phòng ");
		colsName3.add("Số lượng");
		Vector<String> vector3 = new Vector<String>();

		tableModel3.setColumnIdentifiers(colsName3);
		vector3 = con.select2colLOI("select dv.TenDV, count(ctdv.MaDV) from dichvu dv, chitietdichvu ctdv where dv.MaDV = ctdv.MaDv group by dv.TenDV");

		DichVu = new String[vector3.size()][];
		for (int i = 0; i < vector3.size(); i++) {
			DichVu[i] = vector3.get(i).split("\t");

		}
		for (int i = 0; i < vector3.size(); i++) {
			Vector<String> vec1 = new Vector<>();
			String[][] str1 = new String[vector3.size()][];
			str1[i] = vector3.get(i).split("\t");
			for (int j = 0; j < str1[i].length; j++) {
				vec1.add(str1[i][j]);
			}
			tableModel3.addRow(vec1);
		}
		tbLuongSuDung.setModel(tableModel3);

// phong
		DefaultTableModel tableModel4 = new DefaultTableModel();
		DefaultTableModel tableModel5 = new DefaultTableModel();
		Vector<String> colsName4 = new Vector<String>(10);
		colsName4.add("Loại Phòng ");
		colsName4.add("Số lượng");
		Vector<String> vector4 = new Vector<String>();
		Vector<String> vector5 = new Vector<String>();

		tableModel4.setColumnIdentifiers(colsName4);
		tableModel5.setColumnIdentifiers(colsName4);

		String sql4 = "SELECT phong.LoaiPhong , count(phong.LoaiPhong) as Soluong\n"
				+ "from phong\n"
				+ "where phong.TrangThai not like 'Trống'\n"
				+ "group by phong.LoaiPhong";
		String sql5 = "SELECT phong.LoaiPhong , count(phong.LoaiPhong) as Soluong\n"
				+ "from phong\n"
				+ "where phong.TrangThai='Trống'\n"
				+ "group by phong.LoaiPhong";
		vector4 = con.select2colLOI(sql4);
		Phongdat = new String[vector4.size()][];
		for (int i = 0; i < vector4.size(); i++) {
			Phongdat[i] = vector4.get(i).split("\t");

		}

		vector5 = con.select2colLOI(sql5);
		Phongtrong = new String[vector5.size()][];
		for (int i = 0; i < vector5.size(); i++) {
			Phongtrong[i] = vector5.get(i).split("\t");

		}

		for (int i = 0; i < vector4.size(); i++) {
			Vector<String> vec1 = new Vector<>();
			String[][] str1 = new String[vector4.size()][];
			str1[i] = vector4.get(i).split("\t");
			for (int j = 0; j < str1[i].length; j++) {
				vec1.add(str1[i][j]);
			}
			tableModel4.addRow(vec1);
		}
		lbLuongDatPhong.setModel(tableModel4);
		// lay du lieu cua bang thu 2
		for (int i = 0; i < vector5.size(); i++) {
			Vector<String> vec1 = new Vector<>();
			String[][] str1 = new String[vector5.size()][];
			str1[i] = vector5.get(i).split("\t");
			for (int j = 0; j < str1[i].length; j++) {
				vec1.add(str1[i][j]);
			}
			tableModel5.addRow(vec1);
		}
		tbLuongPhongTrong.setModel(tableModel5);

	}

	public PanelBaoCao() {
		con.connect("khachsan", UserInfo.username, UserInfo.password);
		initComponents();
		JSpinner.DateEditor d1 = new JSpinner.DateEditor(date1, "dd-MM-yyyy");
		date1.setEditor(d1);
		JSpinner.DateEditor d2 = new JSpinner.DateEditor(date2, "dd-MM-yyyy");
		date2.setEditor(d2);

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel(){
            public void paintComponent(Graphics g){
                ImageIcon icon = new ImageIcon("background2.jpg");
                Image image = icon.getImage();
                g.drawImage(image, 0, 0, this.getSize().width, this.getSize().height, this);

            }
        };
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnBaoCao = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        date2 = new javax.swing.JSpinner();
        date1 = new javax.swing.JSpinner();
        jPanel4 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbTongSoKhachHang = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbNhanVien = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbTongSoNhanVien = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbKhachHang = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lbTongSoDichVu = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lbTongSoDichVuduocsuDung = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbLuongSuDung = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lbTongSoPhong = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lbTongSoPhong1 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        lbLuongDatPhong = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        lbTongSoPhong2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbLuongPhongTrong = new javax.swing.JTable();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lbTongTienPhong = new javax.swing.JLabel();
        lbTongTienDichVu = new javax.swing.JLabel();
        lbTongDoanhThu = new javax.swing.JLabel();
        btnXuatPDF = new javax.swing.JButton();

        setOpaque(false);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(228, 44, 219));
        jLabel2.setText("BÁO CÁO HOẠT ĐỘNG KHÁCH SẠN");

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255), 2));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("THÔNG TIN BÁO CÁO");

        jSeparator1.setBackground(new java.awt.Color(255, 51, 51));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/baocao128_2.png"))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Nhân viên");
        jLabel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Khách hàng");
        jLabel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Dịch vụ");
        jLabel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Doanh thu");
        jLabel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnBaoCao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/thongke24.png"))); // NOI18N
        btnBaoCao.setText("Báo cáo");
        btnBaoCao.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnBaoCao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaoCaoActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Phòng");
        jLabel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Từ ngày ");
        jLabel29.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Đến ngày ");
        jLabel30.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        date2.setModel(new javax.swing.SpinnerDateModel());

        date1.setModel(new javax.swing.SpinnerDateModel());

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(107, 107, 107))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel3)
                .addGap(0, 68, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(date1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(date2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(btnBaoCao, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jLabel4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(9, 9, 9)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(date1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(date2)
                        .addGap(3, 3, 3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBaoCao, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255), 2));

        jSeparator2.setBackground(new java.awt.Color(255, 0, 0));
        jSeparator2.setOpaque(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("I. Nhân viên");

        jLabel8.setText("Tổng số nhân viên :");

        lbTongSoKhachHang.setText(". . . . . . . . ");

        jLabel12.setText("Số nhân viên theo giới tính :");

        tbNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        tbNhanVien.setFillsViewportHeight(true);
        jScrollPane1.setViewportView(tbNhanVien);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("II. Khách hàng");

        jLabel13.setText("Tổng số khách hàng :");

        lbTongSoNhanVien.setText(". . . . . . . . ");

        jLabel14.setText("Số khách hàng theo giới tính :");

        tbKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        tbKhachHang.setFillsViewportHeight(true);
        jScrollPane2.setViewportView(tbKhachHang);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("III. Dịch vụ");

        jLabel16.setText("Tổng số dịch vụ :");

        lbTongSoDichVu.setText(". . . . . . . . ");

        jLabel17.setText("Tổng số dịch vụ được sử dụng  :");

        lbTongSoDichVuduocsuDung.setText(". . . . . . . . ");

        jLabel18.setText("Lượng sử dụng :");

        tbLuongSuDung.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbLuongSuDung.setFillsViewportHeight(true);
        jScrollPane3.setViewportView(tbLuongSuDung);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setText("IV. Phòng");

        jLabel20.setText("Tổng số phòng :");

        lbTongSoPhong.setText(". . . . . . . . ");

        jLabel21.setText("Số phòng đang đặt :");

        lbTongSoPhong1.setText(". . . . . . . . ");

        jLabel22.setText("Thống kê theo từng loại :");

        lbLuongDatPhong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        lbLuongDatPhong.setFillsViewportHeight(true);
        jScrollPane4.setViewportView(lbLuongDatPhong);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
        );

        jLabel23.setText("Số phòng đang trống :");

        jLabel24.setText("Thống kê theo từng loại :");

        lbTongSoPhong2.setText(". . . . . . . . ");

        tbLuongPhongTrong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbLuongPhongTrong.setFillsViewportHeight(true);
        jScrollPane5.setViewportView(tbLuongPhongTrong);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel25.setText("V. Doanh thu ");

        jLabel26.setText("Tổng tiền phòng :");

        jLabel27.setText("Tổng tiền dịch vụ :");

        jLabel28.setText("Tông doanh thu ");

        lbTongTienPhong.setText(". . . . . . . . ");

        lbTongTienDichVu.setText(". . . . . . . . ");

        lbTongDoanhThu.setText(". . . . . . . . ");

        btnXuatPDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/pdf24.png"))); // NOI18N
        btnXuatPDF.setText("Xuất file");
        btnXuatPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatPDFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(lbTongSoNhanVien))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel12)
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGap(44, 44, 44)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(jLabel13)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(lbTongSoKhachHang))
                                            .addComponent(jLabel14)))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel15)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addComponent(jLabel16)
                                            .addGap(18, 18, 18)
                                            .addComponent(lbTongSoDichVu))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addComponent(jLabel17)
                                            .addGap(18, 18, 18)
                                            .addComponent(lbTongSoDichVuduocsuDung))
                                        .addComponent(jLabel18)))
                                .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbTongDoanhThu))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbTongTienPhong))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addGap(18, 18, 18)
                                .addComponent(lbTongTienDichVu)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                        .addComponent(btnXuatPDF)
                        .addGap(35, 35, 35))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel20)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbTongSoPhong))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel21)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(lbTongSoPhong1)))
                            .addComponent(jLabel22)
                            .addComponent(jLabel24)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(18, 18, 18)
                                .addComponent(lbTongSoPhong2))
                            .addComponent(jLabel25)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel19))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(lbTongSoNhanVien))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(lbTongSoKhachHang))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(lbTongSoDichVu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(lbTongSoDichVuduocsuDung))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(lbTongSoPhong))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(lbTongSoPhong1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbTongSoPhong2)
                            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel26)
                                    .addComponent(lbTongTienPhong))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel27)
                                    .addComponent(lbTongTienDichVu))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel28)
                                    .addComponent(lbTongDoanhThu)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnXuatPDF))))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 450, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap(414, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBaoCaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaoCaoActionPerformed
		// TODO add your handling code here:
		loadDataLable();
		loaddataTable();

		JSpinner.DateEditor d1 = new JSpinner.DateEditor(date1, "dd-MM-yyyy");
		date1.setEditor(d1);
		JSpinner.DateEditor d2 = new JSpinner.DateEditor(date2, "dd-MM-yyyy");
		date2.setEditor(d2);

		dateS[0] = CalendarAdapter.DateToStringB((Date) date1.getValue());
		dateS[1] = CalendarAdapter.DateToStringB((Date) date2.getValue());

		tongTienDV = con.select1colLOI("select sum(chitietdichvu.ThanhTien)\n"
				+ "from chitietdichvu, dichvu , chitietdatphong, datphong\n"
				+ "where chitietdichvu.MaDV= dichvu.MaDV\n"
				+ "and chitietdichvu.MaP= chitietdatphong.MaP\n"
				+ "and chitietdatphong.MaDatPhong = datphong.MaDP\n"
				+ "and (datphong.ThoiGianTra >= '" + dateS[0] + "' and datphong.ThoiGianTra <= '" + dateS[1] + "')");
		lbTongTienDichVu.setText(tongTienDV);
		tongTienPhong = con.select1colLOI("select sum(phong.MucGia) \n"
				+ "from phong , chitietdatphong, datphong\n"
				+ "where phong.MP = chitietdatphong.MaP\n"
				+ "and chitietdatphong.MaDatPhong= datphong.MaDP\n"
				+ "and (datphong.ThoiGianTra >= '" + dateS[0] + "' and datphong.ThoiGianTra <= '" + dateS[1] + "') ");
		lbTongTienPhong.setText(tongTienPhong);

		try {
			tongDoanhThu = Float.toString(Float.parseFloat(tongTienDV) + Float.parseFloat(tongTienPhong));
		} catch (Exception e) {
			tongDoanhThu = "";
		}
		lbTongDoanhThu.setText(tongDoanhThu);
    }//GEN-LAST:event_btnBaoCaoActionPerformed

    private void btnXuatPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatPDFActionPerformed
		// TODO add your handling code here:
		dataLable[0] = lbTongSoNhanVien.getText();
		dataLable[1] = lbTongSoKhachHang.getText();
		dataLable[2] = lbTongSoDichVu.getText();
		dataLable[3] = lbTongSoDichVuduocsuDung.getText();
		dataLable[4] = lbTongSoPhong.getText();
		dataLable[5] = lbTongSoPhong1.getText();
		dataLable[6] = lbTongSoPhong2.getText();
		dataLable[7] = lbTongTienPhong.getText();
		dataLable[8] = lbTongTienDichVu.getText();
		dataLable[9] = lbTongDoanhThu.getText();
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			java.io.File file = fc.getSelectedFile();
			PrintPDF pr = new PrintPDF();
			pr.printBaoCao(file.getPath(), dateS, dataLable, GioiTinh, GioiTinh1, DichVu, Phongdat, Phongtrong);

		}


    }//GEN-LAST:event_btnXuatPDFActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBaoCao;
    private javax.swing.JButton btnXuatPDF;
    private javax.swing.JSpinner date1;
    private javax.swing.JSpinner date2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable lbLuongDatPhong;
    private javax.swing.JLabel lbTongDoanhThu;
    private javax.swing.JLabel lbTongSoDichVu;
    private javax.swing.JLabel lbTongSoDichVuduocsuDung;
    private javax.swing.JLabel lbTongSoKhachHang;
    private javax.swing.JLabel lbTongSoNhanVien;
    private javax.swing.JLabel lbTongSoPhong;
    private javax.swing.JLabel lbTongSoPhong1;
    private javax.swing.JLabel lbTongSoPhong2;
    private javax.swing.JLabel lbTongTienDichVu;
    private javax.swing.JLabel lbTongTienPhong;
    private javax.swing.JTable tbKhachHang;
    private javax.swing.JTable tbLuongPhongTrong;
    private javax.swing.JTable tbLuongSuDung;
    private javax.swing.JTable tbNhanVien;
    // End of variables declaration//GEN-END:variables
}

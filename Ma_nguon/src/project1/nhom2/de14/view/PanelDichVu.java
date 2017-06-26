/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1.nhom2.de14.view;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import project1.nhom2.de14.controller.CChiTietDichVu;
import project1.nhom2.de14.controller.CDichVu;
import project1.nhom2.de14.controller.CNhanVien;
import project1.nhom2.de14.controller.CPhong;
import project1.nhom2.de14.controller.UserInfo;
import project1.nhom2.de14.util.ConnectDB;
import project1.nhom2.de14.util.PrintPDF;

/**
 *
 * @author dell
 */
public class PanelDichVu extends javax.swing.JPanel {

	ConnectDB db = new ConnectDB();
	Vector<String> vec = new Vector<String>(100);
	private CDichVu control;
	private CChiTietDichVu details;
	private DefaultTableModel dtm1;
	private DefaultTableModel dtm2;

	private final String[] colName1 = {"Mã dịch vụ", "Tên dịch vụ", "Đơn giá", "Mã nhân viên phụ trách"};
	private final String[] colName2 = {"Mã phòng", "Mã dịch vụ", "Số lượng", "Thành tiền"};
	private final String[] sqlName1 = {"MaDv", "TenDv", "DonGia", "MaNVphutrach"};
	private final String[] sqlName2 = {"MaP", "MaDV", "SoLuong", "ThanhTien"};
	
	private final String[] room_list;
	private final String[] staff_list;

	/**
	 * Creates new form PanelDichVu
	 */
	public PanelDichVu() {
		room_list = new CPhong("", "").getIds();
		staff_list = new CNhanVien("", "").getIds();
		initComponents();
		
		control = new CDichVu("root", "dragonball");
		dtm1 = new DefaultTableModel(control.getTableData(), colName1);
		this.tb1.setModel(dtm1);
		details = new CChiTietDichVu("root", "dragonball");
		dtm2 = new DefaultTableModel(details.getTableData(), colName2);
		this.tb2.setModel(dtm2);

		tb1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				int selected = tb1.getSelectedRow();
				if (selected < 0) {
					madv.setText("");
					tendv.setText("");
					dongia.setText("");
					manv.setSelectedIndex(-1);

					return;
				}
				madv.setText((String) dtm1.getValueAt(selected, 0));
				tendv.setText((String) dtm1.getValueAt(selected, 1));
				dongia.setText((String) dtm1.getValueAt(selected, 2));
				manv.setSelectedItem((String) dtm1.getValueAt(selected, 3));
			}
		});

		tb2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				int selected = tb2.getSelectedRow();
				if (selected < 0) {
					madv_det.setText("");
					map_det.setSelectedIndex(-1);
					soluong_det.setText("");
					thanhtien_det.setText("");

					return;
				}
				map_det.setSelectedItem((String) dtm2.getValueAt(selected, 0));
				madv_det.setText((String) dtm2.getValueAt(selected, 1));
				soluong_det.setText((String) dtm2.getValueAt(selected, 2));
				thanhtien_det.setText((String) dtm2.getValueAt(selected, 3));
			}
		});
		
		madv_det.getDocument().addDocumentListener(new DocListener());
		soluong_det.getDocument().addDocumentListener(new DocListener());
	}

	private void loadDataTableDichVu() {
		try {
			String col = sqlName1[CB1.getSelectedIndex()];
			String key = txt1.getText();
			DefaultTableModel tableModel = new DefaultTableModel();
			db.connect("khachsan", UserInfo.username, UserInfo.password);
			Vector<String> colsName = new Vector<String>(10);
			colsName.add("Mã Dịch vụ");
			colsName.add("Tên Dịch vụ");
			colsName.add("Đợn giá");
			colsName.add("Mã Nhân viên phục vụ");

			tableModel.setColumnIdentifiers(colsName);

			vec = db.selectSearch("dichvu", col, key);

			String[][] str = new String[vec.size()][];
			for (int i = 0; i < vec.size(); i++) {
				Vector<String> vector = new Vector<String>(100);
				str[i] = vec.get(i).split("\t");
				for (int j = 0; j < str[i].length; j++) {
					vector.add(str[i][j]);
				}
				tableModel.addRow(vector);
			}
			tb1.setModel(tableModel);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Không có kết quả tìm kiếm ", "Thông báo", 1);
		}
	}

	private void loadDataTableCTDichvu() {
		try {
			String col = sqlName2[CB2.getSelectedIndex()];
			String key = txt2.getText();
			DefaultTableModel tableModel = new DefaultTableModel();
			db.connect("khachsan", UserInfo.username, UserInfo.password);
			Vector<String> colsName = new Vector<String>(10);
			colsName.add("Mã phòng");
			colsName.add("Mã dịch vụ");
			colsName.add("Số lượng");
			colsName.add("Thành tiền");

			tableModel.setColumnIdentifiers(colsName);

			vec = db.selectSearch("chitietdichvu", col, key);

			String[][] str = new String[vec.size()][];
			for (int i = 0; i < vec.size(); i++) {
				Vector<String> vector = new Vector<String>(100);
				str[i] = vec.get(i).split("\t");
				for (int j = 0; j < str[i].length; j++) {
					vector.add(str[i][j]);
				}
				tableModel.addRow(vector);
			}
			tb2.setModel(tableModel);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Không có kết quả tìm kiếm ", "Thông báo", 1);
		}
	}

	private String getFields() {
		String fields = new String();

		fields += this.madv.getText() + "\t";
		fields += this.tendv.getText() + "\t";
		fields += this.dongia.getText() + "\t";
		fields += this.manv.getSelectedItem().toString();

		return fields;
	}

	private String getDetails() {
		String fields = new String();

		fields += this.map_det.getSelectedItem().toString() + "\t";
		fields += this.madv_det.getText() + "\t";
		fields += this.soluong_det.getText() + "\t";
		fields += this.thanhtien_det.getText();

		return fields;
	}

	private void updateTotal(){
		String mdv = madv_det.getText();
		CDichVu cdv = new CDichVu("","");
		int index = cdv.searchIndex(mdv);
		thanhtien_det.setText("");
		
		if(index>=0){
			try{
				int sl = Integer.parseInt(soluong_det.getText());
				double price = cdv.getDS().get(index).getDonGia();
				thanhtien_det.setText(Double.toString((double)sl * price));
			}catch(NumberFormatException e){
				return;
			}
		}
	}
	
	private class DocListener implements DocumentListener{
		@Override
		public void insertUpdate(DocumentEvent e) {
			updateTotal();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			updateTotal();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			updateTotal();
		}	
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
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        madv = new javax.swing.JTextField();
        dongia = new javax.swing.JTextField();
        tendv = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        CB1 = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        txt1 = new javax.swing.JTextField();
        btnTimKiemDV = new javax.swing.JButton();
        btnXuatPDFDV = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnThemFile = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        manv = new javax.swing.JComboBox<>();
        paneldv = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        soluong_det = new javax.swing.JTextField();
        madv_det = new javax.swing.JTextField();
        thanhtien_det = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        CB2 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        txt2 = new javax.swing.JTextField();
        btnTimKiemCTDV = new javax.swing.JButton();
        btnXuatPDFCTDV = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb2 = new javax.swing.JTable();
        btnSuaCTDV = new javax.swing.JButton();
        btnThemCTDV = new javax.swing.JButton();
        btnXoaCTDV = new javax.swing.JButton();
        btnThemFileCTDV = new javax.swing.JButton();
        map_det = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb1 = new javax.swing.JTable();

        setOpaque(false);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(228, 44, 219));
        jLabel2.setText("QUẢN LÝ DỊCH VỤ");

        jPanel6.setBackground(new java.awt.Color(204, 255, 204));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255), 2), "Thông tin dịch vụ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(255, 0, 0))); // NOI18N

        jLabel3.setText("Mã dịch vụ :");

        jLabel4.setText("Tên dịch vụ :");

        jLabel5.setText("Đơn giá :");

        jLabel6.setText("Mã nhân viên phụ trách :");

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm dịch vụ ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        jPanel9.setOpaque(false);

        jLabel13.setText("Chọn khóa tìm kiếm :");

        CB1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã dịch vụ ", "Tên dịch vụ", "Đơn giá", "Mã nhân viên phụ trách", " " }));
        CB1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        CB1.setOpaque(false);

        jLabel16.setText("Nhập từ khóa tìm kiếm :");

        btnTimKiemDV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/tkiem24.png"))); // NOI18N
        btnTimKiemDV.setText("Tìm kiếm");
        btnTimKiemDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemDVActionPerformed(evt);
            }
        });

        btnXuatPDFDV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/pdf24.png"))); // NOI18N
        btnXuatPDFDV.setText("Xuất PDF");
        btnXuatPDFDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatPDFDVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(btnTimKiemDV)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXuatPDFDV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txt1)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel16))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(CB1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(17, 17, 17))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel13)
                .addGap(1, 1, 1)
                .addComponent(CB1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTimKiemDV, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXuatPDFDV))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel9Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnTimKiemDV, btnXuatPDFDV});

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/them24.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/sua24.png"))); // NOI18N
        jButton2.setText("Sửa");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnThemFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/excel24.png"))); // NOI18N
        btnThemFile.setText("Thêm file");
        btnThemFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemFileActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/xoa24.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        manv.setModel(new DefaultComboBoxModel<String>(staff_list));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnThemFile)
                        .addGap(55, 55, 55))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(madv, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                            .addComponent(tendv, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                            .addComponent(dongia, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                            .addComponent(manv, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnThemFile, btnXoa, jButton2});

        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(madv, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addGap(4, 4, 4)
                        .addComponent(tendv, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dongia, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(manv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThemFile)
                            .addComponent(btnXoa)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnThem, btnThemFile, btnXoa, jButton2});

        paneldv.setBackground(new java.awt.Color(204, 204, 255));
        paneldv.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255), 2), "Chi tiết dịch vụ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(255, 0, 0))); // NOI18N

        jLabel7.setText("Mã dịch vụ : ");

        jLabel8.setText("Mã phòng :");

        jLabel9.setText("Số lượng :");

        jLabel10.setText("Thành tiền :");

        thanhtien_det.setEditable(false);
        thanhtien_det.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thanhtien_detActionPerformed(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm chi tiết dịch vụ ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        jPanel7.setOpaque(false);

        jLabel14.setText("Chọn khóa tìm kiếm :");

        CB2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã phòng", "Mã dịch vụ ", "Số lượng", " " }));
        CB2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        CB2.setOpaque(false);

        jLabel15.setText("Nhập từ khóa tìm kiếm :");

        btnTimKiemCTDV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/tkiem24.png"))); // NOI18N
        btnTimKiemCTDV.setText("Tìm kiếm");
        btnTimKiemCTDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemCTDVActionPerformed(evt);
            }
        });

        btnXuatPDFCTDV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/pdf24.png"))); // NOI18N
        btnXuatPDFCTDV.setText("Xuất PDF");
        btnXuatPDFCTDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatPDFCTDVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnTimKiemCTDV)
                        .addGap(18, 18, 18)
                        .addComponent(btnXuatPDFCTDV))
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(txt2)
                    .addComponent(CB2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CB2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTimKiemCTDV, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXuatPDFCTDV, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnTimKiemCTDV, btnXuatPDFCTDV});

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tb2.setModel(new javax.swing.table.DefaultTableModel(
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
        tb2.setFillsViewportHeight(true);
        jScrollPane1.setViewportView(tb2);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        btnSuaCTDV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/sua24.png"))); // NOI18N
        btnSuaCTDV.setText("Sửa");
        btnSuaCTDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaCTDVActionPerformed(evt);
            }
        });

        btnThemCTDV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/them24.png"))); // NOI18N
        btnThemCTDV.setText("Thêm");
        btnThemCTDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemCTDVActionPerformed(evt);
            }
        });

        btnXoaCTDV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/xoa24.png"))); // NOI18N
        btnXoaCTDV.setText("Xóa");
        btnXoaCTDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaCTDVActionPerformed(evt);
            }
        });

        btnThemFileCTDV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/excel24.png"))); // NOI18N
        btnThemFileCTDV.setText("Thêm file");
        btnThemFileCTDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemFileCTDVActionPerformed(evt);
            }
        });

        map_det.setModel(new DefaultComboBoxModel<String>(room_list));

        javax.swing.GroupLayout paneldvLayout = new javax.swing.GroupLayout(paneldv);
        paneldv.setLayout(paneldvLayout);
        paneldvLayout.setHorizontalGroup(
            paneldvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldvLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneldvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(paneldvLayout.createSequentialGroup()
                        .addGroup(paneldvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(paneldvLayout.createSequentialGroup()
                                .addGroup(paneldvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8))
                                .addGap(22, 22, 22))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, paneldvLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(paneldvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(madv_det, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                            .addComponent(thanhtien_det, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                            .addComponent(soluong_det, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                            .addComponent(map_det, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(paneldvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneldvLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(paneldvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, paneldvLayout.createSequentialGroup()
                                .addComponent(btnXoaCTDV, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnThemFileCTDV, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(paneldvLayout.createSequentialGroup()
                                .addComponent(btnThemCTDV)
                                .addGap(18, 18, 18)
                                .addComponent(btnSuaCTDV, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61))))
                    .addGroup(paneldvLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 19, Short.MAX_VALUE))))
        );

        paneldvLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnSuaCTDV, btnThemCTDV, btnThemFileCTDV, btnXoaCTDV});

        paneldvLayout.setVerticalGroup(
            paneldvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldvLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneldvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneldvLayout.createSequentialGroup()
                        .addGroup(paneldvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(map_det, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(paneldvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(madv_det, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(paneldvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(soluong_det, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(11, 11, 11)
                        .addGroup(paneldvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(thanhtien_det, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(paneldvLayout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(paneldvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThemCTDV, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSuaCTDV, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(paneldvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnXoaCTDV, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThemFileCTDV, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        paneldvLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnSuaCTDV, btnThemCTDV, btnThemFileCTDV, btnXoaCTDV});

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255), 2), "Danh sách dịch vụ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(255, 0, 0))); // NOI18N
        jPanel3.setOpaque(false);

        tb1.setModel(new javax.swing.table.DefaultTableModel(
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
        tb1.setFillsViewportHeight(true);
        jScrollPane2.setViewportView(tb1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(paneldv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paneldv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
		if (ConfirmAdd.main(null)) {
			String fields = getFields();
			if (control.add(fields)) {
				dtm1.addRow(fields.split("\t"));
			}else{
				JOptionPane.showMessageDialog(this, "Thực hiện không thành công");
			}
		}
    }//GEN-LAST:event_btnThemActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
		if (ConfirmEdit.main(null)) {
			int selected = tb1.getSelectedRow();
			String key = (String) dtm1.getValueAt(selected, 0);
			String fields = getFields();
			if (control.edit(fields, key)) {
				tb1.getSelectionModel().removeSelectionInterval(selected, 0);
				dtm1.removeRow(selected);
				dtm1.insertRow(selected, fields.split("\t"));
			}else{
				JOptionPane.showMessageDialog(this, "Thực hiện không thành công");
			}
		}
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
		if (ConfirmDelete.main(null)) {
			int selected = tb1.getSelectedRow();
			String key = (String) dtm1.getValueAt(selected, 0);
			if (control.delete(key)) {
				tb1.getSelectionModel().removeSelectionInterval(selected, 0);
				dtm1.removeRow(selected);
			}else{
				JOptionPane.showMessageDialog(this, "Thực hiện không thành công");
			}
		}
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnThemFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemFileActionPerformed
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Workbook", "xls");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(this);

		File file;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
			file = file.getAbsoluteFile();
			Vector<String> rows = control.addFile(file.getPath());

			for (String s : rows) {
				dtm1.addRow(s.split("\t"));
			}
		}
    }//GEN-LAST:event_btnThemFileActionPerformed

    private void btnTimKiemDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemDVActionPerformed
		loadDataTableDichVu();
    }//GEN-LAST:event_btnTimKiemDVActionPerformed

    private void btnXuatPDFDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatPDFDVActionPerformed
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Document", "pdf");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(this);

		File file;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
			file = file.getAbsoluteFile();
			String[][] data = new String[dtm1.getDataVector().size()][colName1.length];
			for (int i = 0; i < data.length; i++) {
				for (int j = 0; j < colName1.length; j++) {
					data[i][j] = (String) dtm1.getValueAt(i, j);
				}
			}
			try {
				PrintPDF.printSearchReport(file.getAbsolutePath(), "dichvu", colName1, data, CB1.getSelectedItem().toString(), txt1.getText());
				Desktop.getDesktop().open(file);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Đã có lỗi xảy ra");
			}
		}
    }//GEN-LAST:event_btnXuatPDFDVActionPerformed

    private void btnThemCTDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemCTDVActionPerformed
		if (ConfirmAdd.main(null)) {
			String fields = getDetails();
			if (details.add(fields)) {
				dtm2.addRow(fields.split("\t"));
			}else{
				JOptionPane.showMessageDialog(this, "Thực hiện không thành công");
			}
		}
    }//GEN-LAST:event_btnThemCTDVActionPerformed

    private void btnSuaCTDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaCTDVActionPerformed
		if (ConfirmEdit.main(null)) {
			int selected = tb2.getSelectedRow();
			String key1 = (String) dtm2.getValueAt(selected, 0);
			String key2 = (String) dtm2.getValueAt(selected, 1);
			String fields = getDetails();
			if (details.edit(fields, key1, key2)) {
				tb2.getSelectionModel().removeSelectionInterval(selected, 0);
				dtm2.removeRow(selected);
				dtm2.insertRow(selected, fields.split("\t"));
			}else{
				JOptionPane.showMessageDialog(this, "Thực hiện không thành công");
			}
		}
    }//GEN-LAST:event_btnSuaCTDVActionPerformed

    private void btnXoaCTDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaCTDVActionPerformed
		if (ConfirmDelete.main(null)) {
			int selected = tb2.getSelectedRow();
			String key1 = (String) dtm2.getValueAt(selected, 0);
			String key2 = (String) dtm2.getValueAt(selected, 1);
			if (details.delete(key1, key2)) {
				tb2.getSelectionModel().removeSelectionInterval(selected, 0);
				dtm2.removeRow(selected);
			}else{
				JOptionPane.showMessageDialog(this, "Thực hiện không thành công");
			}
		}
    }//GEN-LAST:event_btnXoaCTDVActionPerformed

    private void btnTimKiemCTDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemCTDVActionPerformed
		loadDataTableCTDichvu();
    }//GEN-LAST:event_btnTimKiemCTDVActionPerformed

    private void btnThemFileCTDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemFileCTDVActionPerformed
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Workbook", "xls");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(this);

		File file;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
			file = file.getAbsoluteFile();
			Vector<String> rows = details.addFile(file.getPath());

			for (String s : rows) {
				dtm2.addRow(s.split("\t"));
			}
		}
    }//GEN-LAST:event_btnThemFileCTDVActionPerformed

    private void btnXuatPDFCTDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatPDFCTDVActionPerformed
        JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Document", "pdf");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(this);

		File file;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
			file = file.getAbsoluteFile();
			String[][] data = new String[dtm2.getDataVector().size()][colName2.length];
			for (int i = 0; i < data.length; i++) {
				for (int j = 0; j < colName2.length; j++) {
					data[i][j] = (String) dtm2.getValueAt(i, j);
				}
			}
			try {
				PrintPDF.printSearchReport(file.getAbsolutePath(), "chitietdichvu", colName2, data, CB2.getSelectedItem().toString(), txt2.getText());
				Desktop.getDesktop().open(file);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Đã có lỗi xảy ra");
			}
		}
    }//GEN-LAST:event_btnXuatPDFCTDVActionPerformed

    private void thanhtien_detActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thanhtien_detActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_thanhtien_detActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CB1;
    private javax.swing.JComboBox<String> CB2;
    private javax.swing.JButton btnSuaCTDV;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemCTDV;
    private javax.swing.JButton btnThemFile;
    private javax.swing.JButton btnThemFileCTDV;
    private javax.swing.JButton btnTimKiemCTDV;
    private javax.swing.JButton btnTimKiemDV;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaCTDV;
    private javax.swing.JButton btnXuatPDFCTDV;
    private javax.swing.JButton btnXuatPDFDV;
    private javax.swing.JTextField dongia;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField madv;
    private javax.swing.JTextField madv_det;
    private javax.swing.JComboBox<String> manv;
    private javax.swing.JComboBox<String> map_det;
    private javax.swing.JPanel paneldv;
    private javax.swing.JTextField soluong_det;
    private javax.swing.JTable tb1;
    private javax.swing.JTable tb2;
    private javax.swing.JTextField tendv;
    private javax.swing.JTextField thanhtien_det;
    private javax.swing.JTextField txt1;
    private javax.swing.JTextField txt2;
    // End of variables declaration//GEN-END:variables
}

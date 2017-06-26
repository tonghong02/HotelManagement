/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1.nhom2.de14.view;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import project1.nhom2.de14.controller.CChiTietDatPhong;
import project1.nhom2.de14.controller.CDatPhong;
import project1.nhom2.de14.controller.CKhachHang;
import project1.nhom2.de14.controller.CNhanVien;
import project1.nhom2.de14.controller.CPhong;
import project1.nhom2.de14.controller.UserInfo;
import project1.nhom2.de14.util.CalendarAdapter;
import project1.nhom2.de14.util.ConnectDB;
import project1.nhom2.de14.util.PrintPDF;

public class PanelDatPhong extends javax.swing.JPanel {

	ConnectDB db = new ConnectDB();
	Vector<String> vec = new Vector<String>(100);
	private CDatPhong control;
	private CChiTietDatPhong details;
	private DefaultTableModel dtm1;
	private DefaultTableModel dtm2;

	private final String[] customer_list;
	private final String[] staff_list;
	private final String[] room_list;

	private final String[] colName1 = {"Mã đặt phòng", "Mã khách hàng", "Thời gian nhận", "Thời gian trả", "Số phòng", "Tiền đặt cọc", "Mã nhân viên"};
	private final String[] sqlName1 = {"MaDP", "MaKH", "ThoiGianNhan", "ThoiGianTra", "SoPhongDat", "TienDatCoc", "MaNV"};
	private final String[] colName2 = {"Mã đặt phòng", "Mã phòng"};
	private final String[] sqlName2 = {"MaDatPhong", "MaP"};

	/**
	 * Creates new form PanelDatPhong
	 */
	public PanelDatPhong() {
		customer_list = new CKhachHang("", "").getIds();
		staff_list = new CNhanVien("", "").getIds();
		room_list = new CPhong("", "").getIds();
		initComponents();

		JSpinner.DateEditor d1 = new JSpinner.DateEditor(chk_date, "dd-MM-yyyy");
		chk_date.setEditor(d1);
		JSpinner.DateEditor d2 = new JSpinner.DateEditor(return_date, "dd-MM-yyyy");
		return_date.setEditor(d2);

		control = new CDatPhong("root", "dragonball");
		dtm1 = new DefaultTableModel(control.getTableData(), colName1);
		this.tbDp.setModel(dtm1);
		details = new CChiTietDatPhong("root", "dragonball");
		dtm2 = new DefaultTableModel(details.getTableData(), colName2);
		this.tb2.setModel(dtm2);

		tbDp.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				int selected = tbDp.getSelectedRow();
				if (selected < 0) {
					madp.setText("");
					makh.setSelectedIndex(-1);
					chk_date.setValue(new Date());
					return_date.setValue(new Date());
					count.setText("");
					deposit.setText("");
					manv.setSelectedIndex(-1);

					return;
				}
				madp.setText((String) dtm1.getValueAt(selected, 0));
				makh.setSelectedItem((String) dtm1.getValueAt(selected, 1));
				chk_date.setValue(CalendarAdapter.StringToDateB((String) dtm1.getValueAt(selected, 2)));
				return_date.setValue(CalendarAdapter.StringToDateB((String) dtm1.getValueAt(selected, 3)));
				count.setText((String) dtm1.getValueAt(selected, 4));
				deposit.setText((String) dtm1.getValueAt(selected, 5));
				manv.setSelectedItem((String) dtm1.getValueAt(selected, 6));
			}
		});

		tb2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				int selected = tb2.getSelectedRow();
				if (selected < 0) {
					madp_det.setText("");
					map_det.setSelectedIndex(-1);

					return;
				}
				madp_det.setText((String) dtm2.getValueAt(selected, 0));
				map_det.setSelectedItem((String) dtm2.getValueAt(selected, 1));
			}
		});
	}

	private String getFields() {
		String fields = new String();

		fields += this.madp.getText() + "\t";
		fields += this.makh.getSelectedItem().toString() + "\t";
		fields += CalendarAdapter.DateToString((Date) chk_date.getValue()) + "\t";
		fields += CalendarAdapter.DateToString((Date) return_date.getValue()) + "\t";
		System.out.println(fields);
		fields += this.count.getText() + "\t";
		fields += this.deposit.getText() + "\t";
		fields += this.manv.getSelectedItem().toString();

		return fields;
	}

	private String getDetails() {
		String fields = new String();

		fields += this.madp_det.getText() + "\t";
		fields += this.map_det.getSelectedItem().toString();

		return fields;
	}

	private void loadDataTableDatPhong() {
		try {
			String col = sqlName1[CBTK.getSelectedIndex()];
			String key = txtTK.getText();
			DefaultTableModel tableModel = new DefaultTableModel();
			db.connect("khachsan", UserInfo.username, UserInfo.password);
			Vector<String> colsName = new Vector<String>(10);
			colsName.add("Mã Đặt phòng");
			colsName.add("Mã Khách hàng");
			colsName.add("Thời gian nhận");
			colsName.add("Thời gian trả");
			colsName.add("Số phòng");
			colsName.add("Đặt cọc ");
			colsName.add("Mã Nhân viên");

			tableModel.setColumnIdentifiers(colsName);

			vec = db.selectSearch("datphong", col, key);

			String[][] str = new String[vec.size()][];
			for (int i = 0; i < vec.size(); i++) {
				Vector<String> vector = new Vector<String>(100);
				str[i] = vec.get(i).split("\t");
				for (int j = 0; j < str[i].length; j++) {
					vector.add(str[i][j]);
				}
				tableModel.addRow(vector);
			}
			tbDp.setModel(tableModel);

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Không có kết quả tìm kiếm ", "Thông báo", 1);
		}
	}

	private void loadDataTableCTDatPhong() {
		try {
			String col = sqlName2[CB.getSelectedIndex()];
			String key = TXT.getText();
			DefaultTableModel tableModel = new DefaultTableModel();
			db.connect("khachsan", UserInfo.username, UserInfo.password);
			Vector<String> colsName = new Vector<String>(10);
			colsName.add("Mã Đặt phòng");
			colsName.add("Mã phòng");

			tableModel.setColumnIdentifiers(colsName);

			vec = db.selectSearch("chitietdatphong", col, key);

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
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        madp = new javax.swing.JTextField();
        deposit = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtTK = new javax.swing.JTextField();
        btnTimKiemP = new javax.swing.JButton();
        btnXuatPDFP = new javax.swing.JButton();
        CBTK = new javax.swing.JComboBox<>();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnThemFile = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        count = new javax.swing.JTextField();
        chk_date = new javax.swing.JSpinner();
        return_date = new javax.swing.JSpinner();
        makh = new javax.swing.JComboBox<>();
        manv = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        madp_det = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        TXT = new javax.swing.JTextField();
        btnTimKiemCTDP = new javax.swing.JButton();
        btnXuatPDFCTDP = new javax.swing.JButton();
        CB = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb2 = new javax.swing.JTable();
        btnThemCTDP = new javax.swing.JButton();
        btnSuaCTDP = new javax.swing.JButton();
        btnXoaCTDP = new javax.swing.JButton();
        btnThemFileCTDP = new javax.swing.JButton();
        map_det = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbDp = new javax.swing.JTable();

        setOpaque(false);

        jPanel1.setOpaque(false);

        jPanel2.setBackground(new java.awt.Color(102, 153, 255));
        jPanel2.setOpaque(false);

        jLabel1.setBackground(new java.awt.Color(255, 0, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(228, 44, 219));
        jLabel1.setText("QUẢN LÝ ĐẶT PHÒNG");

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255), 2), "Thông tin đặt phòng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(255, 0, 0))); // NOI18N

        jLabel2.setText("Mã đặt phòng :");

        jLabel3.setText("Mã khách hàng :");

        jLabel4.setText("Mã nhân viên");

        jLabel5.setText("Tiền đặt cọc :");

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm phòng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        jPanel6.setOpaque(false);

        jLabel11.setText("Chọn khóa tìm kiếm :");

        jLabel12.setText("Nhập từ khóa tìm kiếm :");

        btnTimKiemP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/tkiem24.png"))); // NOI18N
        btnTimKiemP.setText("Tìm kiếm");
        btnTimKiemP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemPActionPerformed(evt);
            }
        });

        btnXuatPDFP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/pdf24.png"))); // NOI18N
        btnXuatPDFP.setText("Xuất PDF");
        btnXuatPDFP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatPDFPActionPerformed(evt);
            }
        });

        CBTK.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã đặt phòng", "Mã khách hàng", "Mã nhân viên", "Tiền đặt cọc", "Thời gian nhận", "Thời gian trả", "Số phòng đặt", " ", " " }));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(btnTimKiemP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXuatPDFP))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11)
                            .addComponent(txtTK)
                            .addComponent(CBTK, 0, 231, Short.MAX_VALUE))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CBTK, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTK, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXuatPDFP)
                    .addComponent(btnTimKiemP, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/them24.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/sua24.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/xoa24.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnThemFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/excel24.png"))); // NOI18N
        btnThemFile.setText("Thêm file");
        btnThemFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemFileActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel8.setText("Thời gian nhận :");
        jLabel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel9.setText("Thời gian trả :");
        jLabel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel10.setText("Số phòng đặt :");
        jLabel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        count.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                countActionPerformed(evt);
            }
        });

        chk_date.setModel(new javax.swing.SpinnerDateModel());

        return_date.setModel(new javax.swing.SpinnerDateModel());

        makh.setModel(new DefaultComboBoxModel<String>(customer_list));

        manv.setModel(new DefaultComboBoxModel<String>(staff_list));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(madp)
                    .addComponent(deposit)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(count)
                    .addComponent(chk_date, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                    .addComponent(return_date)
                    .addComponent(makh, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(manv, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(btnThem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSua)
                .addGap(14, 14, 14)
                .addComponent(btnXoa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThemFile)
                .addGap(0, 25, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnSua, btnThem, btnThemFile, btnXoa});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(4, 4, 4)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(15, 15, 15))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(madp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(makh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(manv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deposit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chk_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(return_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(count, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 7, Short.MAX_VALUE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa)
                    .addComponent(btnThemFile)))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnSua, btnThem, btnThemFile, btnXoa});

        jPanel5.setBackground(new java.awt.Color(204, 204, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255), 2), "Chi tiết đặt phòng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(255, 0, 0))); // NOI18N

        jLabel6.setText("Mã phòng :");

        jLabel7.setText("Mã đặt phòng :");

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm chi tiết dịch vụ ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        jPanel7.setOpaque(false);

        jLabel14.setText("Chọn khóa tìm kiếm :");

        jLabel15.setText("Nhập từ khóa tìm kiếm :");

        btnTimKiemCTDP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/tkiem24.png"))); // NOI18N
        btnTimKiemCTDP.setText("Tìm kiếm");
        btnTimKiemCTDP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemCTDPActionPerformed(evt);
            }
        });

        btnXuatPDFCTDP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/pdf24.png"))); // NOI18N
        btnXuatPDFCTDP.setText("Xuất PDF");
        btnXuatPDFCTDP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatPDFCTDPActionPerformed(evt);
            }
        });

        CB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã đặt phòng", "Mã phòng", " " }));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel15))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel14))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(btnTimKiemCTDP)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnXuatPDFCTDP)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(TXT, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(CB, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CB, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TXT, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXuatPDFCTDP, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiemCTDP, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel8.setOpaque(false);

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        btnThemCTDP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/them24.png"))); // NOI18N
        btnThemCTDP.setText("Thêm");
        btnThemCTDP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemCTDPActionPerformed(evt);
            }
        });

        btnSuaCTDP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/sua24.png"))); // NOI18N
        btnSuaCTDP.setText("Sửa");
        btnSuaCTDP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaCTDPActionPerformed(evt);
            }
        });

        btnXoaCTDP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/xoa24.png"))); // NOI18N
        btnXoaCTDP.setText("Xóa");
        btnXoaCTDP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaCTDPActionPerformed(evt);
            }
        });

        btnThemFileCTDP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project1/nhom2/de14/images/excel24.png"))); // NOI18N
        btnThemFileCTDP.setText("Thêm file");
        btnThemFileCTDP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemFileCTDPActionPerformed(evt);
            }
        });

        map_det.setModel(new DefaultComboBoxModel<String>(room_list));
        map_det.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                map_detActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(madp_det)
                                    .addComponent(map_det, 0, 194, Short.MAX_VALUE))))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(btnThemCTDP, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSuaCTDP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnXoaCTDP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnThemFileCTDP)))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnSuaCTDP, btnThemCTDP, btnThemFileCTDP, btnXoaCTDP});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(madp_det, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(map_det, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemCTDP, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuaCTDP)
                    .addComponent(btnXoaCTDP, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemFileCTDP))
                .addContainerGap())
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnSuaCTDP, btnThemCTDP, btnThemFileCTDP, btnXoaCTDP});

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255), 2), "Danh sách đặt phòng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(255, 0, 0))); // NOI18N
        jPanel9.setOpaque(false);

        tbDp.setModel(new javax.swing.table.DefaultTableModel(
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
        tbDp.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbDp.setFillsViewportHeight(true);
        tbDp.setUpdateSelectionOnSort(false);
        jScrollPane2.setViewportView(tbDp);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1220, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemFileCTDPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemFileCTDPActionPerformed
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
    }//GEN-LAST:event_btnThemFileCTDPActionPerformed

    private void countActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_countActionPerformed
		// TODO add your handling code here:
    }//GEN-LAST:event_countActionPerformed

    private void btnTimKiemCTDPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemCTDPActionPerformed
		loadDataTableCTDatPhong();
    }//GEN-LAST:event_btnTimKiemCTDPActionPerformed

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

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
		if (ConfirmEdit.main(null)) {
			int selected = tbDp.getSelectedRow();
			String key = (String) dtm1.getValueAt(selected, 0);
			String fields = getFields();
			if (control.edit(fields, key)) {
				tbDp.getSelectionModel().removeSelectionInterval(selected, 0);
				dtm1.removeRow(selected);
				dtm1.insertRow(selected, fields.split("\t"));
			}else{
				JOptionPane.showMessageDialog(this, "Thực hiện không thành công");
			}
		}
    }//GEN-LAST:event_btnSuaActionPerformed

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

    private void btnThemCTDPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemCTDPActionPerformed
		if (ConfirmAdd.main(null)) {
			String fields = getDetails();
			if (details.add(fields)) {
				dtm2.addRow(fields.split("\t"));
			}else{
				JOptionPane.showMessageDialog(this, "Thực hiện không thành công");
			}
		}
    }//GEN-LAST:event_btnThemCTDPActionPerformed

    private void btnSuaCTDPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaCTDPActionPerformed
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
    }//GEN-LAST:event_btnSuaCTDPActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
		if (ConfirmDelete.main(null)) {
			int selected = tbDp.getSelectedRow();
			String key = (String) dtm1.getValueAt(selected, 0);
			if (control.delete(key)) {
				tbDp.getSelectionModel().removeSelectionInterval(selected, 0);
				dtm1.removeRow(selected);
			}else{
				JOptionPane.showMessageDialog(this, "Thực hiện không thành công");
			}
		}
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnXoaCTDPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaCTDPActionPerformed
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
    }//GEN-LAST:event_btnXoaCTDPActionPerformed

    private void btnTimKiemPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemPActionPerformed
		loadDataTableDatPhong();
    }//GEN-LAST:event_btnTimKiemPActionPerformed

    private void btnXuatPDFPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatPDFPActionPerformed
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
				PrintPDF.printSearchReport(file.getAbsolutePath(), "datphong", colName1, data, CBTK.getSelectedItem().toString(), txtTK.getText());
				Desktop.getDesktop().open(file);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Đã có lỗi xảy ra");
			}
		}
    }//GEN-LAST:event_btnXuatPDFPActionPerformed

    private void btnXuatPDFCTDPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatPDFCTDPActionPerformed
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
				PrintPDF.printSearchReport(file.getAbsolutePath(), "chitietdatphong", colName2, data, CB.getSelectedItem().toString(), TXT.getText());
				Desktop.getDesktop().open(file);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Đã có lỗi xảy ra");
			}
		}
    }//GEN-LAST:event_btnXuatPDFCTDPActionPerformed

    private void map_detActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_map_detActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_map_detActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CB;
    private javax.swing.JComboBox<String> CBTK;
    private javax.swing.JTextField TXT;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSuaCTDP;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemCTDP;
    private javax.swing.JButton btnThemFile;
    private javax.swing.JButton btnThemFileCTDP;
    private javax.swing.JButton btnTimKiemCTDP;
    private javax.swing.JButton btnTimKiemP;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaCTDP;
    private javax.swing.JButton btnXuatPDFCTDP;
    private javax.swing.JButton btnXuatPDFP;
    private javax.swing.JSpinner chk_date;
    private javax.swing.JTextField count;
    private javax.swing.JTextField deposit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField madp;
    private javax.swing.JTextField madp_det;
    private javax.swing.JComboBox<String> makh;
    private javax.swing.JComboBox<String> manv;
    private javax.swing.JComboBox<String> map_det;
    private javax.swing.JSpinner return_date;
    private javax.swing.JTable tb2;
    private javax.swing.JTable tbDp;
    private javax.swing.JTextField txtTK;
    // End of variables declaration//GEN-END:variables
}

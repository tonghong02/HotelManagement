/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1.nhom2.de14.util;

import java.io.IOException;
import java.util.Date;

import com.itextpdf.io.font.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import project1.nhom2.de14.controller.UserInfo;
import project1.nhom2.de14.model.HoaDon;
import project1.nhom2.de14.model.KhachHang;
import project1.nhom2.de14.model.Phong;

public class PrintPDF {

	public static void printSearchReport(String filePath, String name, String[] header, String[][] data, String search_key, String search_con) throws IOException {
		//LicenseKey.loadLicenseFile("res/itextkey14773129677280.xml");
		PdfWriter writer = new PdfWriter(filePath);
		PdfDocument pdf = new PdfDocument(writer);
		Document report = new Document(pdf, PageSize.A4);
		PdfFont hfont = PdfFontFactory.createFont("C:\\Windows\\Fonts\\tahoma.ttf", PdfEncodings.IDENTITY_H, true);
		PdfFont rfont = PdfFontFactory.createFont("C:\\Windows\\Fonts\\Calibri.ttf", PdfEncodings.IDENTITY_H, true);
		float fontSize = 14;
		float fontSizes = 16;

		report.setMargins(20, 20, 50, 20);

		// Write start content
		report.add(new Paragraph("KHÁCH SẠN " + UserInfo.hotel_name.toUpperCase()).setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(25).setBold());
		report.add(new Paragraph("Địa chỉ: " + UserInfo.address).setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize));
		report.add(new Paragraph("Điện thoại: " + UserInfo.phone_no + "                        " + "Fax: " + UserInfo.fax_no).setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize));
		report.add(new Paragraph("").setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(14).setBold());

		report.add(new Paragraph("Kết quả tìm kiếm bảng '" + name + "'").setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(14).setBold());
		report.add(new Paragraph("Khóa tìm kiếm: " + search_key + "        " + "Điều kiện: " + search_con).setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize));

		float[] colWidth;

		switch (name) {
			case "phong":
				colWidth = new float[]{2, 2, 2, 2};
				break;
			case "nhanvien":
				colWidth = new float[]{2, 2, 2, 2, 2, 2, 2, 2};
				break;
			case "khachhang":
				colWidth = new float[]{1, 1, 1, 1, 1, 1, 1};
				break;
			case "hoadon":
				colWidth = new float[]{1, 1, 1, 1, 1};
				break;
			case "dichvu":
				colWidth = new float[]{1, 1, 1, 1};
				break;
			case "datphong":
				colWidth = new float[]{1, 1, 1, 1, 1, 1, 1};
				break;
			case "chitietdichvu":
				colWidth = new float[]{1, 1, 1, 1};
				break;
			case "chitietdatphong":
				colWidth = new float[]{1, 1};
				break;
			default:
				colWidth = new float[header.length];
		}

		Table table = new Table(colWidth);
		table.setWidthPercent(100);
		for (String s : header) {
			table.addHeaderCell(new Paragraph(s).setFont(rfont).setBold().setTextAlignment(TextAlignment.CENTER));
		}

		int counter = 0;
		for (int i = 0; i < data.length; i++) {
			boolean empty = true;
			for (String o : data[i]) {
				if (o != null) {
					empty = false;
					break;
				}
			}
			if (empty) {
				continue;
			}

			for (String o : data[i]) {
				if (o == null) {
					table.addCell(new Paragraph("").setFont(rfont));
				} else {
					table.addCell(new Paragraph(o).setFont(rfont));
				}
			}
			counter++;
		}
		report.add(table);

		report.add(new Paragraph("Tổng số kết quả tìm được : " + counter).setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize));
		String[] dates = new String[3];
		String s = CalendarAdapter.DateToString(new Date());
		dates = s.split("-");
		report.add(new Paragraph("Hà Nội, Ngày " + dates[0] + " tháng " + dates[1] + " năm " + dates[2]).setFont(rfont).setTextAlignment(TextAlignment.RIGHT).setFontSize(fontSize).setMarginRight(10));
		report.add(new Paragraph("Người thực hiện").setFont(rfont).setTextAlignment(TextAlignment.RIGHT).setFontSize(fontSize).setMarginRight(70));
		report.add(new Paragraph("(ký ghi rõ họ tên)").setFont(rfont).setTextAlignment(TextAlignment.RIGHT).setFontSize(fontSize).setItalic().setMarginRight(40));

		report.close();
	}

	// thong ke phong 
	// , , int tongSoPhong, String[] hPhong, String[][] phongDat, String[][] phongTrong
	public void printPhong(String filePath, String tongSoPhong, String soPhongDangDat, String[][] phongDat, String soPhongTrong, String[][] phongTrong) {
		try {
			PdfWriter writer = new PdfWriter(filePath);
			PdfDocument pdf = new PdfDocument(writer);
			Document report = new Document(pdf, PageSize.A4);
			PdfFont hfont = PdfFontFactory.createFont("C:\\Windows\\Fonts\\tahoma.ttf", PdfEncodings.IDENTITY_H, true);
			PdfFont rfont = PdfFontFactory.createFont("C:\\Windows\\Fonts\\Calibri.ttf", PdfEncodings.IDENTITY_H, true);
			float fontSize = 14;

			report.setMargins(20, 20, 50, 20);

			report.add(new Paragraph("KHÁCH SẠN " + UserInfo.hotel_name.toUpperCase()).setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(25).setBold());
			report.add(new Paragraph("Địa chỉ: " + UserInfo.address).setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize));
			report.add(new Paragraph("Điện thoại: " + UserInfo.phone_no + "                        " + "Fax: " + UserInfo.fax_no).setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize));
			report.add(new Paragraph("").setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(14).setBold());
			report.add(new Paragraph("THỐNG KÊ THÔNG TIN PHÒNG ").setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(18).setBold());
			report.add(new Paragraph("Tổng số phòng: " + tongSoPhong).setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));
			float[] colWith1 = new float[]{2, 2};
/// so phong dang dat
			report.add(new Paragraph("Số phòng đang đặt: " + soPhongDangDat).setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));

			Table phongDangDat = new Table(colWith1);
			phongDangDat.setWidthPercent(50).setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);
			String[] hPhong = {"Trạng thái", "Số lượng"};
			for (String s : hPhong) {
				phongDangDat.addHeaderCell(new Paragraph(s).setFont(rfont).setBold().setTextAlignment(TextAlignment.CENTER));
			}
			for (int i = 0; i < phongDat.length; i++) {
				boolean empty = true;
				for (String o : phongDat[i]) {
					if (o != null) {
						empty = false;
						break;
					}
				}
				if (empty) {
					continue;
				}

				for (String o : phongDat[i]) {
					if (o == null) {
						phongDangDat.addCell(new Paragraph("").setFont(rfont));
					} else {
						phongDangDat.addCell(new Paragraph(o).setFont(rfont));
					}
				}
			}
			report.add(phongDangDat);

			// chi tiet phong dang trong
			report.add(new Paragraph("Số phòng đang trống: " + soPhongTrong).setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));

			Table phongDangTrong = new Table(colWith1);
			phongDangTrong.setWidthPercent(50).setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);
			for (String s : hPhong) {
				phongDangTrong.addHeaderCell(new Paragraph(s).setFont(rfont).setBold().setTextAlignment(TextAlignment.CENTER));
			}
			for (int i = 0; i < phongTrong.length; i++) {
				boolean empty = true;
				for (String o : phongTrong[i]) {
					if (o != null) {
						empty = false;
						break;
					}
				}
				if (empty) {
					continue;
				}

				for (String o : phongTrong[i]) {
					if (o == null) {
						phongDangTrong.addCell(new Paragraph("").setFont(rfont));
					} else {
						phongDangTrong.addCell(new Paragraph(o).setFont(rfont));
					}
				}
			}

			report.add(phongDangTrong);

			String[] dates = new String[3];
			String s = CalendarAdapter.DateToString(new Date());
			dates = s.split("-");
			report.add(new Paragraph("").setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(14).setBold());
			report.add(new Paragraph("Hà Nội, Ngày " + dates[0] + ", tháng " + dates[1] + ", năm " + dates[2]).setFont(rfont).setTextAlignment(TextAlignment.RIGHT).setFontSize(fontSize).setMarginRight(10));
			report.add(new Paragraph("Người thực hiện").setFont(rfont).setTextAlignment(TextAlignment.RIGHT).setFontSize(fontSize).setMarginRight(70));
			report.add(new Paragraph("(Ký, ghi rõ họ tên)").setFont(rfont).setTextAlignment(TextAlignment.RIGHT).setFontSize(fontSize).setItalic().setMarginRight(70));
			report.close();
		} catch (IOException ex) {
			Logger.getLogger(PrintPDF.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	// thong ke nhan vien 
	public void printNhanVien(String filePath, String tongSoNhanVien, String[][] gioiTinh, String[][] chucVu) {
		try {
			PdfWriter writer = new PdfWriter(filePath);
			PdfDocument pdf = new PdfDocument(writer);
			Document report = new Document(pdf, PageSize.A4);
			PdfFont hfont = PdfFontFactory.createFont("C:\\Windows\\Fonts\\tahoma.ttf", PdfEncodings.IDENTITY_H, true);
			PdfFont rfont = PdfFontFactory.createFont("C:\\Windows\\Fonts\\Calibri.ttf", PdfEncodings.IDENTITY_H, true);
			float fontSize = 14;

			report.setMargins(20, 20, 50, 20);

			report.add(new Paragraph("KHÁCH SẠN " + UserInfo.hotel_name.toUpperCase()).setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(25).setBold());
			report.add(new Paragraph("Địa chỉ: " + UserInfo.address).setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize));
			report.add(new Paragraph("Điện thoại: " + UserInfo.phone_no + "                        " + "Fax: " + UserInfo.fax_no).setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize));
			report.add(new Paragraph("").setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(14).setBold());

			report.add(new Paragraph("Tổng số nhân viên: " + tongSoNhanVien + "\n").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));
			float[] colWith1 = new float[]{2, 2};
			// theo gioi tinh
			Table tbGioiTinh = new Table(colWith1);
			tbGioiTinh.setWidthPercent(50).setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);
			report.add(new Paragraph("Thống kê theo giới tính: " + "\n").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));
			String[] hGioiTinh = {"Giới tính", "Số lượng"};
			for (String s : hGioiTinh) {
				tbGioiTinh.addHeaderCell(new Paragraph(s).setFont(rfont).setBold().setTextAlignment(TextAlignment.CENTER));
			}
			for (int i = 0; i < gioiTinh.length; i++) {
				boolean empty = true;
				for (String o : gioiTinh[i]) {
					if (o != null) {
						empty = false;
						break;
					}
				}
				if (empty) {
					continue;
				}

				for (String o : gioiTinh[i]) {
					if (o == null) {
						tbGioiTinh.addCell(new Paragraph("").setFont(rfont));
					} else {
						tbGioiTinh.addCell(new Paragraph(o).setFont(rfont));
					}
				}
			}
			report.add(tbGioiTinh);

			report.add(new Paragraph("Thống kê theo chức vụ : \n").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));

			Table tbChucVu = new Table(colWith1);
			tbChucVu.setWidthPercent(50).setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);
			String[] hChucVu = {"Chức vụ", "Số lượng"};
			for (String s : hChucVu) {
				tbChucVu.addHeaderCell(new Paragraph(s).setFont(rfont).setBold().setTextAlignment(TextAlignment.CENTER));
			}
			for (int i = 0; i < chucVu.length; i++) {
				boolean empty = true;
				for (String o : chucVu[i]) {
					if (o != null) {
						empty = false;
						break;
					}
				}
				if (empty) {
					continue;
				}

				for (String o : chucVu[i]) {
					if (o == null) {
						tbChucVu.addCell(new Paragraph("").setFont(rfont));
					} else {
						tbChucVu.addCell(new Paragraph(o).setFont(rfont));
					}
				}
			}

			report.add(tbChucVu);

			String[] dates = new String[3];
			String s = CalendarAdapter.DateToString(new Date());
			dates = s.split("-");
			report.add(new Paragraph("").setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(14).setBold());
			report.add(new Paragraph("Hà Nội, Ngày " + dates[0] + ", tháng " + dates[1] + ", năm " + dates[2]).setFont(rfont).setTextAlignment(TextAlignment.RIGHT).setFontSize(fontSize).setMarginRight(10));
			report.add(new Paragraph("Người thực hiện").setFont(rfont).setTextAlignment(TextAlignment.RIGHT).setFontSize(fontSize).setMarginRight(70));
			report.add(new Paragraph("(Ký, ghi rõ họ tên)").setFont(rfont).setTextAlignment(TextAlignment.RIGHT).setFontSize(fontSize).setItalic().setMarginRight(70));
			report.close();
		} catch (IOException ex) {
			Logger.getLogger(PrintPDF.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	// thong ke khach hang
	public void printKhachHang(String filePath, String tongSoKhachHang, String[][] gioiTinh, String[][] diaChi, String[][] quocTich) {
		try {
			PdfWriter writer = new PdfWriter(filePath);
			PdfDocument pdf = new PdfDocument(writer);
			Document report = new Document(pdf, PageSize.A4);
			PdfFont hfont = PdfFontFactory.createFont("C:\\Windows\\Fonts\\tahoma.ttf", PdfEncodings.IDENTITY_H, true);
			PdfFont rfont = PdfFontFactory.createFont("C:\\Windows\\Fonts\\Calibri.ttf", PdfEncodings.IDENTITY_H, true);
			float fontSize = 14;

			report.setMargins(20, 20, 50, 20);

			report.add(new Paragraph("KHÁCH SẠN " + UserInfo.hotel_name.toUpperCase()).setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(25).setBold());
			report.add(new Paragraph("Địa chỉ: " + UserInfo.address).setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize));
			report.add(new Paragraph("Điện thoại: " + UserInfo.phone_no + "                        " + "Fax: " + UserInfo.fax_no).setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize));
			report.add(new Paragraph("").setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(14).setBold());

			report.add(new Paragraph("Tổng số khách hàng: " + tongSoKhachHang + "\n").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));
			float[] colWith1 = new float[]{2, 2};
			// theo gioi tinh
			report.add(new Paragraph("Thống kê theo giới tính: " + "\n").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));
			Table tbGioiTinh = new Table(colWith1);
			tbGioiTinh.setWidthPercent(50).setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);
			String[] hGioiTinh = {"Giới tính", "Số lượng"};
			for (String s : hGioiTinh) {
				tbGioiTinh.addHeaderCell(new Paragraph(s).setFont(rfont).setBold().setTextAlignment(TextAlignment.CENTER));
			}
			for (int i = 0; i < gioiTinh.length; i++) {
				boolean empty = true;
				for (String o : gioiTinh[i]) {
					if (o != null) {
						empty = false;
						break;
					}
				}
				if (empty) {
					continue;
				}

				for (String o : gioiTinh[i]) {
					if (o == null) {
						tbGioiTinh.addCell(new Paragraph("").setFont(rfont));
					} else {
						tbGioiTinh.addCell(new Paragraph(o).setFont(rfont));
					}
				}
			}
			report.add(tbGioiTinh);

			report.add(new Paragraph("Thống kê theo địa chỉ  : \n").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));

			Table tbDiaChi = new Table(colWith1);
			tbDiaChi.setWidthPercent(50).setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);
			String[] hDiaChi = {"Địa chỉ", "Số lượng"};
			for (String s : hDiaChi) {
				tbDiaChi.addHeaderCell(new Paragraph(s).setFont(rfont).setBold().setTextAlignment(TextAlignment.CENTER));
			}
			for (int i = 0; i < diaChi.length; i++) {
				boolean empty = true;
				for (String o : diaChi[i]) {
					if (o != null) {
						empty = false;
						break;
					}
				}
				if (empty) {
					continue;
				}

				for (String o : diaChi[i]) {
					if (o == null) {
						tbDiaChi.addCell(new Paragraph("").setFont(rfont));
					} else {
						tbDiaChi.addCell(new Paragraph(o).setFont(rfont));
					}
				}
			}
			report.add(tbDiaChi);

			report.add(new Paragraph("Thống kê theo quốc tịch : \n").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));

			Table tbQuocTich = new Table(colWith1);
			tbQuocTich.setWidthPercent(50).setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);
			String[] hQuocTich = {"Quốc tịch", "Số lượng"};
			for (String s : hQuocTich) {
				tbQuocTich.addHeaderCell(new Paragraph(s).setFont(rfont).setBold().setTextAlignment(TextAlignment.CENTER));
			}
			for (int i = 0; i < quocTich.length; i++) {
				boolean empty = true;
				for (String o : quocTich[i]) {
					if (o != null) {
						empty = false;
						break;
					}
				}
				if (empty) {
					continue;
				}

				for (String o : quocTich[i]) {
					if (o == null) {
						tbQuocTich.addCell(new Paragraph("").setFont(rfont));
					} else {
						tbQuocTich.addCell(new Paragraph(o).setFont(rfont));
					}
				}
			}

			report.add(tbQuocTich);

			String[] dates = new String[3];
			String s = CalendarAdapter.DateToString(new Date());
			dates = s.split("-");
			report.add(new Paragraph("").setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(14).setBold());
			report.add(new Paragraph("Hà Nội, Ngày " + dates[0] + ", tháng " + dates[1] + ", năm " + dates[2]).setFont(rfont).setTextAlignment(TextAlignment.RIGHT).setFontSize(fontSize).setMarginRight(10));
			report.add(new Paragraph("Người thực hiện").setFont(rfont).setTextAlignment(TextAlignment.RIGHT).setFontSize(fontSize).setMarginRight(70));
			report.add(new Paragraph("(Ký, ghi rõ họ tên)").setFont(rfont).setTextAlignment(TextAlignment.RIGHT).setFontSize(fontSize).setItalic().setMarginRight(70));
			report.close();
		} catch (IOException ex) {
			Logger.getLogger(PrintPDF.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	// thong ke dich vu
	public void printDichVu(String filePath, String tongSoDichVu, String tongDVDuocSuDung, String[] hDichVu, String[][] dichVu, String[][] dichVuDungNhieuNhat, String[][] dichVuDungItNhat, String tongTienDV) {
		try {
			PdfWriter writer = new PdfWriter(filePath);
			PdfDocument pdf = new PdfDocument(writer);
			Document report = new Document(pdf, PageSize.A4);
			PdfFont hfont = PdfFontFactory.createFont("C:\\Windows\\Fonts\\tahoma.ttf", PdfEncodings.IDENTITY_H, true);
			PdfFont rfont = PdfFontFactory.createFont("C:\\Windows\\Fonts\\Calibri.ttf", PdfEncodings.IDENTITY_H, true);
			float fontSize = 14;

			report.setMargins(20, 20, 50, 20);

			report.add(new Paragraph("KHÁCH SẠN " + UserInfo.hotel_name.toUpperCase()).setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(25).setBold());
			report.add(new Paragraph("Địa chỉ: " + UserInfo.address).setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize));
			report.add(new Paragraph("Điện thoại: " + UserInfo.phone_no + "                        " + "Fax: " + UserInfo.fax_no).setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize));
			report.add(new Paragraph("").setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(14).setBold());

			report.add(new Paragraph("Tổng số dịch vụ: " + tongSoDichVu + "\n").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));
			report.add(new Paragraph("Tổng số dịch vụ được sử dụng: " + tongDVDuocSuDung + "\n").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));
			report.add(new Paragraph("Lượng sử dụng: " + tongDVDuocSuDung + "\n").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));
			float[] colWith1 = new float[]{2, 2};

			Table luongSuDungDV = new Table(colWith1);
			luongSuDungDV.setWidthPercent(50).setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);
			for (String s : hDichVu) {
				luongSuDungDV.addHeaderCell(new Paragraph(s).setFont(rfont).setBold().setTextAlignment(TextAlignment.CENTER));
			}
			for (int i = 0; i < dichVu.length; i++) {
				boolean empty = true;
				for (String o : dichVu[i]) {
					if (o != null) {
						empty = false;
						break;
					}
				}
				if (empty) {
					continue;
				}

				for (String o : dichVu[i]) {
					if (o == null) {
						luongSuDungDV.addCell(new Paragraph("").setFont(rfont));
					} else {
						luongSuDungDV.addCell(new Paragraph(o).setFont(rfont));
					}
				}
			}
			report.add(luongSuDungDV);

			report.add(new Paragraph("Ba dịch vụ được sử dụng nhiều nhất : \n").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));

			Table tbDVDungNhieuNhat = new Table(colWith1);
			tbDVDungNhieuNhat.setWidthPercent(50).setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);
			for (String s : hDichVu) {
				tbDVDungNhieuNhat.addHeaderCell(new Paragraph(s).setFont(rfont).setBold().setTextAlignment(TextAlignment.CENTER));
			}
			for (int i = 0; i < dichVuDungNhieuNhat.length; i++) {
				boolean empty = true;
				for (String o : dichVuDungNhieuNhat[i]) {
					if (o != null) {
						empty = false;
						break;
					}
				}
				if (empty) {
					continue;
				}

				for (String o : dichVuDungNhieuNhat[i]) {
					if (o == null) {
						tbDVDungNhieuNhat.addCell(new Paragraph("").setFont(rfont));
					} else {
						tbDVDungNhieuNhat.addCell(new Paragraph(o).setFont(rfont));
					}
				}
			}
			report.add(tbDVDungNhieuNhat);

			report.add(new Paragraph("Ba dịch vụ được sử dụng ít nhất : \n").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));

			Table tbDVDungItNhat = new Table(colWith1);
			tbDVDungItNhat.setWidthPercent(50).setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);
			for (String s : hDichVu) {
				tbDVDungItNhat.addHeaderCell(new Paragraph(s).setFont(rfont).setBold().setTextAlignment(TextAlignment.CENTER));
			}
			for (int i = 0; i < dichVuDungItNhat.length; i++) {
				boolean empty = true;
				for (String o : dichVuDungItNhat[i]) {
					if (o != null) {
						empty = false;
						break;
					}
				}
				if (empty) {
					continue;
				}

				for (String o : dichVuDungItNhat[i]) {
					if (o == null) {
						tbDVDungItNhat.addCell(new Paragraph("").setFont(rfont));
					} else {
						tbDVDungItNhat.addCell(new Paragraph(o).setFont(rfont));
					}
				}
			}

			report.add(tbDVDungItNhat);

			report.add(new Paragraph("Tổng tiền thu được từ dịch vụ: " + tongTienDV).setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));

			String[] dates = new String[3];
			String s = CalendarAdapter.DateToString(new Date());
			dates = s.split("-");
			report.add(new Paragraph("").setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(14).setBold());
			report.add(new Paragraph("Hà Nội, Ngày " + dates[0] + ", tháng " + dates[1] + ", năm " + dates[2]).setFont(rfont).setTextAlignment(TextAlignment.RIGHT).setFontSize(fontSize).setMarginRight(10));
			report.add(new Paragraph("Người thực hiện").setFont(rfont).setTextAlignment(TextAlignment.RIGHT).setFontSize(fontSize).setMarginRight(70));
			report.add(new Paragraph("(Ký, ghi rõ họ tên)").setFont(rfont).setTextAlignment(TextAlignment.RIGHT).setFontSize(fontSize).setItalic().setMarginRight(40));
			report.close();
		} catch (IOException ex) {
			Logger.getLogger(PrintPDF.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public void printDoanhThu(String filePath, String[] dateS, String tongThu, String tongTienDichVu, String tongTienPhong, String[] hloaiDV, String[] hLoaiPhong, String[][] loaiDV, String[][] loaiPhong) {
		try {
			PdfWriter writer = new PdfWriter(filePath);
			PdfDocument pdf = new PdfDocument(writer);
			Document report = new Document(pdf, PageSize.A4);
			PdfFont hfont = PdfFontFactory.createFont("C:\\Windows\\Fonts\\tahoma.ttf", PdfEncodings.IDENTITY_H, true);
			PdfFont rfont = PdfFontFactory.createFont("C:\\Windows\\Fonts\\Calibri.ttf", PdfEncodings.IDENTITY_H, true);
			float fontSize = 14;

			report.setMargins(20, 20, 50, 20);

			report.add(new Paragraph("KHÁCH SẠN " + UserInfo.hotel_name.toUpperCase()).setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(25).setBold());
			report.add(new Paragraph("Địa chỉ: " + UserInfo.address).setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize));
			report.add(new Paragraph("Điện thoại: " + UserInfo.phone_no + "                        " + "Fax: " + UserInfo.fax_no).setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize));
			report.add(new Paragraph("THỐNG KÊ THÔNG DOANH THU ").setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(18).setBold());
			report.add(new Paragraph("Từ ngày: " + dateS[0] + "  Đến ngày :" + dateS[1] + "\n").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));

			report.add(new Paragraph("").setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(14).setBold());

			report.add(new Paragraph("Theo dịch vụ: ").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));

			float[] colWith1 = new float[]{2, 2};
			Table tienDV = new Table(colWith1);
			tienDV.setWidthPercent(50).setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);
			for (String s : hloaiDV) {
				tienDV.addHeaderCell(new Paragraph(s).setFont(rfont).setBold().setTextAlignment(TextAlignment.CENTER));
			}
			for (int i = 0; i < loaiDV.length; i++) {
				boolean empty = true;
				for (String o : loaiDV[i]) {
					if (o != null) {
						empty = false;
						break;
					}
				}
				if (empty) {
					continue;
				}

				for (String o : loaiDV[i]) {
					if (o == null) {
						tienDV.addCell(new Paragraph("").setFont(rfont));
					} else {
						tienDV.addCell(new Paragraph(o).setFont(rfont));
					}
				}
			}
			report.add(tienDV);
			report.add(new Paragraph("Tổng tiền dịch vụ: " + tongTienDichVu + "\n").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));

			report.add(new Paragraph("Theo loại phòng: ").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));

			Table tienPhong = new Table(colWith1);
			tienPhong.setWidthPercent(50).setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);
			for (String s : hLoaiPhong) {
				tienPhong.addHeaderCell(new Paragraph(s).setFont(rfont).setBold().setTextAlignment(TextAlignment.CENTER));
			}
			for (int i = 0; i < loaiPhong.length; i++) {
				boolean empty = true;
				for (String o : loaiPhong[i]) {
					if (o != null) {
						empty = false;
						break;
					}
				}
				if (empty) {
					continue;
				}

				for (String o : loaiPhong[i]) {
					if (o == null) {
						tienPhong.addCell(new Paragraph("").setFont(rfont));
					} else {
						tienPhong.addCell(new Paragraph(o).setFont(rfont));
					}
				}
			}
			report.add(tienPhong);
			report.add(new Paragraph("Tổng tiền phòng: " + tongTienPhong).setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));

			report.add(new Paragraph("Tổng doanh thu: " + tongThu).setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));

			String[] dates = new String[3];
			String s = CalendarAdapter.DateToString(new Date());
			dates = s.split("-");
			report.add(new Paragraph("").setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(14).setBold());
			report.add(new Paragraph("Hà Nội, Ngày " + dates[0] + ", tháng " + dates[1] + ", năm " + dates[2]).setFont(rfont).setTextAlignment(TextAlignment.RIGHT).setFontSize(fontSize).setMarginRight(10));
			report.add(new Paragraph("Người thực hiện").setFont(rfont).setTextAlignment(TextAlignment.RIGHT).setFontSize(fontSize).setMarginRight(70));
			report.add(new Paragraph("(Ký ghi rõ họ tên)").setFont(rfont).setTextAlignment(TextAlignment.RIGHT).setFontSize(fontSize).setItalic().setMarginRight(60));
			report.close();
		} catch (IOException ex) {
			Logger.getLogger(PrintPDF.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public static void printReceipt(String filePath, HoaDon hd, KhachHang kh, Vector<Phong> room, Vector<String[]> serv, String method, double discount, double tax) throws IOException {
		PdfWriter writer = new PdfWriter(filePath);
		PdfDocument pdf = new PdfDocument(writer);
		Document report = new Document(pdf, PageSize.A4);
		PdfFont hfont = PdfFontFactory.createFont("C:\\Windows\\Fonts\\Calibri.ttf", PdfEncodings.IDENTITY_H, true);
		PdfFont rfont = PdfFontFactory.createFont("C:\\Windows\\Fonts\\Calibri.ttf", PdfEncodings.IDENTITY_H, true);
		float fontSize = 14;

		report.setMargins(20, 20, 50, 20);

		report.add(new Paragraph("KHÁCH SẠN " + UserInfo.hotel_name.toUpperCase()).setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(25).setBold());
		report.add(new Paragraph("Địa chỉ: " + UserInfo.address + "\n"
				+ "Điện thoại: " + UserInfo.phone_no + "                        " + "Fax: " + UserInfo.fax_no + "\n"
				+ "MS Thuế: " + UserInfo.tax_code).setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize));

		report.add(new Paragraph("HÓA ĐƠN THANH TOÁN").setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(18).setBold());

		report.add(new Paragraph("Tên khách hàng: " + kh.getTenKH() + "                     " + "Mã khách hàng: " + kh.getMaKH()).setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize));
		report.add(new Paragraph("Địa chỉ: " + kh.getDiaChi()).setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize));
		report.add(new Paragraph("Số điện thoại: " + kh.getSDT()).setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize));
		report.add(new Paragraph("Hình thức thanh toán: " + method).setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize));

		Table rooms = new Table(new float[]{1, 2, 2});
		rooms.setWidthPercent(100).setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);
		String[] hrooms = {"Mã phòng", "Loại phòng", "Mức giá"};
		for (String s : hrooms) {
			rooms.addHeaderCell(new Paragraph(s).setFont(rfont).setBold().setTextAlignment(TextAlignment.CENTER));
		}
		for (Phong p : room) {
			rooms.addCell(new Paragraph(p.getMaP()).setFont(rfont).setTextAlignment(TextAlignment.CENTER));
			rooms.addCell(new Paragraph(p.getLoaiPhong()).setFont(rfont).setTextAlignment(TextAlignment.CENTER));
			rooms.addCell(new Paragraph(Double.toString(p.getMucGia())).setFont(rfont).setTextAlignment(TextAlignment.CENTER));
		}
		report.add(rooms);
		report.add(new Paragraph("Tổng tiền phòng: " + hd.getTienPhong() + "đ").setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize));

		Table service = new Table(new float[]{1, 2, 1, 2, 2});
		service.setWidthPercent(100).setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);
		String[] hservice = {"Mã dịch vụ", "Tên dịch vụ", "Số lượng", "Đơn giá", "Thành tiền"};
		for (String s : hservice) {
			service.addHeaderCell(new Paragraph(s).setFont(rfont).setBold().setTextAlignment(TextAlignment.CENTER));
		}
		for (String[] s : serv) {
			for (int i = 0; i < 5; i++) {
				if (s.length <= i) {
					s[i] = "";
				}
				service.addCell(new Paragraph(s[i]).setFont(rfont).setTextAlignment(TextAlignment.CENTER));
			}
		}
		report.add(service);
		report.add(new Paragraph("Tổng tiền dịch vụ: " + hd.getTienDichvu() + "đ").setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize));

		Paragraph t1 = new Paragraph("Thành tiền: " + (hd.getTienPhong() + hd.getTienDichvu()) + "đ" + "\n" + "Giảm giá: " + discount + "đ\n" + "Thuế: " + tax + "đ").setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize);
		report.add(t1);

		report.add(new Paragraph("____________________").setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize));

		double tt = hd.getTienPhong() + hd.getTienDichvu() + tax - discount;
		Paragraph t2 = new Paragraph("Thanh toán: " + tt + "đ\n" + "Số tiền bằng chữ: " + intToString((int) tt) + " đồng").setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize);
		report.add(t2);

		report.add(new Paragraph("\nKhách hàng" + "                                   " + "Người viết hóa đơn" + "                                  " + "Quản lý" + "\n" + "(ký, ghi rõ họ tên)" + "                         " + "(ký, ghi rõ họ tên)" + "                                    " + "(ký, ghi rõ họ tên)").setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize).setBold());

		report.close();
	}

	public static String intToString(int x) {
		String s = intToString("", x);
		s = s.replaceAll("  ", " ");
		s = s.replaceAll("   ", " ");
		s = s.trim();
		s = s.substring(0, 1).toUpperCase() + s.substring(1);

		return s;
	}

	public static String intToString(String prev, int x) {
		final String[] numbers = {" không", " một", " hai", " ba", " bốn", " năm", " sáu", " bảy", " tám", " chín", " mười"};
		String s = new String();

		if (x == 0) {
			return "";
		}
		if (x <= 10) {
			return numbers[x];
		} else if (x == 15) {
			return " mười lăm";
		} else if (x < 20) {
			return " mười" + intToString("", x % 10);
		} else if (x < 100) {
			if (x % 10 == 5) {
				return intToString(prev, x / 10) + " mươi lăm";
			}
			return intToString(prev, x / 10) + " mươi " + intToString("", x % 10);
		} else if (x < 1000) {
			if (x % 100 > 9 || x % 100 == 0) {
				return intToString(prev, x / 100) + " trăm" + intToString("", x % 100);
			} else {
				return intToString(prev, x / 100) + " trăm lẻ" + intToString("", x % 100);
			}
		} else if (x < 1000000) {
			if (x % 1000 > 99 || x % 1000 == 0) {
				return intToString(prev, x / 1000) + " nghìn" + intToString("", x % 1000);
			} else if (x % 1000 > 9) {
				return intToString(prev, x / 1000) + " nghìn không trăm" + intToString("", x % 1000);
			} else {
				return intToString(prev, x / 1000) + " nghìn không trăm lẻ" + intToString("", x % 1000);
			}
		} else if (x < 1000000000) {
			if (x % 1000000 > 99999 || x % 1000000 == 0) {
				return intToString(prev, x / 1000000) + " triệu" + intToString("", x % 1000000);
			} else if (x % 1000000 > 9999) {
				return intToString(prev, x / 1000000) + " triệu không trăm" + intToString("", x % 1000000);
			} else if (x % 1000000 > 999) {
				return intToString(prev, x / 1000000) + " triệu không trăm lẻ" + intToString("", x % 1000000);
			}
		}

		return prev + s;
	}

	public static void main(String[] args) {
//		try {
//			printReceipt("test.pdf");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		System.out.println(intToString(190009));
	}
	
	public void printBaoCao(String filePath, String[] dateS, String[] dataLabel, String[][] NhanVien, String[][] KhachHang, String[][] DichVu, String[][] Phong1,String[][] Phong2) {

        try {
            PdfWriter writer = null;
            writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document report = new Document(pdf, PageSize.A4);
            PdfFont hfont = PdfFontFactory.createFont("C:\\Windows\\Fonts\\tahoma.ttf", PdfEncodings.IDENTITY_H, true);
            PdfFont rfont = PdfFontFactory.createFont("C:\\Windows\\Fonts\\Calibri.ttf", PdfEncodings.IDENTITY_H, true);
            float fontSize = 14;
            report.setMargins(20, 20, 50, 20);
            report.add(new Paragraph("KHÁCH SẠN " + UserInfo.hotel_name.toUpperCase()).setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(25).setBold());
            report.add(new Paragraph("Địa chỉ: " + UserInfo.address).setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize));
            report.add(new Paragraph("Điện thoại: " + UserInfo.phone_no + "                        " + "Fax: " + UserInfo.fax_no).setFont(rfont).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize));
            report.add(new Paragraph("BÁO CÁO HOẠT ĐỘNG KHÁCH SẠN ").setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(18).setBold());

            report.add(new Paragraph("Thông tin báo cáo ").setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(16).setBold());

            report.add(new Paragraph("Từ ngày: " + dateS[0] + "  Đến ngày :" + dateS[1] + "\n").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));
            report.add(new Paragraph("").setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(14).setBold());
            float[] colWith1 = new float[]{2, 2};

/// Nhan vien
            report.add(new Paragraph("I.Nhân viên: ").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(15));
            report.add(new Paragraph("- Tổng số nhân viên : " + dataLabel[0]).setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));
            report.add(new Paragraph("Số nhân viên theo giới tính : ").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));

            Table tbNhanvien = new Table(colWith1);
            tbNhanvien.setWidthPercent(50).setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);
//            report.add(new Paragraph("Thống kê theo giới tính: " + "\n").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));
            String[] hGioiTinh = {"Giới tính", "Số lượng"};
            for (String s : hGioiTinh) {
                tbNhanvien.addHeaderCell(new Paragraph(s).setFont(rfont).setBold().setTextAlignment(TextAlignment.CENTER));
            }
            for (int i = 0; i < NhanVien.length; i++) {
                boolean empty = true;
                for (String o : NhanVien[i]) {
                    if (o != null) {
                        empty = false;
                        break;
                    }
                }
                if (empty) {
                    continue;
                }

                for (String o : NhanVien[i]) {
                    if (o == null) {
                        tbNhanvien.addCell(new Paragraph("").setFont(rfont));
                    } else {
                        tbNhanvien.addCell(new Paragraph(o).setFont(rfont));
                    }
                }
            }
            report.add(tbNhanvien);

            //  khách hàng
            report.add(new Paragraph("II. Khách hàng : ").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(15));
            report.add(new Paragraph("- Tổng số khách hàng : " + dataLabel[1]).setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));
            report.add(new Paragraph("Số khách hàng theo giới tính : ").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));

            Table tbKhachHang = new Table(colWith1);
            tbKhachHang.setWidthPercent(50).setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);
//            report.add(new Paragraph("Thống kê theo giới tính: " + "\n").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));

            for (String s : hGioiTinh) {
                tbKhachHang.addHeaderCell(new Paragraph(s).setFont(rfont).setBold().setTextAlignment(TextAlignment.CENTER));
            }
            for (int i = 0; i < KhachHang.length; i++) {
                boolean empty = true;
                for (String o : KhachHang[i]) {
                    if (o != null) {
                        empty = false;
                        break;
                    }
                }
                if (empty) {
                    continue;
                }

                for (String o : KhachHang[i]) {
                    if (o == null) {
                        tbKhachHang.addCell(new Paragraph("").setFont(rfont));
                    } else {
                        tbKhachHang.addCell(new Paragraph(o).setFont(rfont));
                    }
                }
            }
            report.add(tbKhachHang);

            // dich vu
            report.add(new Paragraph("III. Dịch Vụ: ").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(15));
            report.add(new Paragraph("- Tổng số dịch vụ : " + dataLabel[2]).setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));
            report.add(new Paragraph("- Tổng số dịch vụ được sử dụng : " + dataLabel[3]).setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));
            report.add(new Paragraph("Lượng sử dụng : ").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));

            String[] hdichvu = {"Loại dịch vụ", "Số lượng"};

            Table tbDichVu = new Table(colWith1);
            tbDichVu.setWidthPercent(50).setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);
//            report.add(new Paragraph("Thống kê theo giới tính: " + "\n").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));

            for (String s : hdichvu) {
                tbDichVu.addHeaderCell(new Paragraph(s).setFont(rfont).setBold().setTextAlignment(TextAlignment.CENTER));
            }
            for (int i = 0; i < DichVu.length; i++) {
                boolean empty = true;
                for (String o : DichVu[i]) {
                    if (o != null) {
                        empty = false;
                        break;
                    }
                }
                if (empty) {
                    continue;
                }

                for (String o : DichVu[i]) {
                    if (o == null) {
                        tbDichVu.addCell(new Paragraph("").setFont(rfont));
                    } else {
                        tbDichVu.addCell(new Paragraph(o).setFont(rfont));
                    }
                }
            }
            report.add(tbDichVu);
            
            // Phòng
            
            report.add(new Paragraph("IV. Phòng: ").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(15));
            report.add(new Paragraph("- Tổng số phòng : " + dataLabel[4]).setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));
            report.add(new Paragraph("- Số phòng đang đặt : " + dataLabel[5]).setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));
            report.add(new Paragraph("Thống kê theo từng loại : ").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));

            String[] hphong = {"Loại phòng ", "Số lượng"};

            Table tbPhong1 = new Table(colWith1);
            tbPhong1.setWidthPercent(50).setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);
//            report.add(new Paragraph("Thống kê theo giới tính: " + "\n").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));

            for (String s : hphong) {
                tbPhong1.addHeaderCell(new Paragraph(s).setFont(rfont).setBold().setTextAlignment(TextAlignment.CENTER));
            }
            for (int i = 0; i < Phong1.length; i++) {
                boolean empty = true;
                for (String o : Phong1[i]) {
                    if (o != null) {
                        empty = false;
                        break;
                    }
                }
                if (empty) {
                    continue;
                }

                for (String o : Phong1[i]) {
                    if (o == null) {
                        tbPhong1.addCell(new Paragraph("").setFont(rfont));
                    } else {
                        tbPhong1.addCell(new Paragraph(o).setFont(rfont));
                    }
                }
            }
            report.add(tbPhong1);
            
             report.add(new Paragraph("- Số phòng đang trống : " + dataLabel[6]).setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));
            report.add(new Paragraph("Thống kê theo từng loại : ").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));

            Table tbPhong2 = new Table(colWith1);
            tbPhong2.setWidthPercent(50).setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);
//            report.add(new Paragraph("Thống kê theo giới tính: " + "\n").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));

            for (String s : hphong) {
                tbPhong2.addHeaderCell(new Paragraph(s).setFont(rfont).setBold().setTextAlignment(TextAlignment.CENTER));
            }
            for (int i = 0; i < Phong2.length; i++) {
                boolean empty = true;
                for (String o : Phong2[i]) {
                    if (o != null) {
                        empty = false;
                        break;
                    }
                }
                if (empty) {
                    continue;
                }

                for (String o : Phong2[i]) {
                    if (o == null) {
                        tbPhong2.addCell(new Paragraph("").setFont(rfont));
                    } else {
                        tbPhong2.addCell(new Paragraph(o).setFont(rfont));
                    }
                }
            }
            report.add(tbPhong2);
            
            // Doanh thu
            report.add(new Paragraph("V. Doanh thu: ").setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(15));
            report.add(new Paragraph("- Tổng tiền phòng : " + dataLabel[7]).setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));
            report.add(new Paragraph("- Tổng tiền dịch vụ : " + dataLabel[8]).setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));
            report.add(new Paragraph("- Tổng doanh thu : "+ dataLabel[9]).setFont(hfont).setTextAlignment(TextAlignment.LEFT).setFontSize(14));

             String[] dates = new String[3];
            String s = CalendarAdapter.DateToString(new Date());
            dates = s.split("-");
            report.add(new Paragraph("").setFont(hfont).setTextAlignment(TextAlignment.CENTER).setFontSize(14).setBold());
            report.add(new Paragraph("Hà Nội, Ngày " + dates[0] + ", tháng " + dates[1] + ", năm " + dates[2]).setFont(rfont).setTextAlignment(TextAlignment.RIGHT).setFontSize(fontSize).setMarginRight(10));
            report.add(new Paragraph("Người thực hiện").setFont(rfont).setTextAlignment(TextAlignment.RIGHT).setFontSize(fontSize).setMarginRight(70));
            report.add(new Paragraph("(Ký, ghi rõ họ tên)").setFont(rfont).setTextAlignment(TextAlignment.RIGHT).setFontSize(fontSize).setItalic().setMarginRight(70));
            report.close();

        } catch (IOException ex) {
            Logger.getLogger(PrintPDF.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

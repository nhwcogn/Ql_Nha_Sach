/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import dao.CTHoaDonDAO;
import dao.HoaDonDAO;
import dao.KhachHangDAO;
import dao.NhanVienDAO;
import dao.SanPhamDAO;
import entity.CT_HoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.SanPham;
import java.awt.event.ItemEvent;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.Phrase;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 *
 * @author NHU NGOC
 */
public class PnlLapHoaDonMoi extends javax.swing.JPanel {

    private HoaDonDAO hdDAO = new HoaDonDAO();
    private DefaultTableModel model;
    private KhachHangDAO khDAO = new KhachHangDAO();
    private SanPhamDAO spDAO = new SanPhamDAO();
    private NhanVienDAO nvDao = new NhanVienDAO();
    private CTHoaDonDAO cthdDao = new CTHoaDonDAO();
    /**
     * Creates new form pnlLapHoaDonMoi
     */
    public PnlLapHoaDonMoi() {
        initComponents();
        LocalTime thoiGianThuc = LocalTime.now();
        
        if(thoiGianThuc.isBefore(LocalTime.parse("15:00")))
            cmbCa.setSelectedIndex(1);
        else
            cmbCa.setSelectedIndex(2);
        cmbCa.setEnabled(false);
        txtMaHoaDon.setText(taoMaMoi());
        txtMaHoaDon.setEnabled(false);
        txtTenNV.setText("Lê Thành Đạt");
        
    }
    public static String tangChuoiSo(String str) {
            String tienTo = str.replaceAll("[0-9]", ""); // Lấy phần prefix của chuỗi (ví dụ: SP)
            String hauTo = str.replaceAll("[^0-9]", ""); // Lấy phần số của chuỗi (ví dụ: 014)
            int so = Integer.parseInt(hauTo); // Chuyển phần số về kiểu int
            so++; // Tăng số lên 1
            String newNumber = String.format("%03d", so); // Định dạng số thành chuỗi với 3 chữ số (ví dụ: "015")
            return tienTo + newNumber; // Ghép phần prefix và phần số mới thành chuỗi mới (ví dụ: "SP015")
}
    private String taoMaMoi(){
        String maCuoi = hdDAO.getMaCuoi();
        
        return tangChuoiSo(maCuoi);
    }
    public void hienThiThongBao(String thongBao, JTextField txt){
        JOptionPane.showMessageDialog(null, thongBao);
        if(txt == null)
            return;
        txt.requestFocus();
    }
    
    public void clearTextFields(){
            txtMaSP.setText("");
            txtTenSP.setText("");
            txtSoLuong.setText("");
            txtDonViTinh.setText("");
            txtDonGia.setText("");
    }
    
    public void inHoaDon() throws IOException{
    
     Document document = new Document();

        try {
        	// khởi tạo một PdfWriter truyền vào document và FileOutputStream
                
              PdfWriter.getInstance(document, new FileOutputStream(txtMaHoaDon.getText() + ".pdf"));
            
            // Create a font for text
            BaseFont baseFont = BaseFont.createFont("c:/windows/fonts/times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font vietnameseFontHead = new Font(baseFont, 7);
            Font vietnameseFont = new Font(baseFont, 12);
            Font vietnameseBoldFont = new Font(baseFont, 12, Font.BOLD);

            // mở file để thực hiện viết
            
            document.open();
            
            //Khai báo  paragraph
            Paragraph paragraph1 = new Paragraph("HIỆU SÁCH TƯ NHÂN \n", vietnameseFontHead);
            Paragraph paragraph2 = new Paragraph("HÓA ĐƠN BÁN HÀNG", vietnameseBoldFont);
            Paragraph paragraph3 = new Paragraph("Mã khách hàng: " + txtMaKH.getText()
                    + "\nTên khách hàng: " + txtTenKH.getText()
                    + "\nSố điện thoại: " +txtSDT.getText()
                    + "\nĐịa chỉ: " + txtDiaChi.getText()
                    + "\n", vietnameseFont);
            
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            
            String tongTienPhaiTra = txtThanhTien.getText();
            double tongTien = Double.parseDouble(tongTienPhaiTra);
            
            String tienKhach = txtTienKhachDua.getText();
            double tienKhachDua = Double.parseDouble(tienKhach);
            
            String tienThoi = txtTraLai.getText();
            double tienThoiLai = Double.parseDouble(tienThoi);
            
            String tongTiendd = decimalFormat.format(tongTien);
            String tienMat = decimalFormat.format(tienKhachDua);
            String tienTraLai = decimalFormat.format(tienThoiLai);
            
            Paragraph paragraph4 = new Paragraph("Tổng thành tiền: " + tongTiendd +" VND"
                    + "\nTiền mặt: " + tienMat +" VND"
                    + "\nTiền trả lại: " + tienTraLai +" VND"
                    + "\n", vietnameseFont);
            
            //Đinh dạng đoạn văn bản thứ 1
            paragraph1.setFont(vietnameseFontHead);
            paragraph1.setSpacingBefore(15);
            paragraph1.setAlignment(Element.ALIGN_LEFT);
            Date date1 = jDateNgayLap.getDate();
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
            String formattedDate = formatter1.format(date1);
            Phrase phrase1 = new Phrase("12 Nguyễn Văn Bảo, Phường 4, Gò Vấp, TP HCM \n"
                    + "Mã hóa đơn: " + txtMaHoaDon.getText()
                    + "\nNgày lập: "+ formattedDate
                    + "\nTên nhân viên: " +txtTenNV.getText()
                    + "\n");
            paragraph1.add(phrase1);
            
            //Định dạng đoạn văn bản thứ 2
            paragraph2.setFont(vietnameseBoldFont);
            paragraph2.setIndentationLeft(100);
            paragraph2.setIndentationRight(80);
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            
            //Đinh dạng đoạn văn bản thứ 3
            paragraph3.setFont(vietnameseFont);
            paragraph3.setSpacingBefore(15);
            paragraph3.setAlignment(Element.ALIGN_LEFT);
            //Thêm nội dung cho cả 2 đoạn văn bản trên
            Phrase phrase = new Phrase("Danh sách sản phẩm:\n \n");
            paragraph3.add(phrase);

            //Khởi tạo một table có 6 cột
            PdfPTable table1 = new PdfPTable(6);
            float[] columnWidths = {20f, 30f, 20f, 15f, 30f, 30f};
            table1.setWidths(columnWidths);
            table1.setTotalWidth(500f);
            table1.setLockedWidth(true);
            
            //Khởi tạo 6 ô header
            PdfPCell header1 = new PdfPCell(new Paragraph("Mã sản phẩm",vietnameseFont));
            PdfPCell header2 = new PdfPCell(new Paragraph("Tên sản phẩm",vietnameseFont));
            PdfPCell header3 = new PdfPCell(new Paragraph("Đơn vị tính",vietnameseFont));
            PdfPCell header4 = new PdfPCell(new Paragraph("Số lượng",vietnameseFont));
            PdfPCell header5 = new PdfPCell(new Paragraph("Đơn giá",vietnameseFont));
            PdfPCell header6 = new PdfPCell(new Paragraph("Thành tiền",vietnameseFont));
            
            //Thêm 6 ô header vào table
            table1.addCell(header1);
            table1.addCell(header2);
            table1.addCell(header3);
            table1.addCell(header4);
            table1.addCell(header5);
            table1.addCell(header6);
            
              // Lấy dữ liệu từ bảng và thêm vào bảng PdfPTable và set font cho mỗi ô trong bảng
              for (int i = 0; i < table.getRowCount(); i++) {
                    String maSP = table.getValueAt(i, 0).toString();
                    String tenSP = table.getValueAt(i, 1).toString();
                    String soLuong = table.getValueAt(i, 2).toString();
                    String donGia = table.getValueAt(i, 3).toString();
                    String thanhTien = table.getValueAt(i, 4).toString();
                    String donViTinh = table.getValueAt(i, 5).toString();
                    
              
                    PdfPCell cellMaSP = new PdfPCell(new Phrase(maSP, vietnameseFont));
                    cellMaSP.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                    cellMaSP.setHorizontalAlignment(Element.ALIGN_RIGHT); // căn sang phải
                    table1.addCell(cellMaSP);

                    PdfPCell cellTenSP = new PdfPCell(new Phrase(tenSP, vietnameseFont));
                    cellTenSP.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                    cellTenSP.setHorizontalAlignment(Element.ALIGN_RIGHT); // căn sang phải
                    table1.addCell(cellTenSP);

                    PdfPCell cellSoLuong = new PdfPCell(new Phrase(soLuong, vietnameseFont));
                    cellSoLuong.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                    cellSoLuong.setHorizontalAlignment(Element.ALIGN_RIGHT); // căn sang phải
                    table1.addCell(cellSoLuong);

                    PdfPCell cellDonViTinh = new PdfPCell(new Phrase(donViTinh, vietnameseFont));
                    cellDonViTinh.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                    cellDonViTinh.setHorizontalAlignment(Element.ALIGN_RIGHT); // căn sang phải
                    table1.addCell(cellDonViTinh);

                    PdfPCell cellDonGia = new PdfPCell(new Phrase(donGia, vietnameseFont));
                    cellDonGia.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                    cellDonGia.setHorizontalAlignment(Element.ALIGN_RIGHT); // căn sang phải
                    table1.addCell(cellDonGia);

                    PdfPCell cellThanhTien = new PdfPCell(new Phrase(thanhTien, vietnameseFont));
                    cellThanhTien.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                    cellThanhTien.setHorizontalAlignment(Element.ALIGN_RIGHT); // căn sang phải
                    table1.addCell(cellThanhTien);
               }
              
            //Định dạng đoạn văn bản thứ 4
            paragraph4.setFont(vietnameseFont);
            paragraph4.setSpacingBefore(15);
            paragraph4.setAlignment(Element.ALIGN_RIGHT);

            //Thêm 2 đoạn văn bản vào document
            document.add(paragraph1);
            document.add(paragraph2);
            document.add(paragraph3);
            document.add(table1);
            document.add(paragraph4);
            
            // đóng file
            document.close();
            hienThiThongBao("Hóa đơn PDF đã được tạo thành công!",null);
            
        // Hiển thị file PDF trên máy tính của người dùng
        File file = new File(txtMaHoaDon.getText() + ".pdf");
        if (file.exists()) {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        }

        } catch (FileNotFoundException | DocumentException ex) {
            Logger.getLogger(PnlLapHoaDonMoi.class.getName()).log(Level.SEVERE, null, ex);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        pnlThongTinKH = new javax.swing.JPanel();
        lblMaKH = new javax.swing.JLabel();
        lblTenKH = new javax.swing.JLabel();
        lblDiaChi = new javax.swing.JLabel();
        lblSDT = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        txtTenKH = new javax.swing.JTextField();
        txtDiaChi = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        cmbGoiYKhachHang = new javax.swing.JComboBox<>();
        pnlThongTinHoaDon = new javax.swing.JPanel();
        lblNgayLap = new javax.swing.JLabel();
        lblCa = new javax.swing.JLabel();
        lblMaHoaDon = new javax.swing.JLabel();
        txtMaHoaDon = new javax.swing.JTextField();
        jDateNgayLap = new com.toedter.calendar.JDateChooser();
        cmbCa = new javax.swing.JComboBox<>();
        txtTenNV = new javax.swing.JTextField();
        lblTenNV = new javax.swing.JLabel();
        pnlThongTinThanhTien = new javax.swing.JPanel();
        lblTienKhachDua = new javax.swing.JLabel();
        lblThanhTien = new javax.swing.JLabel();
        lblTraLai = new javax.swing.JLabel();
        txtTienKhachDua = new javax.swing.JTextField();
        txtThanhTien = new javax.swing.JTextField();
        txtTraLai = new javax.swing.JTextField();
        pnlThongTinSanPham = new javax.swing.JPanel();
        lblMaSP = new javax.swing.JLabel();
        lblTenSP = new javax.swing.JLabel();
        lblSoLuong = new javax.swing.JLabel();
        lblDonViTinh = new javax.swing.JLabel();
        lblDonGia = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        txtDonViTinh = new javax.swing.JTextField();
        txtDonGia = new javax.swing.JTextField();
        cmbGoiYTenSanPham = new javax.swing.JComboBox<>();
        txtTenSP = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btnHuy = new javax.swing.JButton();
        btnInHoaDon = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnXoaTrang = new javax.swing.JButton();
        pnlTieuDe = new javax.swing.JPanel();
        lblTieuDe = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setMaximumSize(new java.awt.Dimension(732, 432));
        setPreferredSize(new java.awt.Dimension(1307, 740));

        pnlThongTinKH.setBackground(new java.awt.Color(204, 204, 204));
        pnlThongTinKH.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblMaKH.setText("Mã KH:");

        lblTenKH.setText("Tên KH:");

        lblDiaChi.setText("Địa chỉ:");

        lblSDT.setText("SĐT:");

        txtMaKH.setBackground(new java.awt.Color(232, 228, 228));
        txtMaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKHActionPerformed(evt);
            }
        });

        txtTenKH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTenKHKeyReleased(evt);
            }
        });

        txtDiaChi.setBackground(new java.awt.Color(232, 228, 228));

        cmbGoiYKhachHang.setVisible(false);
        cmbGoiYKhachHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Tên khách hàng --" }));
        cmbGoiYKhachHang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbGoiYKhachHangItemStateChanged(evt);
            }
        });
        cmbGoiYKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbGoiYKhachHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongTinKHLayout = new javax.swing.GroupLayout(pnlThongTinKH);
        pnlThongTinKH.setLayout(pnlThongTinKHLayout);
        pnlThongTinKHLayout.setHorizontalGroup(
            pnlThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinKHLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMaKH)
                    .addComponent(lblTenKH)
                    .addGroup(pnlThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblSDT)
                        .addComponent(lblDiaChi)))
                .addGap(35, 35, 35)
                .addGroup(pnlThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtDiaChi)
                        .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmbGoiYKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62))
        );
        pnlThongTinKHLayout.setVerticalGroup(
            pnlThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinKHLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaKH))
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenKH)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(cmbGoiYKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDiaChi)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(pnlThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSDT)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlThongTinHoaDon.setBackground(new java.awt.Color(204, 204, 204));
        pnlThongTinHoaDon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblNgayLap.setText("Ngày lập:");

        lblCa.setText("Ca:");

        lblMaHoaDon.setText("Mã hóa đơn:");

        cmbCa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Ca--", "Sáng", "Chiều" }));
        cmbCa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCaItemStateChanged(evt);
            }
        });
        cmbCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCaActionPerformed(evt);
            }
        });

        lblTenNV.setText("Tên Nhân Viên");

        javax.swing.GroupLayout pnlThongTinHoaDonLayout = new javax.swing.GroupLayout(pnlThongTinHoaDon);
        pnlThongTinHoaDon.setLayout(pnlThongTinHoaDonLayout);
        pnlThongTinHoaDonLayout.setHorizontalGroup(
            pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMaHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCa, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNgayLap, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTenNV))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTenNV)
                    .addComponent(cmbCa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMaHoaDon)
                    .addComponent(jDateNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlThongTinHoaDonLayout.setVerticalGroup(
            pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinHoaDonLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNgayLap))
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCa)
                    .addComponent(cmbCa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaHoaDon)
                    .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTenNV))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pnlThongTinThanhTien.setBackground(new java.awt.Color(204, 204, 204));
        pnlThongTinThanhTien.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblTienKhachDua.setText("Khách đưa:");

        lblThanhTien.setText("Thành tiền:");

        lblTraLai.setText("Trả lại:");

        txtTienKhachDua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienKhachDuaActionPerformed(evt);
            }
        });

        txtThanhTien.setBackground(new java.awt.Color(232, 228, 228));

        txtTraLai.setBackground(new java.awt.Color(245, 241, 241));
        txtTraLai.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout pnlThongTinThanhTienLayout = new javax.swing.GroupLayout(pnlThongTinThanhTien);
        pnlThongTinThanhTien.setLayout(pnlThongTinThanhTienLayout);
        pnlThongTinThanhTienLayout.setHorizontalGroup(
            pnlThongTinThanhTienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinThanhTienLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThongTinThanhTienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinThanhTienLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lblTraLai))
                    .addComponent(lblThanhTien)
                    .addComponent(lblTienKhachDua))
                .addGap(39, 39, 39)
                .addGroup(pnlThongTinThanhTienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtThanhTien)
                    .addComponent(txtTienKhachDua)
                    .addComponent(txtTraLai, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlThongTinThanhTienLayout.setVerticalGroup(
            pnlThongTinThanhTienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinThanhTienLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinThanhTienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTienKhachDua)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinThanhTienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblThanhTien)
                    .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinThanhTienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTraLai)
                    .addComponent(txtTraLai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pnlThongTinSanPham.setBackground(new java.awt.Color(204, 204, 204));
        pnlThongTinSanPham.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblMaSP.setText("Mã sản phẩm:");

        lblTenSP.setText("Tên sản phẩm:");

        lblSoLuong.setText("Số lượng:");

        lblDonViTinh.setText("Đơn vị tính:");

        lblDonGia.setText("Đơn giá:");

        cmbGoiYTenSanPham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Tên sản phẩm--" }));
        cmbGoiYTenSanPham.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbGoiYTenSanPhamItemStateChanged(evt);
            }
        });

        txtTenSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongTinSanPhamLayout = new javax.swing.GroupLayout(pnlThongTinSanPham);
        pnlThongTinSanPham.setLayout(pnlThongTinSanPhamLayout);
        pnlThongTinSanPhamLayout.setHorizontalGroup(
            pnlThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinSanPhamLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinSanPhamLayout.createSequentialGroup()
                        .addComponent(lblMaSP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinSanPhamLayout.createSequentialGroup()
                        .addComponent(lblTenSP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbGoiYTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinSanPhamLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblSoLuong))
                    .addComponent(lblDonViTinh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDonViTinh)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblDonGia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90))
        );
        pnlThongTinSanPhamLayout.setVerticalGroup(
            pnlThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaSP)
                    .addComponent(lblSoLuong)
                    .addComponent(lblDonGia)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenSP)
                    .addComponent(lblDonViTinh)
                    .addComponent(txtDonViTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(cmbGoiYTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        String[] heads = {
            "Mã sản phẩm", "Tên sản phẩm", "Số lượng","Đơn giá","Thành tiền","Đơn vị tính"
        };
        model = new DefaultTableModel(heads, 0);
        table.setModel(model);
        table.setGridColor(new java.awt.Color(0, 0, 0));
        table.setShowGrid(true);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table);

        btnHuy.setBackground(new java.awt.Color(255, 102, 102));
        btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancel.png"))); // NOI18N
        btnHuy.setText("Hủy");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        btnInHoaDon.setBackground(new java.awt.Color(0, 153, 204));
        btnInHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print.png"))); // NOI18N
        btnInHoaDon.setText("In hóa đơn");
        btnInHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInHoaDonActionPerformed(evt);
            }
        });

        btnLuu.setBackground(new java.awt.Color(51, 204, 0));
        btnLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N
        btnLuu.setText("Lưu");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(51, 204, 0));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoaTrang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eraser.png"))); // NOI18N
        btnXoaTrang.setText("Xóa trắng");
        btnXoaTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTrangActionPerformed(evt);
            }
        });

        pnlTieuDe.setBackground(new java.awt.Color(0, 153, 153));

        lblTieuDe.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTieuDe.setText("LẬP HÓA ĐƠN");

        javax.swing.GroupLayout pnlTieuDeLayout = new javax.swing.GroupLayout(pnlTieuDe);
        pnlTieuDe.setLayout(pnlTieuDeLayout);
        pnlTieuDeLayout.setHorizontalGroup(
            pnlTieuDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTieuDeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTieuDe)
                .addGap(551, 551, 551))
        );
        pnlTieuDeLayout.setVerticalGroup(
            pnlTieuDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTieuDeLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(lblTieuDe)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTieuDe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(133, 133, 133)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(pnlThongTinKH, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlThongTinHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlThongTinThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pnlThongTinSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 789, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnXoaTrang)))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 958, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 214, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(btnHuy)
                        .addGap(222, 222, 222)
                        .addComponent(btnInHoaDon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLuu)
                        .addGap(289, 289, 289))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlThongTinThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnlThongTinKH, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pnlThongTinHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlThongTinSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaTrang)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHuy)
                    .addComponent(btnInHoaDon)
                    .addComponent(btnLuu))
                .addContainerGap(195, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        // TODO add your handling code here:
        Date utilDate = jDateNgayLap.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        
        KhachHang kh = khDAO.timKiem1KhachHangTheoMaKH(txtMaKH.getText());
        NhanVien nv = nvDao.tim1NhanVienVoiTenNV(txtTenNV.getText());
        HoaDon hd = new HoaDon(txtMaHoaDon.getText(), sqlDate, kh, nv);
        System.out.println(hd);
        
        if(hdDAO.themHoaDon(hd)){
            hienThiThongBao("Thêm thành công !", txtSDT);
            for(int i = 0; i < table.getRowCount(); i++){
                String maSP = table.getValueAt(i, 0).toString();
                SanPham sp = spDAO.timKiemCoSach(maSP);
                
                int soLuong = Integer.parseInt(table.getValueAt(i, 2).toString());
                double donGia = Double.parseDouble(table.getValueAt(i, 3).toString());
                double dongia01 = donGia;
                CT_HoaDon cthd = new CT_HoaDon(soLuong, dongia01, hd, sp);
                if(cthdDao.themCT_HoaDon(cthd)){
                    // hienThiThongBao("Them chi tiet hoa don thanh cong", txtSDT);
                } else {
                    hienThiThongBao("Chưa có sản phẩm nào được mua", txtSDT);
                }
            }
            // ManHinhChinh.getPnlChuongTrinh().removeAll();
            // ManHinhChinh.getPnlChuongTrinh().add(new PnlLapHoaDonMoi());
        } else {
            hienThiThongBao("Thêm thất bại", txtSDT);
        }
    }//GEN-LAST:event_btnLuuActionPerformed


    private void btnInHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInHoaDonActionPerformed
        try {
            // TODO add your handling code here:
            inHoaDon();
        } catch (IOException ex) {
            Logger.getLogger(PnlLapHoaDonMoi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnInHoaDonActionPerformed

    private void txtTenKHKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenKHKeyReleased
        
        ArrayList<String> dsKH = khDAO.timKiemDanhSachTenKHTheoKey(txtTenKH.getText());
        if(txtTenKH.getText().isBlank())
            cmbGoiYKhachHang.setVisible(false);
        
        if(dsKH.isEmpty()){
           for(int i = cmbGoiYKhachHang.getItemCount()-1; i > 0; i--)
                cmbGoiYKhachHang.removeItemAt(i);
            //cmbGoiYKhachHang.setVisible(false);
        
        }
        else{
            cmbGoiYKhachHang.setVisible(true);
            //HashSet<String> newHashet02 = new HashSet<>(dsKH);
            //ArrayList<String> newList02 = new ArrayList<>(newHashet02);
            System.out.println(dsKH);
            for(int i = cmbGoiYKhachHang.getItemCount()-1; i > 0; i--)
                cmbGoiYKhachHang.removeItemAt(i);
            for(String tenKH : dsKH){
                cmbGoiYKhachHang.addItem(tenKH);
            }
            //cmbGoiYKhachHang.setPopupVisible(true);
        }
        
        
    }//GEN-LAST:event_txtTenKHKeyReleased

    private void cmbGoiYKhachHangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbGoiYKhachHangItemStateChanged
        // TODO add your handling code here:
            if(cmbGoiYKhachHang.getSelectedIndex() == 0){
               
            }
            else{
                txtTenKH.setText(cmbGoiYKhachHang.getSelectedItem().toString());
                KhachHang kh = khDAO.timKiem1KhachHangTheoTen(txtTenKH.getText());
                txtMaKH.setText(kh.getMaKH());
                txtSDT.setText(kh.getsDT());
                txtDiaChi.setText(kh.getDiaChi());
                cmbGoiYKhachHang.setVisible(false);
                txtMaKH.setEditable(false);
                txtDiaChi.setEditable(false);
            }   
    }//GEN-LAST:event_cmbGoiYKhachHangItemStateChanged

    private void cmbGoiYKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbGoiYKhachHangActionPerformed
        // TODO add your handling code here:
        //txtTenKH.setText(cmbGoiYKhachHang.getSelectedItem().toString());
        //cmbGoiYKhachHang.setVisible(false);
    }//GEN-LAST:event_cmbGoiYKhachHangActionPerformed

    private void txtMaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaKHActionPerformed

    private void cmbCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCaActionPerformed
        // TODO add your handling code here:
//        LocalTime thoiGianThuc = LocalTime.now();
//        if(thoiGianThuc.isBefore(LocalTime.parse("15:00")))
//            cmbCa.setSelectedIndex(1);
//        else
//            cmbCa.setSelectedIndex(2);
    }//GEN-LAST:event_cmbCaActionPerformed

    private void cmbCaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCaItemStateChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_cmbCaItemStateChanged

    private void txtTenSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenSPActionPerformed
        // TODO add your handling code here:
        ArrayList<String> dsSP = spDAO.timDSTenSanPhamTheokey(txtTenSP.getText());
        if(txtTenSP.getText().isBlank())
            cmbGoiYTenSanPham.setVisible(false);
        
        if(dsSP.isEmpty()){
           for(int i = cmbGoiYTenSanPham.getItemCount()-1; i > 0; i--)
                cmbGoiYTenSanPham.removeItemAt(i);
            //cmbGoiYKhachHang.setVisible(false);
        
        }
        else{
            cmbGoiYTenSanPham.setVisible(true);
            //HashSet<String> newHashet02 = new HashSet<>(dsKH);
            //ArrayList<String> newList02 = new ArrayList<>(newHashet02);
            System.out.println(dsSP);
            for(int i = cmbGoiYTenSanPham.getItemCount()-1; i > 0; i--)
                cmbGoiYTenSanPham.removeItemAt(i);
            for(String tenSP : dsSP){
                cmbGoiYTenSanPham.addItem(tenSP);
            }
            //cmbGoiYKhachHang.setSelectedIndex(1);
        }
    }//GEN-LAST:event_txtTenSPActionPerformed

    private void cmbGoiYTenSanPhamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbGoiYTenSanPhamItemStateChanged
        // TODO add your handling code here:
        if(cmbGoiYTenSanPham.getSelectedIndex() == 0){
               
            }
            else{
                txtTenSP.setText(cmbGoiYTenSanPham.getSelectedItem().toString());
                SanPham sp = spDAO.timKiem1SanPhamTheoTen(txtTenSP.getText());
                txtMaSP.setText(sp.getMa_SanPham());
                txtDonViTinh.setText(sp.getDvTinh());
                txtDonGia.setText(String.valueOf(sp.getDonGia()));
                cmbGoiYTenSanPham.setVisible(false);
                txtMaSP.setEditable(false);
                txtDonViTinh.setEditable(false);
            }   
    }//GEN-LAST:event_cmbGoiYTenSanPhamItemStateChanged
/*
    
    "Mã sản phẩm", "Tên sản phẩm", 
    "Số lượng","Đơn giá",
    "Thành tiền","Đơn vị tính"*/
    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if(true){
            model.addRow(new Object[]{
                txtMaSP.getText(),txtTenSP.getText(), txtSoLuong.getText(), txtDonGia.getText(), Integer.valueOf(txtSoLuong.getText())* Double.valueOf(txtDonGia.getText()), txtDonViTinh.getText()
            });
            double tongTien = 0;
            for(int i = 0; i< table.getRowCount(); i++){
                double tien = Double.valueOf(table.getValueAt(i, 4).toString());
                tongTien += tien;
            }
            txtThanhTien.setText(String.valueOf(tongTien));
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void txtTienKhachDuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienKhachDuaActionPerformed
        // TODO add your handling code here:
        double tienKhachDua = Double.valueOf(txtTienKhachDua.getText());
        double tienThua = tienKhachDua-Double.valueOf(txtThanhTien.getText());
        DecimalFormat format = new DecimalFormat("#,###");
        
//        String stringThanhTien = txtThanhTien.getText();
//        if(stringThanhTien.matches("^\\d{1,3}(,\\d{3})*$"))
//            double tien = format.paser(stringThanhTien).doubleValue ;
            
        double thanhTien = Double.valueOf(txtThanhTien.getText());
        txtThanhTien.setText(String.valueOf(thanhTien));
        txtTraLai.setText(String.valueOf(tienThua));
    }//GEN-LAST:event_txtTienKhachDuaActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 2){
            int row = table.getSelectedRow();
            model = (DefaultTableModel) table.getModel();
            model.removeRow(row);
            model.fireTableDataChanged();
        }
    }//GEN-LAST:event_tableMouseClicked

    private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTrangActionPerformed
        // TODO add your handling code here:
        clearTextFields();
    }//GEN-LAST:event_btnXoaTrangActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        // TODO add your handling code here:
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtDiaChi.setText("");
        txtSDT.setText("");
        
        txtTienKhachDua.setText("");
        txtThanhTien.setText("");
        txtTraLai.setText("");
        clearTextFields();
        model.setRowCount(0);
    }//GEN-LAST:event_btnHuyActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnInHoaDon;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoaTrang;
    private javax.swing.JComboBox<String> cmbCa;
    private javax.swing.JComboBox<String> cmbGoiYKhachHang;
    private javax.swing.JComboBox<String> cmbGoiYTenSanPham;
    private com.toedter.calendar.JDateChooser jDateNgayLap;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblCa;
    private javax.swing.JLabel lblDiaChi;
    private javax.swing.JLabel lblDonGia;
    private javax.swing.JLabel lblDonViTinh;
    private javax.swing.JLabel lblMaHoaDon;
    private javax.swing.JLabel lblMaKH;
    private javax.swing.JLabel lblMaSP;
    private javax.swing.JLabel lblNgayLap;
    private javax.swing.JLabel lblSDT;
    private javax.swing.JLabel lblSoLuong;
    private javax.swing.JLabel lblTenKH;
    private javax.swing.JLabel lblTenNV;
    private javax.swing.JLabel lblTenSP;
    private javax.swing.JLabel lblThanhTien;
    private javax.swing.JLabel lblTienKhachDua;
    private javax.swing.JLabel lblTieuDe;
    private javax.swing.JLabel lblTraLai;
    private javax.swing.JPanel pnlThongTinHoaDon;
    private javax.swing.JPanel pnlThongTinKH;
    private javax.swing.JPanel pnlThongTinSanPham;
    private javax.swing.JPanel pnlThongTinThanhTien;
    private javax.swing.JPanel pnlTieuDe;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtDonViTinh;
    private javax.swing.JTextField txtMaHoaDon;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtThanhTien;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTraLai;
    // End of variables declaration//GEN-END:variables

}
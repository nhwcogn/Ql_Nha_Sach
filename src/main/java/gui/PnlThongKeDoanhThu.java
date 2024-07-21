/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.HoaDonDAO;
import entity.HoaDon;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author letha
 */
public class PnlThongKeDoanhThu extends javax.swing.JPanel {

    /**
     * Creates new form FrmThongKeSanPham
     */
    private HoaDonDAO hdDAO = new HoaDonDAO();
    private DefaultTableModel model;
    public PnlThongKeDoanhThu() {
        initComponents();
        loadTable();
        //int tongHD = table.getRowCount();
        txtTongSoLuong.setText(String.valueOf(table.getRowCount()));
        
        txtTongDoanhThu.setText(String.valueOf(tongTien(table, 4)));
    }
    
    public String  tongTien(JTable table, int indexColumn){
        double t = 0;
        for(int i = 0; i<table.getRowCount(); i++){
            Object value = table.getValueAt(i, indexColumn);
            if(value instanceof Double){
                double amount = (double) value;
                t += amount;
            }
        }
        // Tạo đối tượng DecimalFormat với mẫu "#,### VND"
        DecimalFormat decimalFormat = new DecimalFormat("#,### VND");

        // Định dạng tổng tiền
        String formattedAmount = decimalFormat.format(t);
        return formattedAmount;
    }
    
    /*
    "Mã hóa đơn", "Tên khách hàng", "Tên nhân viên", "Ngày tạo", "Tổng tiền"
    */
    
    public void loadTable(){
        ArrayList<HoaDon> list = hdDAO.danhSachHoaDon();
        
        for (HoaDon h : list) {
            double s = hdDAO.tongGiaTriKhiBietMaHD(h.getMaHoaDon());
            System.out.println(s);
            model.addRow(new Object[]{h.getMaHoaDon(), h.getKhachHang().getTenKH(),
                h.getNhanVien().getTenNV(), h.getNgayLap(),
                hdDAO.tongGiaTriKhiBietMaHD(h.getMaHoaDon())
            });
        }
//        TableColumnModel columnModel = table.getColumnModel();
//        
//        int columIndex = 4;
//        DefaultTableCellRenderer rightRender = new DefaultTableCellRenderer();
//        rightRender.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
//        columnModel.getColumn(columIndex).setCellRenderer(rightRender);
    }
    public void loadTableCoDKNgay(Date fromDate, Date toDate){
        ArrayList<HoaDon> list = hdDAO.dsHoaDonCoDieuKienNgay(fromDate, toDate);
        for(HoaDon h : list){
            double s = hdDAO.tongGiaTriKhiBietMaHD(h.getMaHoaDon());
            System.out.println(s);
            model.addRow(new Object[]{h.getMaHoaDon(), h.getKhachHang().getTenKH(),
                h.getNhanVien().getTenNV(), h.getNgayLap(),
                hdDAO.tongGiaTriKhiBietMaHD(h.getMaHoaDon())
            });
        }
    }
    private void clearTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }
    
    public void hienThiThongBao(String thongBao, JTextField txt) {
        JOptionPane.showMessageDialog(null, thongBao);
        if (txt == null) {
            return;
        }
        txt.requestFocus();
    }
    
    public void inThongKe() throws IOException{
        Document document = new Document();
        
        try {
            try {
                PdfWriter.getInstance(document, new FileOutputStream("ThongKeDoanhThu.pdf"));
                
                // Create a font for text
            BaseFont baseFont = BaseFont.createFont("c:/windows/fonts/times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font vietnameseFont = new Font(baseFont, 12);
            Font vietnameseBoldFont = new Font(baseFont, 12, Font.BOLD);
            
            
            
            document.open();
            
            //Khai báo  paragraph
            Paragraph paragraph1 = new Paragraph("HIỆU SÁCH TƯ NHÂN \n", vietnameseFont);
            Paragraph paragraph2 = new Paragraph("THỐNG KÊ DOANH THU\n\n", vietnameseBoldFont);
            
            
            paragraph1.setFont(vietnameseFont);
            paragraph1.setSpacingBefore(15);
            paragraph1.setAlignment(Element.ALIGN_LEFT);
            
            //Định dạng đoạn văn bản thứ 2
            paragraph2.setFont(vietnameseBoldFont);
            paragraph2.setIndentationLeft(100);
            paragraph2.setIndentationRight(80);
            paragraph2.setSpacingAfter(10);
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            
            Paragraph paragraph3 = new Paragraph("Tổng hóa đơn : " + txtTongSoLuong.getText() +"\n Tổng doanh thu: "+ txtTongDoanhThu.getText(), vietnameseFont);
            
            paragraph3.setFont(vietnameseFont);
            paragraph3.setSpacingBefore(15);
            paragraph3.setAlignment(Element.ALIGN_RIGHT);
            
            
            
            //Khởi tạo một table có 6 cột
            PdfPTable table1 = new PdfPTable(5);
            float[] columnWidths = {15f, 30f, 30f, 15f, 15f};
            table1.setWidths(columnWidths);
            table1.setTotalWidth(500f);
            table1.setLockedWidth(true);
            
            PdfPCell header1 = new PdfPCell(new Paragraph("Mã hóa đơn",vietnameseFont));
            PdfPCell header2 = new PdfPCell(new Paragraph("Tên khách hàng",vietnameseFont));
            PdfPCell header3 = new PdfPCell(new Paragraph("Tên nhân viên",vietnameseFont));
            PdfPCell header4 = new PdfPCell(new Paragraph("Ngày tạo",vietnameseFont));
            PdfPCell header5 = new PdfPCell(new Paragraph("Tổng tiền",vietnameseFont));
            
            table1.addCell(header1);
            table1.addCell(header2);
            table1.addCell(header3);
            table1.addCell(header4);
            table1.addCell(header5);
            
            for (int i = 0; i < table.getRowCount(); i++) {
                    String maHD = table.getValueAt(i, 0).toString();
                    String tenKH = table.getValueAt(i, 1).toString();
                    String tenNV = table.getValueAt(i, 2).toString();
                    String ngayTao = table.getValueAt(i, 3).toString();
                    String tongTien = table.getValueAt(i, 4).toString();
                    
              
                    PdfPCell cellMaHD = new PdfPCell(new Phrase(maHD, vietnameseFont));
                    cellMaHD.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                    cellMaHD.setHorizontalAlignment(Element.ALIGN_RIGHT); // căn sang phải
                    table1.addCell(cellMaHD);

                    PdfPCell cellTenKH = new PdfPCell(new Phrase(tenKH, vietnameseFont));
                    cellTenKH.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                    cellTenKH.setHorizontalAlignment(Element.ALIGN_RIGHT); // căn sang phải
                    table1.addCell(cellTenKH);

                    PdfPCell cellTenNV = new PdfPCell(new Phrase(tenNV, vietnameseFont));
                    cellTenNV.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                    cellTenNV.setHorizontalAlignment(Element.ALIGN_RIGHT); // căn sang phải
                    table1.addCell(cellTenNV);

                    PdfPCell cellNgayTao = new PdfPCell(new Phrase(ngayTao, vietnameseFont));
                    cellNgayTao.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                    cellNgayTao.setHorizontalAlignment(Element.ALIGN_RIGHT); // căn sang phải
                    table1.addCell(cellNgayTao);

                    PdfPCell cellTongTien = new PdfPCell(new Phrase(tongTien, vietnameseFont));
                    cellTongTien.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                    cellTongTien.setHorizontalAlignment(Element.ALIGN_RIGHT); // căn sang phải
                    table1.addCell(cellTongTien);
                }
            
            document.add(paragraph1);
            document.add(paragraph2);
            document.add(table1);
            document.add(paragraph3);
            
            // đóng file
            document.close();
            hienThiThongBao("Hóa đơn PDF đã được tạo thành công!",null);
            
            // Hiển thị file PDF trên máy tính của người dùng
        File file = new File("ThongKeDoanhThu.pdf");
        if (file.exists()) {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        }
            
            } catch (DocumentException ex) {
                Logger.getLogger(PnlThongKeDoanhThu.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PnlThongKeDoanhThu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    private boolean kiemTraDuLieu(){
        Date hienTai = new Date();
        if(dateChooseNgayBatDau.getDate().after(dateChooseNgayKetThuc.getDate())){
            hienThiThongBao("Ngày bắt đầu phải trước ngày kết thúc", null);
            return false;
        }
        if(dateChooseNgayBatDau.getDate().after(hienTai)|| dateChooseNgayKetThuc.getDate().after(hienTai)){
            hienThiThongBao("Ngày bắt đầu và kết thúc không được sau ngày hiện tại", null);
            return false;
        }
        
        return true;
            
            
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTieuDe = new javax.swing.JPanel();
        lblTieuDe = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btnThongKe = new javax.swing.JButton();
        btnXuatFile = new javax.swing.JButton();
        lblTongSanLuong = new javax.swing.JLabel();
        lblTongDoanhThu = new javax.swing.JLabel();
        txtTongSoLuong = new javax.swing.JTextField();
        txtTongDoanhThu = new javax.swing.JTextField();
        pnlThongTin = new javax.swing.JPanel();
        dateChooseNgayBatDau = new com.toedter.calendar.JDateChooser();
        dateChooseNgayKetThuc = new com.toedter.calendar.JDateChooser();
        lblNgayBatDau = new javax.swing.JLabel();
        lblNgayKetThuc = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 204));

        pnlTieuDe.setBackground(new java.awt.Color(0, 153, 153));

        lblTieuDe.setText("Thống kê doanh thu");

        javax.swing.GroupLayout pnlTieuDeLayout = new javax.swing.GroupLayout(pnlTieuDe);
        pnlTieuDe.setLayout(pnlTieuDeLayout);
        pnlTieuDeLayout.setHorizontalGroup(
            pnlTieuDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTieuDeLayout.createSequentialGroup()
                .addGap(550, 550, 550)
                .addComponent(lblTieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlTieuDeLayout.setVerticalGroup(
            pnlTieuDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTieuDeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTieuDe)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        String[] heads =  {
            "Mã hóa đơn", "Tên khách hàng", "Tên nhân viên", "Ngày tạo", "Tổng tiền"
        };
        model = new DefaultTableModel(heads, 0);
        table.setModel(model);
        table.setShowGrid(true);
        jScrollPane1.setViewportView(table);

        btnThongKe.setBackground(new java.awt.Color(51, 102, 255));
        btnThongKe.setText("Thống kê");
        btnThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongKeActionPerformed(evt);
            }
        });

        btnXuatFile.setBackground(new java.awt.Color(0, 153, 0));
        btnXuatFile.setForeground(new java.awt.Color(0, 51, 51));
        btnXuatFile.setText("Xuất file");
        btnXuatFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatFileActionPerformed(evt);
            }
        });

        lblTongSanLuong.setText("Tổng hóa đơn");

        lblTongDoanhThu.setText("Tổng doanh thu");

        lblNgayBatDau.setText("Từ ngày");

        lblNgayKetThuc.setText("Đến ngày");

        javax.swing.GroupLayout pnlThongTinLayout = new javax.swing.GroupLayout(pnlThongTin);
        pnlThongTin.setLayout(pnlThongTinLayout);
        pnlThongTinLayout.setHorizontalGroup(
            pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblNgayBatDau)
                .addGap(18, 18, 18)
                .addComponent(dateChooseNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(lblNgayKetThuc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dateChooseNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        pnlThongTinLayout.setVerticalGroup(
            pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateChooseNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNgayBatDau)
                    .addComponent(dateChooseNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNgayKetThuc))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTieuDe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(pnlThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnXuatFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1054, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblTongSanLuong)
                            .addGap(54, 54, 54)
                            .addComponent(txtTongSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblTongDoanhThu)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTongDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(107, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(btnThongKe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnXuatFile))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(pnlThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTongSanLuong)
                    .addComponent(txtTongSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTongDoanhThu)
                    .addComponent(txtTongDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(79, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeActionPerformed
        // TODO add your handling code here:
        if(kiemTraDuLieu()){
            clearTable();
            loadTableCoDKNgay(dateChooseNgayBatDau.getDate(), dateChooseNgayKetThuc.getDate());
        }
        txtTongSoLuong.setText(String.valueOf(table.getRowCount()));
        
        txtTongDoanhThu.setText(String.valueOf(tongTien(table, 4)));
        
        
    }//GEN-LAST:event_btnThongKeActionPerformed

    private void btnXuatFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatFileActionPerformed
        try {
            // TODO add your handling code here:
            inThongKe();
        } catch (IOException ex) {
            Logger.getLogger(PnlThongKeDoanhThu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnXuatFileActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThongKe;
    private javax.swing.JButton btnXuatFile;
    private com.toedter.calendar.JDateChooser dateChooseNgayBatDau;
    private com.toedter.calendar.JDateChooser dateChooseNgayKetThuc;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblNgayBatDau;
    private javax.swing.JLabel lblNgayKetThuc;
    private javax.swing.JLabel lblTieuDe;
    private javax.swing.JLabel lblTongDoanhThu;
    private javax.swing.JLabel lblTongSanLuong;
    private javax.swing.JPanel pnlThongTin;
    private javax.swing.JPanel pnlTieuDe;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtTongDoanhThu;
    private javax.swing.JTextField txtTongSoLuong;
    // End of variables declaration//GEN-END:variables
}

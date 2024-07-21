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
import dao.KhachHangDAO;
import entity.KhachHang;
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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author letha
 */
public class PnlThongKeKH extends javax.swing.JPanel {

    /**
     * Creates new form FrmThongKeSanPham
     */
    private KhachHangDAO khDAO = new KhachHangDAO();
    private DefaultTableModel model;
    public PnlThongKeKH() {
        initComponents();
        loadTable();
        
        txtSLDonHang.setText(String.valueOf(tongSL(table, 4)));
        txtSLKhachHang.setText(String.valueOf(table.getRowCount()));
    }
    
    public void loadTable(){
       ArrayList<KhachHang> list = khDAO.dsKHDaMuaHang();
       for(KhachHang kh : list){
           model.addRow(new Object[]{
               kh.getMaKH(), kh.getTenKH(),kh.getsDT(), kh.getDiaChi(),
               khDAO.slDonHang(kh.getMaKH())
            });
       }
    }
    public void loadTableCoDKNgay(Date fromDate, Date toDate){
       ArrayList<KhachHang> list = khDAO.dsKHDaMuaHangCoDKNgay(fromDate, toDate);
       for(KhachHang kh : list){
           model.addRow(new Object[]{
               kh.getMaKH(), kh.getTenKH(),kh.getsDT(), kh.getDiaChi(),
               khDAO.slDonHang(kh.getMaKH())
            });
       }
    }
    
    public int  tongSL(JTable table, int indexColumn){
        int t = 0;
        for(int i = 0; i<table.getRowCount(); i++){
            Object value = table.getValueAt(i, indexColumn);
            if(value instanceof Integer){
                int amount = (int) value;
                t += amount;
            }
        }
        
        return t;
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
    private boolean kiemTraDuLieu(){
        Date hienTai = new Date();
        if(datechooseTuNgay.getDate().after(datechooseDenNgay.getDate())){
            hienThiThongBao("Ngày bắt đầu phải trước ngày kết thúc", null);
            return false;
        }
        if(datechooseTuNgay.getDate().after(hienTai)|| datechooseDenNgay.getDate().after(hienTai)){
            hienThiThongBao("Ngày bắt đầu và kết thúc không được sau ngày hiện tại", null);
            return false;
        }
        
        return true;
            
            
    }
    public void inFilePDF() throws IOException{
        Document document = new Document();
        
        try {
            try {
                PdfWriter.getInstance(document, new FileOutputStream("ThongKeKhachHang.pdf"));
                
                // Create a font for text
            BaseFont baseFont = BaseFont.createFont("c:/windows/fonts/times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font vietnameseFont = new Font(baseFont, 12);
            Font vietnameseBoldFont = new Font(baseFont, 12, Font.BOLD);
            
            
            
            document.open();
            
            //Khai báo  paragraph
            Paragraph paragraph1 = new Paragraph("HIỆU SÁCH TƯ NHÂN \n", vietnameseFont);
            Paragraph paragraph2 = new Paragraph("THỐNG KÊ KHÁCH HÀNG\n\n", vietnameseBoldFont);
            
            
            paragraph1.setFont(vietnameseFont);
            paragraph1.setSpacingBefore(15);
            paragraph1.setAlignment(Element.ALIGN_LEFT);
            
            //Định dạng đoạn văn bản thứ 2
            paragraph2.setFont(vietnameseBoldFont);
            paragraph2.setIndentationLeft(100);
            paragraph2.setIndentationRight(80);
            paragraph2.setSpacingAfter(10);
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            
            Paragraph paragraph3 = new Paragraph("Tổng số lương khách hàng : " + txtSLKhachHang.getText() +"\n Tổng số lượng đơn hàng: "+ txtSLDonHang.getText(), vietnameseFont);
            
            paragraph3.setFont(vietnameseFont);
            paragraph3.setSpacingBefore(15);
            paragraph3.setAlignment(Element.ALIGN_RIGHT);
            
            //Khởi tạo một table có 5 cột
            PdfPTable table1 = new PdfPTable(5);
            float[] columnWidths = {15f, 30f, 30f, 15f, 15f};
            table1.setWidths(columnWidths);
            table1.setTotalWidth(500f);
            table1.setLockedWidth(true);
            
            PdfPCell header1 = new PdfPCell(new Paragraph("Mã khách hàng",vietnameseFont));
            PdfPCell header2 = new PdfPCell(new Paragraph("Tên khách hàng",vietnameseFont));
            PdfPCell header3 = new PdfPCell(new Paragraph("Số điện thoại",vietnameseFont));
            PdfPCell header4 = new PdfPCell(new Paragraph("Địa chỉ",vietnameseFont));
            PdfPCell header5 = new PdfPCell(new Paragraph("Số lượng đơn hàng",vietnameseFont));
            
            table1.addCell(header1);
            table1.addCell(header2);
            table1.addCell(header3);
            table1.addCell(header4);
            table1.addCell(header5);
            
            for (int i = 0; i < table.getRowCount(); i++) {
                    String maKH = table.getValueAt(i, 0).toString();
                    String tenKH = table.getValueAt(i, 1).toString();
                    String sdt = table.getValueAt(i, 2).toString();
                    String diaChi = table.getValueAt(i, 3).toString();
                    String slDonHang = table.getValueAt(i, 4).toString();
                    
              
                    PdfPCell cellMaKH = new PdfPCell(new Phrase(maKH, vietnameseFont));
                    cellMaKH.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                    cellMaKH.setHorizontalAlignment(Element.ALIGN_RIGHT); // căn sang phải
                    table1.addCell(cellMaKH);

                    PdfPCell cellTenKH = new PdfPCell(new Phrase(tenKH, vietnameseFont));
                    cellTenKH.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                    cellTenKH.setHorizontalAlignment(Element.ALIGN_RIGHT); // căn sang phải
                    table1.addCell(cellTenKH);

                    PdfPCell cellSdt = new PdfPCell(new Phrase(sdt, vietnameseFont));
                    cellSdt.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                    cellSdt.setHorizontalAlignment(Element.ALIGN_RIGHT); // căn sang phải
                    table1.addCell(cellSdt);

                    PdfPCell cellDiaChi = new PdfPCell(new Phrase(diaChi, vietnameseFont));
                    cellDiaChi.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                    cellDiaChi.setHorizontalAlignment(Element.ALIGN_RIGHT); // căn sang phải
                    table1.addCell(cellDiaChi);

                    PdfPCell cellSlDonHang = new PdfPCell(new Phrase(slDonHang, vietnameseFont));
                    cellSlDonHang.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                    cellSlDonHang.setHorizontalAlignment(Element.ALIGN_RIGHT); // căn sang phải
                    table1.addCell(cellSlDonHang);
                }
            
            document.add(paragraph1);
            document.add(paragraph2);
            document.add(table1);
            document.add(paragraph3);
            
            // đóng file
            document.close();
            hienThiThongBao("Hóa đơn PDF đã được tạo thành công!",null);
            
            // Hiển thị file PDF trên máy tính của người dùng
        File file = new File("ThongKeKhachHang.pdf");
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        lblTuNgay = new javax.swing.JLabel();
        lblDenNgay = new javax.swing.JLabel();
        datechooseTuNgay = new com.toedter.calendar.JDateChooser();
        datechooseDenNgay = new com.toedter.calendar.JDateChooser();
        btnThongKe = new javax.swing.JButton();
        btnXuatFile = new javax.swing.JButton();
        cmbLuaChon = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtSLKhachHang = new javax.swing.JTextField();
        txtSLDonHang = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));

        jLabel1.setText("Thống kê khách hàng");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(550, 550, 550)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        String[] heads =  {
            "Mã khách hàng","Tên khách hàng", "Số điện thoại", "Địa chỉ", "Số lượng đơn hàng"
        };
        model = new DefaultTableModel(heads, 0);
        table.setModel(model);
        table.setShowGrid(true);
        jScrollPane1.setViewportView(table);

        lblTuNgay.setText("Từ ngày");

        lblDenNgay.setText("Đến ngày");

        btnThongKe.setBackground(new java.awt.Color(51, 153, 255));
        btnThongKe.setText("Thống kê");
        btnThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongKeActionPerformed(evt);
            }
        });

        btnXuatFile.setBackground(new java.awt.Color(0, 204, 0));
        btnXuatFile.setText("Xuất file");
        btnXuatFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatFileActionPerformed(evt);
            }
        });

        cmbLuaChon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Khách đã mua hàng", "Khách có nhiều đơn nhất" }));
        cmbLuaChon.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbLuaChonItemStateChanged(evt);
            }
        });

        jLabel4.setText("Tổng số lượng khách hàng");

        jLabel5.setText("Tổng số lượng đơn hàng");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblTuNgay)
                        .addGap(101, 101, 101))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblDenNgay)
                        .addGap(93, 93, 93)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(datechooseDenNgay, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                    .addComponent(datechooseTuNgay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(92, 92, 92)
                .addComponent(cmbLuaChon, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXuatFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(276, 276, 276))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1054, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(269, 269, 269)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(66, 66, 66)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSLKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSLDonHang, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(107, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTuNgay)
                            .addComponent(datechooseTuNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbLuaChon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnThongKe)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(datechooseDenNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDenNgay)
                    .addComponent(btnXuatFile))
                .addGap(44, 44, 44)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtSLKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtSLDonHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(71, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeActionPerformed
        // TODO add your handling code here:
        if(kiemTraDuLieu()){
            clearTable();
            loadTableCoDKNgay(datechooseTuNgay.getDate(), datechooseDenNgay.getDate());
            txtSLDonHang.setText(String.valueOf(tongSL(table, 4)));
            txtSLKhachHang.setText(String.valueOf(table.getRowCount()));
        }
    }//GEN-LAST:event_btnThongKeActionPerformed

    private void cmbLuaChonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbLuaChonItemStateChanged
        // TODO add your handling code here:
        if(cmbLuaChon.getSelectedIndex() == 0){
            clearTable();
            loadTable();
            txtSLDonHang.setText(String.valueOf(tongSL(table, 4)));
            txtSLKhachHang.setText(String.valueOf(table.getRowCount()));
        }
        else{
            clearTable();
            KhachHang kh = khDAO.khachHangCoNhieuDonNhat();
            model.addRow(new Object[]{
               kh.getMaKH(), kh.getTenKH(),kh.getsDT(), kh.getDiaChi(),
               khDAO.slDonHang(kh.getMaKH())
            });
            txtSLDonHang.setText(String.valueOf(tongSL(table, 4)));
            txtSLKhachHang.setText(String.valueOf(table.getRowCount()));
        }
    }//GEN-LAST:event_cmbLuaChonItemStateChanged

    private void btnXuatFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatFileActionPerformed
        try {
            // TODO add your handling code here:
            inFilePDF();
        } catch (IOException ex) {
            Logger.getLogger(PnlThongKeKH.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnXuatFileActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThongKe;
    private javax.swing.JButton btnXuatFile;
    private javax.swing.JComboBox<String> cmbLuaChon;
    private com.toedter.calendar.JDateChooser datechooseDenNgay;
    private com.toedter.calendar.JDateChooser datechooseTuNgay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDenNgay;
    private javax.swing.JLabel lblTuNgay;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtSLDonHang;
    private javax.swing.JTextField txtSLKhachHang;
    // End of variables declaration//GEN-END:variables
}

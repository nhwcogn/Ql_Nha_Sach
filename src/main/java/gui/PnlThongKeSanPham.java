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
import dao.SanPhamDAO;
import entity.SanPham;
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
public class PnlThongKeSanPham extends javax.swing.JPanel {

    /**
     * Creates new form FrmThongKeSanPham
     */
    private SanPhamDAO spDAO = new SanPhamDAO();
    private DefaultTableModel model;
    public PnlThongKeSanPham() {
        initComponents();
        loadTable();
        txtTongSoLuong.setText(String.valueOf(tongSL(table, 2)));
        txtTongTien.setText(String.valueOf(tongTien(table, 3)));
    }
    
    public void loadTable(){
        ArrayList<SanPham> list = spDAO.danhSachSPDaBan();
        
        for(SanPham sp: list){
            model.addRow(new Object[]{
                sp.getMa_SanPham(), sp.getTenSanPham(),
                spDAO.tongSLSanPham(sp.getMa_SanPham()), spDAO.tongGiaTien(sp.getMa_SanPham())
            });
        }
    }
    public void loadTableCoDK(Date fromDate, Date toDate){
        ArrayList<SanPham> list = spDAO.danhSachSPDaBanCoDKNgay(fromDate, toDate);
        
        for(SanPham sp: list){
            model.addRow(new Object[]{
                sp.getMa_SanPham(), sp.getTenSanPham(),
                spDAO.tongSLSanPham(sp.getMa_SanPham()), spDAO.tongGiaTien(sp.getMa_SanPham())
            });
        }
    }
    
    /*"Mã sản phẩm ", "Tên sản phẩm", "số lượng đã bán", "Thành tiền"*/
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
    public void hienThiThongBao(String thongBao, JTextField txt) {
        JOptionPane.showMessageDialog(null, thongBao);
        if (txt == null) {
            return;
        }
        txt.requestFocus();
    }
    private boolean kiemTraDuLieu(){
        Date hienTai = new Date();
        if(datechooseBatDau.getDate().after(dateChooseketThuc.getDate())){
            hienThiThongBao("Ngày bắt đầu phải trước ngày kết thúc", null);
            return false;
        }
        if(datechooseBatDau.getDate().after(hienTai)|| dateChooseketThuc.getDate().after(hienTai)){
            hienThiThongBao("Ngày bắt đầu và kết thúc không được sau ngày hiện tại", null);
            return false;
        }
        
        return true;
            
            
    }
    private void clearTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }
    public void inHoaDon() throws IOException{
         Document document = new Document();
        
        try {
            try {
                PdfWriter.getInstance(document, new FileOutputStream("ThongKeSanPham.pdf"));
                
                // Create a font for text
            BaseFont baseFont = BaseFont.createFont("c:/windows/fonts/times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font vietnameseFont = new Font(baseFont, 12);
            Font vietnameseBoldFont = new Font(baseFont, 12, Font.BOLD);
            
            
            
            document.open();
            
            //Khai báo  paragraph
            Paragraph paragraph1 = new Paragraph("HIỆU SÁCH TƯ NHÂN \n", vietnameseFont);
            Paragraph paragraph2 = new Paragraph("THỐNG KÊ SẢN PHẨM\n\n", vietnameseBoldFont);
            
            
            paragraph1.setFont(vietnameseFont);
            paragraph1.setSpacingBefore(15);
            paragraph1.setAlignment(Element.ALIGN_LEFT);
            
            //Định dạng đoạn văn bản thứ 2
            paragraph2.setFont(vietnameseBoldFont);
            paragraph2.setIndentationLeft(100);
            paragraph2.setIndentationRight(80);
            paragraph2.setSpacingAfter(10);
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            
            
            Paragraph paragraph3 = new Paragraph("Tổng số sản phẩm : " + txtTongSoLuong.getText() +"\n Tổng doanh thu : "+ txtTongTien.getText(), vietnameseFont);
            
            paragraph3.setFont(vietnameseFont);
            paragraph3.setSpacingBefore(15);
            paragraph3.setAlignment(Element.ALIGN_RIGHT);
            //Khởi tạo một table có 6 cột
            PdfPTable table1 = new PdfPTable(4);
            float[] columnWidths = {15f, 50f, 15f, 15f};
            table1.setWidths(columnWidths);
            table1.setTotalWidth(500f);
            table1.setLockedWidth(true);
            
            PdfPCell header1 = new PdfPCell(new Paragraph("Mã sản phẩm",vietnameseFont));
            PdfPCell header2 = new PdfPCell(new Paragraph("Tên sản phẩm",vietnameseFont));
            PdfPCell header3 = new PdfPCell(new Paragraph("số lượng đã bán",vietnameseFont));
            PdfPCell header4 = new PdfPCell(new Paragraph("Thành tiền",vietnameseFont));
            
            table1.addCell(header1);
            table1.addCell(header2);
            table1.addCell(header3);
            table1.addCell(header4);
            
            for (int i = 0; i < table.getRowCount(); i++) {
                    String maSP = table.getValueAt(i, 0).toString();
                    String tenSP = table.getValueAt(i, 1).toString();
                    String sl = table.getValueAt(i, 2).toString();
                    String tongTien = table.getValueAt(i, 3).toString();
                    
              
                    PdfPCell cellMaSP = new PdfPCell(new Phrase(maSP, vietnameseFont));
                    cellMaSP.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                    cellMaSP.setHorizontalAlignment(Element.ALIGN_RIGHT); // căn sang phải
                    table1.addCell(cellMaSP);

                    PdfPCell cellTenSP = new PdfPCell(new Phrase(tenSP, vietnameseFont));
                    cellTenSP.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                    cellTenSP.setHorizontalAlignment(Element.ALIGN_RIGHT); // căn sang phải
                    table1.addCell(cellTenSP);

                    PdfPCell cellSL = new PdfPCell(new Phrase(sl, vietnameseFont));
                    cellSL.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                    cellSL.setHorizontalAlignment(Element.ALIGN_RIGHT); // căn sang phải
                    table1.addCell(cellSL);

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
        File file = new File("ThongKeSanPham.pdf");
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

        pnlTieuDe = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        lblNgayBatDau = new javax.swing.JLabel();
        lblNgayKetThuc = new javax.swing.JLabel();
        datechooseBatDau = new com.toedter.calendar.JDateChooser();
        dateChooseketThuc = new com.toedter.calendar.JDateChooser();
        btnThongKe = new javax.swing.JButton();
        btnXuatFile = new javax.swing.JButton();
        lblTongSoLuong = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        txtTongSoLuong = new javax.swing.JTextField();
        txtTongTien = new javax.swing.JTextField();
        cmbLuaChon = new javax.swing.JComboBox<>();

        pnlTieuDe.setBackground(new java.awt.Color(0, 153, 153));

        jLabel1.setText("Thống kê sản phẩm");

        javax.swing.GroupLayout pnlTieuDeLayout = new javax.swing.GroupLayout(pnlTieuDe);
        pnlTieuDe.setLayout(pnlTieuDeLayout);
        pnlTieuDeLayout.setHorizontalGroup(
            pnlTieuDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTieuDeLayout.createSequentialGroup()
                .addGap(550, 550, 550)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlTieuDeLayout.setVerticalGroup(
            pnlTieuDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTieuDeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        String[] heads =  {
            "Mã sản phẩm ", "Tên sản phẩm", "số lượng đã bán", "Thành tiền"
        };
        model = new DefaultTableModel(heads, 0);
        table.setModel(model);
        table.setShowGrid(true);
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setHeaderValue("STT");
            table.getColumnModel().getColumn(3).setHeaderValue("Loại sản phẩm");
        }

        lblNgayBatDau.setText("Từ ngày");

        lblNgayKetThuc.setText("Đến ngày");

        btnThongKe.setBackground(new java.awt.Color(51, 102, 255));
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

        lblTongSoLuong.setText("Tổng số lượng sản phẩm đã bán");

        lblTongTien.setText("Tổng tiền thu được");

        cmbLuaChon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sản phẩm đã bán", "Sản phẩm bán nhiều nhất", "Sản phẩm có doanh thu cao nhất" }));
        cmbLuaChon.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbLuaChonItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTieuDe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1054, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTongSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTongSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTongTien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(107, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(166, 166, 166)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNgayBatDau)
                    .addComponent(lblNgayKetThuc))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(datechooseBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .addComponent(dateChooseketThuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(92, 92, 92)
                .addComponent(cmbLuaChon, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXuatFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(387, 387, 387))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNgayBatDau)
                    .addComponent(datechooseBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnThongKe)
                        .addComponent(cmbLuaChon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNgayKetThuc)
                            .addComponent(dateChooseketThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnXuatFile)))
                .addGap(38, 38, 38)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTongSoLuong)
                    .addComponent(txtTongSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTongTien)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(82, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeActionPerformed
        // TODO add your handling code here:
        if(kiemTraDuLieu()){
            clearTable();
            loadTableCoDK(datechooseBatDau.getDate(), dateChooseketThuc.getDate());
            txtTongSoLuong.setText(String.valueOf(tongSL(table, 2)));
            txtTongTien.setText(String.valueOf(tongTien(table, 3)));
            
        }
    }//GEN-LAST:event_btnThongKeActionPerformed

    private void cmbLuaChonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbLuaChonItemStateChanged
        // TODO add your handling code here:
        if(cmbLuaChon.getSelectedIndex() ==0){
            clearTable();
            loadTable();
            txtTongSoLuong.setText(String.valueOf(tongSL(table, 2)));
            txtTongTien.setText(String.valueOf(tongTien(table, 3)));
            
        }
        else if(cmbLuaChon.getSelectedIndex()==1){
            clearTable();
            SanPham sp = spDAO.spCoSLBanDuocNhieuNhat();
            model.addRow(new Object[]{
                sp.getMa_SanPham(), sp.getTenSanPham(),
                spDAO.tongSLSanPham(sp.getMa_SanPham()), spDAO.tongGiaTien(sp.getMa_SanPham())
            });
            txtTongSoLuong.setText(String.valueOf(tongSL(table, 2)));
            txtTongTien.setText(String.valueOf(tongTien(table, 3)));
        }
        else{
            clearTable();
            SanPham sp = spDAO.spCoGiaBanCaoNhat();
            model.addRow(new Object[]{
                sp.getMa_SanPham(), sp.getTenSanPham(),
                spDAO.tongSLSanPham(sp.getMa_SanPham()), spDAO.tongGiaTien(sp.getMa_SanPham())
            });
            txtTongSoLuong.setText(String.valueOf(tongSL(table, 2)));
            txtTongTien.setText(String.valueOf(tongTien(table, 3)));
        }
        
        
    }//GEN-LAST:event_cmbLuaChonItemStateChanged

    private void btnXuatFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatFileActionPerformed
        try {
            // TODO add your handling code here:

            inHoaDon();
        } catch (IOException ex) {
            Logger.getLogger(PnlThongKeSanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnXuatFileActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThongKe;
    private javax.swing.JButton btnXuatFile;
    private javax.swing.JComboBox<String> cmbLuaChon;
    private com.toedter.calendar.JDateChooser dateChooseketThuc;
    private com.toedter.calendar.JDateChooser datechooseBatDau;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblNgayBatDau;
    private javax.swing.JLabel lblNgayKetThuc;
    private javax.swing.JLabel lblTongSoLuong;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JPanel pnlTieuDe;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtTongSoLuong;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}

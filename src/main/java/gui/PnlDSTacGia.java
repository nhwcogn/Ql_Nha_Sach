/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import dao.TacGiaDAO;
import entity.TacGia;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author letha
 */
public class PnlDSTacGia extends javax.swing.JPanel {

    /**
     * Creates new form PnlTimKiemNCC
     */
    private TacGia tacGia;
    private ArrayList<TacGia> listTacGia;
    private DefaultTableModel model;
    private static final TacGiaDAO DAO = new TacGiaDAO();
    public PnlDSTacGia() {
        initComponents();
        loadTable();
        //napDLVaoTextfield();
    }
    private void moKhoaControls(boolean b){
        btnThem.setEnabled(b);
        btnXoa.setEnabled(b);
        btnCapNhat.setEnabled(b);
        btnXoaTrang.setEnabled(b);
        btnThoat.setEnabled(b);
    }
    private void moKhoaTextfields(boolean b){
        txtMa.setEditable(b);
        txtTen.setEditable(b);
   }
    private void clearTable(){
        DefaultTableModel model = (DefaultTableModel) tblTacGia.getModel();
        model.setRowCount(0);
    }
    public void loadTable(){
        listTacGia =DAO.getDanhSachTacGia();
//        int i = 0;
        for(TacGia tg: listTacGia){
        	//i+=1;
            model.addRow(new Object[]{tg.getMaTacGia(), tg.getTenTacGia()});
        }
        
    }
    public void loadTableArray(ArrayList<TacGia> listTacGia){
        //int i = 0;
        for(TacGia tg: listTacGia){
            //i += 1;
            model.addRow(new Object[]{tg.getMaTacGia(), tg.getTenTacGia()});
        }
    }
    private void napDLVaoTextfield(){
        int n = tblTacGia.getSelectedRow();
        if(n == -1)
            return;
        String stringMaTG = (String) tblTacGia.getValueAt(n, 0);
        //int maTG = Integer.parseInt(stringMaTG);
        String tenTG = (String) tblTacGia.getValueAt(n, 1);
        txtMa.setText(stringMaTG);
        txtTen.setText(tenTG);
    }
    private void hienThiThongBao(String thongBao, JTextField txt){
        JOptionPane.showMessageDialog(null, thongBao);
        if(txt == null)
            return;
        txt.requestFocus();
    }
    private boolean kiemTraDuLieu(){
        if((!txtMa.getText().matches("^TG\\d{1,10}$"))){
            hienThiThongBao("Định dạng mã tác giả sai (TGxxx)", txtMa);
            txtMa.requestFocus();
            return false;
        }
        if(!(txtTen.getText().matches("^[\\p{L} ]+$"))){
             hienThiThongBao("Tên tác giả chỉ được nhập chữ", txtTen);
            txtTen.requestFocus();
            return false;
        }
        return true;
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
        String maCuoi = DAO.getMaTGCuoi();
        
        return tangChuoiSo(maCuoi);
    }
    private void clearTextFields(){
        txtMa.setText("");
        txtTen.setText("");
    }
    public void themTacGia(){
        String ma = txtMa.getText();
        String hoTen = txtTen.getText();
        TacGia tacGia = new TacGia(ma, hoTen);
        if(DAO.themTacGia(tacGia)){
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            clearTable();
            loadTable();
        }
        else
            JOptionPane.showMessageDialog(this, "Thêm thất bại ");
    }
    public void xoaTacGia(){
        int row = tblTacGia.getSelectedRow();
        if(row == -1)
            hienThiThongBao("Chọn tác giả cần xóa", null);
        String ma = tblTacGia.getValueAt(row, 0).toString();
        int op = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa không", "delete", JOptionPane.YES_NO_OPTION);
        if(op == JOptionPane.YES_OPTION){
            if(DAO.xoaTacGia(ma)){
                hienThiThongBao("Xóa thành công", null);
                clearTable();
                loadTable();
            }
            else{
                hienThiThongBao("Xóa thất bại", null);
                return;
            }
        }
    }
    public void suaThongTinTacGia(){
        int op = JOptionPane.showConfirmDialog(this, 
                "Ban co chac chan muon sua khong", "update",
                JOptionPane.YES_NO_OPTION);
        if(op == JOptionPane.YES_OPTION){
            String ma = txtMa.getText().trim();
            String hoTen = txtTen.getText().trim();
            TacGia tacGia = new TacGia(ma, hoTen);
            if(DAO.suaTacGia(tacGia)){
                hienThiThongBao("Sửa thành công", null);
                clearTable();
                loadTable();
            }
            else
                hienThiThongBao("Sửa thất bại", null);
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
        lblTieuDe = new javax.swing.JLabel();
        pnlThongTin = new javax.swing.JPanel();
        lblMa = new javax.swing.JLabel();
        lblTen = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        txtTen = new javax.swing.JTextField();
        scrTacGia = new javax.swing.JScrollPane();
        tblTacGia = new javax.swing.JTable();
        btnXoaTrang = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 204, 204));

        pnlTieuDe.setBackground(new java.awt.Color(0, 153, 153));

        lblTieuDe.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTieuDe.setText("Danh sách tác giả");

        javax.swing.GroupLayout pnlTieuDeLayout = new javax.swing.GroupLayout(pnlTieuDe);
        pnlTieuDe.setLayout(pnlTieuDeLayout);
        pnlTieuDeLayout.setHorizontalGroup(
            pnlTieuDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTieuDeLayout.createSequentialGroup()
                .addContainerGap(605, Short.MAX_VALUE)
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

        pnlThongTin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblMa.setText("Mã tác giả");

        lblTen.setText("Tên tác giả");

        javax.swing.GroupLayout pnlThongTinLayout = new javax.swing.GroupLayout(pnlThongTin);
        pnlThongTin.setLayout(pnlThongTinLayout);
        pnlThongTinLayout.setHorizontalGroup(
            pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMa)
                    .addComponent(lblTen))
                .addGap(39, 39, 39)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTen, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                    .addComponent(txtMa))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        pnlThongTinLayout.setVerticalGroup(
            pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMa)
                    .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTen)
                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(63, Short.MAX_VALUE))
        );

        String[] heads = {"Mã tác giả", "Tên tác giả"};
        model = new DefaultTableModel(heads, 0);
        tblTacGia.setModel(model);
        tblTacGia.setShowGrid(true);
        tblTacGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTacGiaMouseClicked(evt);
            }
        });
        scrTacGia.setViewportView(tblTacGia);

        btnXoaTrang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eraser.png"))); // NOI18N
        btnXoaTrang.setText("Xóa trắng");
        btnXoaTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTrangActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(0, 204, 0));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnCapNhat.setBackground(new java.awt.Color(0, 153, 204));
        btnCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update.png"))); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(255, 102, 102));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnLuu.setBackground(new java.awt.Color(0, 204, 0));
        btnLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N
        btnLuu.setEnabled(false);
        btnLuu.setText("Lưu");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        btnThoat.setBackground(new java.awt.Color(255, 102, 102));
        btnThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/out.png"))); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTieuDe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(233, 233, 233)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnThem)
                                .addGap(41, 41, 41)
                                .addComponent(btnCapNhat)
                                .addGap(54, 54, 54)
                                .addComponent(btnXoa)
                                .addGap(37, 37, 37)
                                .addComponent(btnXoaTrang)
                                .addGap(37, 37, 37)
                                .addComponent(btnLuu))
                            .addComponent(pnlThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52)
                        .addComponent(btnThoat))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(280, 280, 280)
                        .addComponent(scrTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(pnlThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhat)
                    .addComponent(btnXoa)
                    .addComponent(btnLuu)
                    .addComponent(btnThoat)
                    .addComponent(btnXoaTrang))
                .addGap(39, 39, 39)
                .addComponent(scrTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(141, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    
    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if(evt.getSource().equals(btnThem)){
            if(btnThem.getText().equals("Hủy")){ 
                moKhoaControls(true);
                moKhoaTextfields(false);
                clearTextFields();
                btnThem.setEnabled(true);
                btnLuu.setEnabled(false);
                btnThem.setText("Thêm");
                txtMa.setEditable(false);
            }
            else{
                txtMa.setText(taoMaMoi());
                txtTen.requestFocus();
                moKhoaControls(false);
                moKhoaTextfields(true);
                btnThem.setEnabled(true);
                btnLuu.setEnabled(true);
                btnThem.setText("Hủy");
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        // TODO add your handling code here:
        ManHinhChinh.getPnlChuongTrinh().removeAll();
        ManHinhChinh.getPnlChuongTrinh().repaint();
        ManHinhChinh.getPnlChuongTrinh().revalidate();
    }//GEN-LAST:event_btnThoatActionPerformed

    private void tblTacGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTacGiaMouseClicked
        // TODO add your handling code here:
        napDLVaoTextfield();
        
    }//GEN-LAST:event_tblTacGiaMouseClicked

    private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTrangActionPerformed
        // TODO add your handling code here:
        clearTextFields();
    }//GEN-LAST:event_btnXoaTrangActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        // TODO add your handling code here:
        if(btnThem.getText().equals("Hủy")){
            if(kiemTraDuLieu()){
                themTacGia();
                moKhoaControls(true);
                moKhoaTextfields(false);
                clearTextFields();
                btnLuu.setEnabled(false);
               // btnLuu.setText("Luu f5");
                btnThem.setText("Thêm");
                txtMa.setText("");
            }
        }
        if(btnCapNhat.getText().equals("Hủy")){
            if(kiemTraDuLieu()){
                suaThongTinTacGia();
                moKhoaTextfields(false);
                clearTextFields();
                moKhoaControls(true);
                btnLuu.setEnabled(false);
                btnXoaTrang.setEnabled(false);
                btnLuu.setText("Lưu");
                btnCapNhat.setText("Cập nhật");
                txtMa.setText("");
            }
        }
        
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        // TODO add your handling code here:
        if(btnCapNhat.getText().equals("Cập nhật")){
            int row = tblTacGia.getSelectedRow();
            if(row == -1)
                hienThiThongBao("Chưa chọn tác giả cần cập nhật", null);
            else{
                moKhoaTextfields(true);
                moKhoaControls(false);
                btnLuu.setEnabled(true);
                btnCapNhat.setEnabled(true);
                btnXoaTrang.setEnabled(true);
                btnCapNhat.setText("Hủy");
            }
        }
        else if(btnCapNhat.getText().equals("Hủy")){
            moKhoaTextfields(false);
            moKhoaControls(true);
            btnLuu.setEnabled(false);
            btnXoaTrang.setEnabled(true);
            btnCapNhat.setText("Cập nhật");
        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        xoaTacGia();
        clearTextFields();
        txtMa.setText("");
    }//GEN-LAST:event_btnXoaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaTrang;
    private javax.swing.JLabel lblMa;
    private javax.swing.JLabel lblTen;
    private javax.swing.JLabel lblTieuDe;
    private javax.swing.JPanel pnlThongTin;
    private javax.swing.JPanel pnlTieuDe;
    private javax.swing.JScrollPane scrTacGia;
    private javax.swing.JTable tblTacGia;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import dao.KhachHangDAO;
import entity.KhachHang;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NHU NGOC
 */
public class PnlDSKhachHang extends javax.swing.JPanel {

    /**
     * Creates new form pnlQuanLyKhachHang
     */
    private DefaultTableModel model;
    private static final KhachHangDAO khDAO = new KhachHangDAO();
    private ArrayList<KhachHang> list;
    public PnlDSKhachHang() {
        initComponents();
        loadTable();
    }
    /*
    "Mã khách hàng", "Tên Khách hàng", "Số điện thoại", "Email", "Địa chỉ"
    */
    public void loadTable(){
        list = khDAO.danhSachKhachHang();
        
        for( KhachHang kh: list){
            //System.out.println(s);
            model.addRow(new Object[]{kh.getMaKH(), kh.getTenKH(),
            kh.getsDT(), kh.getDiaChi(), kh.getEmail()});
        }
    }
    public void loadTableTuDanhSach(ArrayList<KhachHang> list){
//        list = khDAO.danhSachKhachHang();
        for( KhachHang kh: list){
            //System.out.println(s);
            model.addRow(new Object[]{kh.getMaKH(), kh.getTenKH(),
            kh.getsDT(), kh.getDiaChi(), kh.getEmail()});
        }
    }
    private void moKhoaControls(boolean b){
        btnCapNhat.setEnabled(b);
        btnLuu.setEnabled(b);
        btnThem.setEnabled(b);
        btnThoat.setEnabled(b);
        btnXoaTrangTT.setEnabled(b);
        btnXoa.setEnabled(b);
    }
    private void clearTable(){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }
    public void napDLVaoTextfield(){
        int n = table.getSelectedRow();
        if(n == -1)
            return;
        ArrayList<KhachHang> list = new ArrayList<>();
        list = khDAO.timKiemDanhSachKHTheoMa(model.getValueAt(n, 0).toString());
        for(KhachHang kh: list){
            txtMaKH.setText(table.getValueAt(n, 0).toString());
            txtTenKH.setText(kh.getTenKH());
            txtDiaChi.setText(kh.getDiaChi());
            txtEmail.setText(kh.getEmail());
            txtSDT.setText(kh.getsDT());
        }
    }
    public void clearTextFields(){
        txtMaKH.setText("");
            txtTenKH.setText("");
            txtDiaChi.setText("");
            txtEmail.setText("");
            txtSDT.setText("");
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
        String maCuoi = khDAO.getMaCuoi();
        
        return tangChuoiSo(maCuoi);
    }
    public void hienThiThongBao(String thongBao, JTextField txt){
        JOptionPane.showMessageDialog(null, thongBao);
        if(txt == null)
            return;
        txt.requestFocus();
    }
    public void themKhachHang(){
        //
        String maKH = txtMaKH.getText().trim();
        String tenKH = txtTenKH.getText().trim();
        
        String sdt = txtSDT.getText().trim();
            
        String tenDiaChi = txtDiaChi.getText();
        String email = txtEmail.getText();
        
        KhachHang kh = new KhachHang(maKH, tenKH, sdt, tenDiaChi, email);
        //System.out.println(lsp);
            // tìm kiếm mã loại sản phẩm theo tên loại sản phẩm
        
        //System.out.println(sp);
        if(khDAO.themKhachHang(kh)){
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            clearTable();
            loadTable();
        }
        else
            JOptionPane.showMessageDialog(this, "Thêm thất bại");
            
    }
    public  void capNhatKhachHang(){
        int op = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn cập nhật thông tin không",
                "Cập nhật",
                JOptionPane.YES_NO_OPTION);
        if(op == JOptionPane.YES_OPTION){
            String maKH = txtMaKH.getText().trim();
        String tenKH = txtTenKH.getText().trim();
        
        String sdt = txtSDT.getText().trim();
            
        String tenDiaChi = txtDiaChi.getText();
        String email = txtEmail.getText();
        
        KhachHang kh = new KhachHang(maKH, tenKH, sdt, tenDiaChi, email);
        //System.out.println(lsp);
            // tìm kiếm mã loại sản phẩm theo tên loại sản phẩm
        
        //System.out.println(sp);
        if(khDAO.suaKhachHang(kh)){
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            clearTable();
            loadTable();
        }
        else
            JOptionPane.showMessageDialog(this, "Thêm thất bại");
        }
    }
    public void xoaKhachHang(){
        int row = table.getSelectedRow();
        if(row == -1)
            hienThiThongBao("Chọn khách hàng cần xóa", null);
        else{
            String ma = table.getValueAt(row, 0).toString();
            int op = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa không","delete",JOptionPane.YES_NO_OPTION);
            if(op == JOptionPane.YES_OPTION){
                if(khDAO.xoaKhachHang(ma)){
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
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlThongTinKH = new javax.swing.JPanel();
        lblMaKH = new javax.swing.JLabel();
        lblTenKH = new javax.swing.JLabel();
        lblSDT = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblDiaChi = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        txtTenKH = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtDiaChi = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnXoaTrangTT = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        pnlTieuDe = new javax.swing.JPanel();
        lblTieuDe = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(732, 432));
        setPreferredSize(new java.awt.Dimension(1307, 740));

        pnlThongTinKH.setBackground(new java.awt.Color(204, 204, 204));
        pnlThongTinKH.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thông tin khách hàng"));

        lblMaKH.setText("Mã khách hàng:");

        lblTenKH.setText("Tên khách hàng:");

        lblSDT.setText("Số điện thoại:");

        lblEmail.setText("Email:");

        lblDiaChi.setText("Địa chỉ:");

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        txtDiaChi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiaChiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongTinKHLayout = new javax.swing.GroupLayout(pnlThongTinKH);
        pnlThongTinKH.setLayout(pnlThongTinKHLayout);
        pnlThongTinKHLayout.setHorizontalGroup(
            pnlThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinKHLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(pnlThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblSDT)
                    .addComponent(lblMaKH)
                    .addComponent(lblTenKH))
                .addGap(27, 27, 27)
                .addGroup(pnlThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtMaKH)
                        .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addGroup(pnlThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlThongTinKHLayout.createSequentialGroup()
                        .addComponent(lblEmail)
                        .addGap(30, 30, 30)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinKHLayout.createSequentialGroup()
                        .addComponent(lblDiaChi)
                        .addGap(30, 30, 30)
                        .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(47, 47, 47))
        );
        pnlThongTinKHLayout.setVerticalGroup(
            pnlThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinKHLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaKH)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinKHLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addGroup(pnlThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTenKH)
                            .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDiaChi))
                        .addGap(27, 27, 27)
                        .addGroup(pnlThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSDT))
                        .addGap(19, 19, 19))
                    .addGroup(pnlThongTinKHLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

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

        btnXoaTrangTT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eraser.png"))); // NOI18N
        btnXoaTrangTT.setText("Xóa trắng");
        btnXoaTrangTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTrangTTActionPerformed(evt);
            }
        });

        btnLuu.setBackground(new java.awt.Color(0, 204, 0));
        btnLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N
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

        String[] heads = {
            "Mã khách hàng", "Tên Khách hàng", "Số điện thoại", "Email", "Địa chỉ"
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
        jScrollPane1.setViewportView(table);

        pnlTieuDe.setBackground(new java.awt.Color(0, 153, 153));

        lblTieuDe.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTieuDe.setText("DANH SÁCH KHÁCH HÀNG");

        javax.swing.GroupLayout pnlTieuDeLayout = new javax.swing.GroupLayout(pnlTieuDe);
        pnlTieuDe.setLayout(pnlTieuDeLayout);
        pnlTieuDeLayout.setHorizontalGroup(
            pnlTieuDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTieuDeLayout.createSequentialGroup()
                .addGap(547, 547, 547)
                .addComponent(lblTieuDe)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 953, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(214, 214, 214)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pnlThongTinKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnThem)
                                .addGap(50, 50, 50)
                                .addComponent(btnCapNhat)
                                .addGap(54, 54, 54)
                                .addComponent(btnXoa)
                                .addGap(42, 42, 42)
                                .addComponent(btnXoaTrangTT)
                                .addGap(41, 41, 41)
                                .addComponent(btnLuu)
                                .addGap(51, 51, 51)
                                .addComponent(btnThoat)))))
                .addContainerGap(239, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(pnlThongTinKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhat)
                    .addComponent(btnXoa)
                    .addComponent(btnXoaTrangTT, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLuu)
                    .addComponent(btnThoat))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtDiaChiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiaChiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiaChiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if(evt.getSource().equals(btnThem)){
            if(btnThem.getText().equals("Hủy")){ 
                moKhoaControls(true);
                //moKhoaTextfield(false);
                clearTextFields();
                btnThem.setEnabled(true);
                btnLuu.setEnabled(false);
                btnThem.setText("Thêm");
                txtTenKH.setEditable(false);
            }
            else{
                txtMaKH.setText(taoMaMoi());
                txtTenKH.requestFocus();
                moKhoaControls(false);
                //moKhoaTextfield(true);
                btnThem.setEnabled(true);
                btnLuu.setEnabled(true);
                btnThem.setText("Hủy");
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        // TODO add your handling code here:
        napDLVaoTextfield();
    }//GEN-LAST:event_tableMouseClicked

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        // TODO add your handling code here:
         ManHinhChinh.getPnlChuongTrinh().removeAll();
        ManHinhChinh.getPnlChuongTrinh().repaint();
        ManHinhChinh.getPnlChuongTrinh().revalidate();
    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnXoaTrangTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTrangTTActionPerformed
        // TODO add your handling code here:
        clearTextFields();
    }//GEN-LAST:event_btnXoaTrangTTActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        // TODO add your handling code here:
        if(btnCapNhat.getText().equals("Cập nhật")){
            int row = table.getSelectedRow();
            if(row == -1)
                hienThiThongBao("Chưa chọn sản phẩm",null );
            else{
                //moKhoaTextfield(true);
                moKhoaControls(false);
                btnLuu.setEnabled(true);
                btnCapNhat.setEnabled(true);
                btnXoaTrangTT.setEnabled(true);
                btnCapNhat.setText("Hủy");
            }
        }
        else if(btnCapNhat.getText().equals("Hủy")){
            //moKhoaTextfield(false);
            moKhoaControls(true);
            btnLuu.setEnabled(false);
            btnXoaTrangTT.setEnabled(true);
            btnCapNhat.setText("Cập nhật");
        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        xoaKhachHang();
        clearTextFields();
        txtMaKH.setText("");
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        // TODO add your handling code here:
        if(btnThem.getText().equals("Hủy")){
            if(true){
                themKhachHang();
                moKhoaControls(true);
                //moKhoaTextfield(false);
                clearTextFields();
                btnLuu.setEnabled(false);
                btnThem.setText("Thêm");
                txtTenKH.setText("");
            }
        }
        if(btnCapNhat.getText().equals("Hủy")){
            if(true){
                capNhatKhachHang();
                //moKhoaTextfield(false);
                clearTextFields();
                moKhoaControls(true);
                btnLuu.setEnabled(false);
                btnXoaTrangTT.setEnabled(false);
                btnLuu.setText("Lưu");
                btnCapNhat.setText("Cập nhật");
                txtTenKH.setText("");
            }
        }
    }//GEN-LAST:event_btnLuuActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaTrangTT;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDiaChi;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblMaKH;
    private javax.swing.JLabel lblSDT;
    private javax.swing.JLabel lblTenKH;
    private javax.swing.JLabel lblTieuDe;
    private javax.swing.JPanel pnlThongTinKH;
    private javax.swing.JPanel pnlTieuDe;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenKH;
    // End of variables declaration//GEN-END:variables
}

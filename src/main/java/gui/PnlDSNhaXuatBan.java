
package gui;

import dao.NhaXuatBanDAO;
import entity.NhaXuatBan;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author letha
 */
public class PnlDSNhaXuatBan extends javax.swing.JPanel {

    /**
     * Creates new form PnlTimKiemNCC
     */
    private DefaultTableModel model;
    private NhaXuatBanDAO nxbDAO = new NhaXuatBanDAO();
    ArrayList<NhaXuatBan> list ;
    public PnlDSNhaXuatBan() {
        initComponents();
        loadTable();
    }
private void moKhoaControls(boolean b){
        btnCapNhat.setEnabled(b);
        btnLuu.setEnabled(b);
        btnThem.setEnabled(b);
        btnThoat.setEnabled(b);
        btnXoa.setEnabled(b);
        btnXoaTrang.setEnabled(b);
    }
private void moKhoaTextfield(boolean b){
        txtMaNXB.setEditable(b);
        txtTenNXB.setEditable(b);
        txtDiaChi.setEditable(b);
    }
private void clearTable(){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }
public void loadTable(){
        list = nxbDAO.danhSachNXB();
        for(NhaXuatBan nxb: list){
            model.addRow(new Object[]{
                nxb.getMaNhaXuatBan(), nxb.getTenNXB(), nxb.getDiaChi()
            });
        }
    }
public void hienThiThongBao(String thongBao, JTextField txt){
        JOptionPane.showMessageDialog(null, thongBao);
        if(txt == null)
            return;
        txt.requestFocus();
    }
 private void clearTextFields(){
        
        
        txtMaNXB.setText("");
        txtTenNXB.setText("");
        txtDiaChi.setText("");
    }
 private void napDLVaoTextField(){
        int n = table.getSelectedRow();
        if(n == -1)
            return;
        ArrayList<NhaXuatBan> list = new ArrayList<>();
        list = nxbDAO.timKiemDanhSachNXBTheoMa(model.getValueAt(n, 0).toString());
        for(NhaXuatBan nxb: list){
            txtMaNXB.setText(model.getValueAt(n, 0).toString());
            txtTenNXB.setText(nxb.getTenNXB());
            txtDiaChi.setText(nxb.getDiaChi());
        }
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
           String maCuoi = nxbDAO.getMaCuoi();
        
          return tangChuoiSo(maCuoi);
    }
public void xoaNhaXuatBan(){
        int row = table.getSelectedRow();
        if(row == -1)
            hienThiThongBao("Chọn sản phẩm cần xóa", null);
        else{
            String ma = table.getValueAt(row, 0).toString();
            int op = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa không","delete",JOptionPane.YES_NO_OPTION);
            if(op == JOptionPane.YES_OPTION){
                if(nxbDAO.xoaNhaXuatBan(ma)){
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
public void suaThongTin(){
        int op = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn cập nhật thông tin không",
                "Cập nhật",
                JOptionPane.YES_NO_OPTION);
        if(op == JOptionPane.YES_OPTION){
            String maNXB = txtMaNXB.getText().trim();
            String tenNXB = txtTenNXB.getText().trim();
            String diaChi = txtDiaChi.getText();
            //LoaiSanPham lsp = new LoaiSanPham(maLSP, loaiSP, tenLSP);
            NhaXuatBan nxb = new NhaXuatBan(maNXB, tenNXB, diaChi);
            if(nxbDAO.suaNhaXuatBan(nxb)){
                hienThiThongBao("Cập nhật thành công", null);
                clearTable();
                loadTable();
            }
            else
                hienThiThongBao("Cập nhật thất bại", null);
        }
    }
public void themNhaXuatBan(){
    String maNXB = txtMaNXB.getText().trim();
    String tenNXB = txtTenNXB.getText().trim();
    String diaChi = txtDiaChi.getText();
    NhaXuatBan nxb = new NhaXuatBan(maNXB, tenNXB, diaChi);
    if(nxbDAO.themNhaXuatBan(nxb)){
        hienThiThongBao(" thành công", null);
        clearTable();
        loadTable();
            }
    else
        hienThiThongBao(" thất bại", null);
}
private boolean kiemTraDuLieu(){
        if((!txtMaNXB.getText().matches("^NXB\\d{1,10}$"))){
            hienThiThongBao("Định dạng mã nhà xuất bản sai (NXBxxx)", txtMaNXB);
            txtMaNXB.requestFocus();
            return false;
        }
        if(!(txtTenNXB.getText().matches("^[\\p{L} ]+$"))){
             hienThiThongBao("Tên nhà xuất bản chỉ được nhập chữ", txtTenNXB);
            txtTenNXB.requestFocus();
            return false;
        }
//        if(!(txtDiaChi.getText().matches("^[\\p{L} ]+$"))){
//            hienThiThongBao("Địa chỉ không được nhập ký tự đặc biệt", txtDiaChi);
//        }
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
        jPanel1 = new javax.swing.JPanel();
        lblMaNXB = new javax.swing.JLabel();
        lblTenNXB = new javax.swing.JLabel();
        lblDiaChi = new javax.swing.JLabel();
        txtMaNXB = new javax.swing.JTextField();
        txtTenNXB = new javax.swing.JTextField();
        txtDiaChi = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btnXoaTrang = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 204, 204));

        pnlTieuDe.setBackground(new java.awt.Color(0, 153, 153));

        lblTieuDe.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTieuDe.setText("Danh sách nhà xuất bản");

        javax.swing.GroupLayout pnlTieuDeLayout = new javax.swing.GroupLayout(pnlTieuDe);
        pnlTieuDe.setLayout(pnlTieuDeLayout);
        pnlTieuDeLayout.setHorizontalGroup(
            pnlTieuDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTieuDeLayout.createSequentialGroup()
                .addContainerGap(561, Short.MAX_VALUE)
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

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblMaNXB.setText("Mã nhà xuất bản");

        lblTenNXB.setText("Tên nhà xuất bản");

        lblDiaChi.setText("Địa chỉ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblMaNXB)
                        .addGap(18, 18, 18)
                        .addComponent(txtMaNXB, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTenNXB)
                            .addComponent(lblDiaChi))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenNXB)
                            .addComponent(txtDiaChi))))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaNXB)
                    .addComponent(txtMaNXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenNXB)
                    .addComponent(txtTenNXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDiaChi)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        String[] heads =  {
            "Mã nhà xuất bản", "Tên nhà xuất bản", "Địa chỉ"
        };
        model = new DefaultTableModel(heads, 0);
        table.setModel(model);
        table.setShowGrid(true);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

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
                        .addComponent(btnThem)
                        .addGap(41, 41, 41)
                        .addComponent(btnCapNhat)
                        .addGap(54, 54, 54)
                        .addComponent(btnXoa)
                        .addGap(37, 37, 37)
                        .addComponent(btnXoaTrang)
                        .addGap(37, 37, 37)
                        .addComponent(btnLuu)
                        .addGap(52, 52, 52)
                        .addComponent(btnThoat))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1062, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(366, 366, 366)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhat)
                    .addComponent(btnXoa)
                    .addComponent(btnLuu)
                    .addComponent(btnThoat)
                    .addComponent(btnXoaTrang))
                .addGap(45, 45, 45)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if(evt.getSource().equals(btnThem)){
            if(btnThem.getText().equals("Hủy")){ 
                moKhoaControls(true);
                moKhoaTextfield(false);
                clearTextFields();
                btnThem.setEnabled(true);
                btnLuu.setEnabled(false);
                btnThem.setText("Thêm");
                txtMaNXB.setEditable(false);
            }
            else{
                txtMaNXB.setText(taoMaMoi());
                txtTenNXB.requestFocus();
                moKhoaControls(false);
                moKhoaTextfield(true);
                btnThem.setEnabled(true);
                btnLuu.setEnabled(true);
                btnThem.setText("Hủy");
            }
        }
        
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        // TODO add your handling code here:
        if(btnCapNhat.getText().equals("Cập nhật")){
            int row = table.getSelectedRow();
            if(row == -1)
                hienThiThongBao("Chưa chọn sản phẩm",null );
            else{
                moKhoaTextfield(true);
                moKhoaControls(false);
                btnLuu.setEnabled(true);
                btnCapNhat.setEnabled(true);
                btnXoaTrang.setEnabled(true);
                btnCapNhat.setText("Hủy");
            }
        }
        else if(btnCapNhat.getText().equals("Hủy")){
            moKhoaTextfield(false);
            moKhoaControls(true);
            btnLuu.setEnabled(false);
            btnXoaTrang.setEnabled(true);
            btnCapNhat.setText("Cập nhật");
        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTrangActionPerformed
        // TODO add your handling code here:
        clearTextFields();
    }//GEN-LAST:event_btnXoaTrangActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        xoaNhaXuatBan();
        clearTextFields();
        clearTable();
        loadTable();
        txtMaNXB.setText("");
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        // TODO add your handling code here:
        if(btnThem.getText().equals("Hủy")){
            if(kiemTraDuLieu()){
                themNhaXuatBan();
                moKhoaControls(true);
                moKhoaTextfield(false);
                clearTextFields();
                btnLuu.setEnabled(false);
                btnThem.setText("Thêm");
                txtMaNXB.setText("");
            }
        }
        if(btnCapNhat.getText().equals("Hủy")){
            if(kiemTraDuLieu()){
                suaThongTin();
                moKhoaTextfield(false);
                clearTextFields();
                moKhoaControls(true);
                btnLuu.setEnabled(false);
                btnXoaTrang.setEnabled(false);
                btnLuu.setText("Lưu");
                btnCapNhat.setText("Cập nhật");
                txtMaNXB.setText("");
            }
        }
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        // TODO add your handling code here:
        ManHinhChinh.getPnlChuongTrinh().removeAll();
        ManHinhChinh.getPnlChuongTrinh().repaint();
        ManHinhChinh.getPnlChuongTrinh().revalidate();
    }//GEN-LAST:event_btnThoatActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        // TODO add your handling code here:
        napDLVaoTextField();
    }//GEN-LAST:event_tableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaTrang;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDiaChi;
    private javax.swing.JLabel lblMaNXB;
    private javax.swing.JLabel lblTenNXB;
    private javax.swing.JLabel lblTieuDe;
    private javax.swing.JPanel pnlTieuDe;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtMaNXB;
    private javax.swing.JTextField txtTenNXB;
    // End of variables declaration//GEN-END:variables
}

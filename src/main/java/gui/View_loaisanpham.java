
package gui;

import dao.LoaiSanPhamDAO;
import entity.LoaiSanPham;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class View_loaisanpham extends javax.swing.JPanel {

    /**
     * Creates new form NewJPanel
     */
    private DefaultTableModel model;
    private LoaiSanPhamDAO lspDAO = new LoaiSanPhamDAO();
    private ArrayList<LoaiSanPham> dsLoaiSP;
    public View_loaisanpham() {
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
        txtMaLoaiSP.setEditable(b);
        txtTenLoaiSP.setEditable(b);
        cmbLoaiSP.setEnabled(b);
    }
private void clearTable(){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }

public void loadTable(){
        dsLoaiSP = lspDAO.danhSachLSP();
        for(LoaiSanPham lsp: dsLoaiSP){
            model.addRow(new Object[]{
                lsp.getMaLoaiSP(), lsp.getTenLoai(), lsp.getLoaiSP()
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
        
        
        txtMaLoaiSP.setText("");
        cmbLoaiSP.setSelectedIndex(0);
        cmbLoaiSP.setSelectedIndex(0);
    }
 
 private void napDLVaoTextField(){
        int n = table.getSelectedRow();
        if(n == -1)
            return;
        ArrayList<LoaiSanPham> list = new ArrayList<>();
        list = lspDAO.timKiemDanhSachTheoMa(model.getValueAt(n, 0).toString());
        
        for(LoaiSanPham lsp: list){
            txtMaLoaiSP.setText(model.getValueAt(n, 0).toString());
            if (cmbLoaiSP.getItemCount() - 1 == 0) {
                ArrayList<String> listLoaiSP = lspDAO.layDSLoaiSanPham();
                HashSet<String> newHashet = new HashSet<>(listLoaiSP);
                ArrayList<String> newList = new ArrayList<>(newHashet);

                for (String loaiSP : newList) {
                    cmbLoaiSP.addItem(loaiSP);
                }
            }
            for (int i = 0; i < cmbLoaiSP.getItemCount(); i++) {
                if (cmbLoaiSP.getItemAt(i).equals(lsp.getLoaiSP())) {
                    cmbLoaiSP.setSelectedIndex(i);
                }
            }
            txtTenLoaiSP.setText(lsp.getTenLoai());
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
        String maCuoi = lspDAO.getMaCuoi();
        
        return tangChuoiSo(maCuoi);
    }
public void xoaLoaiSanPham(){
        int row = table.getSelectedRow();
        if(row == -1)
            hienThiThongBao("Chọn sản phẩm cần xóa", txtMaLoaiSP);
        else{
            String ma = table.getValueAt(row, 0).toString();
            int op = JOptionPane.showConfirmDialog(btnXoaTrang, "Bạn có chắc chắn muốn xóa không","delete",JOptionPane.YES_NO_OPTION);
            if(op == JOptionPane.YES_OPTION){
                if(lspDAO.xoaLoaiSanPham(ma)){
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
        int op = JOptionPane.showConfirmDialog(btnXoaTrang,
                "Bạn có chắc chắn muốn cập nhật thông tin không",
                "Cập nhật",
                JOptionPane.YES_NO_OPTION);
        if(op == JOptionPane.YES_OPTION){
            String maLSP = txtMaLoaiSP.getText().trim();
            String tenLSP = txtTenLoaiSP.getText().trim();
            String loaiSP = cmbLoaiSP.getSelectedItem().toString();
            LoaiSanPham lsp = new LoaiSanPham(maLSP, loaiSP, tenLSP);
            
            if(lspDAO.suaLoaiSanPham(lsp)){
                hienThiThongBao("Cập nhật thành công", null);
                clearTable();
                loadTable();
            }
            else
                hienThiThongBao("Cập nhật thất bại", null);
        }
    }
public void themLoaiSanPham(){
    String ma = txtMaLoaiSP.getText().trim();
    String tenLSP = txtTenLoaiSP.getText().trim();
    String loaiSP = cmbLoaiSP.getSelectedItem().toString().trim();
    
    LoaiSanPham lsp = new LoaiSanPham(ma, loaiSP, tenLSP);
    if(lspDAO.themLoaiSanPham(lsp)){
        hienThiThongBao("Them thanh cong", null);
        clearTable();
        loadTable();
    }
    else
        hienThiThongBao("That bai", null);
}

private boolean kiemTraDuLieu(){
        if((!txtMaLoaiSP.getText().matches("^LSP\\d{1,10}$"))){
            hienThiThongBao("Định dạng mã loại sản phẩm sai (LSPxxx)", txtMaLoaiSP);
            txtMaLoaiSP.requestFocus();
            return false;
        }
        if(!(cmbLoaiSP.getSelectedItem().toString().matches("^[\\p{L} ]+$"))){
             hienThiThongBao("Loại sản phẩm chỉ được nhập chữ", txtMaLoaiSP);
            cmbLoaiSP.requestFocus();
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
        jLabel1 = new javax.swing.JLabel();
        pnlThongTin = new javax.swing.JPanel();
        lblMaLoaiSP = new javax.swing.JLabel();
        lblTenLoaiSP = new javax.swing.JLabel();
        lblLoaiSP = new javax.swing.JLabel();
        txtMaLoaiSP = new javax.swing.JTextField();
        txtTenLoaiSP = new javax.swing.JTextField();
        cmbLoaiSP = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnXoaTrang = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        pnlTieuDe.setBackground(new java.awt.Color(0, 153, 153));

        jLabel1.setBackground(new java.awt.Color(0, 153, 153));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Loại Sản Phẩm");

        javax.swing.GroupLayout pnlTieuDeLayout = new javax.swing.GroupLayout(pnlTieuDe);
        pnlTieuDe.setLayout(pnlTieuDeLayout);
        pnlTieuDeLayout.setHorizontalGroup(
            pnlTieuDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTieuDeLayout.createSequentialGroup()
                .addGap(578, 578, 578)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlTieuDeLayout.setVerticalGroup(
            pnlTieuDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTieuDeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlThongTin.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin Loại Sản Phẩm"));

        lblMaLoaiSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMaLoaiSP.setText("Mã Loại Sản Phẩm:");

        lblTenLoaiSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTenLoaiSP.setText("Tên Loại Sản Phẩm:");

        lblLoaiSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblLoaiSP.setText("Loại Sản Phẩm:");

        cmbLoaiSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Loai san pham--" }));
        cmbLoaiSP.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbLoaiSPItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout pnlThongTinLayout = new javax.swing.GroupLayout(pnlThongTin);
        pnlThongTin.setLayout(pnlThongTinLayout);
        pnlThongTinLayout.setHorizontalGroup(
            pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinLayout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTenLoaiSP)
                    .addComponent(lblMaLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(425, Short.MAX_VALUE))
        );
        pnlThongTinLayout.setVerticalGroup(
            pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaLoaiSP)
                    .addComponent(txtMaLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLoaiSP))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64))
        );

        btnThem.setBackground(new java.awt.Color(0, 153, 51));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnCapNhat.setBackground(new java.awt.Color(0, 204, 255));
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCapNhat.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update.png"))); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        btnXoaTrang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaTrang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eraser.png"))); // NOI18N
        btnXoaTrang.setText("Xóa trắng");
        btnXoaTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTrangActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(204, 51, 0));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnLuu.setBackground(new java.awt.Color(0, 153, 51));
        btnLuu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLuu.setForeground(new java.awt.Color(255, 255, 255));
        btnLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N
        btnLuu.setText("Lưu");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        btnThoat.setBackground(new java.awt.Color(204, 51, 0));
        btnThoat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThoat.setForeground(new java.awt.Color(255, 255, 255));
        btnThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/out.png"))); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        String[] heads =  {
            "Mã Loại Sản Phẩm", "Tên Loại Sản Phẩm", "Loại Sản Phẩm"
        };
        model = new DefaultTableModel(heads, 0);
        table.setModel(model);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(105, 105, 105)
                .addComponent(btnCapNhat)
                .addGap(102, 102, 102)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(105, 105, 105)
                .addComponent(btnXoaTrang)
                .addGap(97, 97, 97)
                .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(119, 119, 119))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(1270, 1270, 1270))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlTieuDe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlThongTin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
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
        xoaLoaiSanPham();
        clearTextFields();
        txtMaLoaiSP.setText("");
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        // TODO add your handling code here:
        if(btnThem.getText().equals("Hủy")){
            if(kiemTraDuLieu()){
                themLoaiSanPham();
                moKhoaControls(true);
                moKhoaTextfield(false);
                clearTextFields();
                btnLuu.setEnabled(false);
                btnThem.setText("Thêm");
                txtMaLoaiSP.setText("");
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
                txtMaLoaiSP.setText("");
            }
        }
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        // TODO add your handling code here:
        ManHinhChinh.getPnlChuongTrinh().removeAll();
        ManHinhChinh.getPnlChuongTrinh().repaint();
        ManHinhChinh.getPnlChuongTrinh().revalidate();
    }//GEN-LAST:event_btnThoatActionPerformed

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
                txtMaLoaiSP.setEditable(false);
            }
            else{
                txtMaLoaiSP.setText(taoMaMoi());
                txtTenLoaiSP.requestFocus();
                moKhoaControls(false);
                moKhoaTextfield(true);
                btnThem.setEnabled(true);
                btnLuu.setEnabled(true);
                btnThem.setText("Hủy");
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        // TODO add your handling code here:
        napDLVaoTextField();
    }//GEN-LAST:event_tableMouseClicked

    private void cmbLoaiSPItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbLoaiSPItemStateChanged

    }//GEN-LAST:event_cmbLoaiSPItemStateChanged
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaTrang;
    private javax.swing.JComboBox<String> cmbLoaiSP;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblLoaiSP;
    private javax.swing.JLabel lblMaLoaiSP;
    private javax.swing.JLabel lblTenLoaiSP;
    private javax.swing.JPanel pnlThongTin;
    private javax.swing.JPanel pnlTieuDe;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtMaLoaiSP;
    private javax.swing.JTextField txtTenLoaiSP;
    // End of variables declaration//GEN-END:variables
}

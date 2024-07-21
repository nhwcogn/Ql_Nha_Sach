/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;
import dao.LoaiSanPhamDAO;

import dao.SanPhamDAO;
import entity.LoaiSanPham;




import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Admin
 */
public class View_timkiemloaisanpham extends javax.swing.JPanel {
    private DefaultTableModel model;
    
    private static final LoaiSanPhamDAO lspDAO = new LoaiSanPhamDAO();
    private ArrayList<LoaiSanPham> list = new ArrayList<>();
    /**
     * Creates new form NewJPanel
     */
    public View_timkiemloaisanpham() {
        initComponents();
        loadTable();
    }
    /**
     * M� lo?i s?n ph?m, T�n lo?i s?n ph?m, Lo?i s?n ph?m
     */
    
    public void loadTable(){
        list = lspDAO.danhSachLSP();
        
        for( LoaiSanPham lsp: list){
            //System.out.println(s);
            model.addRow(new Object[]{
                lsp.getMaLoaiSP(), 
                lsp.getTenLoai(),
                lsp.getLoaiSP()
                
            });
        }
    }
    public void loadTableTuDanhSach(ArrayList<LoaiSanPham> list){
        for( LoaiSanPham lsp: list){
            //System.out.println(s);
            model.addRow(new Object[]{
                lsp.getMaLoaiSP(), 
                lsp.getTenLoai(),
                lsp.getLoaiSP()
            });
        }
    }
    
    private void clearTable(){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }
    private void moKhoaControls(boolean b){
        btnTimKiem.setEnabled(b);
        
        btnXoaTrang.setEnabled(b);
        
    }
    
    public void napDLVaoTextfield(){
        int n = table.getSelectedRow();
        if(n == -1)
            return;
        ArrayList<LoaiSanPham> list = new ArrayList<>();
        list = lspDAO.timKiemDanhSachTheoMa(model.getValueAt(n, 0).toString());
        for(LoaiSanPham lsp: list){
            txtMaLSP.setText(table.getValueAt(n, 0).toString());
            txtTenLSP.setText(lsp.getTenLoai());
            cmbLSP.addItem(lsp.getLoaiSP());
            cmbLSP.setSelectedItem(lsp.getLoaiSP());
        } 
    }
    
    private void clearTextFields(){
            txtMaLSP.setText("");
            txtTenLSP.setText("");
            cmbLSP.setSelectedIndex(0);
            
    }
    public void hienThiThongBao(String thongBao, JTextField txt){
        JOptionPane.showMessageDialog(null, thongBao);
        if(txt == null)
            return;
        txt.requestFocus();
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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMaLSP = new javax.swing.JTextField();
        txtTenLSP = new javax.swing.JTextField();
        cmbLSP = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        btnTimKiem = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btnXoaTrang = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));

        jLabel1.setBackground(new java.awt.Color(0, 153, 153));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Tìm Kiếm Sản Phẩm");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(578, 578, 578)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm Kiếm Loại Sản Phẩm "));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Mã Loại Sản Phẩm:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Tên Loại Sản Phẩm:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Loại Sản Phẩm:");

        txtMaLSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaLSPActionPerformed(evt);
            }
        });

        txtTenLSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenLSPActionPerformed(evt);
            }
        });

        cmbLSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Loại Sản Phẩm--" }));
        cmbLSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbLSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(495, 495, 495)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtMaLSP, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenLSP, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                    .addComponent(cmbLSP, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaLSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenLSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbLSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimKiem.setText("TÌm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        table.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        table.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        String[] heads =  {
            "Mã Loại Sản Phẩm", "Tên Loại Sản Phẩm", "Loại Sản Phẩm"
        };
        model = new DefaultTableModel(heads, 0);
        table.setModel(model);
        table.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tableAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        btnXoaTrang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaTrang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eraser.png"))); // NOI18N
        btnXoaTrang.setText("Xóa trắng");
        btnXoaTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTrangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1299, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTimKiem)
                .addGap(18, 18, 18)
                .addComponent(btnXoaTrang)
                .addGap(92, 92, 92))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(161, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        String ma = txtMaLSP.getText();
        String tenLoai = txtTenLSP.getText();
        String loaiSP = cmbLSP.getSelectedItem().toString();
        
        ArrayList<LoaiSanPham> listMa = new ArrayList<>();
        if(!ma.equals("") && tenLoai.equals("")){
            System.out.println(ma);
            listMa = lspDAO.timKiemDanhSachTheoMa(ma);
            //System.out.println(listMa);
            clearTable();
            loadTableTuDanhSach(listMa);
        }
        else if(!ma.equals("")&& !tenLoai.equals("")){
            listMa = lspDAO.timKiemDanhSachTheoMa(ma);
            ArrayList<LoaiSanPham> listTen = new ArrayList<>();
            for(LoaiSanPham lsp: listMa){
                if(lsp.getTenLoai().toLowerCase().contains(tenLoai.toLowerCase()))
                    listTen.add(lsp);
            }
            clearTable();
            loadTableTuDanhSach(listTen);
        }     
        else 
            JOptionPane.showMessageDialog(this, "Nh?p ?? th�ng tin");
       
        
        
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void cmbLSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbLSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbLSPActionPerformed

    private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTrangActionPerformed
        // TODO add your handling code here:
         clearTextFields();
    }//GEN-LAST:event_btnXoaTrangActionPerformed

    private void txtTenLSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenLSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenLSPActionPerformed

    private void txtMaLSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaLSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaLSPActionPerformed

    private void tableAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tableAncestorAdded
        // TODO add your handling code here:
       
    }//GEN-LAST:event_tableAncestorAdded

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        // TODO add your handling code here:
         napDLVaoTextfield();
    }//GEN-LAST:event_tableMouseClicked
        

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoaTrang;
    private javax.swing.JComboBox<String> cmbLSP;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtMaLSP;
    private javax.swing.JTextField txtTenLSP;
    // End of variables declaration//GEN-END:variables
}

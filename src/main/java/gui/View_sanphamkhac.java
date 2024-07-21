package gui;

import dao.LoaiSanPhamDAO;
import dao.SanPhamDAO;
import entity.LoaiSanPham;
//import entity.NhaCungCap;
import entity.SanPham;
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
public class View_sanphamkhac extends javax.swing.JPanel {

    /**
     * Creates new form NewJPanel
     */
    private DefaultTableModel model;
    private SanPham sanpham;
    private ArrayList<SanPham> dsSanPham;
    private static final SanPhamDAO DAO = new SanPhamDAO();
    private static final LoaiSanPhamDAO LSPDAO = new LoaiSanPhamDAO();

    public View_sanphamkhac() {
        initComponents();
        loadTable();
        //napDLVaoTextField();
    }

    private void moKhoaControls(boolean b) {
        btnCapNhat.setEnabled(b);
        btnLuu.setEnabled(b);
        btnThem.setEnabled(b);
        btnThoat.setEnabled(b);
        btnXoa.setEnabled(b);
        btnXoaTrang.setEnabled(b);
    }

    private void moKhoaTextfield(boolean b) {
        txtDonGia.setEditable(b);
        txtDonViTinh.setEditable(b);
        txtMaSanPham.setEditable(b);
        txtSoLuong.setEditable(b);
        txtTenSanPham.setEditable(b);
        cmbLoaiSP.setEnabled(b);
        cmbTenLSP.setEnabled(b);
    }

    private void clearTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }

    /**
     * {"Mã Sản Phẩm", "Tên Sản Phẩm", "Số Lượng", "Đơn Giá", "Loại Sản Phẩm",
     * "Tên Loại Sản Phẩm", "Nhà Cung Cấp"};
*
     */
    public void loadTable() {
        dsSanPham = DAO.timDanhSachSanPham();
        for (SanPham sp : dsSanPham) {
            model.addRow(new Object[]{
                sp.getMa_SanPham(), sp.getTenSanPham(),
                sp.getSoLuong(), sp.getDonGia(), sp.getDvTinh(),
                sp.getLoaiSanPham().getLoaiSP(), sp.getLoaiSanPham().getTenLoai(), sp.getLoaiSanPham().getMaLoaiSP()});
        }
    }

    public void hienThiThongBao(String thongBao, JTextField txt) {
        JOptionPane.showMessageDialog(null, thongBao);
        if (txt == null) {
            return;
        }
        txt.requestFocus();
    }

    private void clearTextFields() {
        txtDonGia.setText("");
        txtDonViTinh.setText("");

        txtMaSanPham.setText("");
        txtSoLuong.setText("");
        txtTenSanPham.setText("");

        cmbLoaiSP.setSelectedIndex(0);
        cmbTenLSP.setSelectedItem(0);
        txtMaLSP.setText("");
    }

    private void napDLVaoTextField() {
        int n = table.getSelectedRow();
        if (n == -1) {
            return;
        }
        ArrayList<SanPham> listSanPham = new ArrayList<>();
        listSanPham = DAO.timDanhSachSanPhamTheoMaSP(model.getValueAt(n, 0).toString());
        for (SanPham sp : listSanPham) {
            txtMaSanPham.setText(model.getValueAt(n, 0).toString());

            txtTenSanPham.setText(sp.getTenSanPham());
            txtSoLuong.setText(String.valueOf(sp.getSoLuong()));
            //DecimalFormat format = new DecimalFormat("#,###");
            //txtDonGia.setText(format.format(sp.getDonGia()));
            txtDonGia.setText(String.valueOf(sp.getDonGia()));
            txtDonViTinh.setText(sp.getDvTinh());
            LoaiSanPham lsp = sp.getLoaiSanPham();
            //System.out.println(lsp);
            if (cmbLoaiSP.getItemCount() - 1 == 0) {
                ArrayList<String> listLoaiSP = LSPDAO.layDSLoaiSanPham();
                HashSet<String> newHashet = new HashSet<>(listLoaiSP);
                ArrayList<String> newList = new ArrayList<>(newHashet);

                for (String loaiSP : newList) {
                    cmbLoaiSP.addItem(loaiSP);
                }
            }
            for (int i = 0; i < cmbLoaiSP.getItemCount(); i++) {
                if (cmbLoaiSP.getItemAt(i).equals(sp.getLoaiSanPham().getLoaiSP())) {
                    cmbLoaiSP.setSelectedIndex(i);
                }
            }
            
            if (cmbTenLSP.getItemCount() - 1 == 0) {
                ArrayList<String> lisTenLoaiSP = LSPDAO.danhSachTenLoaiSPTheoLSP(sp.getLoaiSanPham().getLoaiSP());
                HashSet<String> newHashet02 = new HashSet<>(lisTenLoaiSP);
                ArrayList<String> newList02 = new ArrayList<>(newHashet02);

                for (String loaiSP : newList02) {
                    cmbTenLSP.addItem(loaiSP);
                }
            }
            for (int i = 0; i < cmbTenLSP.getItemCount(); i++) {
                if (cmbTenLSP.getItemAt(i).contains(sp.getLoaiSanPham().getTenLoai())) {
                    cmbTenLSP.setSelectedIndex(i);
                }
            }
            
            //cmbTenLoaiSP.setSelectedItem(sp.getLoaiSanPham().getTenLoai());
            //txtLoaiSP.setText(sp.getLoaiSanPham().getLoaiSP());
            //txtTenLSP.setText(sp.getLoaiSanPham().getTenLoai());
            txtMaLSP.setText(sp.getLoaiSanPham().getMaLoaiSP());
        }
    }

    public boolean kiemTraDuLieu() {
        if (!(txtMaSanPham.getText().matches("SP\\d{3}"))) {
            hienThiThongBao("Định dạng phải là SP + xxx ", txtMaSanPham);
            txtMaSanPham.requestFocus();
            return false;
        }
        if (txtTenSanPham.getText().isBlank()) {
            hienThiThongBao("Không được để trống tên sản phẩm", txtTenSanPham);
            return false;
        }
        if (!(txtDonGia.getText().matches("^\\d+(\\.\\d+)?$"))) {
            hienThiThongBao("Đơn giá không được nhập chữ", txtTenSanPham);
            return false;
        }
        if (!(txtDonViTinh.getText().matches("^[\\p{L}0-9 ]+$"))) {
            hienThiThongBao("Đơn vị tính không được nhập ký tự đặc biệt", txtTenSanPham);
            return false;
        }
        if (!(txtSoLuong.getText().matches("^\\d+$"))) {
            hienThiThongBao("Định dạng số lượng phải là số dương", txtTenSanPham);
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

    private String taoMaMoi() {
        String maCuoi = DAO.getMaCuoi();

        return tangChuoiSo(maCuoi);
    }

    public void xoaSanPham() {
        int row = table.getSelectedRow();
        if (row == -1) {
            hienThiThongBao("Chọn sản phẩm cần xóa", null);
        } else {
            String ma = table.getValueAt(row, 0).toString();
            int op = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa không", "delete", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_OPTION) {
                if (DAO.xoaSanPham(ma)) {
                    hienThiThongBao("Xóa thành công", null);
                    clearTable();
                    loadTable();
                } else {
                    hienThiThongBao("Xóa thất bại", null);
                    return;
                }
            }
        }
    }

    public void suaThongTin() {
        int op = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn cập nhật thông tin không",
                "Cập nhật",
                JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            String maSP = txtMaSanPham.getText().trim();
            String tenSP = txtTenSanPham.getText().trim();
            int sl = Integer.parseInt(txtSoLuong.getText().trim());
            double donGia = Double.parseDouble(txtDonGia.getText().trim());
            String dvTinh = txtDonViTinh.getText().trim();

            String tenLSP = cmbTenLSP.getSelectedItem().toString();
            String loaiSP = cmbLoaiSP.getSelectedItem().toString();
            String maLSP = txtMaLSP.getText();
            LoaiSanPham lsp = new LoaiSanPham(maLSP, loaiSP, tenLSP);
            //System.out.println(lsp);
            // tìm kiếm mã loại sản phẩm theo tên loại sản phẩm
            SanPham sp = new SanPham(maSP, tenSP, donGia, sl, dvTinh, lsp);
            if (DAO.suaSanPham(sp)) {
                hienThiThongBao("Cập nhật thành công", null);
                clearTable();
                loadTable();
            } else {
                hienThiThongBao("Cập nhật thất bại", null);
            }
        }
    }

    public void themSanPham() {
        //
        String maSP = txtMaSanPham.getText().trim();
        String tenSP = txtTenSanPham.getText().trim();
        int sl = Integer.parseInt(txtSoLuong.getText().trim());
        double donGia = Double.parseDouble(txtDonGia.getText().trim());
        String dvTinh = txtDonViTinh.getText().trim();

        //String loaiSP = txtLoaiSP.getText();
        //String tenLSP = txtTenLSP.getText();
        String loaiSP = cmbLoaiSP.getSelectedItem().toString();
        String tenLSP = cmbTenLSP.getSelectedItem().toString();
        String maLSP = txtMaLSP.getText();
        LoaiSanPham lsp = new LoaiSanPham(maLSP, loaiSP, tenLSP);
        //System.out.println(lsp);
        // tìm kiếm mã loại sản phẩm theo tên loại sản phẩm
        SanPham sp = new SanPham(maSP, tenSP, donGia, sl, dvTinh, lsp);
        //System.out.println(sp);
        if (DAO.themSanPham(sp)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            clearTable();
            loadTable();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại");
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
        lblMaSanPham = new javax.swing.JLabel();
        lblTenSanPham = new javax.swing.JLabel();
        lblSoLuong = new javax.swing.JLabel();
        lblDonGia = new javax.swing.JLabel();
        lblDonViTinh = new javax.swing.JLabel();
        lblLoaiSP = new javax.swing.JLabel();
        lblTenLoaiSP = new javax.swing.JLabel();
        txtMaSanPham = new javax.swing.JTextField();
        txtTenSanPham = new javax.swing.JTextField();
        txtDonGia = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        txtDonViTinh = new javax.swing.JTextField();
        lblMaLSP = new javax.swing.JLabel();
        txtMaLSP = new javax.swing.JTextField();
        cmbLoaiSP = new javax.swing.JComboBox<>();
        cmbTenLSP = new javax.swing.JComboBox<>();
        pnlTable = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnXoaTrang = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btnThoat = new javax.swing.JButton();

        pnlTieuDe.setBackground(new java.awt.Color(0, 153, 153));

        lblTieuDe.setBackground(new java.awt.Color(0, 153, 153));
        lblTieuDe.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTieuDe.setText("Danh Mục Sản Phẩm");

        javax.swing.GroupLayout pnlTieuDeLayout = new javax.swing.GroupLayout(pnlTieuDe);
        pnlTieuDe.setLayout(pnlTieuDeLayout);
        pnlTieuDeLayout.setHorizontalGroup(
            pnlTieuDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTieuDeLayout.createSequentialGroup()
                .addGap(578, 578, 578)
                .addComponent(lblTieuDe)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlTieuDeLayout.setVerticalGroup(
            pnlTieuDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTieuDeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTieuDe)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlThongTin.setBorder(javax.swing.BorderFactory.createTitledBorder("Sản Phẩm Khác"));

        lblMaSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMaSanPham.setText("Mã Sản Phẩm:");

        lblTenSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTenSanPham.setText("Tên Sản Phẩm:");

        lblSoLuong.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSoLuong.setText("Số Lượng:");

        lblDonGia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDonGia.setText("Đơn Giá:");

        lblDonViTinh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDonViTinh.setText("ĐV Tính:");

        lblLoaiSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblLoaiSP.setText("Loại Sản Phẩm:");

        lblTenLoaiSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTenLoaiSP.setText("Tên Loại Sản Phẩm:");

        lblMaLSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMaLSP.setText("Mã loại sản phẩm");

        cmbLoaiSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Loại sản phẩm --" }));

        cmbTenLSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Tên loại sản phẩm --" }));
        cmbTenLSP.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTenLSPItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout pnlThongTinLayout = new javax.swing.GroupLayout(pnlThongTin);
        pnlThongTin.setLayout(pnlThongTinLayout);
        pnlThongTinLayout.setHorizontalGroup(
            pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinLayout.createSequentialGroup()
                .addGap(233, 233, 233)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTenSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMaSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlThongTinLayout.createSequentialGroup()
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(38, 38, 38)
                            .addComponent(lblDonViTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtDonViTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(100, 100, 100)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTenLoaiSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaLSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaLSP, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                    .addComponent(cmbLoaiSP, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbTenLSP, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        pnlThongTinLayout.setVerticalGroup(
            pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMaSanPham))
                        .addGap(20, 20, 20)
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlThongTinLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblDonViTinh)
                                    .addComponent(txtDonViTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlThongTinLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblSoLuong)
                                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDonGia)))
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblLoaiSP)
                            .addComponent(cmbLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTenLoaiSP)
                            .addComponent(cmbTenLSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMaLSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMaLSP))))
                .addContainerGap())
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

        table.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        table.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        String[] heads = {"Mã Sản Phẩm", "Tên Sản Phẩm", "Số Lượng", "Đơn Giá", "Đơn vị tính","Loại Sản Phẩm", "Tên loại sản phẩm","Mã loại sản phẩm"};
        model = new DefaultTableModel(heads,0);
        table.setModel(model);
        table.setShowGrid(true);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

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

        javax.swing.GroupLayout pnlTableLayout = new javax.swing.GroupLayout(pnlTable);
        pnlTable.setLayout(pnlTableLayout);
        pnlTableLayout.setHorizontalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTableLayout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81)
                        .addComponent(btnCapNhat)
                        .addGap(108, 108, 108)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(btnXoaTrang)
                        .addGap(90, 90, 90)
                        .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(115, 115, 115)
                        .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlTableLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1222, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        pnlTableLayout.setVerticalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(224, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTieuDe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTable, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(pnlThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        // TODO add your handling code here:
        if (btnCapNhat.getText().equals("Cập nhật")) {
            int row = table.getSelectedRow();
            if (row == -1) {
                hienThiThongBao("Chưa chọn sản phẩm", null);
            } else {
                moKhoaTextfield(true);
                moKhoaControls(false);
                btnLuu.setEnabled(true);
                btnCapNhat.setEnabled(true);
                btnXoaTrang.setEnabled(true);
                btnCapNhat.setText("Hủy");
            }
        } else if (btnCapNhat.getText().equals("Hủy")) {
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
        xoaSanPham();
        clearTextFields();
        txtMaSanPham.setText("");
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        // TODO add your handling code here:
        if (btnThem.getText().equals("Hủy")) {
            if (kiemTraDuLieu()) {
                themSanPham();
                moKhoaControls(true);
                moKhoaTextfield(false);
                clearTextFields();
                btnLuu.setEnabled(false);
                btnThem.setText("Thêm");
                txtMaSanPham.setText("");
            }
        }
        if (btnCapNhat.getText().equals("Hủy")) {
            if (kiemTraDuLieu()) {
                suaThongTin();
                moKhoaTextfield(false);
                clearTextFields();
                moKhoaControls(true);
                btnLuu.setEnabled(false);
                btnXoaTrang.setEnabled(false);
                btnLuu.setText("Lưu");
                btnCapNhat.setText("Cập nhật");
                txtMaSanPham.setText("");
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

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if (evt.getSource().equals(btnThem)) {
            if (btnThem.getText().equals("Hủy")) {
                moKhoaControls(true);
                moKhoaTextfield(false);
                clearTextFields();
                btnThem.setEnabled(true);
                btnLuu.setEnabled(false);
                btnThem.setText("Thêm");
                txtMaSanPham.setEditable(false);
            } else {
                txtMaSanPham.setText(taoMaMoi());
                txtTenSanPham.requestFocus();
                moKhoaControls(false);
                moKhoaTextfield(true);
                btnThem.setEnabled(true);
                btnLuu.setEnabled(true);
                btnThem.setText("Hủy");
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void cmbTenLSPItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTenLSPItemStateChanged
        // TODO add your handling code here:
        if(evt.getStateChange() == ItemEvent.SELECTED){
            String tenLoaiSach = cmbTenLSP.getSelectedItem().toString();
            //txtMaTacGia.setText(tgDAO.timKiem1TGTheoTen(tenTG).getMaTacGia());
            if(cmbTenLSP.getSelectedIndex() == 0)
                txtMaLSP.setText("Chưa chọn tên loại sản phẩm");
            else
                txtMaLSP.setText(LSPDAO.timKiemTheoTen(tenLoaiSach).getMaLoaiSP());
        }
    }//GEN-LAST:event_cmbTenLSPItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaTrang;
    private javax.swing.JComboBox<String> cmbLoaiSP;
    private javax.swing.JComboBox<String> cmbTenLSP;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDonGia;
    private javax.swing.JLabel lblDonViTinh;
    private javax.swing.JLabel lblLoaiSP;
    private javax.swing.JLabel lblMaLSP;
    private javax.swing.JLabel lblMaSanPham;
    private javax.swing.JLabel lblSoLuong;
    private javax.swing.JLabel lblTenLoaiSP;
    private javax.swing.JLabel lblTenSanPham;
    private javax.swing.JLabel lblTieuDe;
    private javax.swing.JPanel pnlTable;
    private javax.swing.JPanel pnlThongTin;
    private javax.swing.JPanel pnlTieuDe;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtDonViTinh;
    private javax.swing.JTextField txtMaLSP;
    private javax.swing.JTextField txtMaSanPham;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSanPham;
    // End of variables declaration//GEN-END:variables
}

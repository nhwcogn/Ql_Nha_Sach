
package gui;

import dao.LoaiSanPhamDAO;
import dao.NhaCungCapDAO;
import dao.NhaXuatBanDAO;
import dao.SachDAO;
import dao.SanPhamDAO;
import dao.TacGiaDAO;
import entity.LoaiSanPham;
import entity.NhaXuatBan;
import entity.Sach;
import entity.SanPham;
import entity.TacGia;
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
public class View_danhmucsach extends javax.swing.JPanel {

    /**
     * Creates new form NewJPanel
     */
    private DefaultTableModel model;
    private static final SachDAO sachDAO = new SachDAO();
    private static final SanPhamDAO spDAO = new SanPhamDAO();
    private static final LoaiSanPhamDAO lspDAO = new LoaiSanPhamDAO();
    private static final TacGiaDAO tgDAO = new TacGiaDAO();
    private static final NhaXuatBanDAO nxbDAO = new NhaXuatBanDAO();
    private ArrayList<Sach> list = new ArrayList<>();

    public View_danhmucsach() {
        initComponents();
        loadTable();
    }

    /*
    String[] heads =  {
        "Mã Sách", "Tên Sách", "Số Lượng", 
        "Đơn vị tính", "Đơn Giá", 
        "Loại Sách", "Tên loại sách", 
        "Tác Giả", "Số Trang", 
        "Năm xuất bản", "Nhà Xuất Bản"
    };
     */
    public void loadTable() {
        list = sachDAO.dsSach();

        for (Sach s : list) {
            //System.out.println(s);
            model.addRow(new Object[]{s.getSanPham().getMa_SanPham(), s.getSanPham().getTenSanPham(), s.getSanPham().getSoLuong(),
                s.getSanPham().getDvTinh(), s.getSanPham().getDonGia(),
                s.getSanPham().getLoaiSanPham().getLoaiSP(), s.getSanPham().getLoaiSanPham().getTenLoai(),
                s.getTacGia().getTenTacGia(), s.getSoTrang(),
                s.getNamSanXuat(), s.getNhaXuatBan().getTenNXB()});
        }
    }

    private void clearTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }

    private void moKhoaControls(boolean b) {
        btnCapNhat.setEnabled(b);
        btnLuu.setEnabled(b);
        btnThem.setEnabled(b);
        btnThoat.setEnabled(b);
        btnXoaTrang.setEnabled(b);
        btnXoa.setEnabled(b);
    }

    public void napDLVaoTextfield() {
        int n = table.getSelectedRow();
        if (n == -1) {
            return;
        }
        ArrayList<Sach> list = new ArrayList<>();
        list = sachDAO.dsSachTheoMa(model.getValueAt(n, 0).toString());
        for (Sach s : list) {
            txtMaSach.setText(table.getValueAt(n, 0).toString());
            txtTenSach.setText(s.getSanPham().getTenSanPham());
            txtSoLuong.setText(String.valueOf(s.getSanPham().getSoLuong()));
            txtDonViTinh.setText(s.getSanPham().getDvTinh());
            txtDonGia.setText(String.valueOf(s.getSanPham().getDonGia()));

            txtMaLoaiSach.setText(s.getSanPham().getLoaiSanPham().getMaLoaiSP());
//            cmbLoaiSach.addItem(s.getSanPham().getLoaiSanPham().getLoaiSP());
//            cmbLoaiSach.setSelectedItem(s.getSanPham().getLoaiSanPham().getLoaiSP());
//            cmbTenLoaiSach.addItem(s.getSanPham().getLoaiSanPham().getTenLoai());
//            cmbTenLoaiSach.setSelectedItem(s.getSanPham().getLoaiSanPham().getTenLoai());
            if (cmbLoaiSach.getItemCount() - 1 == 0) {
                ArrayList<String> listLoaiSP = lspDAO.layDSLoaiSanPham();
                HashSet<String> newHashet = new HashSet<>(listLoaiSP);
                ArrayList<String> newList = new ArrayList<>(newHashet);

                for (String loaiSP : newList) {
                    cmbLoaiSach.addItem(loaiSP);
                }
            }
            for (int i = 0; i < cmbLoaiSach.getItemCount(); i++) {
                if (cmbLoaiSach.getItemAt(i).equals(s.getSanPham().getLoaiSanPham().getLoaiSP())) {
                    cmbLoaiSach.setSelectedIndex(i);
                }
            }

            if (cmbTenLoaiSach.getItemCount() - 1 == 0) {
                ArrayList<String> lisTenLoaiSP = lspDAO.danhSachTenLoaiSPTheoLSP(s.getSanPham().getLoaiSanPham().getLoaiSP());
                HashSet<String> newHashet02 = new HashSet<>(lisTenLoaiSP);
                ArrayList<String> newList02 = new ArrayList<>(newHashet02);

                for (String loaiSP : newList02) {
                    cmbTenLoaiSach.addItem(loaiSP);
                }
            }
            for (int i = 0; i < cmbTenLoaiSach.getItemCount(); i++) {
                if (cmbTenLoaiSach.getItemAt(i).contains(s.getSanPham().getLoaiSanPham().getTenLoai())) {
                    cmbTenLoaiSach.setSelectedIndex(i);
                }
            }

            txtMaTacGia.setText(s.getTacGia().getMaTacGia());
            //txtTenTacGia.setText(s.getTacGia().getTenTacGia());
            if (cmbTenTG.getItemCount() - 1 == 0) {
                ArrayList<String> lisTenLoaiSP = tgDAO.dsTenTG();
                HashSet<String> newHashet02 = new HashSet<>(lisTenLoaiSP);
                ArrayList<String> newList02 = new ArrayList<>(newHashet02);

                for (String tenTG : newList02) {
                    cmbTenTG.addItem(tenTG);
                }
            }
            for (int i = 0; i < cmbTenTG.getItemCount(); i++) {
                if (cmbTenTG.getItemAt(i).contains(s.getTacGia().getTenTacGia())) {
                    cmbTenTG.setSelectedIndex(i);
                }
            }
            txtSoTrang.setText(String.valueOf(s.getSoTrang()));
            txtNamXuatBan.setText(String.valueOf(s.getNamSanXuat()));
            txtMaNXB.setText(s.getNhaXuatBan().getMaNhaXuatBan());
            if (cmbTenNXB.getItemCount() - 1 == 0) {
                ArrayList<String> lisTenNXB = nxbDAO.dsTenNXB();
                HashSet<String> newHashet02 = new HashSet<>(lisTenNXB);
                ArrayList<String> newList02 = new ArrayList<>(newHashet02);

                for (String tenNXB : newList02) {
                    cmbTenNXB.addItem(tenNXB);
                }
            }
            for (int i = 0; i < cmbTenNXB.getItemCount(); i++) {
                if (cmbTenNXB.getItemAt(i).contains(s.getNhaXuatBan().getTenNXB())) {
                    cmbTenNXB.setSelectedIndex(i);
                }
            }
            //txtTenNXB.setText(s.getNhaXuatBan().getTenNXB());
        }
    }

    private void clearTextFields() {
        txtMaSach.setText("");
        txtTenSach.setText("");
        txtSoLuong.setText("");
        txtDonViTinh.setText("");
        txtDonGia.setText("");

        txtMaLoaiSach.setText("");

        cmbLoaiSach.setSelectedIndex(0);
        cmbTenLoaiSach.setSelectedIndex(0);

        txtMaTacGia.setText("");
        cmbTenTG.setSelectedIndex(0);
        txtSoTrang.setText("");
        txtNamXuatBan.setText("");
        txtMaNXB.setText("");
        cmbTenNXB.setSelectedIndex(0);
    }

    public void hienThiThongBao(String thongBao, JTextField txt) {
        JOptionPane.showMessageDialog(null, thongBao);
        if (txt == null) {
            return;
        }
        txt.requestFocus();
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
        String maCuoi = spDAO.getMaCuoi();
        return tangChuoiSo(maCuoi);
    }

    public void xoaSach() {
        int row = table.getSelectedRow();
        if (row == -1) {
            hienThiThongBao("Chọn sách cần xóa", null);
        } else {
            String ma = table.getValueAt(row, 0).toString();
            String temp = ma;
            int op = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa không",
                    "delete",
                    JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_OPTION) {
                //String temp = ma;

                if (sachDAO.xoaSach(ma)) {
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
            String maSach = txtMaSach.getText().toString();
            String tenSach = txtTenSach.getText().toString();
            double donGia = Double.valueOf(txtDonGia.getText().toString());
            int soluong = Integer.valueOf(txtSoLuong.getText().toString());
            String dvTinh = txtDonViTinh.getText().toString();

            String maLoaiSach = txtMaLoaiSach.getText().toString();
            String loaiSach = cmbLoaiSach.getSelectedItem().toString();
            String tenLoaiSach = cmbTenLoaiSach.getSelectedItem().toString();

            String maTG = txtMaTacGia.getText().toString();
            String tenTG = cmbTenTG.getSelectedItem().toString();

            String maNXB = txtMaNXB.getText().toString();
            String tenNXB = cmbTenNXB.getSelectedItem().toString();
            NhaXuatBan nxb = new NhaXuatBan(maNXB, tenNXB, null);
            TacGia tg = new TacGia(maTG, tenTG);
            LoaiSanPham lsp = new LoaiSanPham(maLoaiSach, loaiSach, tenLoaiSach);
            SanPham sp = new SanPham(maSach,
                    tenSach,
                    donGia,
                    soluong,
                    dvTinh,
                    lsp);

            int soTrang = Integer.valueOf(txtSoTrang.getText().toString());
            int namXB = Integer.valueOf(txtNamXuatBan.getText().toString());
            Sach sach = new Sach(sp, soTrang, namXB, tg, nxb);
            System.out.println(sach);
            if (sachDAO.suaSachDemo(sach)) {
                hienThiThongBao("Cập nhật thành công", null);
                clearTable();
                loadTable();
            } else {
                hienThiThongBao("Cập nhật thất bại", null);
            }
        }
    }

    public void themSach() {
        String maSach = txtMaSach.getText().toString();
        String tenSach = txtTenSach.getText().toString();
        double donGia = Double.parseDouble(txtDonGia.getText().toString());
        int soluong = Integer.parseInt(txtSoLuong.getText().toString());
        String dvTinh = txtDonViTinh.getText().toString();

        String maLoaiSach = txtMaLoaiSach.getText().toString();
        String loaiSach = cmbLoaiSach.getSelectedItem().toString();
        String tenLoaiSach = cmbTenLoaiSach.getSelectedItem().toString();

        String maTG = txtMaTacGia.getText().toString();
        String tenTG = cmbTenTG.getSelectedItem().toString();

        String maNXB = txtMaNXB.getText().toString();
        String tenNXB = cmbTenNXB.getSelectedItem().toString();
        NhaXuatBan nxb = new NhaXuatBan(maNXB, tenNXB, null);
        TacGia tg = new TacGia(maTG, tenTG);
        LoaiSanPham lsp = new LoaiSanPham(maLoaiSach, loaiSach, tenLoaiSach);
        SanPham sp = new SanPham(maSach,
                tenSach,
                donGia,
                soluong,
                dvTinh,
                lsp);
        boolean kqthemSP = spDAO.themSanPham(sp);

        int soTrang = Integer.valueOf(txtSoTrang.getText().toString());
        int namXB = Integer.valueOf(txtNamXuatBan.getText().toString());
        Sach sach = new Sach(sp, soTrang, namXB, tg, nxb);
        System.out.println(sach);

        if (sachDAO.themSach(sach) && kqthemSP) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            clearTable();
            loadTable();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại");
        }
    }
    public boolean kiemTraDuLieu(){
        if((!txtMaSach.getText().matches("^SP\\d{1,10}$"))){
            hienThiThongBao("Định dạng mã sách sai (SPxxx)", txtMaSach);
            txtMaSach.requestFocus();
            return false;
        }
        if(!txtTenSach.getText().matches("^[a-zA-ZÀ-ỹ\\s]+$")){
            
            hienThiThongBao("Không được nhập ký tự đặc biệt", txtTenSach);
            txtTenSach.requestFocus();
            return false;
        }
        if(Integer.parseInt(txtSoLuong.getText())<= 0){
            hienThiThongBao("Số lượng không được <= 0", txtMaNXB);
            txtSoLuong.requestFocus();
            return false;
        }
        if(Integer.parseInt(txtSoTrang.getText())<= 0){
            hienThiThongBao("Số trang không được <= 0", txtMaNXB);
            txtSoTrang.requestFocus();
            return false;
        }
        if(!txtDonViTinh.getText().matches("^[a-zA-ZÀ-ỹ\\s]+$")){
            
            hienThiThongBao("Không được nhập ký tự đặc biệt", txtTenSach);
            txtDonViTinh.requestFocus();
            return false;
        }
        if(Integer.parseInt(txtNamXuatBan.getText())<= 0){
            hienThiThongBao("Năm xuất bản không được <= 0", txtMaNXB);
            txtNamXuatBan.requestFocus();
            return false;
        }
        if(Double.parseDouble(txtDonGia.getText())<= 0){
            hienThiThongBao("Đơn giá không được <= 0", txtMaNXB);
            txtDonGia.requestFocus();
            return false;
        }
        if(cmbLoaiSach.getSelectedIndex() == 0){
            hienThiThongBao("Chưa chọn loại sách", txtMaNXB);
            cmbLoaiSach.requestFocus();
            return false;
        }
        if(cmbTenLoaiSach.getSelectedIndex() == 0){
            hienThiThongBao("Chưa chọn tên loại sách", txtMaNXB);
            cmbTenLoaiSach.requestFocus();
            return false;
        }
        if(cmbTenNXB.getSelectedIndex() == 0){
            hienThiThongBao("Chưa chọn nhà xuất bản", txtMaNXB);
            cmbTenNXB.requestFocus();
            return false;
        }
        if(cmbTenTG.getSelectedIndex() == 0){
            hienThiThongBao("Chưa chọn tác giả", txtMaNXB);
            cmbTenTG.requestFocus();
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
        pnlThongTin = new javax.swing.JPanel();
        lblMaSach = new javax.swing.JLabel();
        lblTenSach = new javax.swing.JLabel();
        lblSoLuong = new javax.swing.JLabel();
        lblDonGia = new javax.swing.JLabel();
        lblDonViTinh = new javax.swing.JLabel();
        lblLoaiSach = new javax.swing.JLabel();
        lblTenLoaiSach = new javax.swing.JLabel();
        lblTenNXB = new javax.swing.JLabel();
        lblTenTacGia = new javax.swing.JLabel();
        lblSoTrang = new javax.swing.JLabel();
        lblNamXuatBan = new javax.swing.JLabel();
        txtMaSach = new javax.swing.JTextField();
        txtTenSach = new javax.swing.JTextField();
        txtDonGia = new javax.swing.JTextField();
        cmbTenLoaiSach = new javax.swing.JComboBox<>();
        txtSoTrang = new javax.swing.JTextField();
        txtNamXuatBan = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        txtDonViTinh = new javax.swing.JTextField();
        cmbLoaiSach = new javax.swing.JComboBox<>();
        lblmaLoaiSach = new javax.swing.JLabel();
        txtMaLoaiSach = new javax.swing.JTextField();
        lblMaTacGia = new javax.swing.JLabel();
        txtMaTacGia = new javax.swing.JTextField();
        lblTenTacGia1 = new javax.swing.JLabel();
        txtMaNXB = new javax.swing.JTextField();
        cmbTenTG = new javax.swing.JComboBox<>();
        cmbTenNXB = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
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
        lblTieuDe.setText("Danh Mục Sách");

        javax.swing.GroupLayout pnlTieuDeLayout = new javax.swing.GroupLayout(pnlTieuDe);
        pnlTieuDe.setLayout(pnlTieuDeLayout);
        pnlTieuDeLayout.setHorizontalGroup(
            pnlTieuDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTieuDeLayout.createSequentialGroup()
                .addGap(583, 583, 583)
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

        pnlThongTin.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Mục Sách"));

        lblMaSach.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMaSach.setText("Mã Sách:");

        lblTenSach.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTenSach.setText("Tên Sách:");

        lblSoLuong.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSoLuong.setText("Số Lượng:");

        lblDonGia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDonGia.setText("Đơn Giá:");

        lblDonViTinh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDonViTinh.setText("ĐV Tính:");

        lblLoaiSach.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblLoaiSach.setText("Loại sách");

        lblTenLoaiSach.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTenLoaiSach.setText("Tên Loại Sách:");

        lblTenNXB.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTenNXB.setText("Tên Nhà xuất bản");

        lblTenTacGia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTenTacGia.setText("Tên Tác Giả:");

        lblSoTrang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSoTrang.setText("Số Trang:");

        lblNamXuatBan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNamXuatBan.setText("Năm Xuất Bản:");

        cmbTenLoaiSach.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Tên loại sách--" }));
        cmbTenLoaiSach.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTenLoaiSachItemStateChanged(evt);
            }
        });

        txtSoTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoTrangActionPerformed(evt);
            }
        });

        txtNamXuatBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamXuatBanActionPerformed(evt);
            }
        });

        cmbLoaiSach.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Loại sách--" }));
        cmbLoaiSach.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbLoaiSachItemStateChanged(evt);
            }
        });

        lblmaLoaiSach.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblmaLoaiSach.setText("Mã loại sách");

        lblMaTacGia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMaTacGia.setText("Mã tác giả");

        lblTenTacGia1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTenTacGia1.setText("Mã nhà xuất bản");

        cmbTenTG.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Tên tác giả--" }));
        cmbTenTG.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTenTGItemStateChanged(evt);
            }
        });
        cmbTenTG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cmbTenTGKeyReleased(evt);
            }
        });

        cmbTenNXB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Tên nhà xuất bản--" }));
        cmbTenNXB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTenNXBItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout pnlThongTinLayout = new javax.swing.GroupLayout(pnlThongTin);
        pnlThongTin.setLayout(pnlThongTinLayout);
        pnlThongTinLayout.setHorizontalGroup(
            pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                            .addComponent(lblDonGia, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE))
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlThongTinLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtMaSach, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                                    .addComponent(txtTenSach)))
                            .addGroup(pnlThongTinLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDonViTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlThongTinLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtDonGia))))
                    .addComponent(lblDonViTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(110, 110, 110)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTenLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblmaLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtMaLoaiSach)
                                .addComponent(cmbLoaiSach, 0, 258, Short.MAX_VALUE))
                            .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cmbTenLoaiSach, 0, 258, Short.MAX_VALUE)
                                .addComponent(txtSoTrang))))
                    .addComponent(lblSoTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addComponent(lblNamXuatBan, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtNamXuatBan, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(100, 100, 100)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblTenTacGia, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                        .addComponent(lblMaTacGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblTenTacGia1)
                    .addComponent(lblTenNXB))
                .addGap(31, 31, 31)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaTacGia)
                    .addComponent(txtMaNXB)
                    .addComponent(cmbTenTG, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbTenNXB, 0, 216, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlThongTinLayout.setVerticalGroup(
            pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaTacGia)
                    .addComponent(txtMaTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenTacGia)
                    .addComponent(cmbTenTG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenTacGia1)
                    .addComponent(txtMaNXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenNXB)
                    .addComponent(cmbTenNXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlThongTinLayout.createSequentialGroup()
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblLoaiSach)
                            .addComponent(cmbLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTenLoaiSach)
                            .addComponent(cmbTenLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSoTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMaSach)
                            .addComponent(lblmaLoaiSach)
                            .addComponent(txtMaLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSoLuong)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDonViTinh)
                            .addComponent(txtDonViTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(9, 9, 9)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblNamXuatBan)
                        .addComponent(txtNamXuatBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinLayout.createSequentialGroup()
                        .addComponent(lblDonGia)
                        .addGap(46, 46, 46))))
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
        String[] heads =  {
            "Mã Sách", "Tên Sách", "Số Lượng", "Đơn vị tính", "Đơn Giá", "Loại Sách", "Tên loại sách", "Tác Giả", "Số Trang", "Năm xuất bản", "Nhà Xuất Bản"
        };
        model = new DefaultTableModel(heads, 0);
        table.setModel(model);
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104)
                .addComponent(btnCapNhat)
                .addGap(88, 88, 88)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112)
                .addComponent(btnXoaTrang)
                .addGap(103, 103, 103)
                .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(118, 118, 118)
                .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(204, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTieuDe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlThongTin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 12, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        // TODO add your handling code here:
        if (btnCapNhat.getText().equals("Cập nhật")) {
            int row = table.getSelectedRow();
            if (row == -1) {
                hienThiThongBao("Chưa chọn sản phẩm", null);
            } else {
                //moKhoaTextfield(true);
                moKhoaControls(false);
                btnLuu.setEnabled(true);
                btnCapNhat.setEnabled(true);
                btnXoa.setEnabled(true);
                btnCapNhat.setText("Hủy");
            }
        } else if (btnCapNhat.getText().equals("Hủy")) {
            //moKhoaTextfield(false);
            moKhoaControls(true);
            btnLuu.setEnabled(false);
            btnXoa.setEnabled(true);
            btnCapNhat.setText("Cập nhật");
        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnXoaTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTrangActionPerformed
        // TODO add your handling code here:
        clearTextFields();
    }//GEN-LAST:event_btnXoaTrangActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        xoaSach();
        clearTextFields();
        txtMaSach.setText("");

    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        // TODO add your handling code here:
        if (btnThem.getText().equals("Hủy")) {
            if (kiemTraDuLieu()) {
                themSach();
                moKhoaControls(true);
                //moKhoaTextfield(false);
                clearTextFields();
                btnLuu.setEnabled(false);
                btnThem.setText("Thêm");
                txtMaSach.setText("");
            }
        }
        if (btnCapNhat.getText().equals("Hủy")) {
            if (kiemTraDuLieu()) {
                suaThongTin();
                //moKhoaTextfield(false);
                clearTextFields();
                moKhoaControls(true);
                btnLuu.setEnabled(false);
                btnXoa.setEnabled(false);
                btnLuu.setText("Lưu");
                btnCapNhat.setText("Cập nhật");
                txtMaSach.setText("");
            }
        }
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        // TODO add your handling code here:
        ManHinhChinh.getPnlChuongTrinh().removeAll();
        ManHinhChinh.getPnlChuongTrinh().repaint();
        ManHinhChinh.getPnlChuongTrinh().revalidate();
    }//GEN-LAST:event_btnThoatActionPerformed

    private void txtSoTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoTrangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoTrangActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        // TODO add your handling code here:
        napDLVaoTextfield();
    }//GEN-LAST:event_tableMouseClicked

    private void txtNamXuatBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamXuatBanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamXuatBanActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if (evt.getSource().equals(btnThem)) {
            if (btnThem.getText().equals("Hủy")) {
                moKhoaControls(true);
                //moKhoaTextfield(false);
                clearTextFields();
                btnThem.setEnabled(true);
                btnLuu.setEnabled(false);
                btnThem.setText("Thêm");
                txtMaSach.setEditable(false);
            } else {
                txtMaSach.setText(taoMaMoi());
                txtTenSach.requestFocus();
                moKhoaControls(false);
                //moKhoaTextfield(true);
                btnThem.setEnabled(true);
                btnLuu.setEnabled(true);
                btnThem.setText("Hủy");
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void cmbTenTGKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbTenTGKeyReleased
        // TODO add your handling code here:
//        String tenTG = cmbTenTG.getSelectedItem().toString();
//        txtMaTacGia.setText(tgDAO.timKiem1TGTheoTen(tenTG).getMaTacGia());

    }//GEN-LAST:event_cmbTenTGKeyReleased

    private void cmbTenTGItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTenTGItemStateChanged
        // TODO add your handling code here:
        if(evt.getStateChange() == ItemEvent.SELECTED){
            String tenTG = cmbTenTG.getSelectedItem().toString();
            if(cmbTenTG.getSelectedIndex() == 0)
                txtMaTacGia.setText("Chưa chọn tác giả");
            else
                txtMaTacGia.setText(tgDAO.timKiem1TGTheoTen(tenTG).getMaTacGia());
        }
        
    }//GEN-LAST:event_cmbTenTGItemStateChanged

    private void cmbTenLoaiSachItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTenLoaiSachItemStateChanged
        // TODO add your handling code here:
        if(evt.getStateChange() == ItemEvent.SELECTED){
            String tenLoaiSach = cmbTenLoaiSach.getSelectedItem().toString();
            //txtMaTacGia.setText(tgDAO.timKiem1TGTheoTen(tenTG).getMaTacGia());
            if(cmbTenLoaiSach.getSelectedIndex() == 0)
                txtMaLoaiSach.setText("Chưa chọn tên loại sách");
            else
                txtMaLoaiSach.setText(lspDAO.timKiemTheoTen(tenLoaiSach).getMaLoaiSP());
        }
    }//GEN-LAST:event_cmbTenLoaiSachItemStateChanged

    private void cmbTenNXBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTenNXBItemStateChanged
        // TODO add your handling code here:
        if(evt.getStateChange() == ItemEvent.SELECTED){
            String tenNXB = cmbTenNXB.getSelectedItem().toString();
            if(cmbTenNXB.getSelectedIndex()== 0)
                txtMaNXB.setText("Chưa chọn nhà xuất bản");
            //txtMaTacGia.setText(tgDAO.timKiem1TGTheoTen(tenTG).getMaTacGia());
            else
                txtMaNXB.setText(nxbDAO.timNXBTheoTen(tenNXB).getMaNhaXuatBan());
        }
    }//GEN-LAST:event_cmbTenNXBItemStateChanged

    private void cmbLoaiSachItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbLoaiSachItemStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_cmbLoaiSachItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaTrang;
    private javax.swing.JComboBox<String> cmbLoaiSach;
    private javax.swing.JComboBox<String> cmbTenLoaiSach;
    private javax.swing.JComboBox<String> cmbTenNXB;
    private javax.swing.JComboBox<String> cmbTenTG;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDonGia;
    private javax.swing.JLabel lblDonViTinh;
    private javax.swing.JLabel lblLoaiSach;
    private javax.swing.JLabel lblMaSach;
    private javax.swing.JLabel lblMaTacGia;
    private javax.swing.JLabel lblNamXuatBan;
    private javax.swing.JLabel lblSoLuong;
    private javax.swing.JLabel lblSoTrang;
    private javax.swing.JLabel lblTenLoaiSach;
    private javax.swing.JLabel lblTenNXB;
    private javax.swing.JLabel lblTenSach;
    private javax.swing.JLabel lblTenTacGia;
    private javax.swing.JLabel lblTenTacGia1;
    private javax.swing.JLabel lblTieuDe;
    private javax.swing.JLabel lblmaLoaiSach;
    private javax.swing.JPanel pnlThongTin;
    private javax.swing.JPanel pnlTieuDe;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtDonViTinh;
    private javax.swing.JTextField txtMaLoaiSach;
    private javax.swing.JTextField txtMaNXB;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JTextField txtMaTacGia;
    private javax.swing.JTextField txtNamXuatBan;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtSoTrang;
    private javax.swing.JTextField txtTenSach;
    // End of variables declaration//GEN-END:variables
}

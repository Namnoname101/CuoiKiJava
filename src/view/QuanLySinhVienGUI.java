package view;

import dao.SinhVienDAO;
import model.SinhVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class QuanLySinhVienGUI extends JFrame {

    private SinhVienDAO sinhVienDAO;
    private JTable studentTable;
    private DefaultTableModel tableModel;

    // Các thành phần form
    private JTextField txtMaSV, txtTenSV, txtTuoi, txtEmail;
    private JButton btnThem, btnSua, btnXoa, btnMoi;

    public QuanLySinhVienGUI() {
        // 1. Khởi tạo
        sinhVienDAO = new SinhVienDAO();
        setTitle("Chương Trình Quản Lý Sinh Viên - Java Swing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        
        // 2. Tạo Table Model và JTable
        String[] columnNames = {"Mã SV", "Tên Sinh Viên", "Tuổi", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        
        // 3. Tạo Form Nhập liệu (Panel trên)
        JPanel formPanel = createFormPanel();
        
        // 4. Bố trí các Panel vào JFrame
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // 5. Load dữ liệu ban đầu
        loadDataToTable();

        // 6. Thêm sự kiện
        addEventListeners();
        
        // Hiển thị ở giữa màn hình
        setLocationRelativeTo(null);
    }
    
    // --- Phương thức tạo Panel Nhập liệu ---
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10)); // 5 hàng, 2 cột
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin Sinh viên"));

        // Hàng 1
        panel.add(new JLabel("Mã SV:"));
        txtMaSV = new JTextField();
        panel.add(txtMaSV);

        // Hàng 2
        panel.add(new JLabel("Tên SV:"));
        txtTenSV = new JTextField();
        panel.add(txtTenSV);

        // Hàng 3
        panel.add(new JLabel("Tuổi:"));
        txtTuoi = new JTextField();
        panel.add(txtTuoi);

        // Hàng 4
        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);
        
        // Hàng 5 (Buttons)
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnMoi = new JButton("Làm mới");
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnMoi);

        panel.add(new JLabel("")); // Khoảng trống
        panel.add(buttonPanel);
        
        return panel;
    }

    // --- Phương thức Load Dữ liệu lên Bảng ---
    private void loadDataToTable() {
        // Xóa dữ liệu cũ
        tableModel.setRowCount(0); 
        List<SinhVien> listSV = sinhVienDAO.layTatCaSinhVien();

        // Thêm dữ liệu mới
        for (SinhVien sv : listSV) {
            Object[] row = new Object[]{
                sv.getMaSV(), 
                sv.getTenSV(), 
                sv.getTuoi(), 
                sv.getEmail()
            };
            tableModel.addRow(row);
        }
    }

    // --- Phương thức Thêm Sự kiện ---
    private void addEventListeners() {
        // Bấm nút Thêm
        btnThem.addActionListener(e -> themSinhVien());

        // Bấm nút Sửa
        btnSua.addActionListener(e -> capNhatSinhVien());

        // Bấm nút Xóa
        btnXoa.addActionListener(e -> xoaSinhVien());
        
        // Bấm nút Làm mới
        btnMoi.addActionListener(e -> clearForm());

        // Click vào dòng trong bảng
        studentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hienThiChiTietSV();
            }
        });
    }
    
    // --- Các Action Handler (Xử lý sự kiện) ---
    
    private void clearForm() {
        txtMaSV.setText("");
        txtTenSV.setText("");
        txtTuoi.setText("");
        txtEmail.setText("");
        txtMaSV.setEnabled(true); // Cho phép sửa mã SV khi thêm mới		
    }

    private SinhVien getSinhVienTuForm() {
        try {
            String maSV = txtMaSV.getText().trim();
            String tenSV = txtTenSV.getText().trim();
            int tuoi = Integer.parseInt(txtTuoi.getText().trim());
            String email = txtEmail.getText().trim();

            if (maSV.isEmpty() || tenSV.isEmpty() || email.isEmpty()) {
                 JOptionPane.showMessageDialog(this, "Vui lòng điền đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                 return null;
            }
            return new SinhVien(maSV, tenSV, tuoi, email);
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Tuổi phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void themSinhVien() {
        SinhVien sv = getSinhVienTuForm();
        if (sv != null) {
            if (sinhVienDAO.themSinhVien(sv)) {
                JOptionPane.showMessageDialog(this, "Thêm sinh viên thành công!");
                loadDataToTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Mã Sinh Viên đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void capNhatSinhVien() {
        SinhVien sv = getSinhVienTuForm();
        if (sv != null) {
            if (sinhVienDAO.capNhatSinhVien(sv)) {
                JOptionPane.showMessageDialog(this, "Cập nhật sinh viên thành công!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy Mã Sinh Viên để cập nhật!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xoaSinhVien() {
        String maSV = txtMaSV.getText().trim();
        if (maSV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc muốn xóa sinh viên có Mã: " + maSV + "?", 
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (sinhVienDAO.xoaSinhVien(maSV)) {
                JOptionPane.showMessageDialog(this, "Xóa sinh viên thành công!");
                loadDataToTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy Mã Sinh Viên để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void hienThiChiTietSV() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Lấy dữ liệu từ dòng được chọn trong JTable
            String maSV = tableModel.getValueAt(selectedRow, 0).toString();
            String tenSV = tableModel.getValueAt(selectedRow, 1).toString();
            String tuoi = tableModel.getValueAt(selectedRow, 2).toString();
            String email = tableModel.getValueAt(selectedRow, 3).toString();

            // Hiển thị lên các JTextField
            txtMaSV.setText(maSV);
            txtTenSV.setText(tenSV);
            txtTuoi.setText(tuoi);
            txtEmail.setText(email);
            
            // Không cho phép sửa Mã SV khi đang ở chế độ chỉnh sửa/xóa
            txtMaSV.setEnabled(false); 
        }
    }

    // --- Phương thức main để chạy ứng dụng ---
    public static void main(String[] args) {
        // Đảm bảo giao diện chạy trên Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new QuanLySinhVienGUI().setVisible(true);
        });
    }
}	
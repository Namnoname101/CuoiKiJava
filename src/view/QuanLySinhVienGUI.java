package view;

import dao.SinhVienDAO;
import model.SinhVien;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class QuanLySinhVienGUI extends JFrame {

    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnNhapFile;
    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    // Màu sắc & Font
    private final Color COL_PRIMARY = new Color(52, 152, 219);
    private final Color COL_SUCCESS = new Color(46, 204, 113);
    private final Color COL_WARNING = new Color(241, 196, 15);
    private final Color COL_DANGER = new Color(231, 76, 60);
    private final Color COL_IMPORT = new Color(155, 89, 182);
    private final Color COL_BG_MAIN = new Color(236, 240, 241);
    
    private final Font FONT_BOLD = new Font("Arial", Font.BOLD, 14);
    private final Font FONT_PLAIN = new Font("Arial", Font.PLAIN, 14);

    public QuanLySinhVienGUI() {
        initGUI();
        addEvents();
        loadDataLenBang();
    }

    private void initGUI() {
        // --- 1. SỬA LỖI LOAD ICON (CHỐNG SẬP) ---
        try {
            // Thử tìm trong thư mục Image (viết hoa I theo ảnh của bạn)
            java.net.URL url = getClass().getResource("/Image/logo.png");
            // Nếu không thấy, thử tìm thư mục images (viết thường) đề phòng bạn đổi tên
            if (url == null) url = getClass().getResource("/images/logo.png");

            if (url != null) {
                setIconImage(Toolkit.getDefaultToolkit().getImage(url));
            } else {
                System.err.println("Cảnh báo: Không tìm thấy file icon (Chương trình vẫn chạy bình thường)");
            }
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi nhưng không làm sập app
        }
        // ----------------------------------------

        setTitle("Hệ Thống Quản Lý Sinh Viên");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COL_BG_MAIN);

        // HEADER
        JPanel panelHeader = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        panelHeader.setBackground(Color.WHITE);
        panelHeader.setBorder(new MatteBorder(0, 0, 2, 0, new Color(220,220,220)));
        
        JLabel lblTitle = new JLabel("CHƯƠNG TRÌNH QUẢN LÝ SINH VIÊN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(44, 62, 80));
        panelHeader.add(lblTitle);
        add(panelHeader, BorderLayout.NORTH);

        // CENTER PANEL
        JPanel panelCenter = new JPanel(new BorderLayout());
        panelCenter.setBackground(COL_BG_MAIN);
        panelCenter.setBorder(new EmptyBorder(20, 20, 20, 20));

        // BUTTONS
        JPanel panelButtons = new JPanel(new GridLayout(1, 5, 20, 0)); 
        panelButtons.setBackground(COL_BG_MAIN);
        panelButtons.setPreferredSize(new Dimension(getWidth(), 60)); 
        panelButtons.setBorder(new EmptyBorder(0, 0, 20, 0)); 

        btnThem = createBtn("THÊM MỚI", COL_SUCCESS);
        btnSua = createBtn("CẬP NHẬT", COL_WARNING);
        btnXoa = createBtn("XÓA BỎ", COL_DANGER);
        btnLamMoi = createBtn("LÀM MỚI", COL_PRIMARY);
        btnNhapFile = createBtn("NHẬP FILE", COL_IMPORT);

        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
        panelButtons.add(btnXoa);
        panelButtons.add(btnLamMoi);
        panelButtons.add(btnNhapFile);

        panelCenter.add(panelButtons, BorderLayout.NORTH);

        // TABLE
        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBorder(new LineBorder(new Color(200, 200, 200)));
        panelTable.setBackground(Color.WHITE);

        String[] columnNames = {"Mã SV", "Họ và Tên", "Tuổi", "Email", "Java", "HTML", "CSS", "ĐTB", "GPA", "Xếp Loại"};
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; 
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 2: return Integer.class;
                    case 4: case 5: case 6: case 7: case 8: return Double.class;
                    default: return String.class;
                }
            }
        };

        table = new JTable(tableModel);
        
        // CẤU HÌNH SẮP XẾP
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        // TÙY CHỈNH SẮP XẾP CỘT XẾP LOẠI (CỘT 9)
        sorter.setComparator(9, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return getRankScore(s1) - getRankScore(s2);
            }
            
            private int getRankScore(String rank) {
                if (rank == null) return 0;
                switch(rank) {
                    case "Xuất sắc": return 5;
                    case "Giỏi": return 4;
                    case "Khá": return 3;
                    case "Trung bình": return 2;
                    case "Yếu": return 1;
                    default: return 0;
                }
            }
        });

        table.getTableHeader().setReorderingAllowed(false); // Cố định cột

        styleTable(table);

        JScrollPane sc = new JScrollPane(table);
        sc.getViewport().setBackground(Color.WHITE);
        sc.setBorder(null);
        panelTable.add(sc);
        
        panelCenter.add(panelTable, BorderLayout.CENTER);
        add(panelCenter, BorderLayout.CENTER);
    }

    private JButton createBtn(String text, Color bg) {
        JButton b = new JButton(text);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFont(FONT_BOLD);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setHorizontalAlignment(SwingConstants.CENTER); 
        return b;
    }

    private void styleTable(JTable t) {
        t.setRowHeight(40); 
        t.setFont(FONT_PLAIN);
        t.setGridColor(new Color(230,230,230));
        t.setShowVerticalLines(false);
        t.setSelectionBackground(new Color(232, 246, 253));
        t.setSelectionForeground(Color.BLACK);
        
        JTableHeader header = t.getTableHeader();
        header.setFont(FONT_BOLD);
        header.setBackground(new Color(245,245,245));
        header.setForeground(new Color(44, 62, 80));
        header.setPreferredSize(new Dimension(header.getWidth(), 45));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        
        for(int i = 0; i < t.getColumnCount(); i++) {
            t.getColumnModel().getColumn(i).setCellRenderer(center);
        }
        
        DefaultTableCellRenderer left = new DefaultTableCellRenderer();
        left.setHorizontalAlignment(JLabel.LEFT);
        t.getColumnModel().getColumn(1).setCellRenderer(left); 
        t.getColumnModel().getColumn(3).setCellRenderer(left); 
    }

    private void loadDataLenBang() {
        tableModel.setRowCount(0);
        ArrayList<SinhVien> list = SinhVienDAO.selectAll();
        for (SinhVien sv : list) {
            tableModel.addRow(new Object[]{
                sv.getMaSV(), 
                sv.getTenSV(), 
                sv.getTuoi(),
                sv.getEmail(),
                sv.getDiemJava(), 
                sv.getDiemHTML(), 
                sv.getDiemCSS(),
                Math.round(sv.getDiemTB() * 10.0) / 10.0,
                sv.getGPA(),
                sv.getXepLoai()
            });
        }
    }

    private void addEvents() {
        btnThem.addActionListener(e -> xuLyThem());
        btnSua.addActionListener(e -> xuLySua());
        btnXoa.addActionListener(e -> xuLyXoa());
        btnLamMoi.addActionListener(e -> loadDataLenBang());
        btnNhapFile.addActionListener(e -> xuLyNhapFile());
    }

    private void xuLyThem() {
        SinhVienDialog dialog = new SinhVienDialog(this, true);
        dialog.setVisible(true);
        if (dialog.isSuccess()) loadDataLenBang();
    }

    private void xuLySua() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên cần sửa!");
            return;
        }

        String ma = table.getValueAt(row, 0).toString();
        String ten = table.getValueAt(row, 1).toString();
        int tuoi = Integer.parseInt(table.getValueAt(row, 2).toString());
        String email = table.getValueAt(row, 3).toString();
        double java = Double.parseDouble(table.getValueAt(row, 4).toString());
        double html = Double.parseDouble(table.getValueAt(row, 5).toString());
        double css = Double.parseDouble(table.getValueAt(row, 6).toString());

        SinhVien sv = new SinhVien(ma, ten, tuoi, email, java, html, css);
        SinhVienDialog dialog = new SinhVienDialog(this, true);
        dialog.setSinhVienData(sv);
        dialog.setVisible(true);
        if (dialog.isSuccess()) loadDataLenBang();
    }

    private void xuLyXoa() {
        int r = table.getSelectedRow();
        if(r == -1) { JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên cần xóa!"); return; }
        
        String ma = table.getValueAt(r, 0).toString();
        if(JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa sinh viên [" + ma + "]?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            SinhVienDAO.delete(ma);
            loadDataLenBang();
        }
    }

    private void xuLyNhapFile() {
        JFileChooser ch = new JFileChooser();
        ch.setFileFilter(new FileNameExtensionFilter("Text File", "txt"));
        if(ch.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                ArrayList<SinhVien> list = dao.FileHelper.readSinhVienFromTextFile(ch.getSelectedFile());
                int countSuccess = 0;
                int countFail = 0;
                for(SinhVien sv : list) {
                    if(SinhVienDAO.insert(sv) > 0) countSuccess++;
                    else countFail++;
                }
                
                String msg = "Kết quả nhập file:\n- Thành công: " + countSuccess + "\n- Thất bại: " + countFail;
                JOptionPane.showMessageDialog(this, msg);
                loadDataLenBang();
            } catch(Exception e) { e.printStackTrace(); }
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch(Exception e){}
        SwingUtilities.invokeLater(() -> new QuanLySinhVienGUI().setVisible(true));
    }
}
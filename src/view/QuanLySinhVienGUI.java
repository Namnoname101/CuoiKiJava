package view;

import dao.SinhVienDAO;
import model.SinhVien;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class QuanLySinhVienGUI extends JFrame {

    
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnNhapFile;
    private JTable table;
    private DefaultTableModel tableModel;

    
    private final Color COL_PRIMARY = new Color(52, 152, 219);
    private final Color COL_SUCCESS = new Color(39, 174, 96);
    private final Color COL_WARNING = new Color(243, 156, 18);
    private final Color COL_DANGER = new Color(231, 76, 60);
    private final Color COL_IMPORT = new Color(142, 68, 173);
    private final Color COL_BG_MAIN = new Color(245, 247, 250);
    
    
    private final Font FONT_BOLD = new Font("Arial", Font.BOLD, 13);
    private final Font FONT_PLAIN = new Font("Arial", Font.PLAIN, 13);

    public QuanLySinhVienGUI() {
        initGUI();
        addEvents();
        loadDataLenBang();
    }

    private void initGUI() {
        setTitle("Quản Lý Sinh Viên & Điểm Hệ Tín Chỉ");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COL_BG_MAIN);

        
        JPanel panelHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 15));
        panelHeader.setBackground(Color.WHITE);
        panelHeader.setBorder(new MatteBorder(0, 0, 2, 0, new Color(230,230,230)));
        
        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ ĐIỂM SINH VIÊN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22)); 
        lblTitle.setForeground(COL_PRIMARY);
        panelHeader.add(lblTitle);
        add(panelHeader, BorderLayout.NORTH);

        
        JPanel panelCenter = new JPanel(new BorderLayout());
        panelCenter.setBackground(COL_BG_MAIN);
        panelCenter.setBorder(new EmptyBorder(10, 20, 10, 20));

        
        JPanel panelButtons = new JPanel(new GridLayout(1, 5, 20, 0)); 
        panelButtons.setBackground(COL_BG_MAIN);
        panelButtons.setPreferredSize(new Dimension(getWidth(), 60)); 
        panelButtons.setBorder(new EmptyBorder(0, 0, 20, 0)); 

        
        btnThem = createBtn("ADD NEW", COL_SUCCESS);
        btnSua = createBtn("EDIT", COL_WARNING);
        btnXoa = createBtn("DELETE", COL_DANGER);
        btnLamMoi = createBtn("REFRESH", COL_PRIMARY);
        btnNhapFile = createBtn("IMPORT FILE", COL_IMPORT);

        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
        panelButtons.add(btnXoa);
        panelButtons.add(btnLamMoi);
        panelButtons.add(btnNhapFile);

        panelCenter.add(panelButtons, BorderLayout.NORTH);

        
        
        
        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBorder(new LineBorder(new Color(220, 220, 220)));
        panelTable.setBackground(Color.WHITE);

        String[] columnNames = {"Mã SV", "Họ Tên", "Tuổi", "Email", "Java", "HTML", "CSS", "ĐTB(10)", "GPA(4)", "Xếp Loại"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
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
        b.setFont(new Font("Arial", Font.BOLD, 14)); 
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    private void styleTable(JTable t) {
        t.setRowHeight(35);
        t.setFont(FONT_PLAIN);
        t.setGridColor(new Color(230,230,230));
        t.setShowVerticalLines(false);
        
        JTableHeader header = t.getTableHeader();
        header.setFont(FONT_BOLD);
        header.setBackground(new Color(245,245,245));
        header.setForeground(new Color(50, 50, 50));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        
        for(int i : new int[]{0, 2, 4, 5, 6, 7, 8}) t.getColumnModel().getColumn(i).setCellRenderer(center);
    }

    
    private void loadDataLenBang() {
        tableModel.setRowCount(0);
        ArrayList<SinhVien> list = SinhVienDAO.selectAll();
        for (SinhVien sv : list) {
            tableModel.addRow(new Object[]{
                sv.getMaSV(), sv.getTenSV(), sv.getTuoi(), sv.getEmail(),
                sv.getDiemJava(), sv.getDiemHTML(), sv.getDiemCSS(),
                String.format("%.1f", sv.getDiemTB()), 
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
        if(r == -1) { JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!"); return; }
        
        String ma = table.getValueAt(r, 0).toString();
        if(JOptionPane.showConfirmDialog(this, "Bạn chắc chắn xóa sinh viên [" + ma + "]?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            SinhVienDAO.delete(ma);
            loadDataLenBang();
        }
    }

   
    private void xuLyNhapFile() {
        JFileChooser ch = new JFileChooser();
        ch.setFileFilter(new FileNameExtensionFilter("Text File", "txt"));
        
        if(ch.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = ch.getSelectedFile();
                
                ArrayList<SinhVien> list = dao.FileHelper.readSinhVienFromTextFile(file);
                
                if (list.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Không đọc được sinh viên nào!\nKiểm tra lại format file (Mã,Tên,Tuổi,Email,Điểm1,Điểm2,Điểm3)", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int countSuccess = 0;
                int countFail = 0;

                for(SinhVien sv : list) {
                    
                    if(SinhVienDAO.insert(sv) > 0) {
                        countSuccess++;
                    } else {
                        countFail++; 
                    }
                }
                
                String msg = "Kết quả nhập file:\n" +
                             "- Thành công: " + countSuccess + "\n" +
                             "- Thất bại (Trùng mã): " + countFail;
                
                JOptionPane.showMessageDialog(this, msg);
                loadDataLenBang(); 
                
            } catch(Exception e) { 
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi đọc file: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch(Exception e){}
        SwingUtilities.invokeLater(() -> new QuanLySinhVienGUI().setVisible(true));
    }
}
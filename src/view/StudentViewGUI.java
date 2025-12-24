package view;

import dao.SinhVienDAO;
import model.SinhVien;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StudentViewGUI extends JFrame {

    private String currentMaSV;
    private final Font FONT_BOLD = new Font("Arial", Font.BOLD, 16);
    private final Font FONT_PLAIN = new Font("Arial", Font.PLAIN, 16);

    public StudentViewGUI(String maSV) {
        this.currentMaSV = maSV;
        initGUI();
    }

    private void initGUI() {
        setTitle("Cổng Thông Tin Sinh Viên");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Header
        JPanel pHeader = new JPanel();
        pHeader.setBackground(new Color(41, 128, 185));
        JLabel lbl = new JLabel("BẢNG ĐIỂM CÁ NHÂN");
        lbl.setFont(new Font("Arial", Font.BOLD, 20));
        lbl.setForeground(Color.WHITE);
        lbl.setBorder(new EmptyBorder(15, 0, 15, 0));
        pHeader.add(lbl);
        add(pHeader, BorderLayout.NORTH);

        // Content
        SinhVien sv = SinhVienDAO.getSinhVienById(currentMaSV);
        
        JPanel pContent = new JPanel(new GridBagLayout());
        pContent.setBackground(Color.WHITE);
        
        if (sv != null) {
            GridBagConstraints g = new GridBagConstraints();
            g.insets = new Insets(10, 20, 10, 20);
            g.fill = GridBagConstraints.HORIZONTAL;
            g.weightx = 1.0;

            addInfoRow(pContent, g, 0, "Mã Sinh Viên:", sv.getMaSV());
            addInfoRow(pContent, g, 1, "Họ và Tên:", sv.getTenSV());
            addInfoRow(pContent, g, 2, "Tuổi:", sv.getTuoi() + "");
            addInfoRow(pContent, g, 3, "Email:", sv.getEmail());

            JSeparator sep = new JSeparator();
            g.gridx = 0; g.gridy = 4; g.gridwidth = 2;
            pContent.add(sep, g);
            g.gridwidth = 1;

            addInfoRow(pContent, g, 5, "Lập trình Java:", sv.getDiemJava() + "");
            addInfoRow(pContent, g, 6, "Thiết kế Web (HTML):", sv.getDiemHTML() + "");
            addInfoRow(pContent, g, 7, "Trang trí Web (CSS):", sv.getDiemCSS() + "");

            JLabel lblGPA = new JLabel("Điểm GPA (Hệ 4): " + sv.getGPA());
            lblGPA.setFont(new Font("Arial", Font.BOLD, 22));
            lblGPA.setForeground(new Color(192, 57, 43));
            g.gridx = 0; g.gridy = 8; g.gridwidth = 2;
            g.insets = new Insets(30, 20, 5, 20);
            lblGPA.setHorizontalAlignment(SwingConstants.CENTER);
            pContent.add(lblGPA, g);

            JLabel lblRank = new JLabel("Xếp Loại: " + sv.getXepLoai());
            lblRank.setFont(new Font("Arial", Font.BOLD, 18));
            lblRank.setForeground(new Color(39, 174, 96));
            lblRank.setHorizontalAlignment(SwingConstants.CENTER);
            g.gridy = 9; g.insets = new Insets(5, 20, 20, 20);
            pContent.add(lblRank, g);

        } else {
            pContent.add(new JLabel("Không tìm thấy dữ liệu sinh viên!"));
        }

        add(pContent, BorderLayout.CENTER);

        // Logout
        JButton btnLogout = new JButton("ĐĂNG XUẤT");
        btnLogout.setBackground(new Color(149, 165, 166));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(FONT_BOLD);
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(e -> {
            new LoginGUI().setVisible(true);
            dispose();
        });
        
        JPanel pBottom = new JPanel();
        pBottom.setBackground(Color.WHITE);
        pBottom.setBorder(new EmptyBorder(10,0,20,0));
        pBottom.add(btnLogout);
        add(pBottom, BorderLayout.SOUTH);
    }

    private void addInfoRow(JPanel p, GridBagConstraints g, int row, String label, String value) {
        g.gridy = row;
        
        g.gridx = 0; 
        JLabel l = new JLabel(label);
        l.setFont(FONT_BOLD);
        l.setForeground(Color.GRAY);
        p.add(l, g);

        g.gridx = 1; 
        JLabel v = new JLabel(value);
        v.setFont(FONT_PLAIN);
        v.setForeground(Color.BLACK);
        v.setHorizontalAlignment(SwingConstants.RIGHT);
        p.add(v, g);
    }
}
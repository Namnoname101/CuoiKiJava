package view;

import dao.SinhVienDAO;
import model.SinhVien;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SinhVienDialog extends JDialog {

    private JTextField txtMa, txtTen, txtTuoi, txtEmail;
    private JTextField txtJava, txtHTML, txtCSS;
    private JButton btnLuu, btnHuy;
    private boolean isSuccess = false; 
    private boolean isEditMode = false;

    // Font chữ
    private final Font FONT_BOLD = new Font("Arial", Font.BOLD, 13);
    private final Font FONT_PLAIN = new Font("Arial", Font.PLAIN, 13);

    public SinhVienDialog(Frame parent, boolean modal) {
        super(parent, modal);
        initGUI();
        addEvents();
    }

    private void initGUI() {
        setTitle("Thông Tin Sinh Viên");
        setSize(450, 600);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Header
        JPanel pHeader = new JPanel();
        pHeader.setBackground(new Color(52, 152, 219));
        JLabel lblTitle = new JLabel("NHẬP THÔNG TIN SINH VIÊN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBorder(new EmptyBorder(15, 0, 15, 0));
        pHeader.add(lblTitle);
        add(pHeader, BorderLayout.NORTH);

        // Form
        JPanel pForm = new JPanel(new GridBagLayout());
        pForm.setBackground(Color.WHITE);
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 10, 8, 10);
        g.fill = GridBagConstraints.HORIZONTAL;

        // Input Fields (Tiếng Việt)
        g.gridx = 0; g.gridy = 0; pForm.add(createLabel("Mã SV:"), g);
        g.gridx = 1; txtMa = createTextField(); pForm.add(txtMa, g);

        g.gridx = 0; g.gridy = 1; pForm.add(createLabel("Họ Tên:"), g);
        g.gridx = 1; txtTen = createTextField(); pForm.add(txtTen, g);

        g.gridx = 0; g.gridy = 2; pForm.add(createLabel("Tuổi:"), g);
        g.gridx = 1; txtTuoi = createTextField(); pForm.add(txtTuoi, g);

        g.gridx = 0; g.gridy = 3; pForm.add(createLabel("Email:"), g);
        g.gridx = 1; txtEmail = createTextField(); pForm.add(txtEmail, g);

        // Điểm số
        g.gridx = 0; g.gridy = 4; g.gridwidth = 2;
        JLabel lblDiem = new JLabel("--- Nhập Điểm (Hệ 10) ---", JLabel.CENTER);
        lblDiem.setFont(FONT_PLAIN);
        lblDiem.setForeground(Color.GRAY);
        pForm.add(lblDiem, g);
        g.gridwidth = 1; 

        g.gridx = 0; g.gridy = 5; pForm.add(createLabel("Điểm Java:"), g);
        g.gridx = 1; txtJava = createTextField(); pForm.add(txtJava, g);

        g.gridx = 0; g.gridy = 6; pForm.add(createLabel("Điểm HTML:"), g);
        g.gridx = 1; txtHTML = createTextField(); pForm.add(txtHTML, g);

        g.gridx = 0; g.gridy = 7; pForm.add(createLabel("Điểm CSS:"), g);
        g.gridx = 1; txtCSS = createTextField(); pForm.add(txtCSS, g);

        add(pForm, BorderLayout.CENTER);

        // Buttons
        JPanel pBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        pBtn.setBackground(new Color(245, 245, 245));
        
        btnLuu = new JButton("LƯU");
        styleButton(btnLuu, new Color(39, 174, 96));
        
        btnHuy = new JButton("HỦY");
        styleButton(btnHuy, new Color(231, 76, 60));

        pBtn.add(btnLuu);
        pBtn.add(btnHuy);
        add(pBtn, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_BOLD);
        return lbl;
    }

    private JTextField createTextField() {
        JTextField t = new JTextField(20);
        t.setFont(FONT_PLAIN);
        t.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200)), new EmptyBorder(5, 5, 5, 5)));
        return t;
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setPreferredSize(new Dimension(120, 40));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void setSinhVienData(SinhVien sv) {
        this.isEditMode = true;
        setTitle("Cập Nhật Sinh Viên");
        txtMa.setText(sv.getMaSV());
        txtMa.setEditable(false);
        txtMa.setBackground(new Color(240, 240, 240));
        txtTen.setText(sv.getTenSV());
        txtTuoi.setText(String.valueOf(sv.getTuoi()));
        txtEmail.setText(sv.getEmail());
        txtJava.setText(String.valueOf(sv.getDiemJava()));
        txtHTML.setText(String.valueOf(sv.getDiemHTML()));
        txtCSS.setText(String.valueOf(sv.getDiemCSS()));
    }

    private void addEvents() {
        btnHuy.addActionListener(e -> dispose());
        btnLuu.addActionListener(e -> luuSinhVien());
    }

    private void luuSinhVien() {
        try {
            String ma = txtMa.getText().trim();
            String ten = txtTen.getText().trim();
            if (ma.isEmpty() || ten.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã và Tên không được để trống!"); return;
            }

            int tuoi = Integer.parseInt(txtTuoi.getText().trim());
            String email = txtEmail.getText().trim();
            double dJava = txtJava.getText().isEmpty() ? 0 : Double.parseDouble(txtJava.getText().trim());
            double dHTML = txtHTML.getText().isEmpty() ? 0 : Double.parseDouble(txtHTML.getText().trim());
            double dCSS = txtCSS.getText().isEmpty() ? 0 : Double.parseDouble(txtCSS.getText().trim());

            if(dJava < 0 || dJava > 10 || dHTML < 0 || dHTML > 10 || dCSS < 0 || dCSS > 10) {
                 JOptionPane.showMessageDialog(this, "Điểm phải từ 0 đến 10"); return;
            }

            SinhVien sv = new SinhVien(ma, ten, tuoi, email, dJava, dHTML, dCSS);
            
            if (isEditMode) {
                if (SinhVienDAO.update(sv) > 0) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                    isSuccess = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
                }
            } else {
                if (SinhVienDAO.insert(sv) > 0) {
                    JOptionPane.showMessageDialog(this, "Thêm mới thành công!");
                    isSuccess = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại (Trùng mã SV?)");
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Tuổi và Điểm phải là số!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
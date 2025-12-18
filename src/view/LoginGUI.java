package view;

import dao.TaiKhoanDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginGUI extends JFrame {

    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin;

    public LoginGUI() {
        initGUI();
    }

    private void initGUI() {
        setTitle("System Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        
        JPanel pHeader = new JPanel();
        pHeader.setBackground(new Color(52, 152, 219));
        JLabel lbl = new JLabel("Đăng Nhập Hệ Thống");
        lbl.setFont(new Font("Arial", Font.BOLD, 24));
        lbl.setForeground(Color.WHITE);
        lbl.setBorder(new EmptyBorder(20, 0, 20, 0));
        pHeader.add(lbl);
        add(pHeader, BorderLayout.NORTH);

        
        JPanel pForm = new JPanel(new GridLayout(2, 1, 10, 10));
        pForm.setBorder(new EmptyBorder(20, 40, 20, 40));
        pForm.setBackground(Color.WHITE);

        txtUser = new JTextField();
        txtUser.setBorder(BorderFactory.createTitledBorder("Tài khoản"));
        txtUser.setFont(new Font("Arial", Font.PLAIN, 14));
        
        txtPass = new JPasswordField();
        txtPass.setBorder(BorderFactory.createTitledBorder("Mật khẩu"));
        txtPass.setFont(new Font("Arial", Font.PLAIN, 14));

        pForm.add(txtUser);
        pForm.add(txtPass);
        add(pForm, BorderLayout.CENTER);

        
        btnLogin = new JButton("Đăng nhập");
        btnLogin.setBackground(new Color(46, 204, 113));
        btnLogin.setForeground(Color.BLACK);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 16));
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        
        btnLogin.addActionListener(e -> processLogin());
        
        
        getRootPane().setDefaultButton(btnLogin);

        JPanel pBtn = new JPanel();
        pBtn.setBackground(Color.WHITE);
        pBtn.setBorder(new EmptyBorder(0, 0, 20, 0));
        pBtn.add(btnLogin);
        add(pBtn, BorderLayout.SOUTH);
    }

    private void processLogin() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword()); 

        if(user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Tài khoản và mật khẩu");
            return;
        }

        
        String result = TaiKhoanDAO.checkLogin(user, pass);

        if (result == null) {
            JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!", "Đăng nhập thất bại", JOptionPane.ERROR_MESSAGE);
        } else {
            
            this.dispose(); 
            
            if (result.equals("TEACHER")) {
                
                new QuanLySinhVienGUI().setVisible(true);
            } else if (result.startsWith("STUDENT:")) {
                
                String maSV = result.split(":")[1];
                
                new StudentViewGUI(maSV).setVisible(true);
            }
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch(Exception e){}
        SwingUtilities.invokeLater(() -> new LoginGUI().setVisible(true));
    }
}
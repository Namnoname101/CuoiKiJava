package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TaiKhoanDAO {
    
    
    public static String checkLogin(String user, String pass) {
        try (Connection c = ConnectDB.getConnection()) {
            
            
            
            String sqlGV = "SELECT role FROM TaiKhoan WHERE username = ? AND password = ?";
            PreparedStatement pstGV = c.prepareStatement(sqlGV);
            pstGV.setString(1, user);
            pstGV.setString(2, pass);
            ResultSet rsGV = pstGV.executeQuery();
            
            if (rsGV.next()) {
                String role = rsGV.getString("role");
                
                if ("TEACHER".equalsIgnoreCase(role)) {
                    return "TEACHER";
                }
            }

            
            
            String sqlSV = "SELECT maSV FROM SinhVien WHERE maSV = ?";
            PreparedStatement pstSV = c.prepareStatement(sqlSV);
            pstSV.setString(1, user); 
            ResultSet rsSV = pstSV.executeQuery();
            
            if (rsSV.next()) {
                
                String dbMaSV = rsSV.getString("maSV");
                
                
                
                String correctPass = dbMaSV + "1";
                
                
                if (pass.equals(correctPass)) {
                    return "STUDENT:" + dbMaSV;
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        return null; 
    }
}
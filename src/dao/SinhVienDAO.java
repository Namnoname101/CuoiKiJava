package dao;

import model.SinhVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SinhVienDAO {

    public static ArrayList<SinhVien> selectAll() {
        ArrayList<SinhVien> ketQua = new ArrayList<>();
        try (Connection c = ConnectDB.getConnection()) {
            String sql = "SELECT * FROM SinhVien";
            PreparedStatement pst = c.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String ma = rs.getString("maSV");
                String ten = rs.getString("tenSV");
                int tuoi = rs.getInt("tuoi");
                String email = rs.getString("email");
                
                double dJava = rs.getDouble("diemJava");
                double dHTML = rs.getDouble("diemHTML");
                double dCSS = rs.getDouble("diemCSS");

                SinhVien sv = new SinhVien(ma, ten, tuoi, email, dJava, dHTML, dCSS);
                ketQua.add(sv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public static int insert(SinhVien sv) {
        int ketQua = 0;
        try (Connection c = ConnectDB.getConnection()) {
            
            String sql = "INSERT INTO SinhVien (maSV, tenSV, tuoi, email, diemJava, diemHTML, diemCSS) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = c.prepareStatement(sql);
            
            pst.setString(1, sv.getMaSV());
            pst.setString(2, sv.getTenSV());
            pst.setInt(3, sv.getTuoi());
            pst.setString(4, sv.getEmail());
            
            pst.setDouble(5, sv.getDiemJava());
            pst.setDouble(6, sv.getDiemHTML());
            pst.setDouble(7, sv.getDiemCSS());
            
            ketQua = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public static int update(SinhVien sv) {
        int ketQua = 0;
        try (Connection c = ConnectDB.getConnection()) {
            
            String sql = "UPDATE SinhVien SET tenSV=?, tuoi=?, email=?, diemJava=?, diemHTML=?, diemCSS=? WHERE maSV=?";
            PreparedStatement pst = c.prepareStatement(sql);
            
            pst.setString(1, sv.getTenSV());
            pst.setInt(2, sv.getTuoi());
            pst.setString(3, sv.getEmail());
            pst.setDouble(4, sv.getDiemJava());
            pst.setDouble(5, sv.getDiemHTML());
            pst.setDouble(6, sv.getDiemCSS());
            pst.setString(7, sv.getMaSV()); 
            
            ketQua = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public static int delete(String maSV) {
        int ketQua = 0;
        try (Connection c = ConnectDB.getConnection()) {
            String sql = "DELETE FROM SinhVien WHERE maSV = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, maSV);
            ketQua = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }
    public static SinhVien getSinhVienById(String maSV) {
        SinhVien sv = null;
        try (Connection c = ConnectDB.getConnection()) {
            String sql = "SELECT * FROM SinhVien WHERE maSV = ?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, maSV);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String ten = rs.getString("tenSV");
                int tuoi = rs.getInt("tuoi");
                String email = rs.getString("email");
                double dJava = rs.getDouble("diemJava");
                double dHTML = rs.getDouble("diemHTML");
                double dCSS = rs.getDouble("diemCSS");
                sv = new SinhVien(maSV, ten, tuoi, email, dJava, dHTML, dCSS);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sv;
    }
}
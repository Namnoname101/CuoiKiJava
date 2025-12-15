package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    
    // ⚠️ CHỈNH SỬA CÁC THÔNG SỐ NÀY
    private static  String DB_URL = "jdbc:mysql://localhost:3306/quanlisinhvien"; 
    private static  String USER = "root";              
    private static String PASS = "0120465126Az";     
    
 
    static {
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver"); 
           
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy MySQL JDBC Driver. Vui lòng kiểm tra file JAR.");
            e.printStackTrace();
        }
    }
    // END: THÊM KHỐI CODE NÀY

    public static Connection getConnection() {
        Connection connection = null;
        try {
           
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối CSDL. Vui lòng kiểm tra mật khẩu, user, và server.");
        
            e.printStackTrace(); 
        }
        return connection;
    }
    
    // ... (Thêm hàm closeConnection nếu chưa có) ...
}
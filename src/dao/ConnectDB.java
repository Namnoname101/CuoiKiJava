package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    
    
    private static  String DB_URL = "jdbc:mysql://localhost:3306/quanlisinhvien"; 
    private static  String USER = "root";              
    private static String PASS = "";     
    
 
    static {
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver"); 
           
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy MySQL JDBC Driver. Vui lòng kiểm tra file JAR.");
            e.printStackTrace();
        }
    }
    

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
    
    public static void main(String[] args) {
        System.out.println("Đang kiểm tra kết nối...");
        
        Connection c = getConnection();
        
        if (c != null) {
            System.out.println("✅ KẾT NỐI THÀNH CÔNG! (Database đã hoạt động)");
            try {
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("❌ KẾT NỐI THẤT BẠI! (Kiểm tra lại XAMPP hoặc Password)");
        }
    }
    
    
    
}
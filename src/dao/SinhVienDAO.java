package dao;

import model.SinhVien;
import java.util.ArrayList;
import java.util.List;

public class SinhVienDAO {
    // Danh sách lưu trữ sinh viên tạm thời (thay thế cho Database)
    private List<SinhVien> danhSachSV;

    public SinhVienDAO() {
        danhSachSV = new ArrayList<>();
        // Thêm vài dữ liệu mẫu
        danhSachSV.add(new SinhVien("SV001", "Nguyễn Văn A", 20, "a@example.com"));
        danhSachSV.add(new SinhVien("SV002", "Trần Thị B", 21, "b@example.com"));
    }

    // Phương thức Lấy tất cả
    public List<SinhVien> layTatCaSinhVien() {
        return danhSachSV;
    }

    // Phương thức Thêm
    public boolean themSinhVien(SinhVien sv) {
        // Kiểm tra trùng mã SV (ví dụ)
        for (SinhVien s : danhSachSV) {
            if (s.getMaSV().equals(sv.getMaSV())) {
                return false; // Mã SV đã tồn tại
            }
        }
        danhSachSV.add(sv);
        return true;
    }

    // Phương thức Xóa theo mã SV
    public boolean xoaSinhVien(String maSV) {
        return danhSachSV.removeIf(sv -> sv.getMaSV().equals(maSV));
    }

    // Phương thức Cập nhật
    public boolean capNhatSinhVien(SinhVien svMoi) {
        for (int i = 0; i < danhSachSV.size(); i++) {
            if (danhSachSV.get(i).getMaSV().equals(svMoi.getMaSV())) {
                danhSachSV.set(i, svMoi);
                return true;
            }
        }
        return false;
    }
    
    // Phương thức Tìm kiếm theo Mã SV
    public SinhVien timKiemTheoMa(String maSV) {
        for (SinhVien sv : danhSachSV) {
            if (sv.getMaSV().equals(maSV)) {
                return sv;
            }
        }
        return null;
    }
}
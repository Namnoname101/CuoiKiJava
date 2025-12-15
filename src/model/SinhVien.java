package model;

public class SinhVien {
    private String maSV;
    private String tenSV;
    private int tuoi;
    private String email;

    // Constructor
    public SinhVien(String maSV, String tenSV, int tuoi, String email) {
        this.maSV = maSV;
        this.tenSV = tenSV;
        this.tuoi = tuoi;
        this.email = email;
    }

    // Constructor mặc định (cần thiết cho một số thao tác)
    public SinhVien() {
    }

    // Getters and Setters
    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getTenSV() {
        return tenSV;
    }

    public void setTenSV(String tenSV) {
        this.tenSV = tenSV;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Ghi đè phương thức toString (hữu ích cho việc debug)
    @Override
    public String toString() {
        return "Mã SV: " + maSV + ", Tên: " + tenSV;
    }
}
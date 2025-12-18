package model;

public class SinhVien {
    private String maSV;
    private String tenSV;
    private int tuoi;
    private String email;
    
    private double diemJava;
    private double diemHTML;
    private double diemCSS;

    public SinhVien() {
    }

    
    public SinhVien(String maSV, String tenSV, int tuoi, String email, double diemJava, double diemHTML, double diemCSS) {
        this.maSV = maSV;
        this.tenSV = tenSV;
        this.tuoi = tuoi;
        this.email = email;
        this.diemJava = diemJava;
        this.diemHTML = diemHTML;
        this.diemCSS = diemCSS;
    }
    
    public SinhVien(String maSV, String tenSV, int tuoi, String email) {
        this.maSV = maSV;
        this.tenSV = tenSV;
        this.tuoi = tuoi;
        this.email = email;
        
        this.diemJava = 0.0;
        this.diemHTML = 0.0;
        this.diemCSS = 0.0;
    }

    
    public String getMaSV() { return maSV; }
    public void setMaSV(String maSV) { this.maSV = maSV; }

    public String getTenSV() { return tenSV; }
    public void setTenSV(String tenSV) { this.tenSV = tenSV; }

    public int getTuoi() { return tuoi; }
    public void setTuoi(int tuoi) { this.tuoi = tuoi; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public double getDiemJava() { return diemJava; }
    public void setDiemJava(double diemJava) { this.diemJava = diemJava; }

    public double getDiemHTML() { return diemHTML; }
    public void setDiemHTML(double diemHTML) { this.diemHTML = diemHTML; }

    public double getDiemCSS() { return diemCSS; }
    public void setDiemCSS(double diemCSS) { this.diemCSS = diemCSS; }

    
    
    
    public double getDiemTB() {
        return (diemJava + diemHTML + diemCSS) / 3;
    }

    
    public double getGPA() {
        double tb = getDiemTB();
        if (tb >= 8.5) return 4.0;      
        else if (tb >= 8.0) return 3.5; 
        else if (tb >= 7.0) return 3.0; 
        else if (tb >= 6.5) return 2.5; 
        else if (tb >= 5.5) return 2.0; 
        else if (tb >= 5.0) return 1.5; 
        else if (tb >= 4.0) return 1.0; 
        else return 0.0;                
    }
    
    
    public String getXepLoai() {
        double gpa = getGPA();
        if (gpa >= 3.6) return "Xuất sắc";
        else if (gpa >= 3.2) return "Giỏi";
        else if (gpa >= 2.5) return "Khá";
        else if (gpa >= 2.0) return "Trung bình";
        else return "Yếu";
    }

    @Override
    public String toString() {
        return maSV + " - " + tenSV;
    }
}
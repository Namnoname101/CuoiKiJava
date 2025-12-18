package dao;

import model.SinhVien;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FileHelper {

    public static ArrayList<SinhVien> readSinhVienFromTextFile(File file) {
        ArrayList<SinhVien> listSV = new ArrayList<>();
        
        
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                
                if (isFirstLine) {
                    isFirstLine = false;
                    if (line.startsWith("\uFEFF")) {
                        line = line.substring(1);
                    }
                }

                line = line.trim(); 
                if (line.isEmpty()) continue; 

                
                String[] parts = line.split(",");
                
                
                if (parts.length >= 7) {
                    try {
                        String ma = parts[0].trim();
                        String ten = parts[1].trim();
                        int tuoi = Integer.parseInt(parts[2].trim());
                        String email = parts[3].trim();
                        double dJava = Double.parseDouble(parts[4].trim());
                        double dHTML = Double.parseDouble(parts[5].trim());
                        double dCSS = Double.parseDouble(parts[6].trim());

                        SinhVien sv = new SinhVien(ma, ten, tuoi, email, dJava, dHTML, dCSS);
                        listSV.add(sv);
                    } catch (NumberFormatException e) {
                        System.err.println("Lỗi định dạng số ở dòng: " + line);
                    }
                } else {
                    System.err.println("Dòng thiếu dữ liệu: " + line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSV;
    }
}
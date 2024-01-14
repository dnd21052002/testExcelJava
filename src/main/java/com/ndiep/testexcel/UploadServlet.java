/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndiep.testexcel;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author ndiep
 */
public class UploadServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    
    private List<ExcelRecord> readData(InputStream fileContent, String contentType) {
        List<ExcelRecord> records = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try {
            HSSFWorkbook workbook = new HSSFWorkbook(fileContent);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                // Bỏ qua hàng tiêu đề
                if (row.getRowNum() == 0) continue;

                ExcelRecord record = new ExcelRecord();
                record.setCode(row.getCell(3).getStringCellValue());
                record.setName(row.getCell(4).getStringCellValue());
                record.setContent((int) row.getCell(5).getNumericCellValue());
                record.setPendingReview((int) row.getCell(6).getNumericCellValue());

                // Định dạng lại ngày tạo
                Cell dateCell = row.getCell(7);
                if (dateCell != null && dateCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(dateCell)) {
                    Date date = dateCell.getDateCellValue();
                    record.setCreationDate(dateFormat.format(date));
                }

                record.setStatus(row.getCell(8).getStringCellValue());

                records.add(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý ngoại lệ
        }

        return records;
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF8");
        PrintWriter out = response.getWriter();
        
        ServletFileUpload sf = new ServletFileUpload(new DiskFileItemFactory());
        
        try {
            List<FileItem> multifiles = sf.parseRequest(request);

            for(FileItem item : multifiles){
                String contentType = item.getContentType();
                InputStream fileContent = item.getInputStream();
                
                // Kiểm tra kiểu file và xử lý tương ứng
                if(contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
                    // Xử lý file .xlsx
                } else if(contentType.equals("application/vnd.ms-excel")) {
                    // Xử lý file .xls
                } else if(contentType.equals("text/csv")) {
                    // Xử lý file .csv
                }

                // Giả sử dữ liệu đã được đọc vào một đối tượng List<YourDataObject>
                List<ExcelRecord> data = readData(fileContent, contentType);

                // Chuyển đổi dữ liệu thành JSON
                Gson gson = new Gson();
                String jsonData = gson.toJson(data);

                // Trả về dữ liệu JSON
                out.print(jsonData);
                out.flush();
            }
        } catch(Exception e) {
            e.printStackTrace();
            // Xử lý ngoại lệ
        }
    }

}

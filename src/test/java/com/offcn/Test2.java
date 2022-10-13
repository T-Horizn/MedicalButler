package com.offcn;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootTest
public class Test2 {

    @Test
    void testMethod() throws IOException {
        //获取工作表
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("C:\\Users\\di999999guishen\\Desktop\\aaa.xlsx"));
        //通过索引获取工作表中的第一个sheet页  索引是0
        XSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);
        //通过sheet页获取页中的行
        int lastRowNum = sheetAt.getLastRowNum();
        for(int i=0;i<lastRowNum;i++){
            //获取sheet页上的制定索引的行
            XSSFRow row = sheetAt.getRow(i + 1);
            //获取行中指定下标的单元格，然后获取单元格中的数据
            System.out.println(row.getCell(0).toString());
            System.out.println(row.getCell(1).toString());
            System.out.println(row.getCell(2).toString());
        }
    }
    @Test
    public void writeInfo() throws Exception {
        //构建一个空的工作簿
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        //创建工作簿的一个sheet页
        XSSFSheet sheet = xssfWorkbook.createSheet("offcn");
        //通过工作簿创建行
        XSSFRow row = sheet.createRow(0);
        //通过行对象创建单元格对象
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("名称");
        XSSFCell cell1 = row.createCell(1);
        cell1.setCellValue("编号");

        //通过工作簿创建行
        XSSFRow row1 = sheet.createRow(1);
        //通过行对象创建单元格对象
        XSSFCell cell2 = row1.createCell(0);
        cell2.setCellValue("赵云");
        XSSFCell cell13 = row1.createCell(1);
        cell13.setCellValue("110");

        FileOutputStream fileOutputStream = new FileOutputStream("D:\\upload\\offcn.xlsx");

        xssfWorkbook.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        xssfWorkbook.close();
    }
}

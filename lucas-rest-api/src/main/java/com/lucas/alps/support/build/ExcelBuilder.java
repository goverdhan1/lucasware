package com.lucas.alps.support.build;

import com.lucas.entity.reporting.Printable;
import com.lucas.support.DefaultGridable;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 9/9/14  Time: 11:46 AM
 * This Class provide the implementation for the functionality of..
 */

public class ExcelBuilder extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook
            , HttpServletRequest request, HttpServletResponse response) throws Exception {
        final DefaultGridable defaultGridable = (DefaultGridable) model.get("defaultGridable");
        final String sheetName = defaultGridable.getTitle();

        // create a new Excel sheet
        final HSSFSheet hssfSheet = workbook.createSheet(sheetName);
        hssfSheet.setDefaultColumnWidth(30);

        // create style for header cells
        final CellStyle style = workbook.createCellStyle();
        final Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);


        // create header row
        final List<String> headerList = defaultGridable.getHeaderRow();
        final HSSFRow header = hssfSheet.createRow(0);
        for (int index = 0; index < headerList.size(); index++) {
            header.createCell(index).setCellValue(this.getHeaderName(headerList.get(index).toString()));
            header.getCell(index).setCellStyle(style);
        }

        final List<Printable> dataList = defaultGridable.getDataRows();
        int rowCount = 1;
        // create data rows
        for (final Printable printable : dataList) {
            final HSSFRow hssfRow = hssfSheet.createRow(rowCount++);
            int column = 0;
            final List<String> dataColumn = printable.getDataElements();
            for (String data : dataColumn) {
                hssfRow.createCell(column).setCellValue((data != null ? data.toString() : ""));
                column++;
            }
        }
    }

    public String getHeaderName(String name) {
        final String heading[] = StringUtils.splitByCharacterTypeCamelCase(name);
        String colHeading = "";
        for (String word : heading) {
            colHeading += " " + (StringUtils.capitalize(word));
        }
        return colHeading;
    }
}

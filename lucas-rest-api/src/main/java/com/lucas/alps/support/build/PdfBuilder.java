package com.lucas.alps.support.build;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;
import com.lucas.entity.reporting.Printable;
import com.lucas.support.DefaultGridable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.List;
import java.util.Map;


/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 9/12/14  Time: 12:41 PM
 * This Class provide the implementation for the functionality of..
 */

public class PdfBuilder extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map model, Document document,
                                    PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

        final DefaultGridable defaultGridable = (DefaultGridable) model.get("defaultGridable");
        final String pdfText = defaultGridable.getTitle();
        document.add(new Paragraph(pdfText));

        final List<String> headerList = defaultGridable.getHeaderRow();
        final List<Printable> dataList = defaultGridable.getDataRows();
        final Table table = new Table(headerList.size(),dataList.size());
        table.setComplete(true);
        table.setRight(0.1F);
        table.setLeft(0.1F);
        table.setBottom(0.5F);
        table.setTop(0.5F);
        // define font for table header row
        final Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.BLUE);
        font.setSize(12f);

        for (int index = 0; index < headerList.size(); index++) {
            final Phrase phrase = new Phrase(this.getHeaderName(headerList.get(index).toString()), font);
            final Cell cell = new Cell(phrase);
            cell.getWidthAsString();
            table.addCell(cell);
        }


        for (final Printable printable : dataList) {
            final List<String> dataColumn = printable.getDataElements();
            for (String data : dataColumn) {
                table.addCell((data != null ? data.toString() : ""));
            }
        }
        document.add(table);
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

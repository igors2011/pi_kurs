package com.ip.pi_kurs.controllers;

import com.ip.pi_kurs.business_logic.CalculationLogic;
import com.ip.pi_kurs.business_logic.ProductLogic;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("main/calculation")
public class CalculationController {
    @Autowired
    private ProductLogic productLogic;
    @Autowired
    private CalculationLogic calculationLogic;

    @GetMapping("init_calculation")
    public String initCalculation(Model model) {
        try {
            var products = productLogic.getProducts();
            model.addAttribute("products", products);
            return "calculations/init_calculation";
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }
    @PostMapping("make_calculation")
    public String makeCalculation(@RequestParam("productId") int productId, @RequestParam("periodString") String periodString, Model model) {
        try {
            var stringsCalculation = calculationLogic.calculate(periodString, productId);
            model.addAttribute("stringsCalculation", stringsCalculation);
            model.addAttribute("productId", productId);
            model.addAttribute("periodString", periodString);
            return "calculations/result";
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }

    @GetMapping("download")
    public ResponseEntity<byte[]> generateReport(@RequestParam("productId") int productId, @RequestParam("periodString") String periodString) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            List<String> report = calculationLogic.calculate(periodString, productId); // Ваш метод получения данных
            PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            PdfFont font = PdfFontFactory.createFont("src/main/resources/static/font/TimesNewRomanRegular.ttf", "Identity-H");

            for (String line : report) {
                document.add(new Paragraph(line).setFont(font));
            }

            document.close();
            pdfDocument.close();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=report.pdf");
        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);
    }
}

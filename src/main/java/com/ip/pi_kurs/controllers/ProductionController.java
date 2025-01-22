package com.ip.pi_kurs.controllers;

import com.ip.pi_kurs.business_logic.ProductLogic;
import com.ip.pi_kurs.models.Production;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@Controller
@RequestMapping("main/production")
public class ProductionController {
    @Autowired
    private ProductLogic productLogic;

    @GetMapping(value = {"", "/"})
    public String production(Model model) {
        try {
            var productions = productLogic.getProduction();
            model.addAttribute("productions", productions);
            return "productions/productions";
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }
    @GetMapping("product/{productId}")
    public String productionByProduct(@PathVariable("productId") int productId, Model model) {
        try {
            var productions = productLogic.getProductionByProduct(productId);
            model.addAttribute("productions", productions);
            model.addAttribute("productId", productId);
            return "productions/production_by_product";
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }
    @GetMapping("product/{productId}/create")
    public String createProductionByProduct(@PathVariable("productId") int productId, Model model) {
        Production production = new Production();
        production.setProductId(productId);
        model.addAttribute("production", production);
        return "productions/create_production";
    }
    @PostMapping("create")
    public String createProductionByProduct(@ModelAttribute("production") Production production, Model model) {
        try {
            production.convertPeriodStringToPeriod();
            productLogic.createProduction(production);
            return productionByProduct(production.getProductId(), model);
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }
}

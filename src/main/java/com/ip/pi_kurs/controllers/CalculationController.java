package com.ip.pi_kurs.controllers;

import com.ip.pi_kurs.business_logic.CalculationLogic;
import com.ip.pi_kurs.business_logic.ProductLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

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
            return "calculations/result";
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }
}

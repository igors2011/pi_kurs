package com.ip.pi_kurs.controllers;

import com.ip.pi_kurs.business_logic.FixedCostsLogic;
import com.ip.pi_kurs.models.FixedCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Controller
@RequestMapping("/main")
public class MainController {
    @Autowired
    private FixedCostsLogic fixedCostsLogic;

    @GetMapping(value = {"", "/"})
    public String mainPage() {
        return "main/main";
    }

    @GetMapping("/fixed_costs")
    public String fixedCosts(Model model) {
        try {
            var fixedCosts = fixedCostsLogic.getFixedCosts();
            model.addAttribute("fixedCosts", fixedCosts);
            return "main/fixed_costs";
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }
    @GetMapping("/fixed_costs/create")
    public String createFixedCost(Model model) {
        FixedCost fixedCost = new FixedCost();
        model.addAttribute("fixedCost", fixedCost);
        return "main/create_fixed_cost";
    }

    @PostMapping("/fixed_costs/create")
    public String createFixedCostEx(@ModelAttribute("fixedCost") FixedCost fixedCost, Model model) {
        try {
            String periodString = fixedCost.getPeriodString();
            LocalDateTime localDateTime = LocalDateTime.parse(periodString);
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            fixedCost.setPeriod(timestamp);
            fixedCostsLogic.createFixedCost(fixedCost);
            return "redirect:/main/fixed_costs";
        } catch (SQLException | IOException | DateTimeParseException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }
}

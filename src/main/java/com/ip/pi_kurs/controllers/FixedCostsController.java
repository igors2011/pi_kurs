package com.ip.pi_kurs.controllers;

import com.ip.pi_kurs.business_logic.FixedCostsLogic;
import com.ip.pi_kurs.models.FixedCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;

@Controller
@RequestMapping("main/fixed_costs")
public class FixedCostsController {
    @Autowired
    private FixedCostsLogic fixedCostsLogic;

    @GetMapping(value = {"", "/"})
    public String fixedCosts(Model model) {
        try {
            var fixedCosts = fixedCostsLogic.getFixedCosts();
            model.addAttribute("fixedCosts", fixedCosts);
            return "fixed_costs/fixed_costs";
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }
    @GetMapping("create")
    public String createFixedCost(Model model) {
        FixedCost fixedCost = new FixedCost();
        model.addAttribute("fixedCost", fixedCost);
        return "fixed_costs/create_fixed_cost";
    }

    @PostMapping("create")
    public String createFixedCostEx(@ModelAttribute("fixedCost") FixedCost fixedCost, Model model) {
        try {
            fixedCost.convertPeriodStringToPeriod();
            fixedCostsLogic.createFixedCost(fixedCost);
            return "redirect:/main/fixed_costs";
        } catch (SQLException | IOException | DateTimeParseException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }
    @GetMapping("edit/{id}")
    public String updateFixedCost(@PathVariable("id") int id, Model model) {
        try {
            FixedCost fixedCostToUpdate = fixedCostsLogic.getFixedCostById(id);
            fixedCostToUpdate.convertPeriodToPeriodString();
            model.addAttribute("fixedCost", fixedCostToUpdate);
            return "fixed_costs/update_fixed_cost";
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }
    @PostMapping("edit")
    public String updateFixedCostEx(Model model, @ModelAttribute("fixedCost") FixedCost fixedCost) {
        try {
            fixedCost.convertPeriodStringToPeriod();
            fixedCostsLogic.updateFixedCost(fixedCost);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/main/fixed_costs";
    }
}

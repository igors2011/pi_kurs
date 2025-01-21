package com.ip.pi_kurs.controllers;

import com.ip.pi_kurs.business_logic.MaterialLogic;
import com.ip.pi_kurs.models.Material;
import com.ip.pi_kurs.models.MaterialCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;

@Controller
@RequestMapping("main/materials")
public class MaterialsController {
    @Autowired
    private MaterialLogic materialLogic;

    @GetMapping(value = {"", "/"})
    public String getMaterials(Model model) {
        try {
            var materials = materialLogic.getMaterials();
            model.addAttribute("materials", materials);
            return "materials/materials";
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }

    @GetMapping("create")
    public String createMaterial(Model model) {
        Material material = new Material();
        model.addAttribute("material", material);
        return "materials/create_material";
    }

    @PostMapping("create")
    public String createMaterialEx(@ModelAttribute("material") Material material, Model model) {
        try {
            materialLogic.createMaterial(material);
            return "redirect:/main/materials";
        } catch (SQLException | IOException | DateTimeParseException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }

    @GetMapping("edit/{id}")
    public String updateMaterial(@PathVariable("id") int id, Model model) {
        try {
            Material materialToUpdate = materialLogic.getMaterialById(id);
            model.addAttribute("material", materialToUpdate);
            return "materials/update_material";
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }
    @PostMapping("edit")
    public String updateMaterialEx(@ModelAttribute("material") Material material, Model model) {
        try {
            materialLogic.updateMaterial(material);
            return "redirect:/main/materials";
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }

    @GetMapping("cost/{materialId}")
    public String materialCosts(@PathVariable("materialId") int materialId, Model model) {
        try {
            var materialCosts = materialLogic.getMaterialCosts(materialId);
            model.addAttribute("materialCosts", materialCosts);
            model.addAttribute("materialId", materialId);
            return "materials/material_costs";
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }

    @GetMapping("cost/{materialId}/create")
    public String createMaterialCost(@PathVariable("materialId") int materialId, Model model) {
        MaterialCost materialCost = new MaterialCost();
        materialCost.setMaterialId(materialId);
        model.addAttribute("materialCost", materialCost);
        return "materials/create_material_cost";
    }
    @PostMapping("cost/create")
    public String createMaterialCostEx(@ModelAttribute("materialCost") MaterialCost materialCost, Model model) {
        try {
            materialCost.convertPeriodStringToPeriod();
            materialLogic.createMaterialCost(materialCost);
            return materialCosts(materialCost.getMaterialId(), model);
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }
}

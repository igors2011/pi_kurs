package com.ip.pi_kurs.controllers;

import com.ip.pi_kurs.business_logic.FixedCostsLogic;
import com.ip.pi_kurs.business_logic.WorkerLogic;
import com.ip.pi_kurs.models.FixedCost;
import com.ip.pi_kurs.models.Salary;
import com.ip.pi_kurs.models.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;

@Controller
@RequestMapping("/main")
public class MainController {
    @Autowired
    private FixedCostsLogic fixedCostsLogic;
    @Autowired
    private WorkerLogic workerLogic;

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
            fixedCost.convertPeriodStringToPeriod();
            fixedCostsLogic.createFixedCost(fixedCost);
            return "redirect:/main/fixed_costs";
        } catch (SQLException | IOException | DateTimeParseException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }
    @GetMapping("/fixed_costs/edit/{id}")
    public String updateFixedCost(@PathVariable("id") int id, Model model) {
        try {
            FixedCost fixedCostToUpdate = fixedCostsLogic.getFixedCostById(id);
            fixedCostToUpdate.convertPeriodToPeriodString();
            model.addAttribute("fixedCost", fixedCostToUpdate);
            return "main/update_fixed_cost";
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }
    @PostMapping("/fixed_costs/edit")
    public String updateFixedCostEx(Model model, @ModelAttribute("fixedCost") FixedCost fixedCost) {
        try {
            fixedCost.convertPeriodStringToPeriod();
            fixedCostsLogic.updateFixedCost(fixedCost);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/main/fixed_costs";
    }
    @GetMapping("/workers")
    public String workers(Model model) {
        try {
            var workers = workerLogic.getWorkers();
            model.addAttribute("workers", workers);
            return "main/workers";
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }

    @GetMapping("/workers/create")
    public String createWorker(Model model) {
        Worker worker = new Worker();
        model.addAttribute("worker", worker);
        return "main/create_worker";
    }

    @PostMapping("/workers/create")
    public String createWorkerEx(@ModelAttribute("worker") Worker worker, Model model) {
        try {
            workerLogic.createWorker(worker);
            return "redirect:/main/workers";
        } catch (SQLException | IOException | DateTimeParseException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }

    @GetMapping("/workers/salary/{workerId}")
    public String workerSalaries(@PathVariable("workerId") int workerId, Model model) {
        try {
            var workerSalaries = workerLogic.getWorkerSalaries(workerId);
            model.addAttribute("workerSalaries", workerSalaries);
            model.addAttribute("workerId", workerId);
            return "main/worker_salaries";
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }

    @GetMapping("/workers/salary/{workerId}/create")
    public String createWorkerSalary(@PathVariable("workerId") int workerId, Model model) {
        Salary salary = new Salary();
        salary.setWorkerId(workerId);
        model.addAttribute("salary", salary);
        return "main/create_worker_salary";
    }
    @PostMapping("workers/salary/create")
    public String createWorkerSalary(@ModelAttribute("salary") Salary salary, Model model) {
        try {
            salary.convertPeriodStringToPeriod();
            workerLogic.createSalary(salary);
            return workerSalaries(salary.getWorkerId(), model);
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }
}

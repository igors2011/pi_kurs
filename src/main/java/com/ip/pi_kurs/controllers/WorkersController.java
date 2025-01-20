package com.ip.pi_kurs.controllers;

import com.ip.pi_kurs.business_logic.WorkerLogic;
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
@RequestMapping("main/workers")
public class WorkersController {
    @Autowired
    private WorkerLogic workerLogic;

    @GetMapping(value = {"", "/"})
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

    @GetMapping("create")
    public String createWorker(Model model) {
        Worker worker = new Worker();
        model.addAttribute("worker", worker);
        return "main/create_worker";
    }

    @PostMapping("create")
    public String createWorkerEx(@ModelAttribute("worker") Worker worker, Model model) {
        try {
            workerLogic.createWorker(worker);
            return "redirect:/main/workers";
        } catch (SQLException | IOException | DateTimeParseException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }

    @GetMapping("edit/{id}")
    public String updateWorker(@PathVariable("id") int id, Model model) {
        try {
            Worker workerToUpdate = workerLogic.getWorkerById(id);
            model.addAttribute("worker", workerToUpdate);
            return "main/update_worker";
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }
    @PostMapping("edit")
    public String updateWorkerEx(@ModelAttribute("worker") Worker worker, Model model) {
        try {
            workerLogic.updateWorker(worker);
            return "redirect:/main/workers";
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }

    @GetMapping("salary/{workerId}")
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

    @GetMapping("salary/{workerId}/create")
    public String createWorkerSalary(@PathVariable("workerId") int workerId, Model model) {
        Salary salary = new Salary();
        salary.setWorkerId(workerId);
        model.addAttribute("salary", salary);
        return "main/create_worker_salary";
    }
    @PostMapping("salary/create")
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

package com.ip.pi_kurs.controllers;

import com.ip.pi_kurs.business_logic.ProductLogic;
import com.ip.pi_kurs.business_logic.WorkerLogic;
import com.ip.pi_kurs.models.Product;
import com.ip.pi_kurs.models.WorkerByProduct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Controller
@RequestMapping("main/products")
public class ProductsController {
    @Autowired
    private ProductLogic productLogic;
    @Autowired
    private WorkerLogic workerLogic;

    @GetMapping(value = {"", "/"})
    public String getProducts(Model model) {
        try {
            var products = productLogic.getProducts();
            model.addAttribute("products", products);
            return "main/products";
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }

    @GetMapping("create")
    public String createProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "main/create_product";
    }

    @PostMapping("create")
    public String createProductEx(@ModelAttribute("product") Product product, Model model) {
        try {
            productLogic.createProduct(product);
            return "redirect:/main/products";
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }

    @GetMapping("edit/{id}")
    public String updateProduct(@PathVariable("id") int id, Model model) {
        try {
            Product productToUpdate = productLogic.getProductById(id);
            model.addAttribute("product", productToUpdate);
            return "main/update_product";
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }

    @PostMapping("edit")
    public String updateProductEx(@ModelAttribute("product") Product product, Model model) {
        try {
            productLogic.updateProduct(product);
            return "redirect:/main/products";
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }

    @GetMapping("workers/{productId}")
    public String getWorkersByProduct(@PathVariable("productId") int productId, Model model) {
        try {
            var workersByProductId = productLogic.getWorkersByProductId(productId);
            model.addAttribute("workersByProduct", workersByProductId);
            return "main/workers_by_product";
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }
    @GetMapping("workers/create_worker_product/{productId}")
    public String addWorkerByProductId(@PathVariable("productId") int productId, Model model) {
        try {
            model.addAttribute("productId", productId);
            var workers = workerLogic.getWorkers();
            model.addAttribute("workers", workers);
            return "main/create_worker_product";
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }

    @PostMapping("workers/create_worker_product")
    public String addWorkerByProductIdEx(@RequestParam("productId") int productId, @RequestParam("workerId") int workerId, @RequestParam("periodString") String periodString, Model model) {
        try {
            WorkerByProduct workerByProduct = new WorkerByProduct();
            workerByProduct.setWorkerId(workerId);
            workerByProduct.setProductId(productId);
            workerByProduct.setPeriodString(periodString);
            workerByProduct.convertPeriodStringToPeriod();

            productLogic.createWorkerByProductId(workerByProduct);

            return getWorkersByProduct(productId, model);
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }
    @GetMapping("workers/delete_worker_product/{id}")
    public String deleteWorkerByProductById(@PathVariable("id") int id, Model model, HttpServletRequest request) {
        try {
            productLogic.deleteWorkerByProductById(id);
            String referer = request.getHeader("referer");
            return "redirect:" + referer;
        } catch (SQLException | IOException e) {
            model.addAttribute("exceptionText", e.getMessage());
            return "exception";
        }
    }
}
package com.ip.pi_kurs.controllers;

import com.ip.pi_kurs.business_logic.FixedCostsLogic;
import com.ip.pi_kurs.business_logic.MaterialLogic;
import com.ip.pi_kurs.business_logic.WorkerLogic;
import com.ip.pi_kurs.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;

@Controller
@RequestMapping("main")
public class MainController {

    @GetMapping(value = {"", "/"})
    public String mainPage() {
        return "main/main";
    }
}

package pe.edu.smartspace.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "forward:/index.html"; // fuerza a servir el index estático
    }
}

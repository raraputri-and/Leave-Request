package id.co.mii.clientapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import id.co.mii.clientapp.services.ReligionService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/religion")
public class ReligionController {
    private ReligionService religionService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("religions", religionService.getAll());
        model.addAttribute("title", "religion");
        return "Admin/religion";
    }
}

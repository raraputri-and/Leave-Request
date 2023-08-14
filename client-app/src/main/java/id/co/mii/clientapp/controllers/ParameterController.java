package id.co.mii.clientapp.controllers;

import id.co.mii.clientapp.models.Parameter;
import id.co.mii.clientapp.services.ParameterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/parameter")
public class ParameterController {
    private ParameterService parameterService;

    @GetMapping
    public String index(Model model){
        model.addAttribute("parameters", parameterService.getAll());
        model.addAttribute("title", "parameter");
        return "Admin/parameter";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable String id, Parameter parameter){
        parameterService.update(id,parameter);
        return "redirect:/parameter";
    }
}

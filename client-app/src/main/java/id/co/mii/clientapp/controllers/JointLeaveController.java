package id.co.mii.clientapp.controllers;

import id.co.mii.clientapp.models.JointLeave;
import id.co.mii.clientapp.services.JointLeaveService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@AllArgsConstructor
@RequestMapping("/joint-leave")
public class JointLeaveController {
    private JointLeaveService jointleaveService;

    @GetMapping
    public String index(Model model){
        model.addAttribute("jointleaves", jointleaveService.getAll());
        model.addAttribute("title", "jointleave");
        return "Admin/jointLeave";
    }

    @PostMapping
    public String create(JointLeave jointleave){
        jointleaveService.create(jointleave);
        return "redirect:/joint-leave";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Integer id, JointLeave jointleave){
        jointleaveService.update(id,jointleave);
        return "redirect:/joint-leave";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        jointleaveService.delete(id);
        return "redirect:/joint-leave";
    }
}

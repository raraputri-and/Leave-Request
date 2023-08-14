package id.co.mii.clientapp.controllers.restcontroller;

import id.co.mii.clientapp.models.Religion;
import id.co.mii.clientapp.services.ReligionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/religion")
@AllArgsConstructor
public class RestReligionController {
    private ReligionService religionService;

    @GetMapping
    public List<Religion> getAll(){
        return religionService.getAll();
    }

    @GetMapping("/{id}")
    public Religion getById(@PathVariable Integer id){
        return religionService.getById(id);
    }
}

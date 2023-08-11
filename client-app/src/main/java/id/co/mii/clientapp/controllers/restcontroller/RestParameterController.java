package id.co.mii.clientapp.controllers.restcontroller;

import id.co.mii.clientapp.models.Parameter;
import id.co.mii.clientapp.services.ParameterService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parameter")
@AllArgsConstructor
public class RestParameterController {
    private ParameterService parameterService;

    @GetMapping
    public List<Parameter> getAll(){
        return parameterService.getAll();
    }

    @GetMapping("/{id}")
    public Parameter getById(@PathVariable String id){
        return parameterService.getById(id);
    }


    @PutMapping("/update/{id}")
    public Parameter update(@PathVariable String id, @RequestBody Parameter parameter){
        return parameterService.update(id,parameter);
    }
}

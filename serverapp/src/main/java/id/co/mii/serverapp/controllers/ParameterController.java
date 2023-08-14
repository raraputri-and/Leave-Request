package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.Parameter;
import id.co.mii.serverapp.services.ParameterService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/parameter")
@PreAuthorize("hasRole('Admin')")
public class ParameterController {
    private ParameterService parameterService;
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping
    public List<Parameter> getAll() {
        return parameterService.getAll();
    }
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("/{id}")
    public Parameter getById(@PathVariable String id) {
        return parameterService.getById(id);
    }
    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    @PostMapping
    public Parameter create(@RequestBody Parameter parameter) {
        return parameterService.create(parameter);
    }
    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    @PutMapping("/{id}")
    public Parameter update(@PathVariable String id, @RequestBody Parameter parameter) {
        return parameterService.update(id, parameter);
    }
}

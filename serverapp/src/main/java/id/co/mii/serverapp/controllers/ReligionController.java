package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.Religion;
import id.co.mii.serverapp.services.ReligionService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/religion")
@PreAuthorize("hasRole('Admin')")
public class ReligionController {
    private ReligionService religionService;
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping
    public List<Religion> getAll() {
        return religionService.getAll();
    }
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("/{id}")
    public Religion getById(@PathVariable Integer id) {
        return religionService.getById(id);
    }
    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    @PostMapping
    public Religion create(@RequestBody Religion religion) {
        return religionService.create(religion);
    }
    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    @PutMapping("/{id}")
    public Religion update(@PathVariable Integer id, @RequestBody Religion religion) {
        return religionService.update(id, religion);
    }
    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("/{id}")
    public Religion delete(@PathVariable Integer id) {
        return religionService.delete(id);
    }
}

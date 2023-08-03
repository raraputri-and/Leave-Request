package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.StatusAction;
import id.co.mii.serverapp.services.StatusActionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/status-action")
public class StatusActionController {
    private StatusActionService statusActionService;
    //    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping
    public List<StatusAction> getAll() {
        return statusActionService.getAll();
    }
    //    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("/{id}")
    public StatusAction getById(@PathVariable Integer id) {
        return statusActionService.getById(id);
    }
}

package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.StatusAction;
import id.co.mii.serverapp.repositories.StatusActionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class StatusActionService {
    private StatusActionRepository statusActionRepository;

    public List<StatusAction> getAll() {
        return statusActionRepository.findAll();
    }

    public StatusAction getById(Integer id) {
        return statusActionRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "StatusAction not found!!!")
                );
    }
}

package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.Religion;
import id.co.mii.serverapp.repositories.ReligionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class ReligionService {
    private ReligionRepository religionRepository;

    public List<Religion> getAll() {
        return religionRepository.findAll();
    }

    public Religion getById(Integer id) {
        return religionRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Religion not found!!!")
                );
    }

    public Religion create(Religion religion) {
        if (religionRepository.findByName(religion.getName()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Name is already exists!!!"
            );
        }

        return religionRepository.save(religion);
    }

    public Religion update(Integer id, Religion religion) {
        getById(id);
        religion.setId(id);
        return religionRepository.save(religion);
    }

    public Religion delete(Integer id) {
        Religion religion = getById(id);
        religionRepository.delete(religion);
        return religion;
    }
}

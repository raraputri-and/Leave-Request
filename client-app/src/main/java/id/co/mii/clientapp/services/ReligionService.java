package id.co.mii.clientapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import id.co.mii.clientapp.models.Religion;
import id.co.mii.clientapp.models.dto.ReligionRequest;

@Service
public class ReligionService {
    private RestTemplate restTemplate;

    @Value("${dns.baseUrl}/religion")
    private String url;

    @Autowired
    public ReligionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Religion> getAll() {
        return restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Religion>>() {
                }).getBody();
    }

    public Religion getById(Integer id) {
        return restTemplate.exchange(url + "/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Religion>() {
                }).getBody();
    }

    public Religion create(ReligionRequest religionRequest) {
        return restTemplate.exchange(url,
                HttpMethod.POST,
                new HttpEntity(religionRequest),
                new ParameterizedTypeReference<Religion>() {
                }).getBody();
    }

    public Religion update(Integer id, ReligionRequest religionRequest) {
        return restTemplate.exchange(url + "/" + id,
                HttpMethod.PUT,
                new HttpEntity(religionRequest),
                new ParameterizedTypeReference<Religion>() {
                }).getBody();
    }

    public Religion delete(Integer id) {
        return restTemplate.exchange(url + "/" + id,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<Religion>() {
                }).getBody();
    }

}

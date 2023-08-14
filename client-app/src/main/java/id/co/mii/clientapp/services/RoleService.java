package id.co.mii.clientapp.services;

import id.co.mii.clientapp.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RoleService {
    private RestTemplate restTemplate;

    @Value("${dns.baseUrl}/role")
    private String url;
    @Autowired
    public RoleService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Role> getAll(){
        return restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Role>>() {
                }).getBody();
    }

    public Role getById(Integer id){
        return restTemplate.exchange(url +"/" +id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Role>() {
                }).getBody();
    }

    public Role update(Integer id, Role role){
        return restTemplate.exchange(url + "/" + id,
                HttpMethod.PUT,
                new HttpEntity(role),
                new ParameterizedTypeReference<Role>() {
                }).getBody();
    }
}

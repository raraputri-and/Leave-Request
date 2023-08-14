package id.co.mii.clientapp.services;

import id.co.mii.clientapp.models.User;
import id.co.mii.clientapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {
    private RestTemplate restTemplate;

    @Value("${dns.baseUrl}/user")
    private String url;

    @Autowired
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getAll(){
        return restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {
                }).getBody();
    }

    public User getById(Integer id){
        return restTemplate.exchange(url +"/" +id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<User>() {
                }).getBody();
    }

    public User update(Integer id, Integer roleId){
        return restTemplate.exchange(url + "/update/" + id + "?roleId=" + roleId,
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<User>() {
                }).getBody();
    }
}

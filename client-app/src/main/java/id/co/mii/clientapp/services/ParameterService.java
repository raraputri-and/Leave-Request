package id.co.mii.clientapp.services;

import id.co.mii.clientapp.models.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ParameterService {
    private RestTemplate restTemplate;

    @Value("${dns.baseUrl}/parameter")
    private String url;
    @Autowired
    public ParameterService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Parameter> getAll(){
        return restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Parameter>>() {
                }).getBody();
    }

    public Parameter getById(String id){
        return restTemplate.exchange(url +"/" +id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Parameter>() {
                }).getBody();
    }

    public Parameter update(String id, Parameter parameter){
        return restTemplate.exchange(url + "/" + id,
                HttpMethod.PUT,
                new HttpEntity(parameter),
                new ParameterizedTypeReference<Parameter>() {
                }).getBody();
    }

}

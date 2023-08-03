package id.co.mii.clientapp.services;

import id.co.mii.clientapp.models.StatusAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Service
public class StatusActionService {
    private RestTemplate restTemplate;

    @Value("${dns.baseUrl}/status-action")
    private String url;

    @Autowired
    public StatusActionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<StatusAction> getAll(){
        return restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<StatusAction>>() {
                }).getBody();
    }

    public StatusAction getById(Integer id){
        return restTemplate.exchange(url + "/" +id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<StatusAction>() {
                }).getBody();
    }
}

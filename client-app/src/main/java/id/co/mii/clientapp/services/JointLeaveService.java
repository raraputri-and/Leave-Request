package id.co.mii.clientapp.services;

import id.co.mii.clientapp.models.JointLeave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class JointLeaveService {
    private RestTemplate restTemplate;

    @Value("${dns.baseUrl}/joint-leave")
    private String url;
    @Autowired
    public JointLeaveService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<JointLeave> getAll(){
        return restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<JointLeave>>() {
                }).getBody();
    }

    public JointLeave getById(Integer id){
        return restTemplate.exchange(url +"/" +id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<JointLeave>() {
                }).getBody();
    }

    public JointLeave create(JointLeave jointLeave){
        return restTemplate.exchange(url,
                HttpMethod.POST,
                new HttpEntity(jointLeave),
                new ParameterizedTypeReference<JointLeave>() {
                }).getBody();
    }

    public JointLeave update(Integer id, JointLeave jointLeave){
        return restTemplate.exchange(url + "/" + id,
                HttpMethod.PUT,
                new HttpEntity(jointLeave),
                new ParameterizedTypeReference<JointLeave>() {
                }).getBody();
    }

    public JointLeave delete(Integer id){
        return restTemplate.exchange(url + "/" + id,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<JointLeave>() {
                }).getBody();
    }
}

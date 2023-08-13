package id.co.mii.clientapp.services;

import id.co.mii.clientapp.models.LeaveRemaining;
import id.co.mii.clientapp.models.dto.LeaveRemainingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class LeaveRemainingService {
    private RestTemplate restTemplate;

    @Value("${dns.baseUrl}/leave-remaining")
    private String url;
    @Autowired
    public LeaveRemainingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<LeaveRemaining> getAll(){
        return restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LeaveRemaining>>() {
                }).getBody();
    }

    public LeaveRemaining getById(Integer id){
        return restTemplate.exchange(url +"/" +id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<LeaveRemaining>() {
                }).getBody();
    }

    public LeaveRemaining getByCurrentUser(Integer id){
        return restTemplate.exchange(url +"/user",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<LeaveRemaining>() {
                }).getBody();
    }
//    public LeaveRemaining create(LeaveRemainingRequest leaveRemainingRequest){
//        return restTemplate.exchange(url,
//                HttpMethod.POST,
//                new HttpEntity(leaveRemainingRequest),
//                new ParameterizedTypeReference<LeaveRemaining>() {
//                }).getBody();
//    }

    public LeaveRemaining update(Integer id, LeaveRemainingRequest leaveRemainingRequest){
        return restTemplate.exchange(url + "/" + id,
                HttpMethod.PUT,
                new HttpEntity(leaveRemainingRequest),
                new ParameterizedTypeReference<LeaveRemaining>() {
                }).getBody();
    }

}

package ru.romansayapin.spring;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.romansayapin.spring.model.User;

import java.util.List;

@Component
public class Communication {

    private final String URL = "http://94.198.50.185:7081/api/users";
    private final RestTemplate restTemplate;
    private String sessionID;

    public Communication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getAllUser() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>() {});
        List<User> users = responseEntity.getBody();
        ResponseEntity<String> cookie = restTemplate.getForEntity(URL, String.class);
        sessionID = cookie.getHeaders().getFirst("Set-Cookie");
        System.out.println(sessionID);
        return users;
    }

    public String saveUser() {
        User james = new User(3, "James", "Brown", (byte)30);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionID);
        HttpEntity<User> entity = new HttpEntity<>(james,headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL,HttpMethod.POST, entity, String.class);
        return responseEntity.getBody();
    }

    public String updateUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionID);
        HttpEntity<User> entity = new HttpEntity<>(user,headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT,entity, String.class);
        return responseEntity.getBody();
    }
    public String deleteUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionID);
        HttpEntity<User> entity = new HttpEntity<>(user,headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL+"/"+user.getId(),
                HttpMethod.DELETE,entity, String.class);
        return responseEntity.getBody();
    }


}

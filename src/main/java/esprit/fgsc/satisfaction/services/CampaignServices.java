package esprit.fgsc.satisfaction.services;

import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CampaignServices {
    @Autowired
    public CampaignServices(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
//
//        @Autowired
//    private WebClient reactiveWebClient;
//    @GetMapping("/test")
//    public Flux<?> serviceUrl(@RequestHeader("Authorization") String token) {
//        return reactiveWebClient.get()
//                .uri("https://fgsc-gateway.herokuapp.com/api/auth/paginated")
//                .accept(MediaType.APPLICATION_JSON)
//                .header("Authorization", token)
//                .retrieve().bodyToFlux(Object.class);
//    }
    private final RestTemplate restTemplate;
    private final static String MAIL_STRING = "https://fgsc-auth-service.herokuapp.com/users/send-mail?email=%s&subject=%s&text=%s";
    public Map getEmployeeById(String employeeId){
        return this.restTemplate.getForObject("https://employee-microservices.herokuapp.com/employee/"+ employeeId, Map.class);
    }
    public void sendInviteMail(String email, String subject, String text){
        new Thread(() -> this.restTemplate.getForObject(String.format(MAIL_STRING,email,subject,text),void.class));
    }

}

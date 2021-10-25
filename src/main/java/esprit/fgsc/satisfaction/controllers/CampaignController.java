package esprit.fgsc.satisfaction.controllers;


import esprit.fgsc.satisfaction.models.Campaign;
import esprit.fgsc.satisfaction.models.Form;
import esprit.fgsc.satisfaction.models.InviteUrl;
import esprit.fgsc.satisfaction.repositories.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.builders.ResponseBuilder;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Stream;

@CrossOrigin("*")
@RestController
public class CampaignController {
    // REGION : COUNT
    // DONE
    @GetMapping("/count")
    public Mono<Long> count(){
        return this.campaignRepository.count();
    }
    // DONE
    @GetMapping("/count/creator")
    public Mono<Long> countByCreatorId(@RequestParam(name = "id") String creatorId){
        return this.campaignRepository.countByCreatorEmployeeId(creatorId);
    }
    // DONE
    @GetMapping("/responses/count/all")
    public Mono<Long> countResponses(){
        return this.campaignRepository
                .findAll()
                .map(Campaign::getInviteUrls)
                .map(Map::values)
                .map(Collection::stream)
                .map(inviteUrlStream -> inviteUrlStream.map(InviteUrl::getResponses))
                .map(Stream::count)
                .collectList()
                .map(Collection::stream)
                .map(longStream -> longStream.reduce(Long::sum))
                .map(Optional::get);
    }
    // DONE
    @GetMapping("/count/invites")
    public Mono<Integer> countByCampaignId(@RequestParam String campaignId){
        return this.campaignRepository.findById(campaignId).map(Campaign::getInviteUrls).map(Map::size);
    }
    // DONE
    @GetMapping("/count/responses")
    public Mono<Long> countResponsesByCampaignId(@RequestParam String campaignId){
        return this.campaignRepository.findById(campaignId)
                .map(Campaign::getInviteUrls)
                .map(Map::values)
                .map(Collection::stream)
                .map(stream -> stream.filter(inviteUrl -> inviteUrl.getResponses() != null))
                .map(Stream::count);
    }
    // DONE
    @GetMapping("/clients/invited")
    public Mono<Set<String>> invitedClientsEmails(@RequestParam String campaignId){
        return this.campaignRepository.findById(campaignId)
                .map(Campaign::getInviteUrls)
                .map(Map::keySet);
    }
    // END REGION : COUNT
    // REGION : CRUD
    // DONE
    @GetMapping
    public Mono<Campaign> getById(@RequestParam String campaignId){
        return this.campaignRepository.findById(campaignId);
    }
    // DONE
    @GetMapping("/paginated")
    public Flux<Campaign> paginated(final @RequestParam(required = false, defaultValue = "0") int page, final @RequestParam(required = false, defaultValue = "10") int size, final @RequestParam(required = false, defaultValue = "") String name){
        return campaignRepository.findByNameLikeOrderByEndDateTimeDesc(name, PageRequest.of(page, size));
    }
    // DONE
    @PostMapping
    public Mono<Campaign> add(@Valid @RequestBody Campaign campaign){
        return this.campaignRepository.insert(campaign);
    }
    // DONE
    @PutMapping
    public Mono<Campaign> update(@Valid @RequestBody Campaign campaign){
        return this.campaignRepository.save(campaign);
    }
    // DONE
    @PutMapping("/update/form")
    public Mono<Campaign> setForm(@RequestBody Form form, @RequestParam String campaignId) {
        return this.campaignRepository.findById(campaignId).flatMap(campaign -> {
            campaign.setForm(form);
            return this.campaignRepository.save(campaign);
        });
    }
    // DONE
    @DeleteMapping
    public Mono<Void> delete(@RequestParam String campaignId){
        return this.campaignRepository.deleteById(campaignId);
    }
    // END REGION : CRUD
//    @Autowired
//    private WebClient reactiveWebClient;
//    @GetMapping("/test")
//    public Flux<?> serviceUrl(@RequestHeader("Authorization") String token) {
//        return reactiveWebClient.get()
//                .uri("https://fgsc-gateway.herokuapp.com/api/auth/paginated")
//                .accept(MediaType.APPLICATION_JSON)
//                .header("Authorization", token)
//                .retrieve().bodyToFlux(Object.class);
//    }
//    @Autowired
//    private RestTemplate restTemplate;
//    @GetMapping("/test/rest")
//    public ResponseEntity<?> testRestTemplate(){
//        ResponseEntity<SecurityProperties.User> response = this.restTemplate.getForEntity("url", SecurityProperties.User.class);
//        if(response.getStatusCode().is2xxSuccessful() && response.hasBody()){
//            User user = response.getBody();
//            return user;
//        }
//
//        return ResponseEntity.ok(Optional.of("User not found"));
//    }
    private final CampaignRepository campaignRepository;
    @Autowired
    public CampaignController(CampaignRepository campaignRepository){
        this.campaignRepository = campaignRepository;
    }
}


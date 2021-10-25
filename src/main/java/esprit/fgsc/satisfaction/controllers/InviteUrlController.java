package esprit.fgsc.satisfaction.controllers;

import esprit.fgsc.satisfaction.models.Campaign;
import esprit.fgsc.satisfaction.models.Form;
import esprit.fgsc.satisfaction.models.InviteUrl;
import esprit.fgsc.satisfaction.repositories.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.time.Instant;

@CrossOrigin("*")
@RequestMapping("/invites")
@RestController
public class InviteUrlController {
    // DONE
    @GetMapping("/form")
    public Mono<Form> findFormByInviteTokenAndEmail(@RequestParam String token, @RequestParam String email, @RequestParam String campaignId){
        return this.campaignRepository
                .findById(campaignId)
                .flatMap(campaign -> {
                    campaign.getInviteUrls().get(email).setSeenDateTime(Instant.now());
                    return campaignRepository.save(campaign);
                })
                .map(Campaign::getForm);
    }
    @Autowired
    private RestTemplate restTemplate;
    // DONE
    @PostMapping
    public Mono<Campaign> addInvite(@RequestBody InviteUrl inviteUrl, @RequestParam String campaignId, @RequestParam String clientEmail){
        return this.campaignRepository.findById(campaignId).flatMap(campaign -> {
                    campaign.getInviteUrls().put(clientEmail,inviteUrl);
                    // TODO : DO THIS IN A MONGO INTERCEPTOR
                    // TODO : ASK GHADA TO CHECK FOR CLIENT BY EMAIL
                    // TODO : SEND CLIENT EMAIL
                    // this.restTemplate.getForEntity("https://fgsc-gateway.herokuapp.com/api/auth/send-mail",String.class);
                    return campaignRepository.save(campaign);
                });
    }
    private final CampaignRepository campaignRepository;
    @Autowired
    public InviteUrlController(CampaignRepository campaignRepository){
        this.campaignRepository = campaignRepository;
    }
}

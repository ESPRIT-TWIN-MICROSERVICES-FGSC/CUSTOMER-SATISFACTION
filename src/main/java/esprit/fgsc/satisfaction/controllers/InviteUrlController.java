package esprit.fgsc.satisfaction.controllers;

import esprit.fgsc.satisfaction.models.Campaign;
import esprit.fgsc.satisfaction.models.Form;
import esprit.fgsc.satisfaction.models.InviteUrl;
import esprit.fgsc.satisfaction.repositories.CampaignRepository;
import esprit.fgsc.satisfaction.services.CampaignServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.Instant;

@CrossOrigin("*")
@RequestMapping("/invites")
@RestController
public class InviteUrlController {
    // DONE
    @GetMapping("/form")
    public Mono<Form> findFormByInviteTokenAndEmail(@RequestParam String email, @RequestParam String campaignId){
        return this.campaignRepository
                .findById(campaignId)
                .flatMap(campaign -> {
                    campaign.getInviteUrls().get(email).setSeenDateTime(Instant.now());
                    return campaignRepository.save(campaign);
                })
                .map(Campaign::getForm);
    }h
    // DONE
    @PostMapping
    public Mono<Campaign> addInvite(@RequestBody InviteUrl inviteUrl, @RequestParam String campaignId, @RequestParam String clientEmail){
        return this.campaignRepository.findById(campaignId).flatMap(campaign -> {
                    campaign.getInviteUrls().put(clientEmail,inviteUrl);
                    this.campaignServices.sendInviteMail(clientEmail,"Vodoo - Invited to campaign",
                            "You have been invited to a feedback acquisition campaign <a href='"+inviteUrl.getToken()+"'>Give us your feedback</a>");
                    return campaignRepository.save(campaign);
                });
    }
    private final CampaignRepository campaignRepository;
    private final CampaignServices campaignServices;
    @Autowired
    public InviteUrlController(CampaignRepository campaignRepository, CampaignServices campaignServices){
        this.campaignServices = campaignServices;
        this.campaignRepository = campaignRepository;
    }
}

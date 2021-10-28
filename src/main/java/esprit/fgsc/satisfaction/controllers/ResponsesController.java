package esprit.fgsc.satisfaction.controllers;

import esprit.fgsc.satisfaction.models.Campaign;
import esprit.fgsc.satisfaction.models.Form;
import esprit.fgsc.satisfaction.models.InviteUrl;
import esprit.fgsc.satisfaction.repositories.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RequestMapping("/responses")
@RestController
public class ResponsesController {
    // TODO : TEST
    @PostMapping
    public Mono<?> addResponse(@RequestParam String campaignId, @RequestParam String clientEmail, @RequestBody Map<String, Object> response) {
        return this.addUpdateForm(campaignId, clientEmail, response);
    }

    @PutMapping
    public Mono<?> updateResponse(@RequestParam String campaignId, @RequestParam String clientEmail, @RequestBody Map<String, Object> response) {
        return this.addUpdateForm(campaignId, clientEmail, response);
    }

    private Mono<?> addUpdateForm(String campaignId, String clientEmail, Map<String, Object> response) {
        return this.campaignRepository.findById(campaignId).map(campaign -> {
            if (campaign.getInviteUrls().containsKey(clientEmail)) {
                campaign.getInviteUrls().get(clientEmail).setResponses(response);
                return campaignRepository.save(campaign);
            }
            return Mono.error(new RuntimeException("Could not find invite"));
        });
    }

    @PatchMapping
    public Mono<Campaign> devOnly() {
        campaignRepository.deleteAll();
        Campaign c = new Campaign();
        c.setDescription("Campaign BETA");
        c.setCreatorEmployeeId("616a1e7e6f8cf03f7ac4916e");
        c.setName("Test campaign generated for testing purposes only");
        for (int i = 0; i < 20; i++) {
            InviteUrl url = new InviteUrl();
//            url.setClientEmail("test@gmail.com");
            Map<String, Object> data = new HashMap<>();
            data.put("test", "ok");
            url.setResponses(data);
            c.getInviteUrls().put("test" + i + "@gmail.com", url);
        }
        Form f = new Form();
        f.setDisplay("Test");
        c.setForm(f);
        return this.campaignRepository.save(c);
    }

    private final CampaignRepository campaignRepository;

    @Autowired
    public ResponsesController(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }
}

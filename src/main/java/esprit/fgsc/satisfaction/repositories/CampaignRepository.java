package esprit.fgsc.satisfaction.repositories;

import esprit.fgsc.satisfaction.models.Campaign;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CampaignRepository extends ReactiveMongoRepository<Campaign, String> {
    Flux<Campaign> findByNameLikeOrderByEndDateTimeDesc(String name, final Pageable page);
    Mono<Long> countByCreatorEmployeeId(String creatorEmployeeId);
    Mono<Long> existsByInviteUrlsContaining(String email);
}

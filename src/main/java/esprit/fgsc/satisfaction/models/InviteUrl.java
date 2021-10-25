package esprit.fgsc.satisfaction.models;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InviteUrl {
    @Getter private String  token = UUID.randomUUID().toString();
    @Getter @Setter private Instant seenDateTime;
    @Getter @Setter private Instant responseDateTime;
    @Getter @Setter private Map<String, Object> responses = new HashMap<>();
}

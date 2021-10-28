package esprit.fgsc.satisfaction.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Document
public class Campaign {
    @MongoId(value = FieldType.OBJECT_ID) @Getter @Setter private String id;
    @Length(max = 120, min = 10) @Getter @Setter private String name;
    @Length(max = 255, min = 10) @Getter @Setter private String description;
    @Transient @Getter @Setter private Map employee;
    // TODO : CONVERT TO MAP
    // @Getter @Setter private List<InviteUrl> inviteUrls = new ArrayList<>();
    @NotNull @Getter @Setter private String creatorEmployeeId;
    @NotNull @Getter @Setter private Instant endDateTime;
    @Getter private Map<String,InviteUrl> inviteUrls = new HashMap<>();
    @Getter private Instant creationDateTime = Instant.now();
    @Getter @Setter private Instant startDateTime = Instant.now();
    @NotNull @Getter @Setter private Form form;
}

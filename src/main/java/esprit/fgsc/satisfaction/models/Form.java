package esprit.fgsc.satisfaction.models;

import lombok.Getter;
import lombok.Setter;
import org.bson.json.JsonObject;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Form {
    @MongoId(value = FieldType.OBJECT_ID) @Getter @Setter private String id;
    @Getter @Setter private String title;
    @Getter @Setter private String display;
    @Getter @Setter private String name;
    @Getter @Setter private String path;
    @Getter @Setter private String type;
    @Getter @Setter private String project;
    @Getter @Setter private String template;
    @Getter @Setter private List<String> tags = new ArrayList<>();
    @Getter @Setter private List<AccessSetting> access = new ArrayList<>();
    @Getter @Setter private List<AccessSetting> submissionAccess = new ArrayList<>();
    @Getter @Setter private List<Map<String, Object>> components = new ArrayList<>();
}

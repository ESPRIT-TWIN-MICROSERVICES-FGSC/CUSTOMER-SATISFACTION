package esprit.fgsc.satisfaction.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class AccessSetting {
    @Getter @Setter private String type;
    @Getter @Setter private List<String> roles = new ArrayList<>();
}

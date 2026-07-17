package uk.pilk.snek.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RuleInfo {
    private String name;
    private String version;
    private RulesetSettings settings;
}
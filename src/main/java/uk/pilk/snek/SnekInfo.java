package uk.pilk.snek;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SnekInfo {
    private int apiVersion;
    private String author;
    private String color;
    private String head;
    private String tail;
}

package uk.pilk.snek;

import lombok.Data;

@Data
public class SnekInfo {
    private int apiVersion;
    private String author;
    private String color;
    private String head;
    private String tail;
}

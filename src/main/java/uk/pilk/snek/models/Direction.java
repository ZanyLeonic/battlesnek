package uk.pilk.snek.models;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Locale;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    @JsonValue
    @Override
    public String toString() {
        return super.toString().toLowerCase(Locale.ROOT);
    }

}

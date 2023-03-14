package com.manujell.rgb.dto;

import java.util.List;

public class ActiveDecoratorDTO {
    private final String name;
    private final List<?> values;

    public ActiveDecoratorDTO(String name, List<Object> values) {
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public List<?> getValues() {
        return values;
    }
}

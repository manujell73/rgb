package com.manujell.rgb.parameter;

public abstract class Parameter {
    private final String id;
    private final ParameterType type;
    private final String name;

    public Parameter(String id, String name, ParameterType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public ParameterType getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}

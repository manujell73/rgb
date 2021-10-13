package com.manujell.rgb.parameter;

public abstract class Parameter {
    private final String id;
    private final ParameterType parameterType;
    private final String name;

    public Parameter(String id, String name, ParameterType parameterType) {
        this.id = id;
        this.name = name;
        this.parameterType = parameterType;
    }

    public String getId() {
        return id;
    }

    public ParameterType getParameterType() {
        return parameterType;
    }

    public String getName() {
        return name;
    }
}

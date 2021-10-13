package com.manujell.rgb.parameter;

public class EnumParameter extends Parameter {
    private final String[] values;

    public EnumParameter(String id, String name, String... values) {
        super(id, name, ParameterType.ENUM);
        this.values = values;
    }

    public String[] getValues() {
        return values;
    }
}

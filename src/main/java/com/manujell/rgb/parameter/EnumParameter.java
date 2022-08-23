package com.manujell.rgb.parameter;

public class EnumParameter extends Parameter {
    private final String[] enums;

    public EnumParameter(String id, String name, String... enums) {
        super(id, name, ParameterType.ENUM);
        this.enums = enums;
    }

    public String[] getEnums() {
        return enums;
    }
}

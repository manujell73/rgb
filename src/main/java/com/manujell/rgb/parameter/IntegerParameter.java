package com.manujell.rgb.parameter;

public class IntegerParameter extends Parameter {
    private final int minValue;
    private final int maxValue;

    public IntegerParameter(String id, String name, int minValue, int maxValue) {
        this(ParameterType.NUMBER, id, name, minValue, maxValue);
    }

    protected IntegerParameter(ParameterType parameterType, String id, String name, int minValue, int maxValue) {
        super(id, name, parameterType);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }
}

package com.manujell.rgb.patterns;


import java.awt.*;
import java.util.List;
import java.util.function.Function;

public abstract class Pattern {
    private int length;

    protected Pattern(int length) {
        this.length = length;
    }

    public abstract Color[] getCurrentColors();
    public abstract void setColors(List<Color> colors);
    public abstract void applyDecorator(Function<Color, Color> function);
    public abstract boolean isContinuous();

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}

package com.manujell.rgb.patterns;

import java.awt.Color;
import java.util.List;

public abstract class Pattern {
    private int length;

    protected Pattern(int length) {
        this.length = length;
    }

    public abstract Color[] getCurrentColors();
    public abstract void setColors(List<Color> colors);

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}

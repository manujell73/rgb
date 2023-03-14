package com.manujell.rgb.patterns;

import com.manujell.rgb.color.decorators.ColorDecorator;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public abstract class Pattern {
    private int length;

    protected Pattern(int length) {
        this.length = length;
    }

    public abstract List<Color> getCurrentColors(List<ColorDecorator> decorators);
    public abstract void setColors(List<Color> colors);
    public abstract boolean isContinuous();

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<Color> getColorsWithoutDecorators() {
        return getCurrentColors(Collections.emptyList());
    }
}

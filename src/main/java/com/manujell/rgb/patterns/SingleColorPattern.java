package com.manujell.rgb.patterns;

import com.manujell.rgb.parameter.ColorParameter;
import com.manujell.rgb.parameter.Parameter;
import java.util.function.Function;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class SingleColorPattern extends Pattern {
    private Color color;

    public SingleColorPattern(int length, Color color) {
        super(length);
        this.color = color;
    }

    @Override
    public Color[] getCurrentColors() {
        Color[] colors = new Color[getLength()];

        Arrays.fill(colors, color);

        return colors;
    }

    @Override
    public void setColors(List<Color> colors) {
        if(colors == null || colors.size() <= 0) {
            throw new IllegalArgumentException("There must be a color.");
        }
        this.color = colors.get(0);
    }

    @Override
    public void applyDecorator(Function<Color, Color> function) {
        color = function.apply(color);
    }

    public static Parameter[] getParameters() {
        Parameter[] parameters = new Parameter[1];

        parameters[0] = new ColorParameter("SingleColor", "Color");

        return parameters;
    }
}

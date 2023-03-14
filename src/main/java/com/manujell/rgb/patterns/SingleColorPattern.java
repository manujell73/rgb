package com.manujell.rgb.patterns;

import com.manujell.rgb.color.decorators.ColorDecorator;
import com.manujell.rgb.parameter.ColorParameter;
import com.manujell.rgb.parameter.Parameter;
import com.manujell.rgb.utility.DecoratorUtils;

import java.util.Collections;

import java.awt.*;
import java.util.List;

public class SingleColorPattern extends Pattern {
    protected Color color;

    public SingleColorPattern(int length, Color color) {
        super(length);
        this.color = color;
    }

    @Override
    public List<Color> getCurrentColors(List<ColorDecorator> decorators) {
        return Collections.nCopies(getLength(), DecoratorUtils.applyDecorators(color, decorators));
    }

    @Override
    public void setColors(List<Color> colors) {
        if(colors == null || colors.size() <= 0) {
            throw new IllegalArgumentException("There must be a color.");
        }
        this.color = colors.get(0);
    }

    public static List<Parameter> getParameters() {
        return Collections.singletonList(new ColorParameter("SingleColor", "Color"));
    }

    @Override
    public boolean isContinuous() {
        return false;
    }
}

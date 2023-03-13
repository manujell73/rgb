package com.manujell.rgb.color.decorators;

import com.manujell.rgb.parameter.Parameter;
import com.manujell.rgb.parameter.PercentParameter;
import com.manujell.rgb.utility.DecoratorUtils;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

public class BrightnessDecorator extends Color implements ColorDecorator {
    private final static String ID_PREFIX = "brightness.parameter.";
    private final Color next;
    private final float opacity;

    public BrightnessDecorator(Color next, float opacity) {
        super(next.getRGB());
        this.next = next;
        this.opacity = opacity;
    }

    @Override
    public int getRGB() {
        return DecoratorUtils.calcRGB(next.getRGB(), opacity/100f);
    }

    public static List<Parameter> getParameters() {
        return Collections.singletonList(new PercentParameter(ID_PREFIX + "color", "Brightness"));
    }
}

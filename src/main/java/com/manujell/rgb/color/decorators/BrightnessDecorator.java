package com.manujell.rgb.color.decorators;

import com.manujell.rgb.parameter.ColorParameter;
import com.manujell.rgb.parameter.IntegerParameter;
import com.manujell.rgb.parameter.Parameter;
import com.manujell.rgb.parameter.PercentParameter;

import java.awt.*;

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

    public static Parameter[] getParameters() {
        Parameter[] parameters = new Parameter[1];

        parameters[0] = new PercentParameter(ID_PREFIX + "color", "Brightness");

        return parameters;
    }
}

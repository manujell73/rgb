package com.manujell.rgb.color.decorators;

import com.manujell.rgb.parameter.ColorParameter;
import com.manujell.rgb.parameter.IntegerParameter;
import com.manujell.rgb.parameter.Parameter;
import com.manujell.rgb.utility.DecoratorUtils;

import java.awt.*;

public class TransitionDecorator extends Color implements ColorDecorator {
    private final static String ID_PREFIX = "transition.parameter.";

    private final Color color1;
    private final Color color2;
    private final long speed;
    private final long anchor;

    public TransitionDecorator(Color color1, Color color2, long speed) {
        super(color1.getRGB());
        this.color1 = color1;
        this.color2 = color2;
        this.speed = speed;
        this.anchor = System.currentTimeMillis();
    }

    public static Parameter[] getParameters() {
        Parameter[] parameters = new Parameter[2];

        parameters[0] = new ColorParameter(ID_PREFIX + "color", "Color");
        parameters[1] = new IntegerParameter(ID_PREFIX + "speed", "Speed", 1, Integer.MAX_VALUE);

        return parameters;
    }

    @Override
    public int getRGB() {
        float opacity = getOpacityUpdateAnchor(System.currentTimeMillis());
        return DecoratorUtils.calcRGBTransition(color1.getRGB(), color2.getRGB(), opacity);
    }

    private float getOpacityUpdateAnchor(long now) {
        float periodLength = 60000f/speed;
        long diff = now - anchor;

        float a = (float) Math.cos(Math.PI * diff/periodLength);
        return a*a;
    }
}

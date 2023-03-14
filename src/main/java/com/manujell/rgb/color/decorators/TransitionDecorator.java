package com.manujell.rgb.color.decorators;

import com.manujell.rgb.dto.ActiveDecoratorDTO;
import com.manujell.rgb.parameter.ColorParameter;
import com.manujell.rgb.parameter.IntegerParameter;
import com.manujell.rgb.parameter.Parameter;
import com.manujell.rgb.utility.DecoratorUtils;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class TransitionDecorator extends ColorDecorator {
    private final static String ID_PREFIX = "transition.parameter.";

    private final Color color;
    private final long anchor;
    protected final long speed;

    public TransitionDecorator(Color color, long speed) {
        this.color = color;
        this.speed = speed;
        this.anchor = System.currentTimeMillis();
    }

    public static List<Parameter> getParameters() {
        return Arrays.asList(
                new ColorParameter(ID_PREFIX + "color", "Color"),
                new IntegerParameter(ID_PREFIX + "speed", "Speed", 1, Integer.MAX_VALUE)
        );
    }

    @Override
    public Color calcNewColor(Color color) {
        float opacity = getOpacityUpdateAnchor(System.currentTimeMillis());
        return DecoratorUtils.calcRGBTransition(color, this.color, opacity);
    }

    private float getOpacityUpdateAnchor(long now) {
        float periodLength = 60000f/speed;
        long diff = now - anchor;

        float a = (float) Math.cos(Math.PI * diff/periodLength);
        return a*a;
    }

    @Override
    public Function<Color, ColorDecorator> getDecoratorFunction() {
        return color -> new TransitionDecorator(color, speed);
    }

    @Override
    public ActiveDecoratorDTO mapToDTO() {
        return new ActiveDecoratorDTO(TransitionDecorator.class.getSimpleName(), Arrays.asList(color.getRGB(), speed));
    }

    @Override
    public boolean isContinuous() {
        return true;
    }
}

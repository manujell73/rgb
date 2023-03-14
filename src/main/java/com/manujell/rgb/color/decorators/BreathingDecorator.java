package com.manujell.rgb.color.decorators;

import com.manujell.rgb.dto.ActiveDecoratorDTO;
import com.manujell.rgb.parameter.IntegerParameter;
import com.manujell.rgb.parameter.Parameter;

import java.awt.Color;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class BreathingDecorator extends TransitionDecorator {
    private final static String ID_PREFIX = "breathing.parameter.";

    public BreathingDecorator(long speed) {
        super(Color.BLACK, speed);
    }

    public static List<Parameter> getParameters() {
        return Collections.singletonList(new IntegerParameter(ID_PREFIX + "speed", "Speed", 1, Integer.MAX_VALUE));
    }

    @Override
    public Function<Color, ColorDecorator> getDecoratorFunction() {
        return color -> new BreathingDecorator(speed);
    }

    @Override
    public ActiveDecoratorDTO mapToDTO() {
        return new ActiveDecoratorDTO(BreathingDecorator.class.getSimpleName(), Collections.singletonList(speed));
    }
}

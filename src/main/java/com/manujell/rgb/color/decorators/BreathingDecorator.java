package com.manujell.rgb.color.decorators;

import com.manujell.rgb.parameter.IntegerParameter;
import com.manujell.rgb.parameter.Parameter;

import java.awt.*;

public class BreathingDecorator extends TransitionDecorator implements ColorDecorator {
    private final static String ID_PREFIX = "breathing.parameter.";

    public BreathingDecorator(Color next, long speed) {
        super(Color.BLACK, next, speed);
    }

    public static Parameter[] getParameters() {
        Parameter[] parameters = new Parameter[1];

        parameters[0] = new IntegerParameter(ID_PREFIX + "speed", "Speed", 1, Integer.MAX_VALUE);

        return parameters;
    }
}

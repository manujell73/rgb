package com.manujell.rgb.color.decorators;

import com.manujell.rgb.parameter.IntegerParameter;
import com.manujell.rgb.parameter.Parameter;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

public class BreathingDecorator extends TransitionDecorator implements ColorDecorator {
    private final static String ID_PREFIX = "breathing.parameter.";

    public BreathingDecorator(Color next, long speed) {
        super(next, Color.BLACK, speed);
    }

    public static List<Parameter> getParameters() {
        return Collections.singletonList(new IntegerParameter(ID_PREFIX + "speed", "Speed", 1, Integer.MAX_VALUE));
    }
}

package com.manujell.rgb.color.decorators;

import java.awt.*;

public class BreathingDecorator extends TransitionDecorator {
    public BreathingDecorator(Color next, long speed) {
        super(Color.BLACK, next, speed);
    }
}

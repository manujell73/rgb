package com.manujell.rgb.color.decorators;

import java.awt.*;

public class TransitionDecorator extends Color {
    private final Color color1;
    private final Color color2;
    private final long speed;
    private long anchor;

    public TransitionDecorator(Color color1, Color color2, long speed) {
        super(color1.getRGB());
        this.color1 = color1;
        this.color2 = color2;
        this.speed = speed;
        this.anchor = System.currentTimeMillis();
    }

    @Override
    public int getRGB() {
        float opacity = getOpacityUpdateAnchor(System.currentTimeMillis());
        return DecoratorUtils.calcRGBTransition(color1.getRGB(), color2.getRGB(), opacity);
    }

    private float getOpacityUpdateAnchor(long now) {
        long diff = (now - anchor)%speed;

        anchor += diff - (diff%speed);

        float a = (float) Math.cos(Math.PI * diff/speed);
        return a*a;
    }
}

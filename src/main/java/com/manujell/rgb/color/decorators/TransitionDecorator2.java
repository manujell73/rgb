package com.manujell.rgb.color.decorators;

import java.awt.*;

public class TransitionDecorator2 extends Color {
    private final Color[] colors;
    private final long speed;
    private long anchor;

    public TransitionDecorator2(long speed, Color... colors) {
        super(colors[0].getRGB());
        if(colors.length < 2){
            throw new IllegalArgumentException("Needs at least 2 colors.");
        }
        this.colors = colors;
        this.speed = speed;
        this.anchor = System.currentTimeMillis();
    }

    @Override
    public int getRGB() {
        long diff = System.currentTimeMillis() - anchor;
        anchor += diff - (diff%(2*speed));
        diff %= speed*2;
        float opacity = calcOpacity(diff);
        int ind = (int) ((diff * colors.length) / speed);

        // TODO
        Color c1 = colors[((-ind-1)/2 + colors.length)%colors.length];
        Color c2 = colors[(-ind/2 + 1 + colors.length)%colors.length];

        return DecoratorUtils.calcRGBTransition(c1.getRGB(), c2.getRGB(), opacity);
    }

    private float calcOpacity(long diff) {
        float a = (float) Math.cos(Math.PI * (diff * colors.length)/(speed*2));
        return a*a;
    }
}

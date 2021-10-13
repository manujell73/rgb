package com.manujell.rgb.color.decorators;

import java.awt.*;

public class BrightnessDecorator extends Color {
    private final Color next;
    private final float opacity;

    public BrightnessDecorator(Color next, float opacity) {
        super(next.getRGB());
        this.next = next;
        this.opacity = opacity;
    }

    @Override
    public int getRGB() {
        return DecoratorUtils.calcRGB(next.getRGB(), opacity);
    }
}

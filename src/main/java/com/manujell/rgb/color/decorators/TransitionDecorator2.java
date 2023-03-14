package com.manujell.rgb.color.decorators;

import com.manujell.rgb.dto.ActiveDecoratorDTO;

import java.awt.*;
import java.util.function.Function;

@Deprecated
public class TransitionDecorator2 extends ColorDecorator {
    private final Color[] colors;
    private final long speed;
    private long anchor;

    public TransitionDecorator2(long speed, Color... colors) {
        if(colors.length <= 0){
            throw new IllegalArgumentException("Needs at least 1 colors.");
        }
        this.colors = colors;
        this.speed = speed;
        this.anchor = System.currentTimeMillis();
    }

    public int getRGB(int rgb) {
        throw new UnsupportedOperationException();
    }

    private float calcOpacity(long diff) {
        float a = (float) Math.cos(Math.PI * (diff * colors.length)/(speed*2));
        return a*a;
    }

    @Override
    public Function<Color, ColorDecorator> getDecoratorFunction() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ActiveDecoratorDTO mapToDTO() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isContinuous() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Color calcNewColor(Color color) {
        throw new UnsupportedOperationException();
    }
}

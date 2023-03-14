package com.manujell.rgb.color.decorators;

import com.manujell.rgb.dto.ActiveDecoratorDTO;

import java.awt.*;
import java.util.function.Function;

public abstract class ColorDecorator {
    public abstract Function<Color, ColorDecorator> getDecoratorFunction();
    public abstract ActiveDecoratorDTO mapToDTO();
    public abstract boolean isContinuous();
    public abstract Color calcNewColor(Color color);
}

package com.manujell.rgb.color.decorators;

import com.manujell.rgb.dto.ActiveDecoratorDTO;
import com.manujell.rgb.parameter.Parameter;
import com.manujell.rgb.parameter.PercentParameter;
import com.manujell.rgb.utility.DecoratorUtils;

import java.awt.Color;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class BrightnessDecorator extends ColorDecorator {
    private final static String ID_PREFIX = "brightness.parameter.";
    private final float opacity;

    public BrightnessDecorator(float opacity) {
        this.opacity = opacity;
    }

    @Override
    public Color calcNewColor(Color color) {
        return DecoratorUtils.calcColor(color, opacity/100f);
    }

    public static List<Parameter> getParameters() {
        return Collections.singletonList(new PercentParameter(ID_PREFIX + "color", "Brightness"));
    }

    @Override
    public Function<Color, ColorDecorator> getDecoratorFunction() {
        return color -> new BrightnessDecorator(opacity);
    }

    @Override
    public ActiveDecoratorDTO mapToDTO() {
        return new ActiveDecoratorDTO(BrightnessDecorator.class.getSimpleName(), Collections.singletonList(opacity));
    }

    @Override
    public boolean isContinuous() {
        return false;
    }
}

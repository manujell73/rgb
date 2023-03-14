package com.manujell.rgb.utility;

import com.manujell.rgb.color.decorators.BreathingDecorator;
import com.manujell.rgb.color.decorators.BrightnessDecorator;
import com.manujell.rgb.color.decorators.ColorDecorator;
import com.manujell.rgb.color.decorators.TransitionDecorator;
import com.manujell.rgb.parameter.Parameter;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class DecoratorUtils {
    public static final List<Class<? extends ColorDecorator>> colorDecorators = List.of(BreathingDecorator.class, BrightnessDecorator.class, TransitionDecorator.class);

    public static Color calcColor(Color color, float opacity) {
        int red = Math.round(color.getRed() * opacity);
        int green = Math.round(color.getGreen() * opacity);
        int blue = Math.round(color.getBlue() * opacity);
        return new Color(red, green, blue);
    }

    public static Color calcRGBTransition(Color color1, Color color2, float opacity) {
        Color c1 = calcColor(color1, opacity);
        Color c2 = calcColor(color2, 1-opacity);
        int red = c1.getRed() + c2.getRed();
        int green = c1.getGreen() + c2.getGreen();
        int blue = c1.getBlue() + c2.getBlue();

        return new Color(red, green, blue);
    }

    public static ColorDecorator getDecoratorInstance(Class<? extends ColorDecorator> clazz, List<Integer> parameter) {
        if(clazz == BreathingDecorator.class) {
            checkExpectedParameterLength(parameter, 1);
            return new BreathingDecorator(parameter.get(0));
        }
        if(clazz == BrightnessDecorator.class){
            checkExpectedParameterLength(parameter, 1);
            return new BrightnessDecorator(parameter.get(0));
        }
        if(clazz == TransitionDecorator.class){
            checkExpectedParameterLength(parameter, 2);
            return new TransitionDecorator(new Color(parameter.get(0)), parameter.get(1));
        }
        throw new IllegalArgumentException("Unsupported ColorDecorator class: " + clazz.getSimpleName());
    }

    private static void checkExpectedParameterLength(List<Integer> parameter, int expectedLength) {
        if(parameter.size() != expectedLength) {
            throw new IllegalArgumentException("wrong parameters");
        }
    }

    // Please don't ask why
    public static List<Parameter> getParametersOfDecorator(int decoratorCode){
        Class<? extends ColorDecorator> decoratorClass = colorDecorators.get(decoratorCode);
        if(decoratorClass == BreathingDecorator.class) {
            return BreathingDecorator.getParameters();
        }
        else if(decoratorClass == BrightnessDecorator.class) {
            return BrightnessDecorator.getParameters();
        }
        else if(decoratorClass == TransitionDecorator.class) {
            return TransitionDecorator.getParameters();
        }
        return Collections.emptyList();
    }

    public static Color applyDecorators(Color color, List<ColorDecorator> decorators) {
        for(ColorDecorator decorator : decorators) {
            color = decorator.calcNewColor(color);
        }
        return color;
    }

    public static Class<? extends ColorDecorator> getDecoratorByName(String name) {
        return colorDecorators.stream()
                .filter(clazz -> clazz.getSimpleName().equals(name))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}

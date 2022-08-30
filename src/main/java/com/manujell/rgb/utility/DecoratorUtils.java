package com.manujell.rgb.utility;

import com.manujell.rgb.color.decorators.BreathingDecorator;
import com.manujell.rgb.color.decorators.BrightnessDecorator;
import com.manujell.rgb.color.decorators.ColorDecorator;
import com.manujell.rgb.color.decorators.TransitionDecorator;
import com.manujell.rgb.parameter.Parameter;

import java.awt.*;
import java.util.List;
import java.util.function.Function;

public class DecoratorUtils {
    public static final List<Class<? extends ColorDecorator>> colorDecorators = List.of(BreathingDecorator.class, BrightnessDecorator.class, TransitionDecorator.class);

    public static int calcRGB(int rgb, float opacity) {
        int red = Math.round(((rgb>>16) & 255) * opacity);
        int green = Math.round(((rgb>>8) & 255) * opacity);
        int blue = Math.round((rgb & 255) * opacity);
        return (red << 16) | (green << 8) | blue;
    }

    public static int calcRGBTransition(int rgb1, int rgb2, float opacity) {
        int red = Math.round(((rgb1>>16) & 255) * opacity);
        int green = Math.round(((rgb1>>8) & 255) * opacity);
        int blue = Math.round((rgb1 & 255) * opacity);

        red += Math.round(((rgb2>>16) & 255) * (1-opacity));
        green += Math.round(((rgb2>>8) & 255) * (1-opacity));
        blue += Math.round((rgb2 & 255) * (1-opacity));

        return (red << 16) | (green << 8) | blue;
    }

    public static Function<Color, Color> getDecoratorFunction(Class<? extends ColorDecorator> clazz, List<Integer> parameter) {
        if(clazz == BreathingDecorator.class) {
            return color -> new BreathingDecorator(color, parameter.get(0));
        }
        if(clazz == BrightnessDecorator.class){
            return color -> new BrightnessDecorator(color, parameter.get(0));
        }
        if(clazz == TransitionDecorator.class){
            return color -> new TransitionDecorator(color, new Color(parameter.get(0)), parameter.get(1));
        }
        return color -> color;
    }

    // Please don't ask why
    public static Parameter[] getParametersOfDecorator(int decoratorCode){
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
        return new Parameter[0];
    }

    public static Class<? extends ColorDecorator> getDecoratorByName(String name) {
        return colorDecorators.stream()
                .filter(clazz -> clazz.getSimpleName().equals(name))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}

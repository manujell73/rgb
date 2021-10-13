package com.manujell.rgb.color.decorators;

import java.awt.*;
import java.util.List;

public class DecoratorUtils {
    public static final List<Class> colorDecorators = List.of(BreathingDecorator.class, BrightnessDecorator.class, TransitionDecorator.class);

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

    public static Color getInstance(Class clazz, Color color, int parameter) {
        if(clazz == BreathingDecorator.class) {
            return new BreathingDecorator(color, parameter);
        }
        if(clazz == BrightnessDecorator.class){
            return new BrightnessDecorator(color, parameter);
        }
        return color;
    }
}

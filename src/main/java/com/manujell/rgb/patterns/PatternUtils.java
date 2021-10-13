package com.manujell.rgb.patterns;

import com.manujell.rgb.parameter.Parameter;

import java.awt.*;
import java.util.List;

public class PatternUtils {
    public static final List<Class<? extends Pattern>> patterns = List.of(SingleColorPattern.class, RainDropsPattern.class, NightRiderPattern.class, TransitionPattern.class);
    // TODO change to getRGB()
    public static Color calcColor(Color color, float opacity) {
        int red = Math.round(color.getRed() * opacity);
        int green = Math.round(color.getGreen() * opacity);
        int blue = Math.round(color.getBlue() * opacity);
        return new Color(red, green, blue);
    }

    public static Color calcColor(Color color1, double opacity1, Color color2, double opacity2) {
        int red = (int)Math.min(Math.round(color1.getRed() * opacity1 + color2.getRed() * opacity2), 255);
        int green = (int)Math.min(Math.round(color1.getGreen() * opacity1 + color2.getGreen() * opacity2), 255);
        int blue = (int)Math.min(Math.round(color1.getBlue() * opacity1 + color2.getBlue() * opacity2), 255);
        return new Color(red, green, blue);
    }

    public static int calcIndex(int ind, int offset, int arrayLength) {
        return (((ind + offset) % arrayLength) + arrayLength) % arrayLength;    // Please don't ask
    }

    public static Pattern getInstance(Class<? extends Pattern> clazz, int length, List<Integer> parameters) {
        if(clazz == NightRiderPattern.class) {
            return new NightRiderPattern(length, new Color(parameters.get(0)), parameters.get(1), parameters.get(2), parameters.get(3));
        }
        if(clazz == RainDropsPattern.class) {
            return new RainDropsPattern(length, new Color(parameters.get(0)), parameters.get(1), parameters.get(2));
        }
        if(clazz == SingleColorPattern.class) {
            return new SingleColorPattern(length, new Color(parameters.get(0)));
        }
        if(clazz == TransitionPattern.class) {
            return new TransitionPattern(length, new Color(parameters.get(0)), parameters.get(1), new Color(parameters.get(2)), parameters.get(3));
        }
        return new SingleColorPattern(length, Color.BLACK);
    }

    // Please don't ask why
    public static Parameter[] getParametersOfPattern(int patternCode){
        Class<? extends Pattern> patternClass = patterns.get(patternCode);
        if(patternClass == SingleColorPattern.class) {
            return SingleColorPattern.getParameters();
        }
        else if(patternClass == RainDropsPattern.class) {
            return RainDropsPattern.getParameters();
        }
        else if(patternClass == NightRiderPattern.class) {
            return NightRiderPattern.getParameters();
        }
        else if(patternClass == TransitionPattern.class) {
            return TransitionPattern.getParameters();
        }
        return new Parameter[0];
    }
}

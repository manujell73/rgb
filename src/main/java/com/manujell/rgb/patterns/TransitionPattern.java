package com.manujell.rgb.patterns;

import com.manujell.rgb.parameter.ColorParameter;
import com.manujell.rgb.parameter.IntegerParameter;
import com.manujell.rgb.parameter.Parameter;
import com.manujell.rgb.utility.PatternUtils;

import java.util.function.Function;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransitionPattern extends Pattern {
    private final List<ColorPair> colorPairs;

    public TransitionPattern(int length, List<Color> colors, List<Integer> offsets) {
        super(length);
        if(colors.size() != offsets.size())
            throw new IllegalArgumentException("Colors and offsets must be equal size.");
        if(colors.size() <= 1)
            throw new IllegalArgumentException("Needs at least two colors.");

        colorPairs = new ArrayList<>();
        for(int i=0; i<colors.size(); i++) {
            colorPairs.add(new ColorPair(colors.get(i), offsets.get(i)));
        }
        Collections.sort(colorPairs);
    }

    public TransitionPattern(int length, Color color1, int offset1, Color color2, int offset2) {
        this(length, List.of(color1, color2), List.of(offset1, offset2));
    }

    public TransitionPattern(int length, Color color1, float offset1, Color color2, float offset2) {
        this(length, color1, Math.round(offset1 * length), color2, Math.round(offset2 * length));
    }

    @Override
    public Color[] getCurrentColors() {
        Color[] colors = new Color[getLength()];

        for(int i=0; i<colors.length; i++) {
            colors[i] = calcColorAtIndexNew(i);
        }
        return colors;
    }

    @Override
    public void setColors(List<Color> colors) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void applyDecorator(Function<Color, Color> function) {
        colorPairs.forEach(pair -> pair.color = function.apply(pair.color));
    }

    public static Parameter[] getParameters() {
        Parameter[] parameters = new Parameter[4];

        parameters[0] = new ColorParameter("TransitionColor1", "Color 1");
        parameters[1] = new IntegerParameter("TransitionOffset1", "Offset 1", 0, Integer.MAX_VALUE);
        parameters[2] = new ColorParameter("TransitionColor2", "Color 2");
        parameters[3] = new IntegerParameter("TransitionOffset2", "Offset 2", 0, Integer.MAX_VALUE);

        return parameters;
    }

    private Color calcColorAtIndex(int ind, ColorPair pair2, ColorPair pair1) {
        int anchorDiff = PatternUtils.calcIndex(pair1.offset, -pair2.offset, getLength());
        int diff = PatternUtils.calcIndex(ind, -pair2.offset, getLength());

        if(anchorDiff < diff) {
            anchorDiff = getLength() - anchorDiff;
            diff = getLength() - diff;
        }

        double opacity1 = diff / (double) anchorDiff;
        double opacity2 = 1.0d - opacity1;

        return PatternUtils.calcColor(pair1.color, opacity1, pair2.color, opacity2);
    }

    private Color calcColorAtIndexNew(int ind) {
        ColorPair lowerColor = colorPairs.get(colorPairs.size()-1);
        ColorPair higherColor = colorPairs.get(0);
        for(int i=1; ind > higherColor.offset && i <= colorPairs.size(); i++) {
            lowerColor = higherColor;
            higherColor = colorPairs.get(i%colorPairs.size());
        }
        return calcColorAtIndex(ind, lowerColor, higherColor);
    }

    private static class ColorPair implements Comparable<ColorPair> {
        private Color color;
        private final int offset;

        public ColorPair(Color color, int offset) {
            this.color = color;
            this.offset = offset;
        }

        @Override
        public int compareTo(ColorPair o) {
            return Integer.compare(offset, o.offset);
        }
    }
}

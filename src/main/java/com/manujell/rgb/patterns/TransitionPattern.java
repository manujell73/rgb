package com.manujell.rgb.patterns;

import com.manujell.rgb.parameter.ColorParameter;
import com.manujell.rgb.parameter.IntegerParameter;
import com.manujell.rgb.parameter.Parameter;

import java.awt.*;
import java.util.List;

public class TransitionPattern extends Pattern {
    private Color color1;
    private final int offset1;
    private Color color2;
    private final int offset2;

    public TransitionPattern(int length, Color color1, int offset1, Color color2, int offset2) {
        super(length);
        this.color1 = color1;
        this.offset1 = offset1;
        this.color2 = color2;
        this.offset2 = offset2;
    }

    public TransitionPattern(int length, Color color1, float offset1, Color color2, float offset2) {
        super(length);
        this.color1 = color1;
        this.offset1 = Math.round(offset1 * length);
        this.color2 = color2;
        this.offset2 = Math.round(offset2 * length);
    }

    @Override
    public Color[] getCurrentColors() {
        Color[] colors = new Color[getLength()];

        for(int i=0; i<colors.length; i++) {
            colors[i] = calcColorAtIndex(i);
        }
        return colors;
    }

    @Override
    public void setColors(List<Color> colors) {
        if(colors == null || colors.size() <= 1) {
            throw new IllegalArgumentException("There must be two colors.");
        }
        this.color1 = colors.get(0);
        this.color2 = colors.get(1);
    }

    public static Parameter[] getParameters() {
        Parameter[] parameters = new Parameter[4];

        parameters[0] = new ColorParameter("TransitionColor1", "Color 1");
        parameters[1] = new IntegerParameter("TransitionOffset1", "Offset 1", 0, Integer.MAX_VALUE);
        parameters[2] = new ColorParameter("TransitionColor2", "Color 2");
        parameters[3] = new IntegerParameter("TransitionOffset2", "Offset 2", 0, Integer.MAX_VALUE);

        return parameters;
    }

    private Color calcColorAtIndex(int ind) {
        int anchorDiff = PatternUtils.calcIndex(offset1, -offset2, getLength());
        int diff = PatternUtils.calcIndex(ind, -offset2, getLength());

        if(anchorDiff < diff) {
            anchorDiff = getLength() - anchorDiff;
            diff = getLength() - diff;
        }

        double opacity1 = diff / (double) anchorDiff;
        double opacity2 = 1.0d - opacity1;

        return PatternUtils.calcColor(color1, opacity1, color2, opacity2);
    }
}

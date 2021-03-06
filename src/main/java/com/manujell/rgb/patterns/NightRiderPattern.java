package com.manujell.rgb.patterns;

import com.manujell.rgb.parameter.ColorParameter;
import com.manujell.rgb.parameter.EnumParameter;
import com.manujell.rgb.parameter.IntegerParameter;
import com.manujell.rgb.parameter.Parameter;

import java.awt.Color;
import java.util.List;

public class NightRiderPattern extends Pattern {
    private Color color;
    private final int speed; // Umdrehungen / Minute
    private final int lineLength;
    private final int dir;
    private long anchor;

    public NightRiderPattern(int length, Color color, int speed, int lineLength, int dir) {
        super(length);
        this.color = color;
        this.speed = speed;
        this.lineLength = lineLength;
        this.dir = dir;
        this.anchor = System.currentTimeMillis();
    }

    @Override
    public Color[] getCurrentColors() {
        Color[] colors = new Color[getLength()];
        int ind = calcCurrentLocation();

        for(int i=0; i<lineLength; i++) {
            float opacity = (lineLength - i) / (float) lineLength;
            int subInd = PatternUtils.calcIndex(ind, dir < 0 ? i : -i, getLength());
            colors[subInd] = PatternUtils.calcColor(color, opacity);
        }

        for(int i=0; i<colors.length; i++) {
            if(colors[i] == null){
                colors[i] = Color.BLACK;
            }
        }

        return colors;
    }

    @Override
    public void setColors(List<Color> colors) {
        if(colors == null || colors.size() <= 0) {
            throw new IllegalArgumentException("There must be a color.");
        }
        this.color = colors.get(0);
    }

    public static Parameter[] getParameters() {
        Parameter[] parameters = new Parameter[4];

        parameters[0] = new ColorParameter("NightRiderColor", "Color");
        parameters[1] = new IntegerParameter("NightRiderSpeed", "Speed", 1, Integer.MAX_VALUE);
        parameters[2] = new IntegerParameter("NightRiderLength", "Line-length", 1, Integer.MAX_VALUE);
        parameters[3] = new EnumParameter("NightRiderDirection", "Direction", "Forwards", "Backwards");

        return parameters;
    }

    private int calcCurrentLocation() {
        int time = (int) (System.currentTimeMillis() - anchor) % 60_000;

        anchor %= 60_000L;

        int offset = (time * getLength() * speed) / 60_000;
        if(dir < 0)
            offset*=-1;

        return PatternUtils.calcIndex(0, offset, getLength());
    }
}

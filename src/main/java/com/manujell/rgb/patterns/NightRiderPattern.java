package com.manujell.rgb.patterns;

import com.manujell.rgb.color.decorators.ColorDecorator;
import com.manujell.rgb.parameter.ColorParameter;
import com.manujell.rgb.parameter.EnumParameter;
import com.manujell.rgb.parameter.IntegerParameter;
import com.manujell.rgb.parameter.Parameter;
import com.manujell.rgb.utility.PatternUtils;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class NightRiderPattern extends SingleColorPattern {
    private final int speed; // Umdrehungen / Minute
    private final int lineLength;
    private final int dir;
    private long anchor;

    public NightRiderPattern(int length, Color color, int speed, int lineLength, int dir) {
        super(length, color);
        this.speed = speed;
        this.lineLength = lineLength;
        this.dir = dir;
        this.anchor = System.currentTimeMillis();
    }

    @Override
    public List<Color> getCurrentColors(List<ColorDecorator> decorators) {
        List<Color> colors = PatternUtils.getAllBlackList(getLength());
        int ind = calcCurrentLocation();

        for(int i=0; i<lineLength; i++) {
            float opacity = (lineLength - i) / (float) lineLength;
            int subInd = PatternUtils.calcIndex(ind, dir > 0 ? i : -i, getLength());
            colors.set(subInd, PatternUtils.calcColor(color, opacity));
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

    public static List<Parameter> getParameters() {
        return Arrays.asList(
                new ColorParameter("NightRiderColor", "Color"),
                new IntegerParameter("NightRiderSpeed", "Speed", 1, Integer.MAX_VALUE),
                new IntegerParameter("NightRiderLength", "Line-length", 1, Integer.MAX_VALUE),
                new EnumParameter("NightRiderDirection", "Direction", "Forwards", "Backwards")
        );
    }

    private int calcCurrentLocation() {
        int time = (int) (System.currentTimeMillis() - anchor) % 60_000;

        anchor %= 60_000L;

        int offset = (time * getLength() * speed) / 60_000;
        if(dir > 0)
            offset*=-1;

        return PatternUtils.calcIndex(0, offset, getLength());
    }

    @Override
    public boolean isContinuous() {
        return true;
    }
}

package com.manujell.rgb.patterns;

import com.manujell.rgb.parameter.ColorParameter;
import com.manujell.rgb.parameter.IntegerParameter;
import com.manujell.rgb.parameter.Parameter;

import java.awt.*;
import java.util.List;
import java.util.*;

public class RainDropsPattern extends Pattern {
    private final Map<Integer, Long> rainDrops;
    private Color color;
    private final int dropsPerMinute;
    private final float dropDuration;
    private final Random rng;
    private long anchor;
    private int dropsSinceAnchor;

    public RainDropsPattern(int length, Color color, int dropsPerMinute, float dropDuration) {
        super(length);
        this.rainDrops = new HashMap<>();
        this.anchor = System.currentTimeMillis();
        this.dropsSinceAnchor = 0;
        this.rng = new Random();

        this.color = color;
        this.dropDuration = dropDuration;
        this.dropsPerMinute = dropsPerMinute;
    }

    @Override
    public Color[] getCurrentColors() {
        long now = System.currentTimeMillis();
        updateRaindrops(now);

        Color[] colors = new Color[getLength()];

        synchronized (rainDrops) {
            Iterator<Map.Entry<Integer, Long>> iter = rainDrops.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<Integer, Long> entry = iter.next();
                float opacity = calcOpacity(now - entry.getValue());
                if (opacity <= 0) {
                    iter.remove();
                    continue;
                }
                colors[entry.getKey()] = PatternUtils.calcColor(color, opacity);
            }
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
        Parameter[] parameters = new Parameter[3];

        parameters[0] = new ColorParameter("RaindropColor", "Color");
        parameters[1] = new IntegerParameter("RaindropSpeed", "Drops per Minute", 1, Integer.MAX_VALUE);
        parameters[2] = new IntegerParameter("RaindropDuration", "Drop duration", 1, Integer.MAX_VALUE);

        return parameters;
    }

    private void updateRaindrops(long now) {
        long nDrops = calcNewDropsUpdateAnchor(now);
        dropsSinceAnchor += nDrops;

        synchronized (rainDrops) {
            for (long i = 0; i < nDrops; i++) {
                int ind = rng.nextInt(getLength());
                rainDrops.put(ind, now);
            }
        }
    }

    private long calcNewDropsUpdateAnchor(long now) {
        while(now - anchor > 60_000) {
            dropsSinceAnchor -= dropsPerMinute;
            anchor += 60_000;
        }

        long drops = ((now - anchor) * dropsPerMinute) / 60_000;
        return drops - dropsSinceAnchor;
    }

    private float calcOpacity(long time) {
        return Math.max(0, (dropDuration * 1_000 - time)/(dropDuration*1_000f));
    }
}

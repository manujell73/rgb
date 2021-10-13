package com.manujell.rgb.service;

import com.github.mbelling.ws281x.LedStripType;
import com.github.mbelling.ws281x.Ws281xLedStrip;
import com.manujell.rgb.color.decorators.DecoratorUtils;
import com.manujell.rgb.patterns.Pattern;
import com.manujell.rgb.patterns.PatternUtils;
import com.manujell.rgb.patterns.SingleColorPattern;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RgbTableService {
    private static final boolean LOCALLY = false;

    private static final int REFRESH_FREQUENCY = 30;
    private static final int REFRESH_TIME = 1000/REFRESH_FREQUENCY;
    public int stripLength = 64;
    private Pattern ledPattern;
    private List<Color> colors;
    private Thread thread;

    public RgbTableService() {
        colors = new ArrayList<>();
        ledPattern = PatternUtils.getInstance(SingleColorPattern.class, stripLength, List.of(Color.BLACK.getRGB()));
        thread = startLedStrip();
    }

    public int getStripLength() {
        return stripLength;
    }

    public void setStripLength(int stripLength) {
        this.stripLength = stripLength;
        ledPattern.setLength(stripLength);
        thread = startLedStrip();
    }

    public Pattern getLedPattern() {
        return ledPattern;
    }

    public void setLedPattern(Class patternClass, List<Integer> parameters) {
        setLedPattern(PatternUtils.getInstance(patternClass, stripLength, parameters));
    }

    public void setLedPattern(Pattern ledPattern) {
        this.ledPattern = ledPattern;
        System.out.println("Changed Pattern: " + ledPattern.getClass().getSimpleName());
    }

    public void addColorDecorator(Class decoratorClass, int parameter) {
        colors.set(0, DecoratorUtils.getInstance(decoratorClass, colors.get(0), parameter));
        ledPattern.setColors(colors);
    }

    public RgbTableService(Pattern ledPattern) {
        this.ledPattern = ledPattern;
    }

    public void setColors(List<Color> newColors) {
        if(newColors.isEmpty()) {
            return;
        }
        for(int i=0; i<newColors.size(); i++) {
            if(colors.size()<=i)
                colors.add(newColors.get(i));
            else
                colors.set(i, newColors.get(i));
        }
        ledPattern.setColors(colors);
        System.out.println("Changed colors: " + colors.stream().mapToInt(Color::getRGB).map(a -> a&((1<<24) - 1)).mapToObj(a->String.format("%6x", a)).collect(Collectors.joining(" - ")));
    }

    private Thread startLedStrip() {
        if(!LOCALLY) {
            Thread thread = new Thread(() -> {
                Ws281xLedStrip rgb = new Ws281xLedStrip(stripLength, 10, 800000, 10, 4, 0, false, LedStripType.WS2811_STRIP_GRB, true);
                while (true) {
                    long start = (System.currentTimeMillis() / REFRESH_TIME) * REFRESH_TIME;
                    Color[] colors = getLedPattern().getCurrentColors();
                    for (int i = 0; i < rgb.getLedsCount(); i++) {
                        rgb.setPixel(i, new com.github.mbelling.ws281x.Color(colors[i].getRGB()));
                    }
                    rgb.render();
                    //    System.out.println(Arrays.stream(colors).mapToInt(Color::getRGB).map(a->a&((2<<24)-1)).mapToObj(a->String.format("%6x", a)).collect(Collectors.joining(" - ")));
                    try {
                        Thread.sleep(Math.max(0, REFRESH_TIME - (System.currentTimeMillis() - start)));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            return thread;
        }
        Thread thread = new Thread(() -> {});
        thread.start();
        return thread;
    }
}

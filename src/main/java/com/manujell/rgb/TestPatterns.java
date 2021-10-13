package com.manujell.rgb;

import com.manujell.rgb.patterns.Pattern;
import com.manujell.rgb.patterns.TransitionPattern;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TestPatterns {
    public static void main(String[] args) {
//        Pattern pattern = new RainDropsPattern(Color.BLUE, 100, 850, .7f);
//        Pattern pattern = new NightRiderPattern(Color.BLUE, 100, 10, 10, -1);
        Pattern pattern = new TransitionPattern(10, Color.BLUE, 0, Color.GREEN, 9);
//        Pattern pattern = new SingleColorPattern(new TransitionDecorator2(20000, Color.RED, Color.GREEN, Color.BLUE, Color.GREEN), 10);

        do {
            System.out.println(System.currentTimeMillis() + ": " +
                    Arrays.stream(pattern.getCurrentColors())
                            .map(color->String.format("%3d %3d %3d", color.getRed(), color.getGreen(), color.getBlue()))
                            .collect(Collectors.joining(" - ")));
        } while(!(pattern instanceof TransitionPattern));
    }
}

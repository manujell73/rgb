package com.manujell.rgb;

import com.github.mbelling.ws281x.LedStripType;
import com.github.mbelling.ws281x.Ws281xLedStrip;
import com.manujell.rgb.color.decorators.TransitionDecorator;
import com.manujell.rgb.patterns.NightRiderPattern;
import com.manujell.rgb.patterns.Pattern;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class TestWS2812B {

    public static void main(String[] args) throws InterruptedException {

        Ws281xLedStrip rgb = new Ws281xLedStrip(64,10,800000,10,2,0,false, LedStripType.WS2811_STRIP_RGB,true);
        Pattern pattern = new NightRiderPattern(rgb.getLedsCount(), new TransitionDecorator(Color.RED, Color.GREEN, 1500), 30, 5, 1);

        System.out.println("printing...");
        while(true) {
            Color[] colors = pattern.getCurrentColors();
            for (int i = 0; i < rgb.getLedsCount(); i++) {
                rgb.setPixel(i, new com.github.mbelling.ws281x.Color(colors[i].getRGB()));
            }
            rgb.render();
            TimeUnit.MILLISECONDS.sleep(20);
        }
    }
}

package com.manujell.rgb.service;

import com.github.mbelling.ws281x.LedStripType;
import com.github.mbelling.ws281x.Ws281xLedStrip;
import com.manujell.rgb.ApplicationProperties;
import com.manujell.rgb.color.decorators.ColorDecorator;
import com.manujell.rgb.utility.DecoratorUtils;
import com.manujell.rgb.dto.StripInfoDTO;
import com.manujell.rgb.patterns.Pattern;
import com.manujell.rgb.utility.PatternUtils;
import com.manujell.rgb.patterns.SingleColorPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.util.List;

@Service
public class RgbTableService {
    private static final int REFRESH_FREQUENCY = 30;
    private static final int REFRESH_TIME = 1000/REFRESH_FREQUENCY;
    public int stripLength = 64;
    private Pattern ledPattern;
    private Thread thread;

    @Autowired
    private ApplicationProperties applicationProperties;

    public RgbTableService() {
        ledPattern = PatternUtils.getInstance(SingleColorPattern.class, stripLength, List.of(Color.BLACK.getRGB()));
    }

    @PostConstruct
    private void postConstruct() {
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

    public void setLedPattern(Class<? extends Pattern> patternClass, List<Integer> parameters) {
        setLedPattern(PatternUtils.getInstance(patternClass, stripLength, parameters));
    }

    public void setLedPattern(Pattern ledPattern) {
        this.ledPattern = ledPattern;
        System.out.println("Changed Pattern: " + ledPattern.getClass().getSimpleName());
    }

    public void addColorDecorator(Class<? extends ColorDecorator> decoratorClass, List<Integer> parameter) {
        ledPattern.applyDecorator(DecoratorUtils.getDecoratorFunction(decoratorClass, parameter));
    }

    public RgbTableService(Pattern ledPattern) {
        this.ledPattern = ledPattern;
    }

    public StripInfoDTO getStripInfo() {
        return new StripInfoDTO(getLedPattern().getCurrentColors());
    }

    private Thread startLedStrip() {
        if(applicationProperties.getIsTestEnv()) {
            return null;
        }
        Thread thread = new Thread(() -> {
            try {
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
            }
            catch (Exception e){
                while(true){
                    getLedPattern().getCurrentColors();
                }
            }
        });
        thread.start();
        return thread;
    }
}

package com.manujell.rgb.controller;

import com.manujell.rgb.color.decorators.DecoratorUtils;
import com.manujell.rgb.dto.PatternDTO;
import com.manujell.rgb.dto.StripInfoDTO;
import com.manujell.rgb.parameter.Parameter;
import com.manujell.rgb.patterns.PatternUtils;
import com.manujell.rgb.patterns.SingleColorPattern;
import com.manujell.rgb.service.RgbTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@CrossOrigin
@RestController
public class RgbTableController {

    @Autowired
    private RgbTableService rgbTableService;

    public RgbTableController() {
    }

    @RequestMapping(value="/getStripInfo", method = GET)
    public StripInfoDTO getStripInfo() {
        return rgbTableService.getStripInfo();
    }

    @RequestMapping(value="/setLength", method = POST)
    public void setLength(@RequestParam int length) {
        rgbTableService.setStripLength(length);
    }

    @RequestMapping(value="/reset", method = POST)
    public void reset() {
        rgbTableService.setLedPattern(new SingleColorPattern(rgbTableService.getStripLength(), Color.BLACK));
    }

    @RequestMapping(value="/setPattern", method = POST)
    public void setPattern(@RequestParam int patternCode, @RequestParam List<Integer> parameters) {
        rgbTableService.setLedPattern(PatternUtils.patterns.get(patternCode), parameters);
    }

    @RequestMapping(value="/getPatternsOld", method = GET)
    public List<String> getPatternsOld() {
        return PatternUtils.patterns.stream().map(Class::getSimpleName).collect(Collectors.toList());
    }

    @RequestMapping(value="/getPatterns", method = GET)
    public List<PatternDTO> getPatterns() {
        return PatternUtils.patterns.stream().map(PatternDTO::new).collect(Collectors.toList());
    }

    // For the future
    @RequestMapping(value="/getColorDecorators", method = GET)
    public List<String> getColorDecorators() {
        return DecoratorUtils.colorDecorators.stream().map(Class::getSimpleName).collect(Collectors.toList());
    }

    @Deprecated
    @RequestMapping(value="/setColors", method = POST)
    public void setColors(@RequestParam List<Integer> colorCodes) {
        rgbTableService.setColors(colorCodes.stream()
                .map(Color::new)
                .collect(Collectors.toList()));
    }

    @Deprecated
    @RequestMapping(value="/addColorDecorator", method = POST)
    public void addDecorator(@RequestParam int decoratorCode, int parameter) {

        rgbTableService.addColorDecorator(DecoratorUtils.colorDecorators.get(decoratorCode), parameter);
    }

    @Deprecated
    @RequestMapping(value="/getColorPickerImage", method = GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getColorPickerImage() throws IOException {
        BufferedImage image = new BufferedImage(256*6, 100, Image.SCALE_DEFAULT);
        for(int x=0; x<image.getWidth(); x++) {
            int rgb = calcRgb(x);
            for(int y=0; y<image.getHeight(); y++) {
                image.setRGB(x, y, rgb);
            }
        }
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bao);
        System.out.println("image");
        return bao.toByteArray();
    }

    @Deprecated
    @RequestMapping(value="/getColorPickerBrightness", method = GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getColorPickerBrightness(@RequestParam int xImage) throws IOException {
        int width = 256;
        int height = 100;
        BufferedImage image = new BufferedImage(width, height, Image.SCALE_DEFAULT);
        int sourceRgb = calcRgb(xImage);
        int sourceR = (sourceRgb >> 16) & 255;
        int sourceG = (sourceRgb >> 8) & 255;
        int sourceB = sourceRgb & 255;
        for(int x=0; x<image.getWidth(); x++) {
            for(int y=0; y<image.getHeight(); y++) {
                int r;
                int g;
                int b;
                if(y < 50) {
                    r = (int) Math.min(255, Math.round((50-y) * (255d/50d) + (sourceR * y / 50d)));
                    g = (int) Math.min(255, Math.round((50-y) * (255d/50d) + (sourceG * y / 50d)));
                    b = (int) Math.min(255, Math.round((50-y) * (255d/50d) + (sourceB * y / 50d)));
                }
                else {
                    r = (int) Math.max(0, Math.round((100-y)*2 * sourceR / 100d));
                    g = (int) Math.max(0, Math.round((100-y)*2 * sourceG / 100d));
                    b = (int) Math.max(0, Math.round((100-y)*2 * sourceB / 100d));
                }
                image.setRGB(x, y, r<<16 | g<<8 | b);
            }
        }
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bao);
        System.out.println("image");
        return bao.toByteArray();
    }

    private int calcRgb(int x) {
        int increasing = x%256;
        int decreasing = 255 - increasing;
        if(x<256) {
            return (255 << 16) | (increasing << 8);
        }
        if(x<256*2) {
            return (decreasing << 16) | (255 << 8);
        }
        if(x<256*3) {
            return (255 << 8) | increasing;
        }
        if(x<256*4) {
            return (decreasing << 8) | 255;
        }
        if(x<256*5) {
            return (increasing << 16) | 255;
        }
        return (255 << 16) | decreasing;
    }
}

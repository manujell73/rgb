package com.manujell.rgb.controller;

import com.manujell.rgb.utility.DecoratorUtils;
import com.manujell.rgb.dto.PatternDTO;
import com.manujell.rgb.dto.StripInfoDTO;
import com.manujell.rgb.parameter.Parameter;
import com.manujell.rgb.utility.PatternUtils;
import com.manujell.rgb.patterns.SingleColorPattern;
import com.manujell.rgb.service.RgbTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.List;
import java.util.Map;
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
    public void setPattern(@RequestParam String name, @RequestParam List<Integer> parameters) {
        rgbTableService.setLedPattern(PatternUtils.getPatternByName(name), parameters);
    }

    @RequestMapping(value="/getPatterns", method = GET)
    public Map<String, PatternDTO> getPatternsNew() {
        return PatternUtils.patterns.stream().map(PatternDTO::createInstanceFromPattern).collect(Collectors.toMap(PatternDTO::getName, patternDTO -> patternDTO));
    }

    @RequestMapping(value="/getDecorators", method = GET)
    public Map<String, PatternDTO> getDecoratorsNew() {
        return DecoratorUtils.colorDecorators.stream().map(PatternDTO::createInstanceFromDecorator).collect(Collectors.toMap(PatternDTO::getName, patternDTO -> patternDTO));
    }

    @RequestMapping(value="/setDecorator", method = POST)
    public void setDecorator(@RequestParam String name, @RequestParam List<Integer> parameters) {

        rgbTableService.addColorDecorator(DecoratorUtils.getDecoratorByName(name), parameters);
    }
}

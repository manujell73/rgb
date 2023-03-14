package com.manujell.rgb.controller;

import com.manujell.rgb.color.decorators.ColorDecorator;
import com.manujell.rgb.dto.ActiveDecoratorDTO;
import com.manujell.rgb.dto.PatternDTO;
import com.manujell.rgb.dto.StripInfoDTO;
import com.manujell.rgb.patterns.SingleColorPattern;
import com.manujell.rgb.service.RgbTableService;
import com.manujell.rgb.utility.DecoratorUtils;
import com.manujell.rgb.utility.PatternUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@CrossOrigin
@RestController
public class RgbTableController {

    @Autowired
    private RgbTableService rgbTableService;

    public RgbTableController() {
    }

    @RequestMapping(value="/stripInfo", method = GET)
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

    @RequestMapping(value="/patterns", method = POST)
    public StripInfoDTO setPattern(@RequestParam String name, @RequestParam List<Integer> parameters) {
        rgbTableService.setLedPattern(PatternUtils.getPatternByName(name), parameters);
        return getStripInfo();
    }

    @RequestMapping(value="/patterns", method = GET)
    public Map<String, PatternDTO> getPatternsNew() {
        return PatternUtils.patterns
                .stream()
                .map(PatternDTO::createInstanceFromPattern)
                .collect(Collectors.toMap(PatternDTO::getName, patternDTO -> patternDTO));
    }

    @RequestMapping(value="/decorators", method = POST)
    public StripInfoDTO setDecorator(@RequestParam String name, @RequestParam List<Integer> parameters) {
        rgbTableService.addColorDecorator(DecoratorUtils.getDecoratorByName(name), parameters);
        return getStripInfo();
    }

    @RequestMapping(value="/decorators", method = GET)
    public Map<String, PatternDTO> getDecorators() {
        return DecoratorUtils.colorDecorators
                .stream()
                .map(PatternDTO::createInstanceFromDecorator)
                .collect(Collectors.toMap(PatternDTO::getName, patternDTO -> patternDTO));
    }

    @RequestMapping(value="/activeDecorators", method = GET)
    public List<ActiveDecoratorDTO> getActiveDecorators() {
        return rgbTableService.getActiveDecorators()
                .stream()
                .map(ColorDecorator::mapToDTO)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/activeDecorators", method = DELETE)
    public void removeDecorator(@RequestParam int index) {
        rgbTableService.removeDecorator(index);
    }
}

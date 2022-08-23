package com.manujell.rgb.dto;

import com.manujell.rgb.parameter.Parameter;
import com.manujell.rgb.patterns.Pattern;
import com.manujell.rgb.patterns.PatternUtils;

public class PatternDTO {
    private final int id;
    private final String name;
    private final Parameter[] parameters;

    public PatternDTO(Class<? extends Pattern> patternClass) {
        this.id = PatternUtils.patterns.indexOf(patternClass);
        this.name = patternClass.getSimpleName();
        this.parameters = PatternUtils.getParametersOfPattern(id);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Parameter[] getParameters() {
        return parameters;
    }
}

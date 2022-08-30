package com.manujell.rgb.dto;

import com.manujell.rgb.color.decorators.ColorDecorator;
import com.manujell.rgb.color.decorators.DecoratorUtils;
import com.manujell.rgb.parameter.Parameter;
import com.manujell.rgb.patterns.Pattern;
import com.manujell.rgb.patterns.PatternUtils;

public class PatternDTO {
    private final int id;
    private final String name;
    private final Parameter[] parameters;

    private PatternDTO(int id, String name, Parameter[] parameters) {
        this.id = id;
        this.name = name;
        this.parameters = parameters;
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

    public static PatternDTO createInstanceFromPattern(Class<? extends Pattern> patternClass) {
        int id = PatternUtils.patterns.indexOf(patternClass);
        String name = patternClass.getSimpleName();
        Parameter[] parameters = PatternUtils.getParametersOfPattern(id);

        return new PatternDTO(id, name, parameters);
    }

    public static PatternDTO createInstanceFromDecorator(Class<? extends ColorDecorator> decoratorClass) {
        int id = DecoratorUtils.colorDecorators.indexOf(decoratorClass);
        String name = decoratorClass.getSimpleName();
        Parameter[] parameters = DecoratorUtils.getParametersOfDecorator(id);

        return new PatternDTO(id, name, parameters);
    }
}

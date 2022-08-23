package com.manujell.rgb;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {

    @Value("${isTestEnv:false}")
    private boolean isTestEnv;

    public boolean getIsTestEnv() {
        return isTestEnv;
    }
}

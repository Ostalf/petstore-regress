package org.example.util;

import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PropertySources({@PropertySource(
        value = {"classpath:application.yaml"},
        factory = YamlPropertySourceFactory.class
)})
public @interface ApplicationPropertySources {
}

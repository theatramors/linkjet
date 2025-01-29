package ru.codexus.linkjet.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.codexus.linkjet.configuration.properties.AppProperties;

@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class AppConfiguration {
}

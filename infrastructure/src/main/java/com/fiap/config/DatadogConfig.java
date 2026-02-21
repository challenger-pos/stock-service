package com.fiap.config;

import com.timgroup.statsd.NonBlockingStatsDClientBuilder;
import com.timgroup.statsd.StatsDClient;
import com.timgroup.statsd.StatsDClientException;
import datadog.trace.api.GlobalTracer;
import datadog.trace.api.Tracer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatadogConfig {

    @Value("${datadog.statsd.host:localhost}")
    private String statsdHost;

    @Value("${datadog.statsd.port:8125}")
    private int statsdPort;

    @Bean
    public Tracer tracer() {
        return GlobalTracer.get();
    }

    @Bean
    @ConditionalOnProperty(name = "datadog.enabled", havingValue = "true")
    public StatsDClient statsDClient() {
        try {
            return new NonBlockingStatsDClientBuilder()
                    .prefix("stock")
                    .hostname(statsdHost)
                    .port(statsdPort)
                    .build();
        } catch (StatsDClientException e) {
            // Log warning but don't fail application startup
            System.err.println("Warning: Failed to create StatsD client: " + e.getMessage());
            return null;
        }
    }
}

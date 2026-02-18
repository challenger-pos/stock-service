package com.fiap.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import io.awspring.cloud.sqs.support.converter.SqsMessagingMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.time.Duration;

@Configuration
public class SqsConfig {

    @Value("${aws.region:us-east-2}")
    private String awsRegion;

    @Value("${aws.access-key-id:}")
    private String awsAccessKey;

    @Value("${aws.secret-access-key:}")
    private String awsSecretKey;

    @Bean
    public SqsMessagingMessageConverter sqsMessageConverter(ObjectMapper objectMapper) {
        SqsMessagingMessageConverter converter = new SqsMessagingMessageConverter();
        converter.setPayloadTypeHeader("eventType");
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Bean
    public SdkAsyncHttpClient httpClient() {
        return NettyNioAsyncHttpClient.builder()
                .maxConcurrency(200)
                .connectionTimeout(Duration.ofSeconds(30))
                .readTimeout(Duration.ofSeconds(30))
                .writeTimeout(Duration.ofSeconds(30))
                .build();
    }

    @Bean
    public SqsAsyncClient sqsAsyncClient() {
        var builder = SqsAsyncClient.builder()
                .region(Region.of(awsRegion))
                .httpClient(httpClient());

        // Se credenciais foram fornecidas, use-as (para development/homolog)
        // Em produção, use IAM Roles (IRSA)
        if (!awsAccessKey.isEmpty() && !awsSecretKey.isEmpty()) {
            builder.credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(awsAccessKey, awsSecretKey)
                )
            );
        }

        return builder.build();
    }

    @Bean
    public SqsTemplate sqsTemplate(SqsAsyncClient client,
                                   SqsMessagingMessageConverter converter) {

        return SqsTemplate.builder()
                .sqsAsyncClient(client)
                .messageConverter(converter)
                .build();
    }
}

package com.fiap.message;

import io.awspring.cloud.sqs.operations.SqsTemplate;
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

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Configuration
@ConditionalOnProperty(name = "aws.sqs.enabled", havingValue = "true", matchIfMissing = true)
public class SqsConfig {

    @Value("${aws.region:${spring.cloud.aws.region.static:us-east-2}}")
    private String awsRegion;

    /** Supports aws.access-key-id (Terraform/env) or spring.cloud.aws.credentials.access-key */
    @Value("${aws.access-key-id:${spring.cloud.aws.credentials.access-key:}}")
    private String awsAccessKey;

    /** Supports aws.secret-access-key (Terraform/env) or spring.cloud.aws.credentials.secret-key */
    @Value("${aws.secret-access-key:${spring.cloud.aws.credentials.secret-key:}}")
    private String awsSecretKey;

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
    public SqsTemplate sqsTemplate(SqsAsyncClient client) {
        return SqsTemplate.newTemplate(client);
    }
}

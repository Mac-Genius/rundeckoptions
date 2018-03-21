package io.macgenius.options;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RundeckOptions {
    public static void main(String[] args) {
        SpringApplication.run(RundeckOptions.class, args);
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClientBuilder.create().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
    }
}

package io.macgenius.options;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RundeckOptions {
    public static void main(String[] args) {
        SpringApplication.run(RundeckOptions.class, args);
    }

//    @Bean
//    public HttpClient httpClient() {
//        return HttpClientBuilder.create().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
//    }
}

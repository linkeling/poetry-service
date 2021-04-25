package com.poetry.admin;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableTransactionManagement
@MapperScan("com.poetry.admin.dao")
@SpringBootApplication(scanBasePackages ="com.poetry")

public class PoetryAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(PoetryAdminApplication.class, args);
    }

    private static void openLocalApiDocs(ConfigurableEnvironment environment) {
        try {
            if ("local".equals(environment.getActiveProfiles()[0])) {
                // 注意：不要使用IE作为默认浏览器
                String command = String.format("cmd /c start http://localhost:%s/docs.html", environment.getProperty("server.port"));
                System.out.println(command);
                Runtime.getRuntime().exec(command);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(httpRequestFactory());
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowCredentials(true);
            }
        };
    }

    @Bean
    public MybatisPaginationInterceptor paginationInterceptor() {
        return new MybatisPaginationInterceptor();
    }

    @Bean
    public HttpClient httpClient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
        // 设置每个路由基础的连接
        cm.setDefaultMaxPerRoute(800);
        //设置最大连接数
        cm.setMaxTotal(1000);
        RequestConfig requestConfig = RequestConfig.custom()
                //服务器返回数据(response)的时间，超过该时间抛出read timeout
                .setSocketTimeout(10000)
                //连接上服务器(握手成功)的时间，超出该时间抛出connect timeout
                .setConnectTimeout(5000)
                //从连接池中获取连接的超时时间，超过该时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
                .setConnectionRequestTimeout(1000)
                .build();
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(cm)
                .build();
    }
}

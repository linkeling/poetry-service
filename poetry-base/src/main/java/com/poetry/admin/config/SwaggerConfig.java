package com.poetry.admin.config;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

//@Profile({"local","dev","test"})
@Configuration
@EnableSwagger2
@ConfigurationProperties(prefix="swagger")
@ConditionalOnProperty(value="swagger.enable")
public class SwaggerConfig {
    private Boolean enable;
    private String title;
    private String description;
    private String basePackage = "com.poetry";
    private Boolean globalHeader = false;

    /**
     * 配置swagger接口文档
     */
    @Bean
    @ConditionalOnMissingBean(name = "swaggerDocket")
    public Docket createRestApi() {
        List<Parameter> pars = new ArrayList<>();
        if(globalHeader){
            ParameterBuilder ticketPar = new ParameterBuilder();
            ticketPar.name("Authorization").description("user token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
            pars.add(ticketPar.build());
        }

        Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(this.apiInfo()).select()
            .apis(RequestHandlerSelectors.basePackage(this.basePackage)).paths(PathSelectors.any()).build().globalOperationParameters(pars);
        Set<String> produces = new HashSet<String>(){{add(MediaType.APPLICATION_JSON_VALUE);}};
        Set<String> consumes = new HashSet<String>(){{add(MediaType.APPLICATION_FORM_URLENCODED_VALUE);}};
        docket.produces(produces);
        docket.consumes(consumes);
        return docket;
    }

    /**
     * 配置swagger api参数信息
     */
    private ApiInfo apiInfo() {
        String version = DateFormatUtils.format(new Date(),"yyyyMMddHHmm");
        return new ApiInfoBuilder().title(title).version(version)
            .description(description).build();
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public Boolean getGlobalHeader() {
        return globalHeader;
    }

    public void setGlobalHeader(Boolean globalHeader) {
        this.globalHeader = globalHeader;
    }
}

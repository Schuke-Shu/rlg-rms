package cn.maplerabbit.rlg.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Slf4j
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration
{
    public Knife4jConfiguration() {
        log.trace("Knife4jConfiguration()...");
    }

    /**
     * 【重要】指定Controller包路径
     */
    private String basePackage = "cn.maplerabbit.rlg.controller";
    /**
     * 分组名称
     */
    private String groupName = "rlg-backend";
    /**
     * 主机名
     */
    private String host = "http://maplerabbit.cn";
    /**
     * 标题
     */
    private String title = "红叶园API";
    /**
     * 简介
     */
    private String description = "红叶园家庭资源管理系统后台在线API文档";
    /**
     * 服务条款URL
     */
    private String termsOfServiceUrl = "http://www.apache.org/licenses/LICENSE-2.0";
    /**
     * 联系人
     */
    private String contactName = "一只枫兔";
    /**
     * 联系网址
     */
    private String contactUrl = "http://maplerabbit.cn";
    /**
     * 联系邮箱
     */
    private String contactEmail = "schuke-shu@outlook.com";
    /**
     * 版本号
     */
    private String version = "1";

    @Bean
    public Docket docket()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .host(host)
                .apiInfo(
                        new ApiInfoBuilder()
                                .title(title)
                                .description(description)
                                .termsOfServiceUrl(termsOfServiceUrl)
                                .contact(
                                        new Contact(
                                                contactName,
                                                contactUrl,
                                                contactEmail
                                        )
                                )
                                .version(version)
                                .build()
                )
                .groupName(groupName)
                .select()
                .apis(
                        RequestHandlerSelectors
                                .basePackage(basePackage)
                )
                .paths(
                        PathSelectors
                                .any()
                )
                .build();
    }
}
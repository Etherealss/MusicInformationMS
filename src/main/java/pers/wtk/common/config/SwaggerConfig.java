package pers.wtk.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Map;

/**
 * @author wtk
 * @description
 * @date 2021-07-14
 */
// @Configuration // 如果项目架构是SSM，那就不要加这个注解，如果是 springboot 就必须加上这个注解
@EnableWebMvc // springboot 项目不需要添加此注解，SSM 项目需要加上此注解
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket buildDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                .select()
                // 指定controller路径。RequestHandlerSelectors是扫描包的方式
                .apis(RequestHandlerSelectors.basePackage("pers.wtk.controller"))
                // 过滤路径。any：扫描全部，ant指定路径
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 配置API文档标题、描述、作者等等相关信息。
     * @return
     */
    private ApiInfo buildApiInfo(){
        Contact contact = new Contact("JavaEE大作业", "", "");
        return new ApiInfoBuilder()
                .title("NoWander前后端交接文档")
                .description("NoWander中使用Swagger2构建Restful API")
                .version("v1.0")
                .contact(contact)
                .build();

    }
}
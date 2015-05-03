package info.smartkit.hairy_batman.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import com.wordnik.swagger.model.ApiInfo;

@Configuration
@EnableSwagger
public class SwaggerConfig
{

    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig)
    {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean
    // Don't forget the @Bean annotation
    public SwaggerSpringMvcPlugin customImplementation()
    {
        // AbsoluteSwaggerPathProvider pathProvider = new AbsoluteSwaggerPathProvider();
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(apiInfo())
        // .pathProvider(pathProvider)
            .includePatterns("/v0/.*");
    }

    private ApiInfo apiInfo()
    {
        ApiInfo apiInfo =
            new ApiInfo("KPIKit Restful API", "API for KPIKit", "KPIKit API terms of service", "contact@KPIKit.com",
                "KPIKit API Licence Type", "KPIKit API License URL");
        return apiInfo;
    }

}

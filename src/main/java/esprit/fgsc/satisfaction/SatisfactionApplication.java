package esprit.fgsc.satisfaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@EnableSwagger2
@EnableWebFlux
@RestController
@CrossOrigin("*")
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
@EnableReactiveMongoRepositories
@FeignClient(name = "cfa")
@RibbonClient(name = "cfa")
public class SatisfactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SatisfactionApplication.class, args);
    }
   // @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {return new RestTemplate();}
    @Bean
   // @LoadBalanced
    public WebClient getWebClient(){
        return WebClient.builder().build();
    }

    @Autowired
    private MappingMongoConverter mongoConverter;
    @PostConstruct
    public void setUpMongoEscapeCharacterConversion() {
        mongoConverter.setMapKeyDotReplacement("_");
    }

    @Bean
    CorsWebFilter corsWebFilter() {
        ArrayList<String> allowed = new ArrayList<>();
        allowed.add("*");
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(allowed);
        corsConfig.setMaxAge(8000L);
        corsConfig.addAllowedMethod("*");
        corsConfig.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return new CorsWebFilter(source);
    }
}

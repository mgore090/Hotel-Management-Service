package com.MyApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient 
//@EnableSwagger2
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
		String c;
	}
	
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
		return new RestTemplate();
	}
//    @Bean("rstTempl")
////    @LoadBalanced
//    RestTemplate restTemplate1() {
//    	return new RestTemplate();
//    }
//    @Bean
////    @LoadBalanced
//    RestTemplate restTemplate2() {
//    	return new RestTemplate();
//    }
    
    
//    @Bean
//    Docket api() { 
//        return new Docket(DocumentationType.SWAGGER_2)  
//          .select()                                  
//          .apis(RequestHandlerSelectors.any())              
//          .paths(PathSelectors.any())                          
//          .build();                                           
//    }
    

}

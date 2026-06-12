package krefature.studvisit;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class StudVisitApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudVisitApplication.class, args);
    }

}

package eatku.eatkuserver.swagger;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
public class SwaggerUrlLogger implements ApplicationRunner {

    @Value("${server.port}")
    private String serverPort;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Swagger UI available at http://localhost:" + serverPort + "/swagger-ui/index.html");
    }
}

package playground.leones.node;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LeonesArcNodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeonesArcNodeApplication.class, args);
    }

}
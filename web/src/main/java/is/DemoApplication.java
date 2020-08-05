package is;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) throws IllegalAccessException, IOException {
        SpringApplication.run(DemoApplication.class, args);
    }
}

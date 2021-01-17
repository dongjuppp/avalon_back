package web.game.avalon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AvalonApplication {

    public static void main(String[] args) {
        System.out.println("db 3306");
        SpringApplication.run(AvalonApplication.class, args);
    }

}

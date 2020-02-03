package tim63.sistemKlinickogCentar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
/*
 * Ukljucivanje podrske za raspored izvrsavanja.
 */
@EnableScheduling
public class SistemKlinickogCentarApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemKlinickogCentarApplication.class, args);
    }

}

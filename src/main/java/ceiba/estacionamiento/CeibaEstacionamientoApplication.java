package ceiba.estacionamiento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;



@SpringBootApplication
@EnableJpaAuditing
public class CeibaEstacionamientoApplication {

	public static void main(String[] args) {		
		SpringApplication.run(CeibaEstacionamientoApplication.class, args);
	}
}

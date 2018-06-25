package ceiba.CeibaEstacionamiento;

import org.joda.time.DateTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import ceiba.CeibaEstacionamiento.dominio.Carro;
import ceiba.CeibaEstacionamiento.dominio.Moto;
import ceiba.CeibaEstacionamiento.dominio.Vehiculo;
import ceiba.CeibaEstacionamiento.dominio.Vigilante;

@SpringBootApplication
@EnableJpaAuditing
public class CeibaEstacionamientoApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(CeibaEstacionamientoApplication.class, args);
		/*DateTime dt = new DateTime();
		System.out.println("dia= " + dt.getDayOfWeek());*/
	}
}

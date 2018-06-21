package ceiba.CeibaEstacionamiento;

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
		
		/*Vehiculo v1 = new Carro("ASD123", "C");
		Vehiculo v2 = new Carro("AHD123", "C");
		Vehiculo v3 = new Carro("ASD123", "C");
		Vehiculo v4 = new Carro("AHD123", "C");
		Vehiculo v5 = new Carro("ASD123", "C");
		Vehiculo v6 = new Carro("AHD123", "C");
		Vehiculo v7 = new Carro("ASD123", "C");
		Vehiculo v8 = new Carro("AHD123", "C");
		Vehiculo v9 = new Carro("ASD123", "C");
		Vehiculo v10 = new Carro("AHD123", "C");
		Vehiculo v11 = new Carro("ASD123", "C");
		Vehiculo v12 = new Carro("AHD123", "C");
		Vehiculo v13 = new Carro("ASD123", "C");
		Vehiculo v14 = new Carro("AHD123", "C");
		Vehiculo v15 = new Carro("ASD123", "C");
		Vehiculo v16 = new Carro("AHD123", "C");
		Vehiculo v17 = new Carro("ASD123", "C");
		Vehiculo v18 = new Carro("AHD123", "C");
		Vehiculo v19 = new Carro("ASD123", "C");
		Vehiculo v20 = new Carro("AHD123", "C");
		Vehiculo v21 = new Carro("AHD123", "C");
		Vehiculo v22 = new Carro("AHD123", "C");
		
		Vehiculo m1 = new Moto("AHD123", "M", 400);
		Vehiculo m2 = new Moto("AHD123", "M", 400);
		Vehiculo m3 = new Moto("AHD123", "M", 400);
		Vehiculo m4 = new Moto("AHD123", "M", 400);
		Vehiculo m5 = new Moto("AHD123", "M", 400);
		Vehiculo m6 = new Moto("AHD123", "M", 400);
		Vehiculo m7 = new Moto("AHD123", "M", 400);
		Vehiculo m8 = new Moto("AHD123", "M", 400);
		Vehiculo m9 = new Moto("AHD123", "M", 400);
		Vehiculo m10 = new Moto("AHD123", "M", 400);
		Vehiculo m11 = new Moto("AHD123", "M", 400);
		Vehiculo m12 = new Moto("AHD123", "M", 400);
		
		
		Vigilante vigilante = new Vigilante();
		vigilante.registrarIngresoVehiculo(v1, "DOMINGO");		
		vigilante.registrarIngresoVehiculo(v2, "DOMINGO");
		vigilante.registrarIngresoVehiculo(v3, "DOMINGO");		
		vigilante.registrarIngresoVehiculo(v4, "DOMINGO");
		vigilante.registrarIngresoVehiculo(v5, "DOMINGO");		
		vigilante.registrarIngresoVehiculo(v6, "DOMINGO");
		vigilante.registrarIngresoVehiculo(v7, "DOMINGO");		
		vigilante.registrarIngresoVehiculo(v8, "DOMINGO");
		vigilante.registrarIngresoVehiculo(v9, "DOMINGO");		
		vigilante.registrarIngresoVehiculo(v10, "DOMINGO");
		vigilante.registrarIngresoVehiculo(v11, "DOMINGO");		
		vigilante.registrarIngresoVehiculo(v12, "DOMINGO");
		vigilante.registrarIngresoVehiculo(v13, "DOMINGO");		
		vigilante.registrarIngresoVehiculo(v14, "DOMINGO");
		vigilante.registrarIngresoVehiculo(v15, "DOMINGO");		
		vigilante.registrarIngresoVehiculo(v16, "DOMINGO");
		vigilante.registrarIngresoVehiculo(v17, "DOMINGO");		
		vigilante.registrarIngresoVehiculo(v18, "DOMINGO");
		vigilante.registrarIngresoVehiculo(v19, "DOMINGO");		
		vigilante.registrarIngresoVehiculo(v20, "DOMINGO");
		
		vigilante.registrarSalidaVehiculo(v20, "");
		vigilante.registrarIngresoVehiculo(v21, "DOMINGO");
		vigilante.registrarIngresoVehiculo(v22, "DOMINGO");
		
		vigilante.registrarIngresoVehiculo(m1, "DOMINGO");
		vigilante.registrarIngresoVehiculo(m2, "DOMINGO");
		vigilante.registrarIngresoVehiculo(m3, "DOMINGO");
		vigilante.registrarIngresoVehiculo(m4, "DOMINGO");
		vigilante.registrarIngresoVehiculo(m5, "DOMINGO");
		vigilante.registrarIngresoVehiculo(m6, "DOMINGO");
		vigilante.registrarIngresoVehiculo(m7, "DOMINGO");
		vigilante.registrarIngresoVehiculo(m8, "DOMINGO");
		vigilante.registrarIngresoVehiculo(m9, "DOMINGO");
		vigilante.registrarIngresoVehiculo(m10, "DOMINGO");		
		
		vigilante.registrarSalidaVehiculo(m10, "");
		vigilante.registrarIngresoVehiculo(m11, "DOMINGO");
		vigilante.registrarIngresoVehiculo(m12, "DOMINGO");*/
		
		
	}
}

package ceiba.estacionamiento.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ceiba.estacionamiento.dominio.Parqueadero;
import ceiba.estacionamiento.dominio.Vehiculo;
import ceiba.estacionamiento.dominio.Vigilante;

@RestController
@RequestMapping("/ceiba")
public class Parqueo {
	
	Parqueadero parqueadero = new Parqueadero();
	
	@Autowired
	Vigilante vigilante;
	
	@PostMapping("/prueba")
	public Vehiculo registrarVehiculo(@RequestBody Vehiculo vehiculo) {
		vigilante.registrarIngresoVehiculo(vehiculo, parqueadero);
		System.out.println(vehiculo.getPlaca());
		return vehiculo;
	    //return repositorioVehiculo.save(vehiculo);
	}
}

package ceiba.estacionamiento.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ceiba.estacionamiento.dominio.Parqueadero;
import ceiba.estacionamiento.dominio.Vehiculo;
import ceiba.estacionamiento.dominio.Vigilante;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders="*", maxAge = 3600)
@RestController
@RequestMapping("/ceiba")
public class ControladorParqueo {

	@Autowired
	Parqueadero parqueadero;
	
	@Autowired
	Vigilante vigilante;

    // Registrar ingreso de un vehiculo - POST
	@PostMapping("/vehiculos")
	public Vehiculo registrarVehiculo(@RequestBody Vehiculo vehiculo) {
		return vigilante.registrarIngresoVehiculo(vehiculo, parqueadero);		
	}
	
	// Registrar la salida de un vehiculo y cobrar
	@PostMapping("/vehiculos/salida/{placa}")
	public double registrarSalidaYCobrar(@PathVariable(value = "placa") String placa) {
		return vigilante.registrarSalidaVehiculo(placa, parqueadero);		
	}
	
	// Obtener todos los vehiculos - GET
	@GetMapping("/vehiculos")
	public List<Vehiculo> consultarVehiculos() {
	    return vigilante.consultarVehiculos();
	}
	
	// Obtener un solo vehiculo por placa - GET
	@GetMapping("/vehiculos/{id}")
	public Vehiculo consultarPorPlaca(@PathVariable(value = "id") String placa) {
		return vigilante.consultarVehiculoPorPlaca(placa);		
	}
}

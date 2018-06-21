package ceiba.CeibaEstacionamiento.controlador;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ceiba.CeibaEstacionamiento.dominio.Parqueadero;
import ceiba.CeibaEstacionamiento.dominio.Vehiculo;
import ceiba.CeibaEstacionamiento.dominio.Vigilante;
import ceiba.CeibaEstacionamiento.dominio.repositorio.RepositorioVehiculo;

@RestController
@RequestMapping("/ceiba")
public class ControladorParqueo {

	@Autowired
	Parqueadero parqueadero;
	
	@Autowired
	Vigilante vigilante;	

    // Registrar ingreso de un vehiculo - POST
	@PostMapping("/vehiculos")
	public String registrarVehiculo(@RequestBody Vehiculo vehiculo) {
		return vigilante.registrarIngresoVehiculo(vehiculo, parqueadero);		
	}
	
	// Obtener todos los vehiculos - GET
	/*@GetMapping("/vehiculos")
	public List<Vehiculo> obtenerVehiculos() {
	    return repositorioVehiculo.findAll();
	}

    // Obtener un solo vehiculo - GET
	@GetMapping("/vehiculos/{placa}")
	public Vehiculo obtenerVehiculo(@PathVariable(value = "placa") String placa) {
	    return repositorioVehiculo.findById(placa).orElse(null); 
	}

    // Registrar salida de un vehiculo - PUT
	@PutMapping("/vehiculos/{placa}")
	public Vehiculo updateNote(@PathVariable(value = "placa") String placa,
	                                        @Valid @RequestBody Vehiculo detallesVehiculo) {

	    Vehiculo vehiculo = repositorioVehiculo.findById(placa)
	            .orElse(null);

	    vehiculo.setEstado("Inactivo");

	    Vehiculo vehiculoActualizado = repositorioVehiculo.save(vehiculo);
	    return vehiculoActualizado;
	}*/
}

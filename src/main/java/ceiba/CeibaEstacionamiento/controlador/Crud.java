package ceiba.CeibaEstacionamiento.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ceiba.CeibaEstacionamiento.dominio.Parqueadero;
import ceiba.CeibaEstacionamiento.dominio.Vehiculo;
import ceiba.CeibaEstacionamiento.dominio.repositorio.RepositorioVehiculo;

@Service
@RestController
@RequestMapping("/ceiba")
public class Crud {
	
	@Autowired
    RepositorioVehiculo repositorioVehiculo;
	
	public Vehiculo registrarVehiculo(Vehiculo vehiculo) {
		System.out.println(vehiculo.getPlaca());
	    return repositorioVehiculo.save(vehiculo);
	}
	
	// Obtener todos los vehiculos - GET
	@GetMapping("/vehiculos")
	public List<Vehiculo> obtenerVehiculos() {
	    return repositorioVehiculo.findAll();
	}
	
	// Obtener un solo vehiculo - GET
	@GetMapping("/vehiculos/{tipo}")
	public List<Vehiculo> obtenerVehiculo(@PathVariable(value = "tipo") String tipo) {
	    return repositorioVehiculo.findByTipo(tipo);
	}
	
	// Registrar salida de un vehiculo - PUT
	@PutMapping("/vehiculos/{placa}")
	public Vehiculo updateNote(@PathVariable(value = "placa") String placa,
	                                        @RequestBody Vehiculo detallesVehiculo) {

	    Vehiculo vehiculo = repositorioVehiculo.findById(placa)
	            .orElse(null);

	    vehiculo.setEstado("Inactivo");

	    Vehiculo vehiculoActualizado = repositorioVehiculo.save(vehiculo);
	    return vehiculoActualizado;
	}
	
}

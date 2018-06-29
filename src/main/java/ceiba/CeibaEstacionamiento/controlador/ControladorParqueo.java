package ceiba.CeibaEstacionamiento.controlador;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import ceiba.CeibaEstacionamiento.dominio.Parqueadero;
import ceiba.CeibaEstacionamiento.dominio.Vehiculo;
import ceiba.CeibaEstacionamiento.dominio.Vigilante;
import ceiba.CeibaEstacionamiento.modelo.ModeloVehiculo;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
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
		/*List<ModeloVehiculo> lista = vigilante.consultarVehiculos();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String date = sdf.format(lista.get(0).getFechaIngreso());
		System.out.println("date= " + date);*/
	    return vigilante.consultarVehiculos();
	}

    // Obtener un solo vehiculo - GET
	/*@GetMapping("/vehiculos/{placa}")
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

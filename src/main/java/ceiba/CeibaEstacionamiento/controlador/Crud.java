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
import ceiba.CeibaEstacionamiento.modelo.ModeloVehiculo;

@Service
@RestController
@RequestMapping("/ceiba")
public class Crud {
	
	public static final String REGISTRO_EXITOSO = "Se registro el ingreso del vehiculo exitosamente";
	public static final String REGISTRO_FALLIDO = "Registro fallido. El vehiculo ya se encuentra en el parqueadero";
	
	@Autowired
    RepositorioVehiculo repositorioVehiculo;	
	
	public String registrarVehiculo(Vehiculo vehiculo) {
		ModeloVehiculo modeloVehiculo = new ModeloVehiculo(vehiculo.getPlaca(), vehiculo.getTipo(), vehiculo.getCilindraje(), 
				vehiculo.getEstado());
		System.out.println(vehiculo.getPlaca());
		
		if(obtenerVehiculoPorPlaca(modeloVehiculo.getPlaca())!=null){
			//return repositorioVehiculo.save(modeloVehiculo);			
			System.out.println();
			return REGISTRO_FALLIDO;
			//return "Vehiculo registrado";
		}else{
			//return "El vehiculo ya se encuentra en el parqueadero";
			System.out.println("Si pudo ingresar");
			repositorioVehiculo.save(modeloVehiculo);
			return REGISTRO_EXITOSO;
		}	    
	}
	
	// Obtener todos los vehiculos - GET
	@GetMapping("/vehiculos")
	public List<ModeloVehiculo> obtenerVehiculos() {
	    return repositorioVehiculo.findAll();
	}
	
	// Obtener un solo vehiculo por tipo - GET
	@GetMapping("/vehiculos/tipo/{tipo}")
	public List<ModeloVehiculo> obtenerVehiculoPorTipo(@PathVariable(value = "tipo") String tipo) {
	    return repositorioVehiculo.findByTipo(tipo);
	}
	
	// Obtener un solo vehiculo por placa - GET
	@GetMapping("/vehiculos/{id}")
	public ModeloVehiculo obtenerVehiculoPorPlaca(@PathVariable(value = "id") String placa) {
	    return repositorioVehiculo.findById(placa).orElse(null);
	}
	
	// Registrar salida de un vehiculo - PUT
	@PutMapping("/vehiculos/{placa}")
	public ModeloVehiculo registrarSalidaVehiculo(@PathVariable(value = "placa") String placa,
	                                        @RequestBody Vehiculo detallesVehiculo) {

	    ModeloVehiculo mVehiculo = repositorioVehiculo.findById(placa)
	            .orElse(null);

	    mVehiculo.setEstado("Inactivo");

	    ModeloVehiculo vehiculoActualizado = repositorioVehiculo.save(mVehiculo);
	    return vehiculoActualizado;
	}
	
}

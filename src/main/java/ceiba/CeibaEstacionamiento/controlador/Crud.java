package ceiba.CeibaEstacionamiento.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public static final String REGISTRO_FALLIDO = "Registro fallido. No hay celdas disponibles";
	public static final String VEHICULO_YA_INGRESADO = "Registro fallido. El vehiculo ya esta en el parqueadero";

	@Autowired
	RepositorioVehiculo repositorioVehiculo;

	public Vehiculo registrarVehiculo(Vehiculo vehiculo, Parqueadero parqueadero) {
		ModeloVehiculo modeloVehiculo = new ModeloVehiculo(vehiculo.getPlaca(), vehiculo.getTipo(),
				vehiculo.getCilindraje(), vehiculo.getEstado());
		ModeloVehiculo resultadoInsercion = null;
		System.out.println(vehiculo.getPlaca());

		if (obtenerVehiculoPorPlaca(modeloVehiculo.getPlaca()) != null) {
			System.out.println("ya existe vehiculo");
			return null;

		} else {

			// if(vehiculo.getTipo().equals("C")){
			// validarCeldasDisponiblesCarro(vehiculo, parqueadero,
			// modeloVehiculo);
			//
			// }else{
			// validarCeldasDisponiblesMoto(vehiculo, parqueadero,
			// modeloVehiculo);
			// }
			resultadoInsercion = repositorioVehiculo.save(modeloVehiculo);
			return convertirADominio(resultadoInsercion);
		}
	}

	public int obtenerCantidadCeldasDisponibles(String tipoVehiculo) {

		List<ModeloVehiculo> lista = obtenerListaActivos(tipoVehiculo);

		// System.out.println("Tamano de la lista: " +
		// obtenerVehiculoPorTipo(tipoVehiculo).size());
		int cantidad = 0;

		for (ModeloVehiculo listaFinal : lista) {
			// System.out.println(listaFinal);

			if (listaFinal.getEstado().equals("Activo")) {
				cantidad += 1;
			}
		}

		// System.out.println("Cantidad en crud: " + cantidad);
		return cantidad;

	}

	public List<ModeloVehiculo> obtenerListaActivos(String tipo) {

		return repositorioVehiculo.findByTipo(tipo);
	}

	// Obtener todos los vehiculos - GET
	@GetMapping("/vehiculos")
	public List<ModeloVehiculo> obtenerVehiculos() {
		return repositorioVehiculo.findAll();
	}

	// Obtener un solo vehiculo por tipo - GET
	@GetMapping("/vehiculos/tipo/{tipo}")
	public List<ModeloVehiculo> obtenerVehiculoPorTipo(@PathVariable(value = "tipo") String tipo) {
		// System.out.println("Tamano de la lista: " +
		// obtenerVehiculoPorTipo(tipo).size());
		// System.out.println(repositorioVehiculo.findByTipo(tipo).size());
		List<ModeloVehiculo> lista = repositorioVehiculo.findByTipo(tipo);
		System.out.println("-------------------------");
		int cantidad = 0;
		for (ModeloVehiculo listaFinal : lista) {

			System.out.println(listaFinal.getPlaca());

			if (listaFinal.getEstado().equals("Activo")) {
				cantidad += 1;
			}

		}
		System.out.println("Cantidad = " + cantidad);
		System.out.println("-------------------------");
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

		ModeloVehiculo mVehiculo = repositorioVehiculo.findById(placa).orElse(null);

		mVehiculo.setEstado("Inactivo");

		ModeloVehiculo vehiculoActualizado = repositorioVehiculo.save(mVehiculo);
		return vehiculoActualizado;
	}

	public boolean validarCeldasDisponiblesMoto(Vehiculo vehiculo, Parqueadero parqueadero) {
		int ocupadas = obtenerCantidadCeldasDisponibles(vehiculo.getTipo());
		System.out.println(
				"ocupadas antes de ingresar: " + ocupadas + "   -----  " + parqueadero.getCeldasDisponiblesMoto());

		int totalDisponibles;
		if (parqueadero.getCeldasDisponiblesMoto() == 10) {
			totalDisponibles = (parqueadero.getCeldasDisponiblesMoto() - ocupadas);
		} else {
			totalDisponibles = parqueadero.getCeldasDisponiblesMoto();
		}
		actualizarCeldasDisponibles(totalDisponibles, vehiculo.getTipo(), parqueadero);
		return totalDisponibles > 0;
	}

	public boolean validarCeldasDisponiblesCarro(Vehiculo vehiculo, Parqueadero parqueadero) {
		int ocupadas = obtenerCantidadCeldasDisponibles(vehiculo.getTipo());
		System.out.println(
				"ocupadas antes de ingresar: " + ocupadas + "   -----  " + parqueadero.getCeldasDisponiblesCarro());

		int totalDisponibles;
		if (parqueadero.getCeldasDisponiblesCarro() == 10) {
			totalDisponibles = (parqueadero.getCeldasDisponiblesCarro() - ocupadas);
		} else {
			totalDisponibles = parqueadero.getCeldasDisponiblesCarro();
		}
		actualizarCeldasDisponibles(totalDisponibles, vehiculo.getTipo(), parqueadero);
		return totalDisponibles > 0;
	}

	public void actualizarCeldasDisponibles(int totalDisponibles, String tipoVehiculo, Parqueadero parqueadero) {
		if (totalDisponibles > 0) {
			if (tipoVehiculo.equals("C")) {
				parqueadero.setCeldasDisponiblesCarro(totalDisponibles - 1);
			} else {
				parqueadero.setCeldasDisponiblesMoto(totalDisponibles - 1);
			}
		}
	}

	public Vehiculo convertirADominio(ModeloVehiculo modeloVehiculo) {
		Vehiculo vehiculo = null;
		if (modeloVehiculo != null) {
			vehiculo = new Vehiculo(modeloVehiculo.getPlaca(), modeloVehiculo.getTipo(),
					modeloVehiculo.getCilindraje());
		}
		return vehiculo;
	}

}

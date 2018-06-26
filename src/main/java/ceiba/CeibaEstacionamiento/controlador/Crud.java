package ceiba.CeibaEstacionamiento.controlador;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
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

		if(obtenerVehiculoPorPlaca(modeloVehiculo.getPlaca()) != null) {
			
			ModeloVehiculo vehiculoActualizado = obtenerVehiculoPorPlaca(modeloVehiculo.getPlaca());
			if(vehiculoActualizado.getEstado().equals("Inactivo")){
				vehiculoActualizado.setEstado("Activo");
				DateTime dt = new DateTime();
				vehiculoActualizado.setFechaIngreso(dt.toDate());
				resultadoInsercion = repositorioVehiculo.save(vehiculoActualizado);
				return convertirADominio(resultadoInsercion);
			} else {
				System.out.println("ya existe vehiculo");
				return null;
			}			
		} else {
			resultadoInsercion = repositorioVehiculo.save(modeloVehiculo);
			return convertirADominio(resultadoInsercion);
		}
	}
	
	public Vehiculo registrarSalida(String placa, Parqueadero parqueadero) {
		
		ModeloVehiculo resultadoSalida = null;
		
		if(obtenerVehiculoPorPlaca(placa) != null) {
			ModeloVehiculo modeloVehiculo = obtenerVehiculoPorPlaca(placa);
			if(modeloVehiculo.getEstado().equals("Inactivo")){
				System.out.println("El vehiculo no se encuentra en el parqueadero");
				return null;				
			} else {
				System.out.println("Se registro la salida del vehiculo");				
				modeloVehiculo.setEstado("Inactivo");
				resultadoSalida = repositorioVehiculo.save(modeloVehiculo);				
				
				if(modeloVehiculo.getPlaca().equals("C")) {
					parqueadero.setCeldasDisponiblesCarro(parqueadero.getCeldasDisponiblesCarro()+1);
				} else {
					parqueadero.setCeldasDisponiblesMoto(parqueadero.getCeldasDisponiblesMoto()+1);
				}				
				System.out.println("vehiculo salida = " + resultadoSalida.getPlaca());
				System.out.println("vehiculo salida fecha bd = " + resultadoSalida.getFechaIngreso());
				return convertirADominio(resultadoSalida);			
			}			
		} else {
			return null;
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
		List<ModeloVehiculo> lista = repositorioVehiculo.findByTipo(tipo);
		int cantidad = 0;
		for (ModeloVehiculo listaFinal : lista) {			
			if (listaFinal.getEstado().equals("Activo")) {
				cantidad += 1;
			}
		}
		return repositorioVehiculo.findByTipo(tipo);
	}

	// Obtener un solo vehiculo por placa - GET
	@GetMapping("/vehiculos/{id}")
	public ModeloVehiculo obtenerVehiculoPorPlaca(@PathVariable(value = "id") String placa) {
		return repositorioVehiculo.findById(placa).orElse(null);
	}

	// Registrar salida de un vehiculo - PUT
	/*@PutMapping("/vehiculos/{placa}")
	public ModeloVehiculo registrarSalidaVehiculo(@PathVariable(value = "placa") String placa,
			@RequestBody Vehiculo detallesVehiculo) {

		ModeloVehiculo mVehiculo = repositorioVehiculo.findById(placa).orElse(null);

		mVehiculo.setEstado("Inactivo");

		ModeloVehiculo vehiculoActualizado = repositorioVehiculo.save(mVehiculo);
		return vehiculoActualizado;
	}*/
	
	/*@PutMapping("vehiculos/salida/{placa}")
	public ModeloVehiculo registrarSalidaVehiculoYCobrar(@PathVariable(value = "placa") String placa,
			@RequestBody Vehiculo detallesVehiculo){
		
		ModeloVehiculo mVehiculo = repositorioVehiculo.findById(placa).orElse(null);

		mVehiculo.setEstado("Inactivo");

		ModeloVehiculo vehiculoActualizado = repositorioVehiculo.save(mVehiculo);
		return vehiculoActualizado;
		
		
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.mmm");
		
		Date mili = listaFinal.getFechaIngreso();
		System.out.println("Tiempo bd en Date: " + mili);
		System.out.println("Tiempo bd en mili: " + mili.getTime());
		System.out.println("Tiempo bd en mili: " + sdf.format(mili.getTime()));

		Date date = new Date();
		System.out.println("Tiempo nuevo en Date: " + date);
		String otro = sdf.format(date.getTime());
		System.out.println("Tiempo nuevo en mili: " + sdf.format(date.getTime()));
		Timestamp timpestamp = new Timestamp(date.getTime());
		
	}*/

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
					modeloVehiculo.getCilindraje(), modeloVehiculo.getEstado(), modeloVehiculo.getFechaIngreso());
		}
		return vehiculo;
	}

}
package ceiba.estacionamiento.persistencia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import ceiba.estacionamiento.dominio.Celda;
import ceiba.estacionamiento.dominio.CeldaFactory;
import ceiba.estacionamiento.dominio.Vehiculo;
import ceiba.estacionamiento.modelo.ModeloVehiculo;
import ceiba.estacionamiento.repositorio.RepositorioVehiculo;

@Service
public class Crud {

	public static final String INACTIVO = "Inactivo";
	public static final String ACTIVO = "Activo";

	@Autowired
	RepositorioVehiculo repositorioVehiculo;

	public Vehiculo registrarVehiculo(Vehiculo vehiculo) {
		ModeloVehiculo modeloVehiculo = new ModeloVehiculo(vehiculo.getPlaca(), vehiculo.getTipo(),
				vehiculo.getCilindraje(), vehiculo.getEstado());
		ModeloVehiculo resultadoInsercion = null;

		if(obtenerVehiculoPorPlaca(modeloVehiculo.getPlaca()) != null) {
			
			ModeloVehiculo vehiculoActualizado = obtenerVehiculoPorPlaca(modeloVehiculo.getPlaca());
			if(vehiculoActualizado.getEstado().equals(INACTIVO)){
				vehiculoActualizado.setEstado(ACTIVO);
				DateTime dt = new DateTime();
				vehiculoActualizado.setFechaIngreso(dt.toDate());
				resultadoInsercion = repositorioVehiculo.save(vehiculoActualizado);
				return convertirADominio(resultadoInsercion);
			} else {
				return null;
			}			
		} else {
			resultadoInsercion = repositorioVehiculo.save(modeloVehiculo);
			return convertirADominio(resultadoInsercion);
		}
	}
	
	public Vehiculo registrarSalida(String placa) {	
		ModeloVehiculo resultadoSalida = null;
		ModeloVehiculo modeloVehiculo = obtenerVehiculoPorPlaca(placa);
		if (modeloVehiculo != null) {
			CeldaFactory celdaFactory = new CeldaFactory();
			Celda celda = celdaFactory.obtenerCeldas(modeloVehiculo.getTipo());
			if(modeloVehiculo.getEstado().equals(INACTIVO)){
				return null;
			} else {	
				modeloVehiculo.setEstado(INACTIVO);
				resultadoSalida = repositorioVehiculo.save(modeloVehiculo);
				aumentarCeldasDisponibles(modeloVehiculo.getTipo(), celda);	
				return convertirADominio(resultadoSalida);
			}	
		} else {
			return null;
		}
	}
	
	public void aumentarCeldasDisponibles(String tipo, Celda celda) {
		if("C".equals(tipo)) {
			celda.setCeldasDisponiblesCarro(celda.getCeldasDisponiblesCarro()+1);
		} else {
			celda.setCeldasDisponiblesMoto(celda.getCeldasDisponiblesMoto()+1);
		}
	}
	
	public ModeloVehiculo obtenerVehiculoPorPlaca(@PathVariable(value = "id") String placa) {
		return repositorioVehiculo.findById(placa).orElse(null);		
	}
	
	public List<Vehiculo> consultarVehiculosActivos(){
		List<ModeloVehiculo> lista = repositorioVehiculo.findAll();
		List<Vehiculo> listaVehiculos = new ArrayList<>();
		Vehiculo vehiculo = null;
		if(!lista.isEmpty()) {
			for(int i=0;i<lista.size();i++){
				Date fechaIngreso = lista.get(i).getFechaIngreso();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
				String fecha = sdf.format(fechaIngreso);
				vehiculo = new Vehiculo(lista.get(i).getPlaca(), lista.get(i).getTipo(), 
						lista.get(i).getCilindraje(), lista.get(i).getEstado(), lista.get(i).getFechaIngreso(), fecha);
				if(validarEstado(vehiculo) != null){
					listaVehiculos.add(vehiculo);
				}								
			}
		}
		return listaVehiculos;
	}
	
	public Vehiculo validarEstado(Vehiculo vehiculo){
		if(vehiculo.getEstado().equals(ACTIVO)){
			return vehiculo;
		} else {
			return null;
		}
	}	
	
	public Vehiculo consultarPorPlaca(@PathVariable(value = "id") String placa) {
		ModeloVehiculo resultadoBusqueda = repositorioVehiculo.findById(placa).orElse(null);
		if(resultadoBusqueda.getEstado().equals(INACTIVO)){
			return null;
		}
		return convertirADominio(resultadoBusqueda);		
	}	
	
	public boolean validarCeldasDisponibles(String tipo) {
		int ocupadas = obtenerCantidadCeldasOcupadas(tipo);		
		CeldaFactory celdaFactory = new CeldaFactory();
		Celda celda = celdaFactory.obtenerCeldas(tipo);
		int totalDisponibles;
		if("C".equals(tipo)){
			celda.setCeldasDisponiblesCarro(celda.getCeldasDisponiblesCarro() - ocupadas);
			totalDisponibles = celda.getCeldasDisponiblesCarro();
		} else {
			celda.setCeldasDisponiblesMoto(celda.getCeldasDisponiblesMoto() - ocupadas);
			totalDisponibles = celda.getCeldasDisponiblesMoto();
		}
		disminuirCeldasDisponibles(totalDisponibles, tipo, celda);
		return totalDisponibles > 0;
	}
	
	public void disminuirCeldasDisponibles(int totalDisponibles, String tipoVehiculo, Celda celda) {
		if (totalDisponibles > 0) {
			if ("C".equals(tipoVehiculo)) {
				celda.setCeldasDisponiblesCarro(totalDisponibles - 1);
			} else if ("M".equals(tipoVehiculo)) {
				celda.setCeldasDisponiblesMoto(totalDisponibles - 1);
			}
		}
	}
	
	public int obtenerCantidadCeldasOcupadas(String tipoVehiculo) {
		List<ModeloVehiculo> lista = repositorioVehiculo.findByTipo(tipoVehiculo);
		int cantidad = 0;
		for (ModeloVehiculo listaFinal : lista) {
			if (listaFinal.getEstado().equals(ACTIVO)) {
				cantidad += 1;
			}
		}
		return cantidad;
	}	

	public Vehiculo convertirADominio(ModeloVehiculo modeloVehiculo) {
		Date fechaIngreso = modeloVehiculo.getFechaIngreso();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String fecha = sdf.format(fechaIngreso);		
		return new Vehiculo(modeloVehiculo.getPlaca(), modeloVehiculo.getTipo(),
				modeloVehiculo.getCilindraje(), modeloVehiculo.getEstado(), modeloVehiculo.getFechaIngreso(), fecha);
	}
}
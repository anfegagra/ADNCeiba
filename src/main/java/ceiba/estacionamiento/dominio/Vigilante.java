package ceiba.estacionamiento.dominio;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ceiba.estacionamiento.persistencia.Crud;

@Service
public class Vigilante {

	public static final String VALIDACION_PLACA_CARRO = "Verifique la placa del carro";
	public static final String VALIDACION_CAMPOS_MOTO = "Verifique el cilindraje o placa de la moto";
	public static final String VALIDACION_PLACA_MOTO = "Verifique la placa de la moto";

	@Autowired
	Crud crud;
	
	@Autowired
	Fecha fecha;
		
	@Autowired
	Cobro cobro;
	
	public Vigilante(){
		
	}
	
	public Vigilante(Fecha fecha){
		this.fecha = fecha;
	}
	
	public Vigilante(Fecha fecha, Crud crud){
		this.fecha = fecha;
		this.crud = crud;
	}
	
	public Vigilante(Crud crud){
		this.crud = crud;
	}
	
	public Vigilante(Crud crud, Fecha fecha, Cobro cobro){
		this.crud = crud;
		this.fecha = fecha;
		this.cobro = cobro;
	}

	public Vehiculo registrarIngresoVehiculo(Vehiculo vehiculo) {
		Vehiculo vehiculoAIngresar = null;
		if ("C".equals(vehiculo.getTipo())){
			vehiculoAIngresar = new Carro(vehiculo.getPlaca(), vehiculo.getTipo(), vehiculo.getCilindraje());
		} else {
			vehiculoAIngresar = new Moto(vehiculo.getPlaca(), vehiculo.getTipo(), vehiculo.getCilindraje());
		}
		return hacerValidaciones(vehiculoAIngresar);
	}

	public Vehiculo hacerValidaciones(Vehiculo vehiculo) {
		String placaActualizada = vehiculo.getPlaca().toUpperCase();		
		vehiculo.setPlaca(placaActualizada);		
		if (esPlacaValida(vehiculo.getPlaca()) && crud.validarCeldasDisponibles(vehiculo.getTipo())){			
			return crud.registrarVehiculo(vehiculo);			
		}
		return null;
	}
	
	public boolean esPlacaValida(String placa) {
		String primeraLetraPlaca = placa.substring(0, 1);
		int dia = fecha.obtenerDia(); 
		if ("A".equals(primeraLetraPlaca)) {			
			return (dia == 7 || dia == 1);
		} else {
			return true;
		}		
	}
	
	public double registrarSalidaVehiculo(String placa){
		Vehiculo vehiculoASalir = crud.registrarSalida(placa);
		double totalAPagar = 0;
		if(vehiculoASalir != null) {
			Date fechaBD = vehiculoASalir.getFechaIngreso();
			DateTime fechaInicial = fecha.obtenerFechaEntrada(fechaBD);
			DateTime fechaFinal = fecha.obtenerFechaActual();
			Duration duracionParqueo = fecha.obtenerDuracionParqueo(fechaInicial, fechaFinal);
			totalAPagar = cobro.generarCobro(duracionParqueo, vehiculoASalir.getTipo(), vehiculoASalir.getCilindraje());
			return totalAPagar;
		} else {
			return totalAPagar;
		}
	}
	
	public List<Vehiculo> consultarVehiculos(){
		return crud.consultarVehiculosActivos();
	}
	
	public Vehiculo consultarVehiculoPorPlaca(String placa){
		return crud.consultarPorPlaca(placa);
	}
	
}
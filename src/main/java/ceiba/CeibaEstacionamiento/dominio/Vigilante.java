package ceiba.CeibaEstacionamiento.dominio;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ceiba.CeibaEstacionamiento.controlador.Crud;

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
	Validacion validacion;
	
	@Autowired
	Cobro cobro;
	
	public Vigilante(){
		
	}
	
	public Vigilante(Validacion validacion){
		this.validacion = validacion;
	}
	
	public Vigilante(Validacion validacion, Crud crud){
		this.validacion = validacion;
		this.crud = crud;
	}

	public Vehiculo registrarIngresoVehiculo(Vehiculo vehiculo, Parqueadero parqueadero) {
		Vehiculo vehiculoAIngresar = null;
		if (vehiculo.getTipo().equals("C")) {
			System.out.println("Nuevo carro");
			vehiculoAIngresar = new Carro(vehiculo.getPlaca(), vehiculo.getTipo(), vehiculo.getCilindraje());
		} else {
			System.out.println("Nueva moto");
			vehiculoAIngresar = new Moto(vehiculo.getPlaca(), vehiculo.getTipo(), vehiculo.getCilindraje());
		}
		return hacerValidaciones(vehiculoAIngresar, parqueadero);
	}

	public Vehiculo hacerValidaciones(Vehiculo v, Parqueadero p) {
		Vehiculo vehiculo = null;
		String letras = v.getPlaca().substring(0,3).toUpperCase();
		String numeros = v.getPlaca().substring(3,6);
		String placaActualizada = letras+numeros;
		System.out.println(v.getPlaca());
		v.setPlaca(placaActualizada);
		System.out.println(v.getPlaca());
		boolean res = validacion.esPlacaValida(v.getPlaca());
		if (res){
			if (v.getTipo().equals("C") && crud.validarCeldasDisponiblesCarro(v, p)) {
				System.out.println("entro");
				vehiculo = crud.registrarVehiculo(v, p);

			} else if (crud.validarCeldasDisponiblesMoto(v, p)) {
				vehiculo = crud.registrarVehiculo(v, p);
			}
		}
		return vehiculo;
	}
	
	public int registrarSalidaVehiculo(String placa, Parqueadero parqueadero){
		Vehiculo vehiculoASalir = crud.registrarSalida(placa, parqueadero);
		int totalAPagar = 0;
		System.out.println("Vehiculo a salir = " + vehiculoASalir.getPlaca());
		if(vehiculoASalir != null) {
			System.out.println("Entro aca");
			//comparar fechas para cobrar
			Date fechaBD = vehiculoASalir.getFechaIngreso();
			DateTime fechaInicial = new DateTime(fechaBD);
			DateTime fechaFinal = new DateTime();			
			int diferenciaDias = Days.daysBetween(fechaInicial, fechaFinal).getDays();
			int diferenciaHoras = Hours.hoursBetween(fechaInicial, fechaFinal).getHours();
			int diferenciaMinutos = Minutes.minutesBetween(fechaInicial, fechaFinal).getMinutes();
			//Hours hours = Hours.hoursBetween(start, end);
			//Minutes minu = Minutes.minutesBetween(start, end);
			//Seconds secs = Seconds.secondsBetween(start, end);
			//int nroDiasEntreFechas = diff.getDays();
			System.out.println("Diferencia dias = " + diferenciaDias);
			System.out.println("Fecha ingreso = " + fechaInicial);
			
			if(vehiculoASalir.getTipo().equals("C")) {
				if(diferenciaDias < 1) {	
					if(diferenciaHoras == 8 && fecha.obtenerMinutos()>0) {						
						totalAPagar = cobro.getValorDiaCarro();
					} else if(diferenciaHoras==0) {
						totalAPagar = (diferenciaHoras+1)*cobro.getValorHoraCarro();						
					} else {
						totalAPagar = diferenciaHoras*cobro.getValorHoraCarro();
					}
				} else {
					totalAPagar = diferenciaDias*cobro.getValorDiaCarro();
				}
				
			} else if(vehiculoASalir.getTipo().equals("M") && vehiculoASalir.getCilindraje()>500){
				if(diferenciaDias < 1) {
					if(diferenciaHoras == 8 && fecha.obtenerMinutos()>0) {						
						totalAPagar = cobro.getValorDiaMoto()+2000;
					} else if (diferenciaHoras==0) {
						totalAPagar = (diferenciaHoras+1)*cobro.getValorHoraMoto()+2000;						
					}
				} else {
					totalAPagar = diferenciaDias*cobro.getValorDiaMoto()+2000;
				}
			} else {
				if(diferenciaDias < 1) {					
					if(diferenciaHoras == 8 && fecha.obtenerMinutos()>0) {						
						totalAPagar = cobro.getValorDiaMoto();
					} else if (diferenciaHoras==0){
						totalAPagar = (diferenciaHoras+1)*cobro.getValorHoraMoto();						
					}
				} else {
					totalAPagar = diferenciaDias*cobro.getValorDiaMoto();
				}
			}			
			
		}
		return totalAPagar;
	}

	public void cobrar() {

	}

	/*public boolean esPlacaValida(String placa) {
		String primeraLetraPlaca = placa.substring(0, 1);
		//Calendar calendar = Calendar.getInstance();
		//DateTime date = new DateTime();
		//int dia = calendar.get(Calendar.DAY_OF_WEEK);
		//int dia = date.getDayOfWeek();
		int dia = fecha.obtenerDia();
		System.out.println(dia);
		if (primeraLetraPlaca.equals("A")) {
			if (dia == 7 || dia == 1) {
				return true;
			}
			return false;
		} else {
			return true;
		}
	}*/

}

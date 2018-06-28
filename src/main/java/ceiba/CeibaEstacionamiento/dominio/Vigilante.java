package ceiba.CeibaEstacionamiento.dominio;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ceiba.CeibaEstacionamiento.controlador.Crud;
import ceiba.CeibaEstacionamiento.modelo.ModeloVehiculo;

@Service
public class Vigilante {

	public static final String VALIDACION_PLACA_CARRO = "Verifique la placa del carro";
	public static final String VALIDACION_CAMPOS_MOTO = "Verifique el cilindraje o placa de la moto";
	public static final String VALIDACION_PLACA_MOTO = "Verifique la placa de la moto";
	//public static final int MINUTOS_EN_NUEVE_HORAS = 540;
	//public static final int MINUTOS_DIA = 1440;

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
	
	public Vigilante(Crud crud){
		this.crud = crud;
	}
	
	public Vigilante(Crud crud, Fecha fecha, Cobro cobro){
		this.crud = crud;
		this.fecha = fecha;
		this.cobro = cobro;
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
		String placaActualizada = v.getPlaca().toUpperCase();
		System.out.println(v.getPlaca());
		v.setPlaca(placaActualizada);
		System.out.println(v.getPlaca());
		//boolean res = validacion.esPlacaValida(v.getPlaca());
		if (validacion.esPlacaValida(v.getPlaca())){
			if (v.getTipo().equals("C") && crud.validarCeldasDisponiblesCarro(v, p)) {
				System.out.println("entro");
				vehiculo = crud.registrarVehiculo(v, p);

			} else if (crud.validarCeldasDisponiblesMoto(v, p)) {
				vehiculo = crud.registrarVehiculo(v, p);
			}
		}
		return vehiculo;
	}
	
	/*public String convertirPlacaAMayusculas(Vehiculo vehiculo){
		String placa = "";
		if(vehiculo.getTipo().equals("C")){
			String letras = vehiculo.getPlaca().substring(0,3).toUpperCase();
			String numeros = vehiculo.getPlaca().substring(3,6);
			placa = letras+numeros;
		}else{
			String primerasTresLetras = vehiculo.getPlaca().substring(0,3).toUpperCase();
			String numeros = vehiculo.getPlaca().substring(3,5);
			String ultimaLetra = vehiculo.getPlaca().substring(5,6).toUpperCase();
			placa = primerasTresLetras+numeros+ultimaLetra;
		}
		return placa;
	}*/
	
	public double registrarSalidaVehiculo(String placa, Parqueadero parqueadero){
		Vehiculo vehiculoASalir = crud.registrarSalida(placa, parqueadero);
		double totalAPagar = 0;
		if(vehiculoASalir != null) {
			Date fechaBD = vehiculoASalir.getFechaIngreso();
			DateTime fechaInicial = fecha.obtenerFechaEntrada(fechaBD);
			DateTime fechaFinal = fecha.obtenerFechaActual();
			Duration duracionParqueo = fecha.obtenerDuracionParqueo(fechaInicial, fechaFinal);
			System.out.println("Duracion parqueo = " + duracionParqueo);
			//System.out.println("cantidad minutos: " + duracionParqueo.getStandardMinutes());
			if(vehiculoASalir.getTipo().equals("C")) {
				totalAPagar = cobro.registrarSalidaCarro(duracionParqueo);
			} else {
				totalAPagar = cobro.registrarSalidaMoto(duracionParqueo);
				totalAPagar += cobro.calcularCobroCilindraje(vehiculoASalir);
			}
			return totalAPagar;
		} else {
			return totalAPagar;
		}
	}
	
	public List<Vehiculo> consultarVehiculos(){
		return crud.consultarVehiculos();
	}

	/*public double registrarSalidaCarro(Duration duracionParqueo) {
		if(duracionParqueo.getStandardMinutes() < MINUTOS_EN_NUEVE_HORAS){
			System.out.println("Horas < 9");
			return calcularCobroMenorANueveHorasCarro(duracionParqueo);
		} else {
			long cantidadDias = duracionParqueo.getStandardDays();
			System.out.println("cantidad de dias: " + cantidadDias);
			if(cantidadDias > 0) {			
				System.out.println("Dias > 0");
				return calcularCobroDiasMayorACeroCarro(duracionParqueo, cantidadDias);			
			} else {
				System.out.println("Dias = 0 y horas >= 9, entonces cobro el dia");
				return cobro.getValorDiaCarro();
			}
		}
	}*/
	
	/*public double calcularCobroMenorANueveHorasCarro(Duration duracionParqueo){
		int cantidadHoras = (int)(Math.ceil((float)duracionParqueo.getStandardMinutes()/60));
		System.out.println(cantidadHoras);
		return cobro.getValorHoraCarro()*cantidadHoras;
	}*/
	
	/*public double calcularCobroDiasMayorACeroCarro(Duration duracionParqueo, long cantidadDias){
		long cantidadMinutosUltimoDia = duracionParqueo.getStandardMinutes()-cantidadDias*MINUTOS_DIA;
		int cantidadHorasUlitmoDia = (int)(Math.ceil((float)(duracionParqueo.getStandardMinutes()-cantidadDias*MINUTOS_DIA)/60));
		if(cantidadMinutosUltimoDia >= MINUTOS_EN_NUEVE_HORAS){
			cantidadHorasUlitmoDia = 0;
			cantidadDias = cantidadDias+1;							
		}
		return cobro.getValorDiaCarro()*cantidadDias + cobro.getValorHoraCarro()*cantidadHorasUlitmoDia;	
	}*/
	
	/*public double registrarSalidaMoto(Duration duracionParqueo){	
		if(duracionParqueo.getStandardMinutes() < MINUTOS_EN_NUEVE_HORAS){			
			return calcularCobroMenorANueveHorasMoto(duracionParqueo);
		} else {
			long cantidadDias= duracionParqueo.getStandardDays();
			if(cantidadDias > 0) {		
				System.out.println("Dias > 0");
				return calcularCobroDiaMayorACeroMoto(duracionParqueo, cantidadDias);
			} else {
				System.out.println("Dias = 0, entonces cobro el dia");
				return cobro.getValorDiaMoto();		
			}
		}		
	}*/
	
	/*public double calcularCobroMenorANueveHorasMoto(Duration duracionParqueo){
		int cantidadHoras = (int)(Math.ceil((float)duracionParqueo.getStandardMinutes()/60));
		return cobro.getValorHoraMoto()*cantidadHoras;
	}*/
	
	/*public double calcularCobroDiaMayorACeroMoto(Duration duracionParqueo, long cantidadDias){
		long cantidadMinutosUltimoDia = duracionParqueo.getStandardMinutes()-cantidadDias*MINUTOS_DIA;
		int cantidadHorasUlitmoDia = (int)(Math.ceil((float)(duracionParqueo.getStandardMinutes()-cantidadDias*MINUTOS_DIA)/60));
		if(cantidadMinutosUltimoDia >= MINUTOS_EN_NUEVE_HORAS){
			cantidadDias = cantidadDias+1;
			cantidadHorasUlitmoDia = 0;
		}
		return cobro.getValorDiaMoto()*cantidadDias + cobro.getValorHoraMoto()*cantidadHorasUlitmoDia;
	}*/
	
	/*public double calcularCobroCilindraje(Vehiculo vehiculoASalir){
		if(vehiculoASalir.getCilindraje() > 500){
			return cobro.getValorAdicionalMoto();
		}else {
			return 0;
		}
	}*/	
	
}

package ceiba.CeibaEstacionamiento.dominio;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ceiba.CeibaEstacionamiento.controlador.Crud;

@Service
public class Vigilante {

	public static final String VALIDACION_PLACA_CARRO = "Verifique la placa del carro";
	public static final String VALIDACION_CAMPOS_MOTO = "Verifique el cilindraje o placa de la moto";
	public static final String VALIDACION_PLACA_MOTO = "Verifique la placa de la moto";
	public static final int MINUTOS_EN_NUEVE_HORAS = 540;
	public static final int MINUTOS_DIA = 1440;

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
	
	public double registrarSalidaVehiculo(String placa, Parqueadero parqueadero){
		Vehiculo vehiculoASalir = crud.registrarSalida(placa, parqueadero);
		double totalAPagar = 0;
		if(vehiculoASalir != null) {
			Date fechaBD = vehiculoASalir.getFechaIngreso();
			DateTime fechaInicial = fecha.obtenerFechaEntrada(fechaBD);
			DateTime fechaFinal = fecha.obtenerFechaActual();
			Duration duracionParqueo = fecha.obtenerDuracionParqueo(fechaInicial, fechaFinal);
			System.out.println("cantidad minutos: " + duracionParqueo.getStandardMinutes());
			
			if(vehiculoASalir.getTipo().equals("C")) {
				if(duracionParqueo.getStandardMinutes() < MINUTOS_EN_NUEVE_HORAS){
					System.out.println("Horas < 9");
					int cantidadHoras =(int)(Math.ceil((float)duracionParqueo.getStandardMinutes()/60));
					totalAPagar = cobro.getValorHoraCarro()*cantidadHoras;
				} else {
					Long cantidadDias = duracionParqueo.getStandardDays();
					System.out.println("cantidad de dias: " + cantidadDias);
					if(cantidadDias > 0) {					
						System.out.println("Dias > 0");
						Long cantidadMinutosUltimoDia = duracionParqueo.getStandardMinutes()-cantidadDias*MINUTOS_DIA;
						int cantidadHorasUlitmoDia = (int)(Math.ceil((float)(duracionParqueo.getStandardMinutes()-cantidadDias*MINUTOS_DIA)/60));
						if(cantidadMinutosUltimoDia >= MINUTOS_EN_NUEVE_HORAS){
							cantidadHorasUlitmoDia = 0;
							cantidadDias = cantidadDias+1;							
						}
						totalAPagar = cobro.getValorDiaCarro()*cantidadDias + cobro.getValorHoraCarro()*cantidadHorasUlitmoDia;						
					} else {
						System.out.println("Dias = 0, entonces cobro el dia");
						totalAPagar = cobro.getValorDiaCarro();
					}
				}		
			} else {
				//vehiculoASalir.getTipo().equals("M") && vehiculoASalir.getCilindraje()>500
				if(duracionParqueo.getStandardMinutes() < MINUTOS_EN_NUEVE_HORAS){
					int cantidadHoras = (int)(Math.ceil((float)duracionParqueo.getStandardMinutes()/60));
					totalAPagar = cobro.getValorHoraMoto()*cantidadHoras;
				} else {
					Long cantidadDias= duracionParqueo.getStandardDays();
					if(cantidadDias == 0) {
						System.out.println("Dias = 0");
						totalAPagar = cobro.getValorDiaMoto();
					} else {
						System.out.println("Dias > 0");
						Long cantidadMinutosUltimoDia = duracionParqueo.getStandardMinutes()-cantidadDias*MINUTOS_DIA;
						int cantidadHorasUlitmoDia = (int)(Math.ceil((float)(duracionParqueo.getStandardMinutes()-cantidadDias*MINUTOS_DIA)/60));
						if(cantidadMinutosUltimoDia >= MINUTOS_EN_NUEVE_HORAS){
							cantidadDias = cantidadDias+1;
							cantidadHorasUlitmoDia = 0;
						}
						if(vehiculoASalir.getCilindraje() > 500){
							totalAPagar = cobro.getValorDiaMoto()*cantidadDias + cobro.getValorHoraMoto()*cantidadHorasUlitmoDia + 
									(double)cobro.getValorAdicionalMoto();
						}else {
							totalAPagar = cobro.getValorDiaMoto()*cantidadDias + cobro.getValorHoraMoto()*cantidadHorasUlitmoDia;
						}						
					}
				}
			}	
			return totalAPagar;
		} else {
			return totalAPagar;
		}
	}

	public void cobrar() {

	}
}

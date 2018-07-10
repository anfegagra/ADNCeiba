package ceiba.estacionamiento.dominio;

import org.joda.time.Duration;
import org.springframework.stereotype.Service;

@Service
public class Cobro {
	
	public static final int MINUTOS_EN_NUEVE_HORAS = 540;
	public static final int MINUTOS_DIA = 1440;
	
	public Cobro(){
		// Constructor que requiere springboot
	}
	
	public double generarCobro(Duration duracionParqueo, String tipo, int cilindraje) {
		TarifaFactory tarifaFactory = new TarifaFactory();
		Tarifa tarifa = tarifaFactory.obtenerTarifa(tipo, cilindraje);
		if(obtenerMinutos(duracionParqueo) < MINUTOS_EN_NUEVE_HORAS){
			return calcularCobroMenorANueveHoras(duracionParqueo, tarifa);
		} else {
			long cantidadDias = obtenerDias(duracionParqueo);
			if(cantidadDias > 0) {
				return calcularCobroDiasMayorACero(duracionParqueo, cantidadDias, tarifa);			
			} else {				
				return tarifa.getValorDia();
			}
		}
	}
	
	public double calcularCobroMenorANueveHoras(Duration duracionParqueo, Tarifa tarifa){		
		int cantidadHoras = (int)(Math.ceil((float)obtenerMinutos(duracionParqueo)/60));		
		return (double)tarifa.getValorHora()*cantidadHoras + tarifa.getValorAdicional();
	}
	
	public double calcularCobroDiasMayorACero(Duration duracionParqueo, long cantidadDias, Tarifa tarifa){		
		int cantidadHorasUlitmoDia = (int)(Math.ceil((float)(obtenerMinutos(duracionParqueo)-cantidadDias*MINUTOS_DIA)/60));
		if(cantidadHorasUlitmoDia >= 9){
			cantidadHorasUlitmoDia = 0;
			cantidadDias = cantidadDias+1;							
		}
		return (double)tarifa.getValorDia()*cantidadDias + tarifa.getValorHora()*cantidadHorasUlitmoDia + 
				tarifa.getValorAdicional();	
	}	
	
	public long obtenerMinutos(Duration duracionParqueo){
		return duracionParqueo.getStandardMinutes();
	}
	
	public long obtenerDias(Duration duracionParqueo){
		return duracionParqueo.getStandardDays();
	}
	
}

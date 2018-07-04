package ceiba.estacionamiento.dominio;

import org.joda.time.Duration;
import org.springframework.stereotype.Service;

@Service
public class Cobro {
	
	public static final int MINUTOS_EN_NUEVE_HORAS = 540;
	public static final int MINUTOS_DIA = 1440;
	
	private int valorHoraCarro = 1000;
	private int valorDiaCarro = 8000;
	private int valorHoraMoto = 500;
	private int valorDiaMoto = 4000;
	private int valorAdicionalMoto = 2000;
	
	public Cobro(){
		// Constructor que requiere springboot
	}
	
	public double registrarSalidaCarro(Duration duracionParqueo) {
		if(obtenerMinutos(duracionParqueo) < MINUTOS_EN_NUEVE_HORAS){
			return calcularCobroMenorANueveHorasCarro(duracionParqueo);
		} else {
			long cantidadDias = obtenerDias(duracionParqueo);
			if(cantidadDias > 0) {
				return calcularCobroDiasMayorACeroCarro(duracionParqueo, cantidadDias);			
			} else {				
				return valorDiaCarro;
			}
		}
	}	
	
	public double calcularCobroMenorANueveHorasCarro(Duration duracionParqueo){
		int cantidadHoras = (int)(Math.ceil((float)obtenerMinutos(duracionParqueo)/60));		
		return valorHoraCarro*cantidadHoras;
	}
	
	public double calcularCobroDiasMayorACeroCarro(Duration duracionParqueo, long cantidadDias){
		int cantidadHorasUlitmoDia = Math.abs((int)(Math.ceil((float)(obtenerMinutos(duracionParqueo)-cantidadDias*MINUTOS_DIA)/60)));
		if(cantidadHorasUlitmoDia >= 9){
			cantidadHorasUlitmoDia = 0;
			cantidadDias = cantidadDias+1;							
		}
		return valorDiaCarro*cantidadDias + valorHoraCarro*cantidadHorasUlitmoDia;	
	}
	
	public double registrarSalidaMoto(Duration duracionParqueo){	
		if(obtenerMinutos(duracionParqueo) < MINUTOS_EN_NUEVE_HORAS){			
			return calcularCobroMenorANueveHorasMoto(duracionParqueo);
		} else {
			long cantidadDias= obtenerDias(duracionParqueo);
			if(cantidadDias > 0) {				
				return calcularCobroDiasMayorACeroMoto(duracionParqueo, cantidadDias);
			} else {				
				return valorDiaMoto;		
			}
		}		
	}
	
	public double calcularCobroMenorANueveHorasMoto(Duration duracionParqueo){
		int cantidadHoras = (int)(Math.ceil((float)obtenerMinutos(duracionParqueo)/60));
		return valorHoraMoto*cantidadHoras;
	}
	
	public double calcularCobroDiasMayorACeroMoto(Duration duracionParqueo, long cantidadDias){		
		int cantidadHorasUlitmoDia = Math.abs((int)(Math.ceil((float)(obtenerMinutos(duracionParqueo)-cantidadDias*MINUTOS_DIA)/60)));
		if(cantidadHorasUlitmoDia >= 9){
			cantidadDias = cantidadDias+1;
			cantidadHorasUlitmoDia = 0;
		}
		return valorDiaMoto*cantidadDias + valorHoraMoto*cantidadHorasUlitmoDia;
	}
	
	public long obtenerMinutos(Duration duracionParqueo){
		return duracionParqueo.getStandardMinutes();
	}
	
	public long obtenerDias(Duration duracionParqueo){
		return duracionParqueo.getStandardDays();
	}
	
	public double calcularCobroCilindraje(Vehiculo vehiculoASalir){
		if(vehiculoASalir.getCilindraje() > 500){
			return valorAdicionalMoto;
		}else {
			return 0;
		}
	}	
}

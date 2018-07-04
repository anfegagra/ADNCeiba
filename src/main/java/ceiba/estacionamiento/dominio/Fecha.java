package ceiba.estacionamiento.dominio;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.stereotype.Service;

@Service
public class Fecha {	
	
	public Fecha(){
		// Constructor que requiere springboot
	}
	
	public DateTime obtenerFechaEntrada(Date fechaBD){
		return new DateTime(fechaBD);
	}
	
	public DateTime obtenerFechaActual(){
		return new DateTime();
	}
	
	public Duration obtenerDuracionParqueo(DateTime fechaIngreso, DateTime fechaSalida){
		return new Duration(fechaIngreso, fechaSalida);
	}
		
	public int obtenerDia(){
		DateTime date = new DateTime();
		return date.getDayOfWeek();
	}
	
}

package ceiba.CeibaEstacionamiento.dominio;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Fecha {
	
	
	public Fecha(){
		
	}
		
	public int obtenerDia(){
		DateTime date = new DateTime();
		int dia = date.getDayOfWeek();
		return dia;
	}
	
	public int obtenerHora(){
		DateTime date = new DateTime();
		int hora = date.getHourOfDay();
		return hora;
	}
	
	public int obtenerMinutos(){
		DateTime date = new DateTime();
		int minuto = date.getMinuteOfDay();
		return minuto;
	}
	
	public int obtenerSegundos(){
		DateTime date = new DateTime();
		int segundo = date.getSecondOfDay();
		return segundo;
	}
	
}

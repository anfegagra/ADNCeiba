package ceiba.estacionamiento.dominio;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class Validacion {

	public Validacion(){
		
	}
	
	@Autowired
	Fecha fecha;
	
	public Validacion(Fecha fecha){
		this.fecha = fecha;
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
}

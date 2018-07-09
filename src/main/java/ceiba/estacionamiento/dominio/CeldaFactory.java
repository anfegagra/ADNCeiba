package ceiba.estacionamiento.dominio;

public class CeldaFactory {
	
	public Celda obtenerCeldas(String tipo){
		Celda celda = null;
		
		if("C".equals(tipo)) {
			celda = new CeldasCarro();
		} else if("M".equals(tipo)) {
			celda = new CeldasMoto();
		}
		return celda;
	}
}
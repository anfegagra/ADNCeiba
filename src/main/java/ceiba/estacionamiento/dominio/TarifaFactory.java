package ceiba.estacionamiento.dominio;

public class TarifaFactory {
	
	public Tarifa obtenerTarifa(String tipo, int cilindraje){
		Tarifa tarifa = null;
		if("C".equals(tipo)) {
			tarifa = new TarifaCarro();
		} else if("M".equals(tipo)) {
			tarifa = new TarifaMoto(cilindraje);
		}
		return tarifa;
	}
}

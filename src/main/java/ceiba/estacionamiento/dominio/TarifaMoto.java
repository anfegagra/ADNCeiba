package ceiba.estacionamiento.dominio;

public class TarifaMoto extends Tarifa{
	
	public TarifaMoto(int cilindraje){
		setValorHora(500);
		setValorDia(4000);
		if(cilindraje > 500) {
			setValorAdicional(2000);
		} else {
			setValorAdicional(0);
		}
	}
	
}

package ceiba.CeibaEstacionamiento.dominio;

public class Moto extends Vehiculo{

	private int cilindraje;
	
	public Moto(String placa, String tipo, int cilindraje) {
		super(placa, tipo, cilindraje);
		this.cilindraje = cilindraje;
		
	}
	
}

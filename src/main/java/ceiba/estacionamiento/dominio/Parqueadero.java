package ceiba.estacionamiento.dominio;

import org.springframework.stereotype.Service;

@Service
public class Parqueadero {

	private int celdasDisponiblesCarro = 20;
	private int celdasDisponiblesMoto = 10;	
	
	public Parqueadero() {
		// Constructor que requiere springboot
	}
	
	public int getCeldasDisponiblesCarro() {
		return celdasDisponiblesCarro;
	}
	public void setCeldasDisponiblesCarro(int celdasDisponiblesCarro) {
		this.celdasDisponiblesCarro = celdasDisponiblesCarro;
	}
	public int getCeldasDisponiblesMoto() {
		return celdasDisponiblesMoto;
	}
	public void setCeldasDisponiblesMoto(int celdasDisponiblesMoto) {
		this.celdasDisponiblesMoto = celdasDisponiblesMoto;
	}
}

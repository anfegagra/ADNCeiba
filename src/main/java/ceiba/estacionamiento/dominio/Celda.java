package ceiba.estacionamiento.dominio;

public abstract class Celda {
	
	private int celdasDisponiblesCarro = 20;
	private int celdasDisponiblesMoto = 10;
	
	public Celda(){
		
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
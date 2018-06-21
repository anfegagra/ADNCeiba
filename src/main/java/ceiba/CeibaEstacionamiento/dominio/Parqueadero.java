package ceiba.CeibaEstacionamiento.dominio;

public class Parqueadero {

	private int celdasDisponiblesCarro = 20;
	private int celdasDisponiblesMoto = 10;
	//private Vehiculo vehiculo;
	
	public Parqueadero() {
		
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
	
	public boolean hayCeldaDisponible(String tipoVehiculo) {
		
		if(tipoVehiculo.equals("C")){
			if(celdasDisponiblesCarro > 0){
				System.out.println("Hay " + celdasDisponiblesCarro + " celdas de Carro disponibles");
				return true;
			}else{
				System.out.println("No hay celdas de carro disponibles");
				return false;
			}
		}else{
			if(celdasDisponiblesMoto > 0){
				System.out.println("Hay " + celdasDisponiblesMoto + " celdas de Moto disponibles");
				return true;
			}else{
				System.out.println("No hay celdas de moto disponibles");
				return false;
			}
		}
		
	}
}

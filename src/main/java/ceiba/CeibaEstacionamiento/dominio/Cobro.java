package ceiba.CeibaEstacionamiento.dominio;

import java.util.Date;

public class Cobro {
	
	private int valorHoraCarro = 1000;
	private int valorDiaCarro = 8000;
	private int valorHoraMoto = 500;
	private int valorDiaMoto = 4000;
	
	public int getValorHoraCarro() {
		return valorHoraCarro;
	}
	public int getValorDiaCarro() {
		return valorDiaCarro;
	}
	public int getValorHoraMoto() {
		return valorHoraMoto;
	}
	public int getValorDiaMoto() {
		return valorDiaMoto;
	}
	
	public int realizarCobro(Date fechaIngreso){
		
		return 0;
	}
	
	
}

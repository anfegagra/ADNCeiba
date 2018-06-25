package ceiba.CeibaEstacionamiento.dominio;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Cobro {
	
	private int valorHoraCarro = 1000;
	private int valorDiaCarro = 8000;
	private int valorHoraMoto = 500;
	private int valorDiaMoto = 4000;
	
	@Autowired
	public Cobro(){
		
	}
	
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
	
}

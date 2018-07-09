package ceiba.estacionamiento.dominio;

public abstract class Tarifa {
	
	private int valorHora;
	private int valorDia;
	private int valorAdicional;
	
	public int getValorHora() {
		return valorHora;
	}
	public void setValorHora(int valorHora) {
		this.valorHora = valorHora;
	}
	public int getValorDia() {
		return valorDia;
	}
	public void setValorDia(int valorDia) {
		this.valorDia = valorDia;
	}
	public int getValorAdicional() {
		return valorAdicional;
	}
	public void setValorAdicional(int valorAdicional) {
		this.valorAdicional = valorAdicional;
	}
	
}

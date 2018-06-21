package ceiba.CeibaEstacionamiento.dominio;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Service;

@Entity
@Table(name = "vehiculos")
@EntityListeners(AuditingEntityListener.class)

public class Vehiculo implements Serializable{
	
	@Id
	private String placa;
	
	@Column(name="tipo")
	private String tipo;
	
	
	private int cilindraje;
	//private String estado;
	//private Date horaIngreso;
	//private Date horaSalida;
	@Column(name="estado")
	private String estado = "Activo";
	
	@Autowired // revisar
	public Vehiculo(){
		
	}
	
	// Constructor Moto
	public Vehiculo(String placa, String tipo, int cilindraje) {
		super();
		this.placa = placa;
		this.tipo = tipo;
		this.cilindraje = cilindraje;
	}
	
	// Constructor Carro
	public Vehiculo(String placa, String tipo) {
		super();
		this.placa = placa;
		this.tipo = tipo;
	}
	
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getCilindraje() {
		return cilindraje;
	}

	public void setCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}	
		
}

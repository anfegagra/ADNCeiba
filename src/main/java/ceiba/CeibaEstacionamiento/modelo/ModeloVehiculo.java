package ceiba.CeibaEstacionamiento.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "vehiculos")
@EntityListeners(AuditingEntityListener.class)
public class ModeloVehiculo implements Serializable{
	
	@Id
	private String placa;
	
	@Column(name="tipo")
	private String tipo;	
	
	private int cilindraje;
	
	//private Date horaIngreso;
	//private Date horaSalida;
	@Column(name="estado")
	private String estado = "Activo";
	
	@Autowired
	public ModeloVehiculo() {
		
	}	
	
	public ModeloVehiculo(String placa, String tipo, int cilindraje, String estado) {
		this.placa = placa;
		this.tipo = tipo;
		this.cilindraje = cilindraje;
		this.estado = estado;
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

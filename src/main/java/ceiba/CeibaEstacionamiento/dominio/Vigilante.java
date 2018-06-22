package ceiba.CeibaEstacionamiento.dominio;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ceiba.CeibaEstacionamiento.controlador.Crud;

@Service
public class Vigilante {

	public static final String VALIDACION_PLACA_CARRO = "Verifique la placa del carro";
	public static final String VALIDACION_CAMPOS_MOTO = "Verifique el cilindraje o placa de la moto";
	public static final String VALIDACION_PLACA_MOTO = "Verifique la placa de la moto";

	@Autowired
	Crud crud;

	public Vehiculo registrarIngresoVehiculo(Vehiculo vehiculo, Parqueadero parqueadero) {
		Vehiculo vehiculoAIngresar = null;
		if (vehiculo.getTipo().equals("C")) {
			System.out.println("Nuevo carro");
			vehiculoAIngresar = new Carro(vehiculo.getPlaca(), vehiculo.getTipo(), vehiculo.getCilindraje());
		} else {
			System.out.println("Nueva moto");
			vehiculoAIngresar = new Moto(vehiculo.getPlaca(), vehiculo.getTipo(), vehiculo.getCilindraje());
		}
		return hacerValidaciones(vehiculoAIngresar, parqueadero);
	}

	public Vehiculo hacerValidaciones(Vehiculo v, Parqueadero p) {
		Vehiculo vehiculo = null;
		if (esPlacaValida(v.getPlaca())) {
			if (v.getTipo().equals("C") && crud.validarCeldasDisponiblesCarro(v, p)) {
				vehiculo = crud.registrarVehiculo(v, p);

			} else if (crud.validarCeldasDisponiblesMoto(v, p)) {
				vehiculo = crud.registrarVehiculo(v, p);
			}
		}
		return vehiculo;
	}

	public void cobrar() {

	}

	public boolean esPlacaValida(String placa) {
		String primeraLetraPlaca = placa.substring(0, 1);
		Calendar calendar = Calendar.getInstance();
		int dia = calendar.get(Calendar.DAY_OF_WEEK);
		if (primeraLetraPlaca.equals("A")) {
			if (dia == 1 || dia == 2) {
				return true;
			}
			return false;
		} else {
			return true;
		}
	}

}

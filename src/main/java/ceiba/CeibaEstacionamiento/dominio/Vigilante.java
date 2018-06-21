package ceiba.CeibaEstacionamiento.dominio;


import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ceiba.CeibaEstacionamiento.controlador.Crud;

@Service
public class Vigilante {

	@Autowired
	Crud crud;
	//Vehiculo vehiculo;
	
	/*Calendar calendar = Calendar.getInstance();
    Date date =  calendar.getTime();
    int n = calendar.get(Calendar.DAY_OF_WEEK);*/

	/*public void registrarIngresoVehiculo(Vehiculo vehiculo, String fecha){
		if(esPlacaValida(vehiculo, fecha) && parqueadero.hayCeldaDisponible(vehiculo.getTipo())){
			System.out.println("Puede ingresar");
			//System.out.println(date);
			//System.out.println(n);
			if(vehiculo.getTipo() == "C"){
				parqueadero.setCeldasDisponiblesCarro(parqueadero.getCeldasDisponiblesCarro()-1);
			}else if(vehiculo.getTipo() == "M"){
				parqueadero.setCeldasDisponiblesMoto(parqueadero.getCeldasDisponiblesMoto()-1);
			}
			
		}else{
			System.out.println("No puede ingresar");
		}
	}*/
    
    public void registrarIngresoVehiculo(Vehiculo vehiculo, Parqueadero parqueadero){
    	System.out.println(vehiculo.getPlaca());
    	System.out.println(parqueadero.getCeldasDisponiblesCarro());
		if(esPlacaValida(vehiculo.getPlaca()) && parqueadero.hayCeldaDisponible(vehiculo.getTipo())){
			System.out.println("Puede ingresar");
			//System.out.println(date);
			//System.out.println(n);
			if(vehiculo.getTipo() == "C"){
				parqueadero.setCeldasDisponiblesCarro(parqueadero.getCeldasDisponiblesCarro()-1);
			}else if(vehiculo.getTipo() == "M"){
				parqueadero.setCeldasDisponiblesMoto(parqueadero.getCeldasDisponiblesMoto()-1);
			}
			crud.registrarVehiculo(vehiculo);
			
		}else{
			System.out.println("No puede ingresar");
		}
	}
	
	/*public void registrarSalidaVehiculo(Vehiculo vehiculo, String fecha){
		if(vehiculo.getTipo() == "C"){
			parqueadero.setCeldasDisponiblesCarro(parqueadero.getCeldasDisponiblesCarro()+1);
			System.out.println("Ha salido un vehiculo. Hay " + parqueadero.getCeldasDisponiblesCarro() + " celdas de carro disponibles");
		}else if(vehiculo.getTipo() == "M"){
			parqueadero.setCeldasDisponiblesMoto(parqueadero.getCeldasDisponiblesMoto()+1);
			System.out.println("Ha salido un vehiculo. Hay " + parqueadero.getCeldasDisponiblesMoto() + " celdas de moto disponibles");
		}
	}*/
	
	public void cobrar(){
		
	}
	
	/*public boolean esPlacaValida(Vehiculo vehiculo){
		String placa = vehiculo.getPlaca().substring(0, 1);
		int dia = calendar.get(Calendar.DAY_OF_WEEK);
		if(placa.equals("A")){
			if(dia == 1 || dia == 2){
				return true;
			}
			return false;
		}else{
			return true;
		}
	}*/
	
	public boolean esPlacaValida(String placa){
		String primeraLetraPlaca = placa.substring(0, 1);
		Calendar calendar = Calendar.getInstance();	    
	    int dia = calendar.get(Calendar.DAY_OF_WEEK);
	    if(primeraLetraPlaca.equals("A")){
			if(dia == 1 || dia == 2){
				return true;
			}
			return false;
		}else{
			return true;
		}
	}
	
	/*public boolean esPlacaValida(Vehiculo vehiculo, String fecha){
		String placa = vehiculo.getPlaca().substring(0, 1);
		if(placa.equals("A")){
			//Fecha fecha = new Fecha(dia);
			int dia = 0;
			switch (fecha) {
			case "LUNES":
				dia = 2;
				break;
				
			case "MARTES":
				dia = 3;
				break;
				
			case "MIERCOLES":
				dia = 4;
				break;
				
			case "JUEVES":
				dia = 5;
				break;
				
			case "VIERNES":
				dia = 6;
				break;
				
			case "SABADO":
				dia = 7;
				break;
				
			case "DOMINGO":
				dia = 1;
				break;
				
			default:
				break;
			}
			
			if(esDiaValido(dia)){
				return true;
			}else{
				return false;
			}
			
		}else{
			return true;
		}
	}*/
	
	public boolean esDiaValido(int dia) {
		if (dia == 1 || dia == 2){
			return true;
		}
		return false;		
	}
	
	/*public boolean esDiaValido(String dia) {
		if (dia.equals("DOMINGO") || dia.equals("LUNES")){
			return true;
		}
		return false;		
	}*/
	
}

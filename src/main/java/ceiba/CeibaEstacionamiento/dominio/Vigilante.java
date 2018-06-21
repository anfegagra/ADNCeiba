package ceiba.CeibaEstacionamiento.dominio;


import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ceiba.CeibaEstacionamiento.controlador.Crud;

@Service
public class Vigilante {
	
	public static final String VALIDACION_PLACA_CARRO = "Verifique la placa del carro";
	public static final String VALIDACION_CILINDRAJE_MOTO = "Verifique el cilindraje de la moto";
	public static final String VALIDACION_PLACA_MOTO = "Verifique la placa de la moto";

	@Autowired
	Crud crud;
		    
    public String registrarIngresoVehiculo(Vehiculo vehiculo, Parqueadero parqueadero){
    	System.out.println(vehiculo.getPlaca());
    	System.out.println(parqueadero.getCeldasDisponiblesCarro());
    	System.out.println("Valor cilindraje: " + vehiculo.getCilindraje());
    	
    	/*if(vehiculo.getCilindraje() == 0){
    		System.out.println("Tipo: " + vehiculo.getTipo());
    		if(vehiculo.getTipo().equals("M")){
    			System.out.println("Verifique el cilindraje de la moto");
    		}else{
    			Vehiculo vehiculoAIngresar = new Carro(vehiculo.getPlaca(), vehiculo.getTipo(), vehiculo.getCilindraje());
    		}
    		
    	}*/
    	
    	if(vehiculo.getTipo().equals("C")){
    		if(vehiculo.getPlaca().equals("")){
    			System.out.println("Verifique la placa del carro");
    			return VALIDACION_PLACA_CARRO;
    		}else{
    			System.out.println("Nuevo carro");
    			Vehiculo vehiculoAIngresar = new Carro(vehiculo.getPlaca(), vehiculo.getTipo(), vehiculo.getCilindraje());
    			return hacerValidaciones(vehiculoAIngresar, parqueadero);    			
    		}
    	}else if(vehiculo.getCilindraje() == 0){
    		System.out.println("Tipo: " + vehiculo.getTipo());    		
    		System.out.println("Verifique el cilindraje de la moto");
    		return VALIDACION_CILINDRAJE_MOTO;
    	}else{
    		System.out.println("Nueva moto");
			Vehiculo vehiculoAIngresar = new Moto(vehiculo.getPlaca(), vehiculo.getTipo(), vehiculo.getCilindraje());
			return hacerValidaciones(vehiculoAIngresar, parqueadero);
    	}
	}
    
    public String hacerValidaciones(Vehiculo v, Parqueadero p){
		System.out.println("hola");
		if(esPlacaValida(v.getPlaca()) && p.hayCeldaDisponible(v.getTipo())){
			System.out.println("Puede ingresar");
			//System.out.println(date);
			//System.out.println(n);
			if(v.getTipo().equals("C")){
				p.setCeldasDisponiblesCarro(p.getCeldasDisponiblesCarro()-1);
			}else if(v.getTipo().equals("M")){
				p.setCeldasDisponiblesMoto(p.getCeldasDisponiblesMoto()-1);
			}
			
			// Validar si ya esta en el parqueadero
			return crud.registrarVehiculo(v);			
			
		}else{
			System.out.println("No puede ingresar");
			return crud.registrarVehiculo(v);
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
	
}

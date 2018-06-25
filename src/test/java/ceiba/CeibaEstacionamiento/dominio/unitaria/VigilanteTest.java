package ceiba.CeibaEstacionamiento.dominio.unitaria;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ceiba.CeibaEstacionamiento.controlador.Crud;
import ceiba.CeibaEstacionamiento.dominio.Carro;
import ceiba.CeibaEstacionamiento.dominio.Fecha;
import ceiba.CeibaEstacionamiento.dominio.Moto;
import ceiba.CeibaEstacionamiento.dominio.Parqueadero;
import ceiba.CeibaEstacionamiento.dominio.Validacion;
import ceiba.CeibaEstacionamiento.dominio.Vehiculo;
import ceiba.CeibaEstacionamiento.dominio.Vigilante;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class VigilanteTest {
	
	@Mock
	Crud mockCrud;
	
	//Vigilante vig;
	
	@Spy
	Vigilante spyVigilante;
	
	@Mock 
	Fecha mockFecha;
	
	@Mock
	Validacion validacion;
	
	@Mock
	Vehiculo veh;
	
	@Before public void initMocks() {
	       MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testRegistrarIngresoVehiculoTipoCarro() {
		//Arrange
		Vehiculo vehiculo = new Carro("saf934", "C", 1250);
		Parqueadero parqueadero = new Parqueadero();
		//Vigilante spyVigilante = Mockito.spy(new Vigilante());
		Mockito.doReturn(vehiculo).when(spyVigilante).hacerValidaciones(Mockito.any(), Mockito.any());
		
		//Act		
		
		Vehiculo resultadoVehiculo = spyVigilante.registrarIngresoVehiculo(vehiculo, parqueadero);
		
		//Assert
	    assertEquals(vehiculo, resultadoVehiculo);
	}
	
	@Test
	public void testRegistrarIngresoVehiculoTipoMoto() {
		//Arrange
		Vehiculo vehiculo = new Moto("kjh783", "M", 200);
		Parqueadero parqueadero = new Parqueadero();
		Mockito.doReturn(vehiculo).when(spyVigilante).hacerValidaciones(Mockito.any(), Mockito.any());
		
		//Act		
		
		Vehiculo resultadoVehiculo = spyVigilante.registrarIngresoVehiculo(vehiculo, parqueadero);
		
		//Assert
	    assertEquals(vehiculo, resultadoVehiculo);
	}
	
	@Test
	public void testHacerValidacionesConPlacaInvalida() {		
		//Arrange
		Vehiculo esperado = null;
		Vehiculo vehiculo = new Moto("eih783", "M", 200);
		Vigilante vigilante = new Vigilante(validacion);
		Parqueadero parqueadero = new Parqueadero();
		Mockito.doReturn(false).when(validacion).esPlacaValida(Mockito.anyString());
		
		//Act
		Vehiculo resultado = vigilante.hacerValidaciones(vehiculo, parqueadero);
		
		//Assert
		assertEquals(esperado, resultado);
		
	}
	
	@Test
	public void testHacerValidacionesConPlacaValidaCarro() {		
		//Arrange
		Parqueadero parqueadero = new Parqueadero();
		Vehiculo esperado = null;		
		Vehiculo vehiculo = new Carro("ukh783", "C", 200);
		Vigilante vigilante = new Vigilante(validacion, mockCrud);
		Mockito.doReturn(true).when(validacion).esPlacaValida(Mockito.anyString());
		Mockito.doReturn(true).when(mockCrud).validarCeldasDisponiblesCarro(vehiculo, parqueadero);
		
		//Act
		Vehiculo resultado = vigilante.hacerValidaciones(vehiculo, parqueadero);
		
		//Assert
		assertEquals(esperado, resultado);
		
	}
	
	@Test
	public void testHacerValidacionesConPlacaValidaCarroPeroNoEspacio() {		
		//Arrange
		Parqueadero parqueadero = new Parqueadero();
		Vehiculo esperado = null;		
		Vehiculo vehiculo = new Carro("ukh783", "C", 200);
		Vigilante vigilante = new Vigilante(validacion, mockCrud);
		Mockito.doReturn(true).when(validacion).esPlacaValida(Mockito.anyString());
		Mockito.doReturn(false).when(mockCrud).validarCeldasDisponiblesCarro(vehiculo, parqueadero);
		Mockito.doReturn(false).when(mockCrud).validarCeldasDisponiblesMoto(vehiculo, parqueadero);
		
		//Act
		Vehiculo resultado = vigilante.hacerValidaciones(vehiculo, parqueadero);
		
		//Assert
		assertEquals(esperado, resultado);
		
	}
	
	@Test
	public void testHacerValidacionesConPlacaValidaMotoYHayEspacio() {		
		//Arrange
		Parqueadero parqueadero = new Parqueadero();
		Vehiculo esperado = null;		
		Vehiculo vehiculo = new Moto("ukh783", "M", 200);
		Vigilante vigilante = new Vigilante(validacion, mockCrud);
		Mockito.doReturn(true).when(validacion).esPlacaValida(Mockito.anyString());
		Mockito.doReturn(false).when(mockCrud).validarCeldasDisponiblesCarro(vehiculo, parqueadero);
		Mockito.doReturn(true).when(mockCrud).validarCeldasDisponiblesMoto(vehiculo, parqueadero);
		
		//Act
		Vehiculo resultado = vigilante.hacerValidaciones(vehiculo, parqueadero);
		
		//Assert
		assertEquals(esperado, resultado);
		
	}
	
	@Test
	public void testHacerValidacionesConPlacaValidaMotoPeroNoEspacio() {		
		//Arrange
		Parqueadero parqueadero = new Parqueadero();
		Vehiculo esperado = null;		
		Vehiculo vehiculo = new Moto("ukh783", "M", 200);
		Vigilante vigilante = new Vigilante(validacion, mockCrud);
		Mockito.doReturn(true).when(validacion).esPlacaValida(Mockito.anyString());
		Mockito.doReturn(false).when(mockCrud).validarCeldasDisponiblesCarro(vehiculo, parqueadero);
		Mockito.doReturn(false).when(mockCrud).validarCeldasDisponiblesMoto(vehiculo, parqueadero);
		
		//Act
		Vehiculo resultado = vigilante.hacerValidaciones(vehiculo, parqueadero);
		
		//Assert
		assertEquals(esperado, resultado);
		
	}
	
	@Test
	public void testEsPlacaValidaEmpiezaConAYLunes() {
		//Arrange
		Validacion validacion = new Validacion(mockFecha);
		String placa = "ASD123";
		Mockito.doReturn(1).when(mockFecha).obtenerDia();
		//Mockito.when(mockFecha.obtenerDia()).thenReturn(1);
		
		//Act
		boolean resultado = validacion.esPlacaValida(placa);
		
		//Assert
		assertTrue(resultado);
	}
	
	@Test
	public void testEsPlacaValidaEmpiezaConAYDomingo() {
		//Arrange
		Validacion validacion = new Validacion(mockFecha);
		String placa = "ASD123";
		Mockito.doReturn(7).when(mockFecha).obtenerDia();
		//Mockito.when(mockFecha.obtenerDia()).thenReturn(1);
		
		//Act
		boolean resultado = validacion.esPlacaValida(placa);
		
		//Assert
		assertTrue(resultado);
	}
	
	@Test
	public void testEsPlacaValidaEmpiezaConAYMiercoles() {
		//Arrange
		Validacion validacion = new Validacion(mockFecha);
		String placa = "ASD123";
		Mockito.doReturn(3).when(mockFecha).obtenerDia();
		//Mockito.when(mockFecha.obtenerDia()).thenReturn(1);
		
		//Act
		boolean resultado = validacion.esPlacaValida(placa);
		
		//Assert
		assertFalse(resultado);
	}
	
	@Test
	public void testEsPlacaValidaNoEmpiezaConA() {
		//Arrange
		Validacion validacion = new Validacion(mockFecha);
		String placa = "BSD123";		
		//Mockito.when(mockFecha.obtenerDia()).thenReturn(1);
		
		//Act
		boolean resultado = validacion.esPlacaValida(placa);
		
		//Assert
		assertTrue(resultado);
	}
	
}

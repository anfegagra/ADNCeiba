package ceiba.estacionamiento.dominio.unitaria;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import ceiba.estacionamiento.dominio.Cobro;
import ceiba.estacionamiento.dominio.Fecha;
import ceiba.estacionamiento.dominio.Vehiculo;
import ceiba.estacionamiento.dominio.Vigilante;
import ceiba.estacionamiento.persistencia.Crud;
import ceiba.estacionamiento.testdatabuilder.CarroTestDataBuilder;
import ceiba.estacionamiento.testdatabuilder.MotoTestDataBuilder;

public class VigilanteTest {
	
	@Mock
	Crud mockCrud;
	
	@Mock
	Fecha mockFecha;

	@Mock
	Cobro mockCobro;
	
	@Spy
	Vigilante spyVigilante;
		
	@Before public void initMocks() {
	       MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testRegistrarIngresoVehiculoTipoCarro() {
		//Arrange
		Vehiculo vehiculo = new CarroTestDataBuilder().build();
		Mockito.doReturn(vehiculo).when(spyVigilante).hacerValidaciones(Mockito.any());
		
		//Act		
		Vehiculo resultadoVehiculo = spyVigilante.registrarIngresoVehiculo(vehiculo);
		
		//Assert
	    assertEquals(vehiculo, resultadoVehiculo);
	}
	
	@Test
	public void testRegistrarIngresoVehiculoTipoMoto() {
		//Arrange
		Vehiculo vehiculo = new MotoTestDataBuilder().build();
		Mockito.doReturn(vehiculo).when(spyVigilante).hacerValidaciones(Mockito.any());
		
		//Act		
		Vehiculo resultadoVehiculo = spyVigilante.registrarIngresoVehiculo(vehiculo);
		
		//Assert
	    assertEquals(vehiculo, resultadoVehiculo);
	}
	
	@Test
	public void testHacerValidacionesConPlacaInvalida() {		
		//Arrange
		Vehiculo esperado = null;
		Vehiculo vehiculo = new MotoTestDataBuilder().withPlaca("ASD12E").build();
		Vigilante vigilante = new Vigilante(mockFecha);
		Mockito.doReturn(false).when(spyVigilante).esPlacaValida(Mockito.anyString());
		
		//Act
		Vehiculo resultado = vigilante.hacerValidaciones(vehiculo);
		
		//Assert
		assertEquals(esperado, resultado);
		
	}
	
	@Test
	public void testHacerValidacionesConPlacaValidaCarroYHayEspacio(){
		//Arrange
		Vehiculo esperado = null;
		Vehiculo vehiculo = new CarroTestDataBuilder().build();
		Vigilante vigilante = new Vigilante(mockFecha, mockCrud);
		Mockito.doReturn(true).when(spyVigilante).esPlacaValida(Mockito.anyString());
		Mockito.doReturn(true).when(mockCrud).validarCeldasDisponibles(Mockito.any());
		
		//Act
		Vehiculo resultado = vigilante.hacerValidaciones(vehiculo);
		
		//Assert
		assertEquals(esperado, resultado);
	}
	
	@Test
	public void testHacerValidacionesConPlacaValidaCarroPeroNoEspacio() {		
		//Arrange
		Vehiculo esperado = null;
		Vehiculo vehiculo = new CarroTestDataBuilder().build();
		Vigilante vigilante = new Vigilante(mockFecha, mockCrud);
		Mockito.doReturn(true).when(spyVigilante).esPlacaValida(Mockito.any());
		Mockito.doReturn(false).when(mockCrud).validarCeldasDisponibles(Mockito.any());
		Mockito.doReturn(false).when(mockCrud).validarCeldasDisponibles(Mockito.any());
		
		//Act
		Vehiculo resultado = vigilante.hacerValidaciones(vehiculo);
		
		//Assert
		assertEquals(esperado, resultado);
		
	}
	
	@Test
	public void testHacerValidacionesConPlacaValidaMotoYHayEspacio() {
		//Arrange
		Vehiculo esperado = null;
		Vehiculo vehiculo = new MotoTestDataBuilder().build();
		Vigilante vigilante = new Vigilante(mockFecha, mockCrud);
		Mockito.doReturn(true).when(spyVigilante).esPlacaValida(Mockito.anyString());
		Mockito.doReturn(false).when(mockCrud).validarCeldasDisponibles(Mockito.any());
		
		//Act
		Vehiculo resultado = vigilante.hacerValidaciones(vehiculo);
		
		//Assert
		assertEquals(esperado, resultado);
		
	}
	
	@Test
	public void testHacerValidacionesConPlacaValidaMotoPeroNoEspacio() {		
		//Arrange
		Vehiculo esperado = null;
		Vehiculo vehiculo = new MotoTestDataBuilder().build();
		Vigilante vigilante = new Vigilante(mockFecha, mockCrud);
		Mockito.doReturn(true).when(spyVigilante).esPlacaValida(Mockito.anyString());
		Mockito.doReturn(false).when(mockCrud).validarCeldasDisponibles(Mockito.any());
		Mockito.doReturn(false).when(mockCrud).validarCeldasDisponibles(Mockito.any());
		
		//Act
		Vehiculo resultado = vigilante.hacerValidaciones(vehiculo);
		
		//Assert
		assertEquals(esperado, resultado);		
	}
	
	@Test
	public void testEsPlacaValidaEmpiezaConAYLunes() {
		//Arrange
		Vigilante vigilante = new Vigilante(mockFecha);
		String placa = "ASD123";
		Mockito.doReturn(1).when(mockFecha).obtenerDia();
		
		//Act
		boolean resultado = vigilante.esPlacaValida(placa);
		
		//Assert
		assertTrue(resultado);
	}
	
	@Test
	public void testEsPlacaValidaEmpiezaConAYDomingo() {
		//Arrange
		Vigilante vigilante = new Vigilante(mockFecha);
		String placa = "ASD123";
		Mockito.doReturn(7).when(mockFecha).obtenerDia();
		
		//Act
		boolean resultado = vigilante.esPlacaValida(placa);
		
		//Assert
		assertTrue(resultado);
	}
	
	@Test
	public void testEsPlacaValidaEmpiezaConAYMiercoles() {
		//Arrange
		Vigilante vigilante = new Vigilante(mockFecha);
		String placa = "ASD123";
		Mockito.doReturn(3).when(mockFecha).obtenerDia();
		
		//Act
		boolean resultado = vigilante.esPlacaValida(placa);
		
		//Assert
		assertFalse(resultado);
	}
	
	@Test
	public void testEsPlacaValidaNoEmpiezaConA() {
		//Arrange
		Vigilante vigilante = new Vigilante(mockFecha);
		String placa = "BSD123";
		
		//Act
		boolean resultado = vigilante.esPlacaValida(placa);
		
		//Assert
		assertTrue(resultado);
	}
	
	@Test
	public void testRegistrarSalidaVehiculoNoExistente(){
		//Arrange
		double totalAPagar = 0;
		String placa = "ERT45I";
		Vigilante vigilante = new Vigilante(mockCrud);
		Mockito.doReturn(null).when(mockCrud).registrarSalida(Mockito.any());
		
		//Act
		double resultado = vigilante.registrarSalidaVehiculo(placa);
		
		//Assert
		assertEquals(totalAPagar, resultado, 0);
	}

	@Test
	public void testRegistrarSalidaVehiculoCarro(){
		//Arrange
		double totalAPagar = 4000;
		String placa = "GFU123";
		Vehiculo vehiculo = new CarroTestDataBuilder().build();
		Vigilante vigilante = new Vigilante(mockCrud, mockFecha, mockCobro);
		Mockito.doReturn(vehiculo).when(mockCrud).registrarSalida(Mockito.any());
		Mockito.doReturn(new DateTime(new Date(2018, 6, 27, 6, 00))).when(mockFecha).obtenerFechaEntrada(Mockito.any());
		Mockito.doReturn(new DateTime(new Date(2018, 6, 27, 10, 00))).when(mockFecha).obtenerFechaActual();
		Mockito.doReturn((double)4000).when(mockCobro).generarCobro(Mockito.any(), Mockito.any(), Mockito.anyInt());
		
		//Act
		double resultado = vigilante.registrarSalidaVehiculo(placa);
		
		//Assert
		assertEquals(totalAPagar, resultado, 0);
	}
	
	@Test
	public void testRegistrarSalidaVehiculoMoto(){
		//Arrange
		double totalAPagar = 500;
		String placa = "KTF12A";
		Vehiculo vehiculo = new MotoTestDataBuilder().build();
		Vigilante vigilante = new Vigilante(mockCrud, mockFecha, mockCobro);
		Mockito.doReturn(vehiculo).when(mockCrud).registrarSalida(Mockito.any());
		Mockito.doReturn(new DateTime(new Date(2018, 6, 27, 6, 00))).when(mockFecha).obtenerFechaEntrada(Mockito.any());
		Mockito.doReturn(new DateTime(new Date(2018, 6, 27, 7, 00))).when(mockFecha).obtenerFechaActual();
		Mockito.doReturn((double)500).when(mockCobro).generarCobro(Mockito.any(), Mockito.any(), Mockito.anyInt());
		//Mockito.doReturn((double)0).when(mockCobro).calcularCobroCilindraje(Mockito.any());
		
		//Act
		double resultado = vigilante.registrarSalidaVehiculo(placa);
		
		//Assert
		assertEquals(totalAPagar, resultado, 0);
	}
}
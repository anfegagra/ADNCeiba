package ceiba.estacionamiento.dominio.integracion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ceiba.estacionamiento.controlador.Crud;
import ceiba.estacionamiento.dominio.Cobro;
import ceiba.estacionamiento.dominio.Fecha;
import ceiba.estacionamiento.dominio.Parqueadero;
import ceiba.estacionamiento.dominio.Validacion;
import ceiba.estacionamiento.dominio.Vehiculo;
import ceiba.estacionamiento.dominio.Vigilante;
import ceiba.estacionamiento.dominio.repositorio.RepositorioVehiculo;
import ceiba.estacionamiento.modelo.ModeloVehiculo;
import ceiba.estacionamiento.test.testdatabuilder.CarroTestDataBuilder;
import ceiba.estacionamiento.test.testdatabuilder.MotoTestDataBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
public class VigilanteTest {
	
	@Autowired
	Validacion validacion;
	
	@Autowired
	Fecha fecha;
	
	@Autowired
	Crud crud;
	
	@Autowired
	Cobro cobro;
	
	@Autowired
	RepositorioVehiculo repositorioVehiculo;
		
	@Test
	public void testRegistrarIngresoVehiculoTipoCarro() {
		//Arrange
		Vigilante vigilante = new Vigilante(validacion, crud);
		Vehiculo vehiculo = new CarroTestDataBuilder().withCilindraje(2000).build();
		Parqueadero parqueadero = new Parqueadero();
		
		//Act		
		Vehiculo resultadoVehiculo = vigilante.registrarIngresoVehiculo(vehiculo, parqueadero);
		
		//Assert
	    assertEquals(vehiculo.getPlaca(), resultadoVehiculo.getPlaca());
	}
	
	@Test
	public void testRegistrarIngresoVehiculoTipoCarroYParqueaderoLLeno() {
		//Arrange
		for(int i=0; i<21; i++){
			String placa = "TGB12" + i;
			ModeloVehiculo modeloVehiculo = new ModeloVehiculo(placa, "C", 1250, "Activo");
			repositorioVehiculo.save(modeloVehiculo);
		}
		
		Vigilante vigilante = new Vigilante(validacion, crud);
		Vehiculo vehiculo = new CarroTestDataBuilder().withCilindraje(2000).build();
		Parqueadero parqueadero = new Parqueadero();
		
		//Act		
		Vehiculo resultadoVehiculo = vigilante.registrarIngresoVehiculo(vehiculo, parqueadero);
		
		//Assert
	    assertNull(resultadoVehiculo);
	}
	
	@Test
	public void testRegistrarIngresoVehiculoTipoMoto() {
		//Arrange
		Vigilante vigilante = new Vigilante(validacion, crud);
		Vehiculo vehiculo = new MotoTestDataBuilder().withCilindraje(600).build();
		Parqueadero parqueadero = new Parqueadero();		
		
		//Act		
		Vehiculo resultadoVehiculo = vigilante.registrarIngresoVehiculo(vehiculo, parqueadero);
		
		//Assert
	    assertEquals(vehiculo.getPlaca(), resultadoVehiculo.getPlaca());
	}
	
	// Retorna null si es un dia diferente a Lunes o Domingo
	/*@Test
	public void testRegistrarIngresoVehiculoTipoCarroPlacaInvalida() {
		//Arrange
		Vigilante vigilante = new Vigilante(validacion, crud);
		Vehiculo vehiculo = new CarroTestDataBuilder().withPlaca("AER147").build();
		Parqueadero parqueadero = new Parqueadero();		
		
		//Act		
		Vehiculo resultadoVehiculo = vigilante.registrarIngresoVehiculo(vehiculo, parqueadero);
		
		//Assert
	    assertNull(resultadoVehiculo);
	}*/
	
	/*@Test
	public void testRegistrarIngresoVehiculoTipoMotoPlacaInvalida() {
		//Arrange
		Vigilante vigilante = new Vigilante(validacion, crud);
		Vehiculo vehiculo = new MotoTestDataBuilder().withPlaca("AFH42J").build();
		Parqueadero parqueadero = new Parqueadero();		
		
		//Act		
		Vehiculo resultadoVehiculo = vigilante.registrarIngresoVehiculo(vehiculo, parqueadero);
		
		//Assert
	    assertNull(resultadoVehiculo);
	}*/
	
	// Retorna null porque el vehiculo a ingresar ya se encuentra en el parqueadero
	@Test
	public void testRegistrarIngresoVehiculoTipoCarroPlacaValidaPeroYaEsta() {
		//Arrange
		ModeloVehiculo modeloVehiculo = new ModeloVehiculo("EJK426", "C", 1250, "Activo");
		repositorioVehiculo.save(modeloVehiculo);
		
		Vigilante vigilante = new Vigilante(validacion, crud);
		Vehiculo vehiculo = new CarroTestDataBuilder().withPlaca("EJK426").build();
		Parqueadero parqueadero = new Parqueadero();		
		
		//Act		
		Vehiculo resultadoVehiculo = vigilante.registrarIngresoVehiculo(vehiculo, parqueadero);
		
		//Assert
	    assertNull(resultadoVehiculo);
	    repositorioVehiculo.deleteAll();
	}
	
	@Test
	public void testRegistrarSalidaVehiculoNoExistente(){
		//Arrange
		double total = 0;
		String placa = "ERT45I";
		Parqueadero parqueadero = new Parqueadero();
		Vigilante vigilante = new Vigilante(crud, fecha, cobro);		
		
		//Act
		double resultado = vigilante.registrarSalidaVehiculo(placa, parqueadero);
		
		//Assert
		assertEquals(total, resultado, 0);		
	}
	
	@Test
	public void testRegistrarSalidaVehiculoCarroMenosDeUnMinuto(){
		//Arrange		
		ModeloVehiculo modeloVehiculo = new ModeloVehiculo("MKI789", "C", 200, "Activo");
		repositorioVehiculo.save(modeloVehiculo);
		double total = 0;
		String placa = "MKI789";
		Parqueadero parqueadero = new Parqueadero();
		Vigilante vigilante = new Vigilante(crud, fecha, cobro);		
		
		//Act
		double resultado = vigilante.registrarSalidaVehiculo(placa, parqueadero);
		
		//Assert
		assertEquals(total, resultado, 0);		
	}
	
	@Test
	public void testRegistrarSalidaVehiculoMotoMenosDeUnMinuto(){
		//Arrange		
		ModeloVehiculo modeloVehiculo = new ModeloVehiculo("TCX197", "M", 200, "Activo");
		repositorioVehiculo.save(modeloVehiculo);
		double total = 0;
		String placa = "TCX197";
		Parqueadero parqueadero = new Parqueadero();
		Vigilante vigilante = new Vigilante(crud, fecha, cobro);		
		
		//Act
		double resultado = vigilante.registrarSalidaVehiculo(placa, parqueadero);
		
		//Assert
		assertEquals(total, resultado, 0);		
	}
	
	// Un dia y alunas horas
	/*@Test
	public void testRegistrarSalidaVehiculoCarro(){
		//Arrange
		
		double total = 11000;
		String placa = "JUN258";
		Parqueadero parqueadero = new Parqueadero();
		Vigilante vigilante = new Vigilante(crud, fecha, cobro);		
		
		//Act
		double resultado = vigilante.registrarSalidaVehiculo(placa, parqueadero);
		
		//Assert
		//assertEquals(total, resultado, 0);
		assertEquals(total, resultado, 0);
		
	}*/

}

package ceiba.estacionamiento.dominio.integracion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ceiba.estacionamiento.dominio.Cobro;
import ceiba.estacionamiento.dominio.Fecha;
import ceiba.estacionamiento.dominio.Vehiculo;
import ceiba.estacionamiento.dominio.Vigilante;
import ceiba.estacionamiento.modelo.ModeloVehiculo;
import ceiba.estacionamiento.persistencia.Crud;
import ceiba.estacionamiento.repositorio.RepositorioVehiculo;
import ceiba.estacionamiento.testdatabuilder.CarroTestDataBuilder;
import ceiba.estacionamiento.testdatabuilder.MotoTestDataBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
public class VigilanteTest {
		
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
		Vigilante vigilante = new Vigilante(fecha, crud);
		Vehiculo vehiculo = new CarroTestDataBuilder().withCilindraje(2000).build();
		
		//Act		
		Vehiculo resultadoVehiculo = vigilante.registrarIngresoVehiculo(vehiculo);
		
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
		
		Vigilante vigilante = new Vigilante(fecha, crud);
		Vehiculo vehiculo = new CarroTestDataBuilder().withCilindraje(2000).build();
		
		//Act		
		Vehiculo resultadoVehiculo = vigilante.registrarIngresoVehiculo(vehiculo);
		
		//Assert
	    assertNull(resultadoVehiculo);
	    repositorioVehiculo.deleteAll();
	}
	
	@Test
	public void testRegistrarIngresoVehiculoTipoMoto() {
		//Arrange
		Vigilante vigilante = new Vigilante(fecha, crud);
		Vehiculo vehiculo = new MotoTestDataBuilder().withCilindraje(600).build();	
		
		//Act		
		Vehiculo resultadoVehiculo = vigilante.registrarIngresoVehiculo(vehiculo);
		
		//Assert
	    assertEquals(vehiculo.getPlaca(), resultadoVehiculo.getPlaca());
	}
	
	// Retorna null porque el vehiculo a ingresar ya se encuentra en el parqueadero
	@Test
	public void testRegistrarIngresoVehiculoTipoCarroPlacaValidaPeroYaEsta() {
		//Arrange
		ModeloVehiculo modeloVehiculo = new ModeloVehiculo("EJK426", "C", 1250, "Activo");
		repositorioVehiculo.save(modeloVehiculo);
		
		Vigilante vigilante = new Vigilante(fecha, crud);
		Vehiculo vehiculo = new CarroTestDataBuilder().withPlaca("EJK426").build();	
		
		//Act		
		Vehiculo resultadoVehiculo = vigilante.registrarIngresoVehiculo(vehiculo);
		
		//Assert
	    assertNull(resultadoVehiculo);
	    repositorioVehiculo.deleteAll();
	}
	
	@Test
	public void testRegistrarIngresoVehiculoTipoCarroPlacaValidaYEstadoInactivo() {
		//Arrange
		ModeloVehiculo modeloVehiculo = new ModeloVehiculo("EJK426", "C", 1250, "Inactivo");
		repositorioVehiculo.save(modeloVehiculo);
		
		Vigilante vigilante = new Vigilante(fecha, crud);
		Vehiculo vehiculo = new CarroTestDataBuilder().withPlaca("EJK426").build();	
		
		//Act		
		Vehiculo resultadoVehiculo = vigilante.registrarIngresoVehiculo(vehiculo);
		
		//Assert
	    Assert.assertTrue(resultadoVehiculo.getEstado().equals("Activo"));
	    repositorioVehiculo.deleteAll();
	}
	
	@Test
	public void testRegistrarSalidaVehiculoNoExistente(){
		//Arrange
		double total = 0;
		String placa = "ERT45I";
		Vigilante vigilante = new Vigilante(crud, fecha, cobro);		
		
		//Act
		double resultado = vigilante.registrarSalidaVehiculo(placa);
		
		//Assert
		assertEquals(total, resultado, 0);		
	}
	
	@Test
	public void testRegistrarSalidaVehiculoEstadoInactivo(){
		//Arrange
		ModeloVehiculo modeloVehiculo = new ModeloVehiculo("EJK426", "C", 1250, "Inactivo");
		repositorioVehiculo.save(modeloVehiculo);
		double total = 0;
		String placa = "EJK426";
		Vigilante vigilante = new Vigilante(crud, fecha, cobro);		
		
		//Act
		double resultado = vigilante.registrarSalidaVehiculo(placa);
		
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
		Vigilante vigilante = new Vigilante(crud, fecha, cobro);		
		
		//Act
		double resultado = vigilante.registrarSalidaVehiculo(placa);
		
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
		Vigilante vigilante = new Vigilante(crud, fecha, cobro);		
		
		//Act
		double resultado = vigilante.registrarSalidaVehiculo(placa);
		
		//Assert
		assertEquals(total, resultado, 0);		
	}	
	
	@Test
	public void testConsultarVehiculos(){
		//Arrange
		Vigilante vigilante = new Vigilante(crud);
		for(int i=0; i<21; i++){
			String placa = "TGB12" + i;
			ModeloVehiculo modeloVehiculo = new ModeloVehiculo(placa, "C", 1250, "Activo");
			repositorioVehiculo.save(modeloVehiculo);			
		}
		
		//Act
		List<Vehiculo> resultado = vigilante.consultarVehiculos();
		
		//Assert
		Assert.assertFalse(resultado.isEmpty());
		repositorioVehiculo.deleteAll();
	}
	
	@Test
	public void testConsultarVehiculosListaVacia(){
		//Arrange
		Vigilante vigilante = new Vigilante(crud);		
		
		//Act
		List<Vehiculo> resultado = vigilante.consultarVehiculos();
		
		//Assert
		Assert.assertTrue(resultado.isEmpty());
	}
	
	@Test
	public void testConsultarVehiculosYUnVehiculoEstadoInactivo(){
		//Arrange
		Vigilante vigilante = new Vigilante(crud);
		ModeloVehiculo modeloVehiculoEstadoInactivo = new ModeloVehiculo("KKK777", "C", 1250, "Inactivo");
		repositorioVehiculo.save(modeloVehiculoEstadoInactivo);
		for(int i=0; i<5; i++){
			String placa = "TGB12" + i;
			ModeloVehiculo modeloVehiculo = new ModeloVehiculo(placa, "C", 1250, "Activo");
			repositorioVehiculo.save(modeloVehiculo);			
		}
		
		//Act
		List<Vehiculo> resultado = vigilante.consultarVehiculos();
		
		//Assert
		Assert.assertFalse(resultado.isEmpty());
		repositorioVehiculo.deleteAll();
	}
	
	@Test
	public void testConsultarPorPlacaEstadoInactivo(){
		//Arrange
		Vigilante vigilante = new Vigilante(crud);
		ModeloVehiculo modeloVehiculo = new ModeloVehiculo("EJK426", "C", 1250, "Inactivo");
		repositorioVehiculo.save(modeloVehiculo);
		
		//Act
		Vehiculo resultado = vigilante.consultarVehiculoPorPlaca("EJK426");
		
		//Assert
		assertNull(resultado);
		repositorioVehiculo.deleteAll();
	}
	
	@Test
	public void testConsultarPorPlacaEstadoActivo(){
		//Arrange
		Vigilante vigilante = new Vigilante(crud);
		ModeloVehiculo modeloVehiculo = new ModeloVehiculo("EJK426", "C", 1250, "Activo");
		repositorioVehiculo.save(modeloVehiculo);
		
		//Act
		Vehiculo resultado = vigilante.consultarVehiculoPorPlaca("EJK426");
		
		//Assert
		Assert.assertNotNull(resultado);
		repositorioVehiculo.deleteAll();
	}
	
}
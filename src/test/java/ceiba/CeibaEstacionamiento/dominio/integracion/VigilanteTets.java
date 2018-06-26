package ceiba.CeibaEstacionamiento.dominio.integracion;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ceiba.CeibaEstacionamiento.controlador.Crud;
import ceiba.CeibaEstacionamiento.dominio.Carro;
import ceiba.CeibaEstacionamiento.dominio.Fecha;
import ceiba.CeibaEstacionamiento.dominio.Moto;
import ceiba.CeibaEstacionamiento.dominio.Parqueadero;
import ceiba.CeibaEstacionamiento.dominio.Validacion;
import ceiba.CeibaEstacionamiento.dominio.Vehiculo;
import ceiba.CeibaEstacionamiento.dominio.Vigilante;
import ceiba.CeibaEstacionamiento.dominio.repositorio.RepositorioVehiculo;
import ceiba.CeibaEstacionamiento.modelo.ModeloVehiculo;

@RunWith(SpringRunner.class)
@SpringBootTest
/*@TestPropertySource(
		  locations = "classpath:application-integrationtest.properties")*/
public class VigilanteTets {
	
	@Autowired
	Validacion validacion;
	
	@Autowired
	Fecha fecha;
	
	@Autowired
	Crud crud;
	
	@Autowired
	RepositorioVehiculo repositorioVehiculo;

	@Before
	public void setUp(){
		repositorioVehiculo.deleteAll();
	}
	
	@After
	public void tearDown(){
		repositorioVehiculo.deleteAll();
	}
		
	@Test
	public void testRegistrarIngresoVehiculoTipoCarro() {
		//Arrange
		Vigilante vigilante = new Vigilante(validacion, crud);
		Vehiculo vehiculo = new Carro("JJJ157", "C", 1250);
		Parqueadero parqueadero = new Parqueadero();
		
		//Act		
		Vehiculo resultadoVehiculo = vigilante.registrarIngresoVehiculo(vehiculo, parqueadero);
		
		//Assert
	    assertEquals(vehiculo.getPlaca(), resultadoVehiculo.getPlaca());
	}
	
	@Test
	public void testRegistrarIngresoVehiculoTipoMoto() {
		//Arrange
		Vigilante vigilante = new Vigilante(validacion, crud);
		Vehiculo vehiculo = new Moto("WER258", "M", 200);
		Parqueadero parqueadero = new Parqueadero();		
		
		//Act		
		Vehiculo resultadoVehiculo = vigilante.registrarIngresoVehiculo(vehiculo, parqueadero);
		
		//Assert
	    assertEquals(vehiculo.getPlaca(), resultadoVehiculo.getPlaca());
	}
	
	// Retorna null si es un dia diferente a Lunes o Domingo
	@Test
	public void testRegistrarIngresoVehiculoTipoCarroPlacaInvalida() {
		//Arrange
		Vigilante vigilante = new Vigilante(validacion, crud);
		Vehiculo vehiculo = new Moto("AER258", "C", 2000);
		Parqueadero parqueadero = new Parqueadero();		
		
		//Act		
		Vehiculo resultadoVehiculo = vigilante.registrarIngresoVehiculo(vehiculo, parqueadero);
		
		//Assert
	    assertNull(resultadoVehiculo);
	}
	
	@Test
	public void testRegistrarIngresoVehiculoTipoMotoPlacaInvalida() {
		//Arrange
		Vigilante vigilante = new Vigilante(validacion, crud);
		Vehiculo vehiculo = new Moto("AER258", "M", 200);
		Parqueadero parqueadero = new Parqueadero();		
		
		//Act		
		Vehiculo resultadoVehiculo = vigilante.registrarIngresoVehiculo(vehiculo, parqueadero);
		
		//Assert
	    assertNull(resultadoVehiculo);
	}
	
	// Retorna null porque el vehiculo a ingresar ya se encuentra en el parqueadero
	@Test
	public void testRegistrarIngresoVehiculoTipoCarroPlacaValidaPeroYaEsta() {
		//Arrange
		ModeloVehiculo modeloVehiculo = new ModeloVehiculo("EJK426", "C", 200, "Activo");
		repositorioVehiculo.save(modeloVehiculo);
		
		Vigilante vigilante = new Vigilante(validacion, crud);
		Vehiculo vehiculo = new Moto("EJK426", "C", 200);
		Parqueadero parqueadero = new Parqueadero();		
		
		//Act		
		Vehiculo resultadoVehiculo = vigilante.registrarIngresoVehiculo(vehiculo, parqueadero);
		
		//Assert
	    assertNull(resultadoVehiculo);
	    repositorioVehiculo.deleteAll();
	}

}

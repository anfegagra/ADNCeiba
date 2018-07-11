package ceiba.estacionamiento.dominio.integracion.rest;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.*;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.CoreMatchers.equalTo;


import ceiba.estacionamiento.dominio.Vehiculo;
import ceiba.estacionamiento.repositorio.RepositorioVehiculo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestTest {
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	RepositorioVehiculo repositorioVehiculo;

	@Test
	public void testRegistrarVehiculoPost() {
		repositorioVehiculo.deleteAll();
		Vehiculo vehiculo = new Vehiculo("SSS222", "C", 0);
        ResponseEntity<Vehiculo> respuestaEntidad = restTemplate.postForEntity(
                "/ceiba/vehiculos", vehiculo, Vehiculo.class);
        Vehiculo vehiculoRetornado = respuestaEntidad.getBody();

        assertNotNull(vehiculoRetornado.getPlaca());
        repositorioVehiculo.deleteAll();
	}
	
	@Test
	public void testConsultarVehiculosGet() {
        ResponseEntity<String> respuestaEntidad = restTemplate.getForEntity(
        		"/ceiba/vehiculos", String.class);

        assertThat(respuestaEntidad.getStatusCode(), equalTo(HttpStatus.OK));
	}

}

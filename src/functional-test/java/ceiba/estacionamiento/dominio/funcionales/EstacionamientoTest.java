package ceiba.estacionamiento.dominio.funcionales;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import ceiba.estacionamiento.dominio.repositorio.RepositorioVehiculo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT)
public class EstacionamientoTest {
	
	private static final String REGISTRO_EXITOSO = "Registro exitoso!";
	private static final String RETIRO_EXITOSO = "Retiro exitoso!";
	
	private static WebDriver driver = null;
	
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
	
	@BeforeClass
	public static void inicializarDriver(){
		//System.setProperty("webdriver.chrome.driver", "driver/driver_windows/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		driver = new ChromeDriver(options);
		//driver = new ChromeDriver();
	}
	
	@AfterClass
	public static void cerrarDriver(){
		driver.quit();
	}

	@Test
	public void registrarEntradaMoto() {	
		
		driver.get("http://localhost:8080/");
				
		WebElement inputPlaca = driver.findElement(By.id("inputPlaca"));
		inputPlaca.sendKeys("JNH76I");
		
		WebElement inputCilindraje = driver.findElement(By.id("inputCilindraje"));
		inputCilindraje.sendKeys("200");		
		
		WebElement botonRegistrar = driver.findElement(By.id("botonRegistrar"));
		botonRegistrar.click();
		
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		WebElement mensajeResp = driver.findElement(By.id("contenedorRespuesta"));
		assertTrue(mensajeResp.getText().equals(REGISTRO_EXITOSO));		
	}
	
	@Test
	public void registrarEntradaCarro() {	
		
		driver.get("http://localhost:8080/");
				
		WebElement inputPlaca = driver.findElement(By.id("inputPlaca"));
		inputPlaca.sendKeys("UBV603");	
		
		WebElement botonRegistrar = driver.findElement(By.id("botonRegistrar"));
		botonRegistrar.click();
		
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		WebElement mensajeResp = driver.findElement(By.id("contenedorRespuesta"));
		assertTrue(mensajeResp.getText().equals(REGISTRO_EXITOSO));		
	}
	
	@Test
	public void registrarSalidaCarro() throws InterruptedException {	
		
		driver.get("http://localhost:8080/");
		
		String placa = "UBV603";
				
		WebElement inputPlaca = driver.findElement(By.id("inputPlaca"));
		inputPlaca.sendKeys(placa);	
		
		WebElement botonRegistrar = driver.findElement(By.id("botonRegistrar"));
		botonRegistrar.click();
		
		Thread.sleep(2000);
		WebElement botonRetirar = driver.findElement(By.id(placa));
		botonRetirar.click();
		
		Thread.sleep(2000);
		WebElement mensajeResp = driver.findElement(By.id("contenedorRespuesta"));
		assertTrue(mensajeResp.getText().equals(RETIRO_EXITOSO));		
	}
	
	@Test
	public void consultarCarro() throws InterruptedException{	
		
		driver.get("http://localhost:8080/");
		
		String placa = "UBV603";
				
		WebElement inputPlaca = driver.findElement(By.id("inputPlaca"));
		inputPlaca.sendKeys(placa);	
		
		WebElement botonRegistrar = driver.findElement(By.id("botonRegistrar"));
		botonRegistrar.click();
		
		Thread.sleep(1000);
		WebElement inputConsulta = driver.findElement(By.id("inputConsulta"));
		inputConsulta.sendKeys(placa);
		
		WebElement botonConsultar = driver.findElement(By.id("botonConsultar"));
		botonConsultar.click();
		
		Thread.sleep(1000);
		WebElement resultadoPlaca = driver.findElement(By.id("labelPlaca"));
		System.out.println("placaaaa-" + resultadoPlaca.getText());
		assertTrue(resultadoPlaca.getText().equals("Placa: " + placa));	
	}

}

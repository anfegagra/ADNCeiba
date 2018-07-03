package ceiba.CeibaEstacionamiento.dominio.funcionales;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EstacionamientoTest {
	
	private static WebDriver driver = null;
	
	@BeforeClass
	public static void inicializarDriver(){
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	@AfterClass
	public static void cerrarDriver(){
		driver.quit();
	}

	@Test
	public void test() throws InterruptedException {
		//boolean result = false;
		String resultado = "";
		String registroExitoso = "Registro exitoso!";
		String registroFallido = "El vehiculo ya se encuentra en el parqueadero";
		
		driver.get("http://localhost:4200/");
		
		WebElement placa = driver.findElement(By.id("inputPlaca"));
		placa.sendKeys("VVV333");
		
		WebElement cilindraje = driver.findElement(By.id("inputCilindraje"));
		cilindraje.sendKeys("200");
		
		
		WebElement registrar = driver.findElement(By.id("botonRegistrar"));
		registrar.click();
		
		driver.manage().timeouts().implicitlyWait(1,TimeUnit.SECONDS);
		WebElement mensajeResp = driver.findElement(By.id("contenedorRespuesta"));
		System.out.println("RESPUESTA: "+mensajeResp.getText());
		
		assertTrue(mensajeResp.getText().equals("Registro exitoso!"));
		
		
	}

}

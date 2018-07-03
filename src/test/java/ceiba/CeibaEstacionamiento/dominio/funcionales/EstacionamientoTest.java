package ceiba.CeibaEstacionamiento.dominio.funcionales;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

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
	public void test() {
		driver.get("http://localhost:4200/");
		
		WebElement placa = driver.findElement(By.id("inputPlaca"));
		placa.sendKeys("SDF123");
		
		WebElement cilindraje = driver.findElement(By.id("inputCilindraje"));
		cilindraje.sendKeys("200");
		
		WebElement registrar = driver.findElement(By.name("botonRegistrar"));
		registrar.click();
		
		//System.out.println(driver.findElement(By.id("tabla")).getText());
		
		//assertTrue(driver.findElement(By.id("tabla")).getText());
	}

}

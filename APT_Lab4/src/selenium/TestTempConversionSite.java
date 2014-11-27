package selenium;

import java.text.DecimalFormat;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestTempConversionSite extends TestCase {
	private static WebDriver driver;

	public void setUp() {
		driver = new FirefoxDriver();
	}

	public void tearDown() {
		driver.quit();
	}


	public void testUserLogins() { // pw apple
		String[] users = {"andy", "bob", "charley"};
		String[] passwords = {"apple", "bathtub", "china"};

		for (int i = 0; i < users.length; ++i) {
			driver.get("http://apt-public.appspot.com/testing-lab-login.html");

			// Find the text input element by its name
			WebElement element;

			// Enter something to search for
			element = driver.findElement(By.name("userId"));
			element.clear();
			element.sendKeys(users[i]);

			element = driver.findElement(By.name("userPassword"));
			element.clear();
			element.sendKeys(passwords[i]);

			element.submit();

			if (driver.getTitle().equals("Frequent Login")) {
				try {
					Thread.sleep(10000);
				} catch (Exception e) {
					fail();
				}
				--i;
				continue;
			}
			element = driver.findElement(By.name("farenheitTemperature"));
			assertTrue(element != null);
		}
	}


	public void testUserLoginCaseInsensitive() {
		String[] users = {"Andy", "boB", "CHARLEY"};
		String[] passwords = {"apple", "bathtub", "china"};

		for (int i = 0; i < users.length; ++i) {
			driver.get("http://apt-public.appspot.com/testing-lab-login.html");

			// Find the text input element by its name
			WebElement element;

			// Enter something to search for
			element = driver.findElement(By.name("userId"));
			element.clear();
			element.sendKeys(users[i]);

			element = driver.findElement(By.name("userPassword"));
			element.clear();
			element.sendKeys(passwords[i]);

			element.submit();
			if (driver.getTitle().equals("Frequent Login")) {
				try {
					Thread.sleep(10000);
				} catch (Exception e) {
					fail();
				}
				--i;
				continue;
			}

			element = driver.findElement(By.name("farenheitTemperature"));
			assertTrue(element != null);
		}
	}


	public void testUserLoginUsersWhitespace() {
		String[] users = {"   andy", "bob  ", "   charley   "};
		String[] passwords = {"apple", "bathtub", "china"};

		for (int i = 0; i < users.length; ++i) {
			driver.get("http://apt-public.appspot.com/testing-lab-login.html");

			// Find the text input element by its name
			WebElement element;

			// Enter something to search for
			element = driver.findElement(By.name("userId"));
			element.clear();
			element.sendKeys(users[i]);

			element = driver.findElement(By.name("userPassword"));
			element.clear();
			element.sendKeys(passwords[i]);

			element.submit();

			if (driver.getTitle().equals("Frequent Login")) {
				try {
					Thread.sleep(10000);
				} catch (Exception e) {
					fail();
				}
				--i;
				continue;
			}

			element = driver.findElement(By.name("farenheitTemperature"));
			assertTrue(element != null);
		}
	}

	public void testUserLoginPasswordsWhitespace() {
		String[] users = {"   andy", "bob  ", "   charley   "};
		String[] passwords = {"  apple ", "  bathtub", "china  "};

		for (int i = 0; i < users.length; ++i) {
			driver.get("http://apt-public.appspot.com/testing-lab-login.html");

			// Find the text input element by its name
			WebElement element;

			// Enter something to search for
			element = driver.findElement(By.name("userId"));
			element.clear();
			element.sendKeys(users[i]);

			element = driver.findElement(By.name("userPassword"));
			element.clear();
			element.sendKeys(passwords[i]);

			element.submit();

			if (driver.getTitle().equals("Frequent Login")) {
				try {
					Thread.sleep(10000);
				} catch (Exception e) {
					fail();
				}
				--i;
				continue;
			}

			element = driver.findElement(By.name("farenheitTemperature"));
			assertTrue(element != null);
		}
	}

	public void testUserLoginPasswordsCaseSensitive() {
		String[] users = {"   andy", "bob  ", "   charley   "};
		String[] passwords = {"  Apple ", "  bATHtub", "chinA  "};

		for (int i = 0; i < users.length; ++i) {
			driver.get("http://apt-public.appspot.com/testing-lab-login.html");

			// Find the text input element by its name
			WebElement element;

			// Enter something to search for
			element = driver.findElement(By.name("userId"));
			element.clear();
			element.sendKeys(users[i]);

			element = driver.findElement(By.name("userPassword"));
			element.clear();
			element.sendKeys(passwords[i]);

			element.submit();

			if (driver.getTitle().equals("Frequent Login")) {
				try {
					Thread.sleep(10000);
				} catch (Exception e) {
					fail();
				}
				--i;
				continue;
			}

			assertEquals(driver.getTitle(), "Bad Login");
		}
	}

	public void testTemperatureDoublePrecision() {
		int[] testValues = {-1, 0, 100, 211};
		for (int i = 0; i < testValues.length; ++i) {
			driver.get("http://apt-public.appspot.com/testing-lab-calculator.html");

			// Find the text input element by its name
			WebElement element;

			element = driver.findElement(By.name("farenheitTemperature"));
			element.clear();
			element.sendKeys(Double.toString(testValues[i]));
			element.submit();

			String text = driver.findElement(By.tagName("h2")).getText();
			double d = ((double)testValues[i]-32.0)/1.8;
			DecimalFormat df = new DecimalFormat("#.##");
			assertTrue(text.endsWith(df.format(d) + " Celsius"));
		}
	}
	
	public void testTemperatureSinglePrecision() {
		int[] testValues = {213, 216, 400};
		for (int i = 0; i < testValues.length; ++i) {
			driver.get("http://apt-public.appspot.com/testing-lab-calculator.html");

			// Find the text input element by its name
			WebElement element;

			element = driver.findElement(By.name("farenheitTemperature"));
			element.clear();
			element.sendKeys(Double.toString(testValues[i]));
			element.submit();

			String text = driver.findElement(By.tagName("h2")).getText();
			double d = ((double)testValues[i]-32)*5.0/9.0;
			DecimalFormat df = new DecimalFormat("#.#");
			assertTrue(text.endsWith(df.format(d) + " Celsius"));
		}
	}
	
	public void testInvalidTemperature() {
		String[] testValues = {"boo!"};
		for (int i = 0; i < testValues.length; ++i) {
			driver.get("http://apt-public.appspot.com/testing-lab-calculator.html");

			// Find the text input element by its name
			WebElement element;

			element = driver.findElement(By.name("farenheitTemperature"));
			element.clear();
			element.sendKeys(testValues[i]);
			element.submit();

			String text = driver.findElement(By.tagName("h2")).getText();
			assertTrue(text.endsWith("NumberFormatException on " + testValues[i]));
			
		}
	}
	
	public void testInvalidTemperatureScientific() {
		String[] testValues = {"9.7e2"};
		for (int i = 0; i < testValues.length; ++i) {
			driver.get("http://apt-public.appspot.com/testing-lab-calculator.html");

			// Find the text input element by its name
			WebElement element;

			element = driver.findElement(By.name("farenheitTemperature"));
			element.clear();
			element.sendKeys(testValues[i]);
			element.submit();

			String text = driver.findElement(By.tagName("h2")).getText();
			assertTrue(text.endsWith("NumberFormatException on " + testValues[i]));
			
		}
	}
	
	public void testTemperatureQuery() {
		int[] testValues = {-1, 0, 100, 211};
		for (int i = 0; i < testValues.length; ++i) {
			driver.get(
			"http://apt-public.appspot.com/testing-lab-conversion?farenheitTemperature="+testValues[i]);

			String text = driver.findElement(By.tagName("h2")).getText();
			double d = ((double)testValues[i]-32.0)/1.8;
			DecimalFormat df = new DecimalFormat("#.##");
			assertTrue(text.endsWith(df.format(d) + " Celsius"));
		}
	}
	
	public void testTemperatureQueryCaseInsensitive() {
		int[] testValues = {-1, 0, 100, 211};
		for (int i = 0; i < testValues.length; ++i) {
			driver.get(
			"http://apt-public.appspot.com/testing-lab-conversion?faRenHeitTEmpeRaturE="+testValues[i]);

			String text = driver.findElement(By.tagName("h2")).getText();
			double d = ((double)testValues[i]-32.0)/1.8;
			DecimalFormat df = new DecimalFormat("#.##");
			assertTrue(text.endsWith(df.format(d) + " Celsius"));
		}
	}
}

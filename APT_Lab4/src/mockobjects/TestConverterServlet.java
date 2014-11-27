package mockobjects;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import com.mockobjects.servlet.MockHttpServletRequest;
import com.mockobjects.servlet.MockHttpServletResponse;

public class TestConverterServlet extends TestCase {

	public void test_bad_temperature() throws Exception {
		TestingLabConverterServlet s = new TestingLabConverterServlet();
		MockHttpServletRequest request = 
				new MockHttpServletRequest();
		MockHttpServletResponse response = 
				new MockHttpServletResponse();

		request.setupAddParameter("farenheitTemperature", "boo!");
		response.setExpectedContentType("text/html");
		s.doGet(request,response);
		response.verify();
		assertTrue(response.getOutputStreamContents()
				.contains("Bad Temperature"));
	}

	public void test_no_temperature() throws Exception {
		TestingLabConverterServlet s = new TestingLabConverterServlet();
		MockHttpServletRequest request = 
				new MockHttpServletRequest();
		MockHttpServletResponse response = 
				new MockHttpServletResponse();

		response.setExpectedContentType("text/html");
		s.doGet(request,response);
		response.verify();
		assertTrue(response.getOutputStreamContents()
				.contains("No Temperature"));
	}

	public void test_standard() throws Exception {
		TestingLabConverterServlet s = new TestingLabConverterServlet();
		MockHttpServletRequest request = 
				new MockHttpServletRequest();
		MockHttpServletResponse response = 
				new MockHttpServletResponse();

		double farTemp = 100.0;

		request.setupAddParameter("farenheitTemperature", Double.toString(farTemp));
		response.setExpectedContentType("text/html");
		s.doGet(request,response);
		response.verify();
		Double celsiusTemp = 100.0*(farTemp - 32.0)/180.0;
		String celTempStr = new DecimalFormat("#.##").format(celsiusTemp);
		assertTrue(response.getOutputStreamContents()
				.contains(farTemp + " Farenheit = " + celTempStr + " Celsius "));

	}

	public void test_austin_threshold()
			throws Exception {
		TestingLabConverterServlet s = new TestingLabConverterServlet();
		MockHttpServletRequest request = 
				new MockHttpServletRequest();
		MockHttpServletResponse response = 
				new MockHttpServletResponse();

		double farTemp = 100.0;

		request.setupAddParameter("farenheitTemperature", Double.toString(farTemp));
		response.setExpectedContentType("text/html");
		s.doGet(request,response);
		response.verify();
		Pattern p = Pattern.compile("temperature in Austin is (\\d+)");
		Matcher m = p.matcher(response.getOutputStreamContents());
		assertTrue(m.find());
		try {
			double austinTemp = Double.parseDouble(m.group(1));
			assertTrue(austinTemp > 0 && austinTemp < 120);
		} catch (NumberFormatException e) {
			fail();
		}
	}


	public void html(String title, String... args) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head><title>"+title+"</title></head><body>");
		for (String arg : args)
			sb.append(arg);
		sb.append("</body></html>");
	}


}


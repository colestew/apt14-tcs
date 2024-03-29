package mockobjects;

import junit.framework.*;
import com.mockobjects.servlet.*;

public class TestTempServlet extends TestCase {

  public void test_bad_parameter() throws Exception {
    TemperatureServlet s = new TemperatureServlet();
    MockHttpServletRequest request = 
      new MockHttpServletRequest();
    MockHttpServletResponse response = 
      new MockHttpServletResponse();
    
    request.setupAddParameter("Fahrenheit", "boo!");
    response.setExpectedContentType("text/html");
    s.doGet(request,response);
    response.verify();
    assertEquals("Invalid temperature: boo!\n",
                 response.getOutputStreamContents());
  }
  
  public void test_boil() throws Exception {
    TemperatureServlet s = new TemperatureServlet();
    MockHttpServletRequest request = 
      new MockHttpServletRequest();
    MockHttpServletResponse response = 
      new MockHttpServletResponse();
    
    request.setupAddParameter("Fahrenheit", "212");
    response.setExpectedContentType("text/html");
    s.doGet(request,response);
    response.verify();
    assertEquals("Fahrenheit: 212, Celsius: 100.0\n",
                 response.getOutputStreamContents());
  }
  
}

package com.dxc.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import com.dxc.web.IRESTHelper;
import com.dxc.web.ExampleServlet;


public class ExampleServletTest {
	
    
	@Test
	public void testDoGet() throws ServletException, IOException {
		//create mock objects
		HttpServletRequest mockRequest = EasyMock.createMock(HttpServletRequest.class);
		HttpServletResponse mockResponse = EasyMock.createMock(HttpServletResponse.class);
		IRESTHelper mockHelper = EasyMock.createMock(IRESTHelper.class);
		
		//test translation servlet
		ExampleServlet servlet = new ExampleServlet();
		servlet.setHelper(mockHelper);
		
		//define the test sequence and set the expected return values
		StringWriter sw = new StringWriter();
	    PrintWriter pw = new PrintWriter(sw);
	    
	    String restResponse = "{\"balance\":1000.21,\"num\":100,\"is_vip\":true,\"name\":\"Mary\"}";
	    EasyMock.expect(mockHelper.processGetRequest
	    		((URL)EasyMock.anyObject(), (String)EasyMock.anyObject(), (String)EasyMock.anyObject())).andReturn(restResponse);
		EasyMock.expect(mockResponse.getWriter()).andReturn(pw).anyTimes();
		
		//bind the mock objects
		EasyMock.replay(mockRequest);
		EasyMock.replay(mockResponse);
		EasyMock.replay(mockHelper);
		
		//send doGet
		servlet.doGet(mockRequest, mockResponse);
		
		//assert result
		Assert.assertTrue(sw.toString().contains("Mary"));
	}

}

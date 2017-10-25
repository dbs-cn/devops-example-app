package com.dxc.web;

import static java.net.HttpURLConnection.HTTP_OK;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

public class ITTranslationServlet {
	
	private WebConversation wc;
    private WebRequest request;
    private WebResponse response;

	@Test
	public void testTranslationServlet() throws MalformedURLException, IOException, SAXException {
		String serverUrl = System.getProperty("integration.test.server.url");
		wc = new WebConversation();
		HttpUnitOptions.setScriptingEnabled(false);
        
		//browse to the page
		request = new GetMethodWebRequest(serverUrl);
        response = wc.getResponse(request);
        int responseCode = response.getResponseCode();
        Assert.assertEquals(HTTP_OK, responseCode);
        Assert.assertTrue(response.getText().contains("EASE Sample Application"));
        

        // Fill out form and submit
        WebForm form = response.getForms()[0];
        form.setParameter("InputText", "hello");
        response = form.submit();
        Assert.assertTrue(response.getText().contains("Bonjour"));
	}

}

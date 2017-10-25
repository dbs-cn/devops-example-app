package com.dxc.rest.response;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

public class MyResponseTest {
	
	@Test
    public void testReponse() throws IOException, ParseException {
		MyResponse response = new MyResponse();
		String result = response .getResponse("David");
		JSONParser parser = new JSONParser();
		
		Object obj = parser.parse(result);
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals("David", (String) jsonObject.get("name"));
		
	}
	
	@Test
    public void testNullReponse() throws IOException, ParseException {
		MyResponse response = new MyResponse();
		String result = response .getResponse("");
		JSONParser parser = new JSONParser();
		
		Object obj = parser.parse(result);
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals(MyResponse.DEFAULT_NAME, (String) jsonObject.get("name"));
		
	}

}

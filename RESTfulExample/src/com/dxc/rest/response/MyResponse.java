package com.dxc.rest.response;

import java.io.IOException;
import java.io.StringWriter;

import org.json.simple.JSONObject;

public class MyResponse {
	
	public static String DEFAULT_NAME = "David";
	
	public MyResponse() {
		super();
	}
	
	public String getResponse(String name) throws IOException {
		if (name == null || name.isEmpty()) {
			name = DEFAULT_NAME;
		}
		
		JSONObject obj = new JSONObject();

		obj.put("name",name);
		obj.put("num",new Integer(100));
		obj.put("balance",new Double(1000.21));
		obj.put("is_vip",new Boolean(true));
	
		StringWriter out = new StringWriter();
		obj.writeJSONString(out);
		      
		return out.toString();
	}

}

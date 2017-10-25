package com.dxc.web;

import java.io.IOException;
import java.net.URL;

public interface IRESTHelper {
	
	public String processGetRequest(URL url, String userName, String password) throws IOException;
	public String processPostRequest(URL url, byte[] data, String contentType, String userName, String password) throws IOException;

}

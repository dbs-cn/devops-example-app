package com.dxc.web;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ServiceManagementRESTHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceManagementRESTHelper.class);
	
	//set trust all certificate
	static {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
            public void checkClientTrusted(X509Certificate[] certs, String authType) { }
            public void checkServerTrusted(X509Certificate[] certs, String authType) { }

        } };

		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("ServiceManagementRESTHelper operation failed", e);
		} catch (KeyManagementException e) {
			LOGGER.error("ServiceManagementRESTHelper operation failed", e);
		}

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) { return true; }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}
     
    // Source - http://www.mkyong.com/java/how-to-convert-inputstream-to-string-in-java/
    private static String getStringFromInputStream(InputStream is) {
          
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
  
        String line;
        try {
  
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
  
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
  
        return sb.toString();
  
    }
     
    public static String processGetRequest(URL url, String userName, String password) throws IOException {
    	HttpsURLConnection  con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        
        String authorization = getAuthorization(userName, password);
        con.setRequestProperty("Authorization", authorization);
        
        InputStream responseStream = (InputStream) con.getContent();
        String response = getStringFromInputStream(responseStream);
        responseStream.close();
        return response;
    }
     
    public static String processPostRequest(URL url, byte[] data, String contentType, String userName, String password) throws IOException {
    	HttpURLConnection con = null;
        con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Length", String.valueOf(data.length));
        con.setRequestProperty("Content-Type", contentType);
        
        String authorization = getAuthorization(userName, password);
        con.setRequestProperty("Authorization", authorization);
         
        DataOutputStream  requestStream = new DataOutputStream (con.getOutputStream());
        requestStream.write(data);
        requestStream.flush();
        requestStream.close();

        String response = getStringFromInputStream(con.getInputStream());
        return response;
    }
    
    private static String getAuthorization(String userName, String password) throws UnsupportedEncodingException {
    	String userpass = userName + ":" + password;
        return "Basic " + DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8"));
    }
    
}

package com.dxc.web;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementing simple GET and POST requests to an external REST API.
 */
@WebServlet("/")
public class ExampleServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9210623634636458884L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ExampleServlet.class);
	
	private String url = "http://localhost:8080/RESTfulExample/rest/person/";
	private String user = "";
	private String password = "";
	private String personName = "";
	private String personBalance = "";
	private Integer personNum = 0;
	private Boolean personIsVIP = false;
	
	private IRESTHelper helper = new RESTHelper();
       
	/**
     * @see HttpServlet#HttpServlet()
     */
    public ExampleServlet() {
        super();
    }
    
    public void init() throws ServletException {
    	// Look up the backend url in environment 
    	String myUrl = System.getenv("devops_exmple_url");
    	if (myUrl != null && !myUrl.isEmpty()) {
    		url = myUrl;
    	}
    	String myUser = System.getenv("devops_exmple_user");
    	if (myUser != null && !myUser.isEmpty()) {
    		user = myUser;
    	}
    	
    	String myPassword = System.getenv("devops_exmple_password");
    	if (myPassword != null && !myPassword.isEmpty()) {
    		password = myPassword;
    	}
    	
    	LOGGER.info("Destination URL: " + url);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("Start doGet......");
		getPerson(url, user, password, "David");
		response.getWriter().println(display());
	}
	
	/**
	 * Display the GUI
	 * @param translatedText
	 * @return HTML string 
	 */
	private String display() {
		return "<p>DevOps Sample Application - Connect to Backend Service!</p>"
				+ "<p><form action=\"\" method=\"post\">" + "Name : " + personName
                + "<br/><br/>Balance : " + personBalance  
                + "<br/><br/>Number : " + personNum 
                + "<br/><br/>Is VIP : " + personIsVIP + "</form></p>";
	}
	
	private void getPerson(String destinationURL, String userName, String password, String myName) throws IOException {
		LOGGER.info("getPerson name");
		URL target = new URL(destinationURL + myName);
        
		//get person information
		String result  = helper.processGetRequest(target, userName, password);
        
		//get name
		JsonReader reader = Json.createReader(new StringReader(result));
		JsonObject resultObject = reader.readObject();
        reader.close();
        
        personName = resultObject.getString("name");
        personBalance = resultObject.getJsonNumber("balance").bigDecimalValue().toString();
        personNum = resultObject.getInt("num");
        personIsVIP = resultObject.getBoolean("is_vip");

	}
	
	/**
	 * @param helper the helper to set
	 */
	public void setHelper(IRESTHelper helper) {
		this.helper = helper;
	}

}

package cn.com.chaser.restapi;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class getStation extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws IOException, ServletException
		    {
		        response.setContentType("text/html");
		        PrintWriter out = response.getWriter();
		        String firstName=request.getParameter("firstName");
		        String lastName=request.getParameter("lastName");
		        
		        if (firstName != null || lastName != null) {
		            out.println("First Name:");
		            out.println(" = " + "<br>");
		            out.println("Last Name:");
		            out.println(" = " );
		        } else {
		            out.println("No Parameters, Please enter some");
		        }
		    }
	  public void doPost(HttpServletRequest request, HttpServletResponse response)
			    throws IOException, ServletException
			    {
			        doGet(request, response);
			    }
}

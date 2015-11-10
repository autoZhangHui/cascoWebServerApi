package cn.com.chaser.restapi;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import analysis.hbase.test.HbaseSet;
import com.manning.hip.ch3.csv.CSVMapReduce;;
public class RestService2 extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws IOException, ServletException
		    {
		        String method=request.getParameter("method");
		        String data=request.getParameter("data");
		        JSONObject jsondata=new JSONObject();
		    	//jsondata=JSONObject.fromObject(data);
		    	
		    	try {
					//HbaseSet.main(null);
					data=CSVMapReduce.resultToJson("AnalysisResult","20141130");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	jsondata=JSONObject.fromObject(data);
		        response.setContentType("application/json;charset=UTF-8");
		        response.setHeader("Cache-Control", "no-store");
		        response.setHeader("Pragrma", "no-cache");
		        response.setDateHeader("Empores", 0);	
		        PrintWriter out = response.getWriter();
		        out.write(jsondata.toString());
		        
		    }
	  public void doPost(HttpServletRequest request, HttpServletResponse response)
			    throws IOException, ServletException
			    {
			        doGet(request, response);
			    }
}

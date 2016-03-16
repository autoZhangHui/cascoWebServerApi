package cn.com.chaser.restapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.manning.hip.ch3.csv.CSVMapReduce;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import analysis.hbase.test.HbaseSet;
public class RestService1 extends HttpServlet{

	public static String getBody(HttpServletRequest request) throws IOException {
	    String body = null;
	    StringBuilder stringBuilder = new StringBuilder();
	    BufferedReader bufferedReader = null;
	    try {
	        InputStream inputStream = request.getInputStream();
	        if (inputStream != null) {
	            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	            char[] charBuffer = new char[128];
	            int bytesRead = -1;
	            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
	                stringBuilder.append(charBuffer, 0, bytesRead);
	            }
	        } else {
	            stringBuilder.append("");
	        }
	    } catch (IOException ex) {
	        throw ex;
	    } finally {
	        if (bufferedReader != null) {
	            try {
	                bufferedReader.close();
	            } catch (IOException ex) {
	                throw ex;
	            }
	        }
	    }

	    body = stringBuilder.toString();
	    return body;
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws IOException, ServletException
		    {
		        String body=getBody(request);
		        JSONObject requestJson=JSONObject.fromObject(body);	
				String method=(String) requestJson.get("method");
		        JSONObject jsondata=new JSONObject();
		        String data="";
		    	//jsondata=JSONObject.fromObject(data);
		    	if(method.equals("dqfx")){
		    		//String[] station=request.getParameterValues("station");
		    		JSONArray deviceArray=(JSONArray) requestJson.get("devices");
		    		String sbtype=(String) requestJson.get("sbtype");
			        String[] device=new String[deviceArray.size()];
			        for(int i=0;i<deviceArray.size();i++){
			        	device[i]=deviceArray.getString(i);
			        }
			        JSONArray varArray=(JSONArray) requestJson.get("type");
			        String[] typeVar=new String[varArray.size()];
			        for(int i=0;i<varArray.size();i++){
			        	typeVar[i]=varArray.getString(i);
			        }
			        String startTime=((String) requestJson.get("start_at")).substring(0,10).replaceAll("-", "");
			        String endTime=((String) requestJson.get("end_at")).substring(0,10).replaceAll("-", "");
			        int deviceNum=device.length;
			        int varNum=typeVar.length;
			        int num=deviceNum*varNum;
			        String[] test=new String[num];
			        num=0;
			        for(int i=0;i<deviceNum;i++){
			        	for(int j=0;j<varNum;j++){
			        		test[num]=device[i]+":"+typeVar[j];
			        		num++;
			        	}
			        }
		    		try {		
		    			data=HbaseSet.runHbase(test,"AnalysisResult", startTime, endTime,sbtype);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		data="{\"results\""+":"+data+"}";
		    	}
		    	if(method.equals("rizhi")){
		    		//String[] station=request.getParameterValues("station");
		    		JSONArray keysArray=(JSONArray) requestJson.get("keys");
			        String[] keywords=new String[keysArray.size()];
			        String startTime=((String) requestJson.get("start_at")).substring(0,10).replaceAll("-", "");
			        String endTime=((String) requestJson.get("end_at")).substring(0,10).replaceAll("-", "");
			        for(int i=0;i<keywords.length;i++){
			        	keywords[i]=keysArray.getString(i);
			        }
			        try {
						data=CSVMapReduce.runLogs(keywords,startTime,endTime);
						//CSVMapReduce.runLogs(keywords);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        data="{\"results\""+":["+data+"]}";
		    	}
		    	if(method.equals("history")){
		    		try {
						data=HbaseSet.getAllHistory("AnalysisHistory");
						//CSVMapReduce.runLogs(keywords);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		 data="{\"result\""+":"+data+"}";
		    	}
		    	if(method.equals("gethabseinfo")){
		    		String tableName=(String) requestJson.get("tableName");
		    		try {
						data=HbaseSet.getAllHistory("tableName");
						//CSVMapReduce.runLogs(keywords);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		 data="{\"result\""+":"+data+"}";
		    	}
		    	if(method.equals("disabletable")){
		    		String tableName=(String) requestJson.get("tableName");
		    		try {
						HbaseSet.disableTable(tableName);
						data="{\"result\""+":"+"success"+"}";
						//CSVMapReduce.runLogs(keywords);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						data="{\"result\""+":"+e+"}";
					}
		    	}
		    	if(method.equals("enabletable")){
		    		String tableName=(String) requestJson.get("tableName");
		    		try {
						HbaseSet.enableTable(tableName);
						data="{\"result\""+":"+"success"+"}";
						//CSVMapReduce.runLogs(keywords);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						data="{\"result\""+":"+e+"}";
					}
		    	}
		    	if(method.equals("droptable")){
		    		String tableName=(String) requestJson.get("tableName");
		    		try {
						HbaseSet.deleteTable(tableName);
						data="{\"result\""+":"+"success"+"}";
						//CSVMapReduce.runLogs(keywords);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						data="{\"result\""+":"+e+"}";
					}
		    		 //data="{\"result\""+":"+data+"}";
		    	}
		    	if(method.equals("addtable")){
		    		String tableName=(String) requestJson.get("tableName");
		    		String cf1=(String) requestJson.get("cf1");
		    		String cf2=(String) requestJson.get("cf2");
		    		String cf3=(String) requestJson.get("cf3");
		    		int validCf=0;
		    		if(!cf1.equals(""))  validCf++;
		    		if(!cf2.equals(""))  validCf++;
		    		if(!cf3.equals(""))  validCf++;
		    		String[] cfs=new String[validCf];
		    		validCf=0;
		    		if(!cf1.equals(""))  {cfs[validCf]=cf1;validCf++;}
		    		if(!cf2.equals(""))  {cfs[validCf]=cf2;validCf++;}
		    		if(!cf3.equals(""))  {cfs[validCf]=cf3;}
		    		if(validCf>0){
		    			try {
							HbaseSet.creatTable(tableName, cfs);
							//CSVMapReduce.runLogs(keywords);
							data="{\"result\""+":"+"创建数据表成功"+"}";
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    		}
		    		
		    		 
		    	}
		    	try {
			    	jsondata=JSONObject.fromObject(data);
				} catch (Exception ejson) {
					jsondata=JSONObject.fromObject("{\"result\""+":处理错误"+ejson+"}");
				}
		    	//jsondata=JSONObject.fromObject(data);
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

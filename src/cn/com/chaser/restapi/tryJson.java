package cn.com.chaser.restapi;
import  net.sf.json.*;
import net.sf.json.util.*;
import net.sf.json.JSONArray;

public class tryJson {
public static JSONObject stringtojson(String input){
	JSONObject jsondata=new JSONObject();
	jsondata=JSONObject.fromObject(input);
	return jsondata;
}
}

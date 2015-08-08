package com.menkj.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

/**
 * http协议  客户端调用实现
 * @author yt
 *
 */
public class UtilHttpClient {

	public static String[] POST(String url,boolean https,String encode
			,Map<String,String>param,Map atts) throws IOException{   
		
		int partSize = 0;
        if(param!=null){
            partSize = param.size();
        }
        if(atts!=null){
            partSize = partSize+atts.size();
        }

        Part[] parts = new Part[partSize];

        int idx = 0;
        // 设置POST参数
        String key = "";
        String value = "";
        Iterator keys = param.keySet().iterator();
        while(keys.hasNext()){
            key = (String)keys.next();
            value = (String)param.get(key);
            parts[idx] = new StringPart(key,value,encode);
            idx++;
        }
        
		String fileName=null;
        Iterator fileNames = null;
        fileNames = atts.keySet().iterator();
        
        while(fileNames.hasNext()){
            fileName = (String)fileNames.next();
            Object obj=atts.get(fileName);
            if(obj instanceof String){
            	File file  = new File((String)atts.get(fileName));
            	System.out.println(fileName);
            	FilePart part = new FilePart( fileName,file);
            	parts[idx]=part;
            	idx++;
            }else{
            	throw new RuntimeException("not impl");
            }
        }
		
		PostMethod method = new PostMethod(url);  
		
		MultipartRequestEntity body=new MultipartRequestEntity( parts, method.getParams());
		//method.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset="+encode); 
		method.setRequestEntity(body);
		

		if(encode==null || encode.trim().equals("")){
			encode="gb2312";
		}
		String protocol="http";
		if(https){
			protocol="https";
		}
		
		HttpClient client = new HttpClient();
//		client.getHostConfiguration().setHost(ip, port,protocol);
		// 使用GET方式提交数据
		int state=client.executeMethod(method);
		System.out.println("HttpClient exec state:"+state);
		
		// 打印服务器返回的状态
		System.out.println("HTTP Response status:" + method.getStatusLine());

	    InputStream inputStream = method.getResponseBodyAsStream();  
	    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream  ,  encode));  
	    StringBuffer stringBuffer = new StringBuffer();  
	    String str= "";  
	    while((str = br.readLine()) != null){  
	    	stringBuffer .append(str );  
	    }  
		String response =stringBuffer.toString();// new String(method.getResponseBody(),encode);

		// 打印返回的信息
		System.out.println("HTTP Response content:" + response);

		method.releaseConnection();
		
		String[] r=new String[3];
		r[0]=String.valueOf(method.getStatusCode());
		r[1]=method.getStatusText();
		r[2]=response;
		
		return r;
	}
	
	public static String[] POSTWithURL(String url,boolean https,String encode,Map<String,String>param) throws IOException{   
		
		int partSize = 0;
        if(param!=null){
            partSize = param.size();
        }
        
        NameValuePair[] nv=formatParam(param);
		PostMethod method = new PostMethod(url);  
		method.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset="+encode); 
		method.setRequestBody(nv);  

		if(encode==null || encode.trim().equals("")){
			encode="gb2312";
		}
		String protocol="http";
		if(https){
			protocol="https";
		}
		
		HttpClient client = new HttpClient();
//		client.getHostConfiguration().setHost(ip, port,protocol);
		// 使用GET方式提交数据
		int state=client.executeMethod(method);
		System.out.println("HttpClient exec state:"+state);
		
		// 打印服务器返回的状态
		System.out.println("HTTP Response status:" + method.getStatusLine());
		
	    InputStream inputStream = method.getResponseBodyAsStream();  
	    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream  ,  encode));  
	    StringBuffer stringBuffer = new StringBuffer();  
	    String str= "";  
	    while((str = br.readLine()) != null){  
	    	stringBuffer .append(str );  
	    }  
		String response =stringBuffer.toString();// new String(method.getResponseBody(),encode);

		// 打印返回的信息
		System.out.println("HTTP Response content:" + response);

		method.releaseConnection();
		
		String[] r=new String[3];
		r[0]=String.valueOf(method.getStatusCode());
		r[1]=method.getStatusText();
		r[2]=response;
		
		return r;
	}
	/**
	 * @param ip
	 * @param port
	 * @param uri
	 * @param https
	 * @param encode
	 * @param param
	 * @return 0 status code 1 status text  2 response text
	 * @throws IOException
	 */
	public static String[] POST(String ip,int port,String uri,boolean https,String encode
			,Map<String,String> param , Map<String , String > headerMap , String contentType) throws IOException{   
		NameValuePair[] nv=formatParam(param);

		PostMethod method = new PostMethod(uri);  
        String _contentType = "application/x-www-form-urlencoded";
        if(contentType!=null){
        	_contentType = contentType;
        }
		method.addRequestHeader("Content-Type",""+_contentType+";charset="+encode); 
		
		method.setRequestBody(nv);  
		setHeader(headerMap , method);
		return request(ip, port, https, encode, method);
    }
	
	public static String[] POST(String ip,int port,String uri,boolean https,String encode
			,Map<String,String> param ) throws IOException{   
		NameValuePair[] nv=formatParam(param);

		PostMethod method = new PostMethod(uri);  
        
		method.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset="+encode); 
		method.setRequestBody(nv);
		return request(ip, port, https, encode, method);
    }
	/**
	 * 设置头部
	 * @param headerMap
	 */
	private static void setHeader(Map headerMap , PostMethod method){
		if(headerMap == null ) return;
		Iterator itr=headerMap.entrySet().iterator();
		while(itr.hasNext()){
			Entry entry=(Entry)itr.next();
			method.setRequestHeader((String)entry.getKey(), (String)entry.getValue());
		}
	}
	
	private static String[] request(String ip,int port,boolean https,String encode,HttpMethodBase method) throws HttpException, IOException{

		if(encode==null || encode.trim().equals("")){
			encode="gb2312";
		}
		String protocol="http";
		if(https){
			protocol="https";
		}
		
		HttpClient client = new HttpClient();
		client.getHostConfiguration().setHost(ip, port,protocol);
		int state=client.executeMethod(method);
		
	    InputStream inputStream = method.getResponseBodyAsStream();  
	    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream  ,  encode));  
	    StringBuffer stringBuffer = new StringBuffer();  
	    String str= "";  
	    while((str = br.readLine()) != null){  
	    	stringBuffer .append(str );  
	    }  
		
		String response = stringBuffer.toString();//new String(method.getResponseBody(),encode);
		method.releaseConnection();
		
		String[] r=new String[3];
		r[0]=String.valueOf(method.getStatusCode());
		r[1]=method.getStatusText();
		r[2]=response;
		
		return r;
	}
	

	
	private static NameValuePair[] formatParam(Map<String,String> param){
		List<NameValuePair> nv=new ArrayList<NameValuePair>();
		Iterator itr=param.entrySet().iterator();
		while(itr.hasNext()){
			Entry entry=(Entry)itr.next();
			NameValuePair n=new NameValuePair();
			n.setName((String)entry.getKey());
			n.setValue((String)entry.getValue());
			nv.add(n);
		}
		return nv.toArray(new NameValuePair[0]);
	}
	
/**
 * 
 * @param url
 * @param https
 * @param encode
 * @param param
 * @return
 * @throws IOException
 */
public static String[] POSTWithURL_ArgsJson(String url,boolean https,String encode,Map<String,String>param , Map<String,String> headerMap , String contentType) throws IOException{   
        
		if(encode==null || encode.trim().equals("")){
			encode="gb2312";
		}
		String protocol="http";
		if(https){
			protocol="https";
		}
		HttpClient client = new HttpClient();
		
		PostMethod method = new PostMethod(url);  
	
		method.addRequestHeader("Content-Type","application/json;charset="+encode);
		method.addRequestHeader("Accept", "application/json");
		
		String json = getJson(param);
		System.out.println(json);
		RequestEntity entity = new StringRequestEntity("{"+json+"}", "application/json",   encode);     
		method.setRequestEntity(entity); 


//		client.getHostConfiguration().setHost(ip, port,protocol);
		setHeader(headerMap , method);
		int state=client.executeMethod(method);
		System.out.println("HttpClient exec state:"+state);
		
		// 打印服务器返回的状态
		System.out.println("HTTP Response status:" + method.getStatusLine());
		
	    InputStream inputStream = method.getResponseBodyAsStream();  
	    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream  ,  encode));  
	    StringBuffer stringBuffer = new StringBuffer();  
	    String str= "";  
	    while((str = br.readLine()) != null){  
	    	stringBuffer .append(str );  
	    }  
		String response =stringBuffer.toString();// new String(method.getResponseBody(),encode);

		// 打印返回的信息
		System.out.println("HTTP Response content:" + response);

		method.releaseConnection();
		
		String[] r=new String[3];
		r[0]=String.valueOf(method.getStatusCode());
		r[1]=method.getStatusText();
		r[2]=response;
		
		return r;
	}
	/**
	 * 返回json格式
	 * @param param
	 * @return
	 */
	private static String getJson(Map<String, String> param) {
		StringBuffer json = new StringBuffer();
		Iterator itr=param.entrySet().iterator();
		int i=0;
		while(itr.hasNext()){
			if(i!=0){
				json.append(",");
			}
			Entry entry=(Entry)itr.next();
			json.append("\""+entry.getKey()+"\"").append(":").append("\""+entry.getValue()+"\"");
			i++;
		}
		return json.toString();
	}

	/**
	 * Get 方式
	 * @param ip
	 * @param port
	 * @param https
	 * @param encode
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String[]  Get(String host,int port,boolean https , String uri,String encode) throws HttpException, IOException{
		String protocol="http";
		if(https){
			protocol="https";
		}
		HttpClient client = new HttpClient();
		client.getHostConfiguration().setHost( host , port, protocol );
	   HttpMethod method=new GetMethod	(uri);
	   client.executeMethod(method);

	   System.out.println(method.getStatusLine());
	   //打印返回的信息
	   String response = method.getResponseBodyAsString();
	   //释放连接
	   method.releaseConnection();
	   
	   String[] r=new String[3];
		r[0]=String.valueOf(method.getStatusCode());
		r[1]=method.getStatusText();
		r[2]=response;
		
		return r;
	}
}

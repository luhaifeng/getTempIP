package com.menkj.utils;

import java.awt.GraphicsEnvironment;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**   
 *    
 * 项目名称：tyylc   
 * 类名称： StrUtil   
 * 类描述：   
 * 创建人： lsc  
 * 创建时间：Aug 30, 2011 1:06:56 PM   
 * 修改人： lsc   
 * 修改时间：Aug 30, 2011 1:06:56 PM   
 * 修改备注：   
 * @version    
 *    
 */

public class StrUtil {
	
	/**
	 * 解析 map，返回json格式字符串
	 * map 中包含map
	 * 
	 * @return
	 *      若map为空，则放回空串 ""
	 *      
	 */
	public static String getJsonByMap(Map map){
		
		//解析resultMap
		Set<String> key = (Set<String>) map.keySet();
		
		StringBuffer sb = new StringBuffer();
		int i=0;
        for (Iterator it = key.iterator(); it.hasNext();) {
        	
            Map contentMap = (Map)map.get(it.next());
            if(contentMap.isEmpty()) return "";
            
            if(i != 0){//第一行没有逗号
            	sb.append(",");
            }
            sb.append("{");
            //遍历contentMap
            Set contentset = contentMap.entrySet();
            int j=0;
            for (Iterator it1 = contentset.iterator(); it1.hasNext();) {
                Map.Entry entry = (Map.Entry) it1.next();
                if(j != 0) sb.append(","); //第一行没有逗号
                sb.append(entry.getKey()+":'"+entry.getValue()+"'");
                j++;
            }    		
            sb.append("}");
            i++;
        }
        
		return sb.toString();
	}
	
	/**
	 * 获取系统的字体库
	 */
	public static String[] getSystemFonts(){
		
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();//获取系统自带的字体库
		
	}
	
	/**
	* 判断是否为空
	* 0 为空
	* 1-正常
	* */
	public static byte isNull(String args){
		
			byte res= 0;
			if(args==null||args.equals("")||args.equals("null")){
				res = 0;
			}else{
				res = 1;
			}
			return res;
			
		}
	/**
	 * 将字节数组转为16进制形式
	 * @param b
	 * @return
	 */
	public static String bytes2Hex(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = "0" + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}
	
	/**
	 * 类型转换
	 */
	public static byte getByte(String s ,String i){
		
		return Byte.parseByte((s==null||s.equals("")||s.equals("null"))?i:s);
	}
	
	public static int getInteger(String s ,String i){
		
		return Integer.parseInt((s==null||s.equals("")||s.equals("null"))?i:s);
	}
	public static String getString(String s){
		return (s==null)?"":s;
	}
	
	/**
	 * 字符的替换 读取时strContent.Replace("\r\n","<br/>");
	 * 将  		pname= pname.replaceAll("&quot;","\"");
     *          pname= pname.replaceAll("&amp;","&");
	 */
	public static String waphtml2Str(String str){
		
		if(str==null) {
			return "";
		}
		str=str.replaceAll("&quot;","\"");
		str=str.replaceAll("&amp;","&");
		str=str.replaceAll("&lt;","<");
		str=str.replaceAll("&gt;",">");
	    return str;
	}
	
	
	/**
	 * 字符的替换 添加
	 * 将  		pname= pname.replaceAll("&quot;","\"");
     *          pname= pname.replaceAll("&amp;","&");
	 */
	public static String str2Waphtml(String str){
		
		if(str==null) {
			return "";
		}
		str=str.trim();
		str=str.replaceAll("\"","&quot;");
		str=str.replaceAll("\'","’");
		str=str.replaceAll("&","&amp;");
		str=str.replaceAll("<","&lt;");
		str=str.replaceAll(">","&gt;");
	    return str;
	}
	
	/**
	 * 按指定长度截取字符串
	 * @param str源串
	 * @param length 截取的长度
	 * @return
	 * 		如果源串的长度>应截取的长度：将字符串进行截取后返回；
	 *      如果源串的长度<应截取的长度：放回源串
	 */
	public static String substring(String str,int length){
	
			if(str==null) return "";
			if(length==0){
				return str;
			}
			int strlen = str.length();
			return strlen>length?str.substring(0,length)+"..":str;
	}
	
	/**
	 *电信号段133,153,180,189
	 *@param phone 带86的手机号码
	 *@ return 
	 *         true 匹配成功
	 *         false 匹配失败
	 **/
	@Deprecated
	public  static boolean checkPhoneNbl1(String phone){
		Pattern pattern = Pattern.compile("^86133\\d{8}||86153\\d{8}||86180\\d{8}||86189\\d{8}||86181\\d{8}$");
		Matcher matcher = pattern.matcher(phone);
		    
		    if (matcher.matches()) {
		        return true;
		    }
		    return false;
	}
	
	/**
	 *电信号段133,153,180,189,181
	 *@param phone 带86的手机号码
	 *@ return 
	 *         true 匹配成功
	 *         false 匹配失败
	 **/
	public  static boolean checkPhone4TeleWith86(String phone){
		Pattern pattern = Pattern.compile("^86133\\d{8}||86153\\d{8}||86180\\d{8}||86189\\d{8}||86181\\d{8}$");
		Matcher matcher = pattern.matcher(phone);
		    
		    if (matcher.matches()) {
		        return true;
		    }
		    return false;
	}
	
	/**
	 * 联通号段130、131、132、155、156、186（新入网3G用户默认号段）、145（无线上网卡专用）
	 * 电信号段133,153,180,189,181
	 * @param phone
	 * @return
	 */
	public  static boolean checkPhoneNbl(String phone){
		Pattern pattern = Pattern.compile("^86133\\d{8}||86153\\d{8}||86180\\d{8}||86189\\d{8}||86181\\d{8}$");
		Matcher matcher = pattern.matcher(phone);
		    
		    if (matcher.matches()) {
		        return true;
		    }
		    return false;
	}
	
	
	/**
	 * 让数组转为字符串
	 * @param arr
	 * @return
	 */
	
	public String arrayToString(String arr[]){
		  if(arr==null)return "";
		  int allLength=arr.length;
		  String string = "";
		  for(int i=0;i<allLength;i++){
			 /* if(i==0){
				  string=arr[0];
			  }else{
				  string=string+","+arr[i];
			  }
			  */
			  if(arr[i]==null||arr[i].equals("0")){ continue;}
			  string=string+arr[i]+",";
		  }
		  System.out.println("String:"+string);
		  if(string!=null){
			  string = string.substring(0,string.length()-1);
		  }
		  return string;
	  }
	  /**
	   * 返回形如 '1','2','3'的字符串
	   * @param arr
	   * @return
	   */
	  public static String arrayToString1(String arr[]){
		  if(arr==null)return "";
		  int allLength=arr.length;
		  String string = "";
		  for(int i=0;i<allLength;i++){
			 /* if(i==0){
				  string=arr[0];
			  }else{
				  string=string+","+arr[i];
			  }
			  */
			 // System.out.println(arr[i]);
			  if(arr[i]==null||arr[i].equals("0")){ continue;}
			  string=string+"'"+arr[i]+"',";
		  }
		  //System.out.println("String:"+string);
		  if(string!=null){
			  string = string.substring(0,string.length()-1);
		  }
		 
		  return string;
	  }
	
    /**
	 * 查找子串在母串中第n次出现的位置后添加子串
	 */
	public static String getSubIndexInStr(String mStr,String nStr, int level,String newStr){
		String nowStr = "";
		if(mStr != null){
			if(level==0){
				nowStr = newStr+nStr;
			}else{
				String mstrs[] = mStr.split(nStr);
				if(mstrs.length>=level){
					for(int j = 0; j < level ; j++){
						nowStr += mstrs[j]+nStr;
					}
					nowStr+=newStr+nStr;
				}
			}
		}
		return nowStr;
	}
	
	/**
	 * 将字符串按分隔符进行分解，并返回分解后的数组
	 * @param sourceStr   源串
	 * @param separator   分隔符
	 * @return            字符串数组
	 * 					  若源串为空，返回null
	 */
	public static String[] getSplitStrWithArray(String sourceStr , String separator ){

		if(isNull(sourceStr)==1){
			
			String newStr[] = sourceStr.split(separator);
			return newStr;
			
		}else{
			
			return null;
			
		}
	}
	
	/**
	 * 根据固定长度截取字符串
	 * content:内容
	 * length:待截取的长度
	 * @return
	 */
	public static String cutStringByLength(String content,int length){
		if(content==null) return "";
		int clength = content.length();
		if(length<clength){
			return content.substring(0,length)+"...";
		}
		return content;
	}
	
	/**
	 * 根据固定长度截取字符串
	 * content:内容
	 * length:待截取的长度
	 * @return
	 */
	public static String cutstring(String content,int length){
		if(content==null) return "";
		int clength = content.length();
		if(length<clength){
			return content.substring(0,length-3)+"...";
		}
		return content;
	}
	
	/**
	 * 字符串替换 
	 * 将字符串指定位置的字符串替换为指定串
	 * @param args
	 */
	public static String getReplaceStr(int start,int end,String srcStr,String replaceStr){
		
		if(replaceStr==null || srcStr.length()<end) 
			return srcStr;
		
		StringBuffer sb= new StringBuffer();
		sb.append(srcStr.substring(0,start));
		for(int i=0;i<end-start;i++){
			sb.append(replaceStr);
		}
		
		sb.append(srcStr.substring(end));
		return sb.toString();

	}
	
	/**  
     * 判断number参数是否是整型数表示方式  
     * @param number  
     * @return  
     */  
    public static boolean isIntegerNumber(String number){
        number=number.trim();   
        String intNumRegex="\\-{0,1}\\d+";//整数的正则表达式   
        if(number.matches(intNumRegex))   
            return true;   
        else  
            return false;   
    } 
    /**  
     * 判断number参数是否是浮点数表示方式  
     * @param number  
     * @return  
     */  
    public static boolean isFloatPointNumber(String number){
    	
        number=number.trim();   
        String pointPrefix="(\\-|\\+){0,1}\\d*\\.\\d+";//浮点数的正则表达式-小数点在中间与前面   
        String pointSuffix="(\\-|\\+){0,1}\\d+\\.";//浮点数的正则表达式-小数点在后面   
        if(number.matches(pointPrefix)||number.matches(pointSuffix))   
            return true;   
        else  
            return false;
        
    }
	
	public static void main(String[] args){
//		if(StrUtil.checkPhoneNbl1("8613335169899")){
//			System.out.println("匹配成功");
//		}
		System.out.println(isIntegerNumber("13") || isFloatPointNumber("23"));
	}
}

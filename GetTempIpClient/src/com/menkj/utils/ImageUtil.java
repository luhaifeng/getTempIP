package com.menkj.utils;

import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ImageUtil {
	
	private static final Map<String , ImageIcon> cachedImgIconMap = new HashMap<String , ImageIcon>();//imageicon 的 缓存记录 
	private static final Map<String , ImageIcon> NotFoundImgIconMap = new HashMap<String , ImageIcon>();//url获取不到的 缓存记录 ，防止重复获取

    public static void clearC()
	{
	    NotFoundImgIconMap.clear();
	    NotFoundImgIconMap.put("-", null);
	}

	public static void clearA()
	{
	    cachedImgIconMap.clear();
	    cachedImgIconMap.put("-", null);
	}

	public static void cacheImageIcon(String paramString, ImageIcon paramImageIcon)
	{
	    if (paramImageIcon == null)
	      cachedImgIconMap.remove(paramString);
	    else
	      cachedImgIconMap.put(paramString, paramImageIcon);
	}

	public static boolean containsKey(String paramString)
	{
	    return cachedImgIconMap.containsKey(paramString);
	}

    public static Icon E(String url)
	{
	    return getImageIcon(url);
	}
    
    public static byte[] intputStream2ByteArray(InputStream inputStream)
	{
	    if (inputStream != null){
	      try
	      {
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(inputStream.available());
	        int i = -1;
	        while ((i = inputStream.read()) != -1)
	        	byteArrayOutputStream.write(i);
	        return byteArrayOutputStream.toByteArray();
	      }
	      catch (Exception localException)
	      {
	        try
	        {
	        	inputStream.close();
	        }catch (IOException localIOException){
	        }
	      }
	    }
	    return null;
	  }
      
      /**
       * 获取文件流的字节码
       * @param url
       * @return
       */
	  public static byte[] getBytesOfFile(String url)
	  {
	    InputStream inputStream = getInputStream(url, true);
	    return intputStream2ByteArray(inputStream);
	  }

	  /**
	   * 将inputStream转为 bytes
	   * @param url
	   * @param bool
	   * @return
	   */
	  public static byte[] getBytesByUrl(String url, boolean bool)
	  {
	    InputStream inputStream = getInputStream(url, bool);
	    return intputStream2ByteArray(inputStream);
	  }

	  public static final ImageIcon getImageIcon(String url)
	  {
	    return getImageIcon(url, true);
	  }

	  /**
	   * 总入口 
	   * @param paramString
	   * @param paramBoolean
	   * @return
	   */
	  public static final ImageIcon getImageIcon(String url, boolean paramBoolean)
	  {
	    if (url == null)
	      return null;
	    
	    if (cachedImgIconMap.containsKey(url))
	      return (ImageIcon)cachedImgIconMap.get(url);
	    
	    byte[] arrayOfByte = getBytesByUrl(url, paramBoolean);
	    if ((arrayOfByte != null) && (arrayOfByte.length > 0))
	    {
	      ImageIcon imageIcon = new ImageIcon(arrayOfByte);
	      cachedImgIconMap.put(url, imageIcon);
	      return imageIcon;
	    }
	    cachedImgIconMap.put(url, null);
	    
	    //if (paramBoolean)
	      //HdktUtil.handleError("can't load image '" + url + "'", null);
	    return null;
	  }

	  /**
	   * 根据 url获取 image对象 
	   * @param url
	   * @return
	   */
	  public static final Image getImageByUrl(String url)
	  {
	    ImageIcon imageIcon = getImageIcon(url);
	    if (imageIcon != null)
	      return imageIcon.getImage();
	    return null;
	  }
	  
	  /**
	   * 根据路径返回文件的数据流 
	   * @param filePath
	   * @param bool
	   * @return
	   */
	  public static InputStream getInputStream(String filePath, boolean bool)
	  {
	    InputStream inputStream = null;
	    if ((filePath == null) || (filePath.trim().equals("")))  return null;

	    if (NotFoundImgIconMap.containsKey(filePath))  return null;
	    
	    if (filePath.indexOf(":") > 0)//获取网络图片
	      inputStream = getInputStream(filePath, null, bool);
	    else//获取本地图片
	      inputStream = getInputStream(filePath, ImageUtil.class, bool);
	    
	    if ( inputStream == null )
	      NotFoundImgIconMap.put(filePath, null);
	    
	    return inputStream;
	  }
	  
	  /**
	   * 
	   * @param url
	   * @param paramClass
	   * @param paramBoolean
	   * @return
	   */
	  public static InputStream getInputStream(String url, Class paramClass, boolean paramBoolean)
	  {
	    try
	    {
	      URL localURL = null;
	      if (paramClass == null)
	        localURL = new URL(url);
	      else
	        localURL = paramClass.getResource(url);

	      if (localURL == null)
	        return null;
	      URLConnection localURLConnection = localURL.openConnection();
	      return localURLConnection.getInputStream();
	    }
	    catch (Exception localException)
	    {
	    	 //if (paramBoolean)
	   	      //HdktUtil.handleError("can't load image '" + url + "'", localException.getMessage());
	    }
	    return null;
	  }

	public static Icon getIcon(String iconURL) {
		    return getImageIcon(iconURL);
	}
}

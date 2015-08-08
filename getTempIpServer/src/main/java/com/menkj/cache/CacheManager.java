package com.menkj.cache;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存助手
 */
public class CacheManager {
	
	private static Logger log = LoggerFactory.getLogger(CacheManager.class);
	private static CacheProvider provider;
    
	/**
	 * 初始化缓存器
	 */
	static {
		initCacheProvider("com.menkj.cache.EhCacheProvider");
	}
	
	private static void initCacheProvider(String prv_name){
		try{
			/*
			 * 采用委托的方式，来设定提供缓存的类
			 * */
			CacheManager.provider = (CacheProvider)Class.forName(prv_name).newInstance();
			/*
			 * 实例化cacheManage
			 * */
			CacheManager.provider.start();
			log.info("Using CacheProvider : " + provider.getClass().getName());
		}catch(Exception e){
			log.error("Unabled to initialize cache provider:" + prv_name + ", using ehcache default.", e);
			CacheManager.provider = new EhCacheProvider();
		}
	}

	/**
	 * 
	 * @param cache_name
	 * @param autoCreate
	 * @return
	 */
	private final static Cache _GetCache(String cache_name, boolean autoCreate) {
		if(CacheManager.provider == null){
			CacheManager.provider = new EhCacheProvider();
		}
		return provider.buildCache(cache_name, autoCreate);
	}

	/**
	 * 获取缓存中的数据
	 * @param name
	 * @param key
	 * @return
	 *          null--没有获取到，则为空
	 */
	public final static Object get(String name, Serializable key){
		//System.out.println("GET1 => " + name+":"+key);
		if(name!=null && key != null)
			return _GetCache(name, true).get(key);
		return null;
	}
	
	/**
	 * 获取缓存中的数据
	 * @param <T>
	 * @param resultClass
	 * @param name
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final static <T> T get(Class<T> resultClass, String name, Serializable key){
		//System.out.println("GET2 => " + name+":"+key);
		if(name!=null && key != null)
			return (T)_GetCache(name, true).get(key);
		return null;
	}
	
	/**
	 * 写入缓存
	 * @param name
	 * @param key
	 * @param value
	 */
	public final static void set(String name, Serializable key, Serializable value){
		//System.out.println("SET => " + name+":"+key+"="+value);
		if(name!=null && key != null && value!=null)
			_GetCache(name, true).put(key, value);		
	}
	
	/**
	 * 清除缓存中的某个数据
	 * @param name
	 * @param key
	 */
	public final static void evict(String name, Serializable key){
		if(name!=null && key != null)
			_GetCache(name, true).remove(key);		
	}

	/**
	 * 清除缓冲中的某个数据
	 * @param name
	 * @param key
	 */
	public final static void justEvict(String name, Serializable key){
		if(name!=null && key != null){
			Cache cache = _GetCache(name, false);
			if(cache != null)
				cache.remove(key);
		}
	}

}


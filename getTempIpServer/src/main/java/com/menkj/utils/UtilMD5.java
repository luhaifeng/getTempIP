package com.menkj.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UtilMD5 {

	private MessageDigest alg;

	public UtilMD5() {
		try {
			alg = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("MD5 error：" + e);
			e.printStackTrace();
		}
	}

	// 传入要加密的字符串，此函数反回此串的16个字节的摘要密码字符串
	public static String md5_32(String key) {
		String m = key;
		return computeDigest(m.getBytes());
	}

	private static String computeDigest(byte[] b) {
		UtilMD5 u=new UtilMD5();
		u.alg.reset();
		u.alg.update(b);
		byte[] hash = u.alg.digest(); // 得到摘要
		return StrUtil.bytes2Hex(hash);
	}
	public static void main(String[] args){
		System.out.println(md5_32("1111"));
	}
}


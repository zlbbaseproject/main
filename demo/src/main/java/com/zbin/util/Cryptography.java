package com.zbin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;


public class Cryptography {

	private static Logger logger = LoggerFactory.getLogger(Cryptography.class);

	private static final String Algorithm = "DESede"; // 定义 加密算法,可用 DES,DESede,Blowfish

	/**
	 * ECB加密
	 * 
	 * @param original
	 *            源文
	 * @param key
	 *            秘钥
	 * @return
	 */
	public static String tripleDESEncrypt(String original, String key) {
		try {
			byte[] keybyte = key.substring(0, 24).getBytes();
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return Base64.getEncoder().encodeToString(c1.doFinal(original.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
			logger.error("3DES加密出错", e);
		}
		return null;
	}

	/**
	 * ECB解密
	 *
	 * @param cryptograph
	 *            密文
	 * @param key
	 *            秘钥
	 * @return
	 */
	public static String tripleDESDecrypt(String cryptograph, String key) {
		try {
			byte[] keybyte = key.substring(0, 24).getBytes();
			byte[] src = Base64.getDecoder().decode(cryptograph);
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return new String(c1.doFinal(src));
		} catch (Exception e) {
			logger.error("3DES解密出错", e);
		}
		return null;
	}

	/**
	 * CBC加密
	 * 
	 * @param key
	 *            密钥
	 * @param keyiv
	 *            IV
	 * @param original
	 *            明文
	 * @return Base64编码的密文
	 * @throws Exception
	 */
	public static String des3EncodeCBC(byte[] key, byte[] keyiv, String original) {
		try{
			byte[] data = original.getBytes("UTF-8");
			Key deskey = null;
			DESedeKeySpec spec = new DESedeKeySpec(key);
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
			deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(keyiv, 0, 8);
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			byte[] bOut = cipher.doFinal(data);
			return Base64.getEncoder().encodeToString(bOut);
		}catch(Exception e){
			logger.error("CBC加密出错", e);
		}
		return null;
	}

	/**
	 * CBC解密
	 * 
	 * @param key
	 *            密钥
	 * @param keyiv
	 *            IV
	 * @param cryptograph
	 *            Base64编码的密文
	 * @return 明文
	 * @throws Exception
	 */
	public static String des3DecodeCBC(byte[] key, byte[] keyiv,String cryptograph ) {
		try {
			byte[] data = Base64.getDecoder().decode(cryptograph);
			Key deskey = null;
			DESedeKeySpec spec = new DESedeKeySpec(key);
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
			deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(keyiv, 0, 8);
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
			byte[] bOut = cipher.doFinal(data);
			return new String(bOut, "UTF-8");
		} catch (Exception e) {
			logger.error("CBC解密出错", e);
		}
		return null;
	}

	public static void main(String[] args) {
		String[] arr = {"E637CB6D-C753-4868-9EF1-AD04812E4FBA","767BF4D7-2E92-4773-9BEC-02DF4003913F","86F0499E-8D24-42E3-BC9E-259046CC2C9A","6DA05E18-0AC8-4D99-A85E-0A29635B534E","4D8579AF-D464-4A05-96C1-07B0EFF70CF9","6E975F1E-6E50-4809-93E4-01E283AFB48A","49D50086-50C4-4860-9599-A0FBEB698011","FB80126A-9EB4-4BC9-B8D9-0E48B6A5B6E6","F482939E-B18E-4F7A-819E-510FE3A54EDE","B0A282CB-54E2-456C-ABC2-992E3B861147"};
		for(String id:arr){
			test(id);
		}

	}

	public static void test(String id){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String dates = sf.format(new Date());
		id += "|"+dates+"|-Chrome";
		String value = Cryptography.tripleDESEncrypt(id, "~#^&tuandai*%#junte#111!");
		String tuandaim = Cryptography.tripleDESEncrypt(dates, "~#^&tuandai*%#junte#111!");
		try {
			value = URLEncoder.encode(value, "UTF-8");
			tuandaim = URLEncoder.encode(tuandaim, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println();
		System.out.println(id);
		System.out.println("tuandaiw======"+value);
		System.out.println("tuandaim======"+tuandaim);
	}
}

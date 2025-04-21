package com.demo.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class CheckMacValueCalculator {
	
	private static String hashKey;
    private static String hashIV;

    @Value("${security.hashkey}")
    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    @Value("${security.hashiv}")
    public void setHashIV(String hashIV) {
        this.hashIV = hashIV;
    }
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    
    
    

    /**
	 * 產生檢查碼
	 * @param Hashtable<String, String> params
	 * @return
	 */
	public final static String genCheckMacValue( Hashtable<String, String> params){
		Set<String> keySet = params.keySet();
		TreeSet<String> treeSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		treeSet.addAll(keySet);
		String name[] = treeSet.toArray(new String[treeSet.size()]);
		String paramStr = "";
		for(int i = 0; i < name.length; i++){
			if(!name[i].equals("CheckMacValue")){
				paramStr += "&" + name[i] + "=" + params.get(name[i]);
			}
		}
		String urlEncode = urlEncode("Hashkey=" + hashKey + paramStr + "&HashIV=" + hashIV).toLowerCase();
		urlEncode = netUrlEncode(urlEncode);
		return hash(urlEncode.getBytes(), "SHA-256");
	}
	
	
	/**
	 * 將資料做 urlEncode編碼
	 * @param data
	 * @return url encoded string
	 */
	public static String urlEncode(String data){
		String result = "";
		try{
			result = URLEncoder.encode(data, "UTF-8");
		} catch(UnsupportedEncodingException e){
			
		}
		return result; 
	}
	
	/**
	 * 將做完的urlEncode字串做轉換符合 .NET語言的轉換規則
	 * @param url
	 * @return .Net url encoded string
	 */
	private static String netUrlEncode(String url){
		String netUrlEncode = url.replaceAll("%21", "\\!").replaceAll("%28", "\\(").replaceAll("%29", "\\)");
		return netUrlEncode;
	}
	
	/**
	 * 將 byte array 資料做 hash md5或 sha256 運算，並回傳 hex值的字串資料
	 * @param data
	 * @param isMD5
	 * @return string
	 */
	private final static String hash(byte data[], String mode){
		MessageDigest md = null;
		try{
			if(mode == "MD5"){
				md = MessageDigest.getInstance("MD5");
			}
			else if(mode == "SHA-256"){
				md = MessageDigest.getInstance("SHA-256");
			}
		} catch(NoSuchAlgorithmException e){
		}
		return bytesToHex(md.digest(data));
	}
	
	
	/**
	 * 將 byte array 資料轉換成 hex字串值
	 * @param bytes
	 * @return string
	 */
	private final static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
//	public static String calculateCheckMacValue(Map<String, String> params) throws Exception {
//        // 排序參數
//        Map<String, String> sortedParams = new TreeMap<>(params);
//
//        // 格式化參數
//        StringBuilder stringBuilder = new StringBuilder();
//        sortedParams.forEach((key, value) -> stringBuilder.append("&").append(key).append("=").append(value));
//        
//        // 加入 HashKey 和 HashIV
//        String rawString = "HashKey=" + hashKey + stringBuilder.toString() + "&HashIV=" + hashIV;
//
//        // URL 編碼並轉小寫
//        String encodedString = URLEncoder.encode(rawString, StandardCharsets.UTF_8.toString())
//                .toLowerCase()
//                .replace("%23", "#")
//                .replace("%24", "$")
//                .replace("%26", "&")
//                .replace("%27", "'")
//                .replace("%2A", "*")
//                .replace("%2B", "+")
//                .replace("%2C", ",")
//                .replace("%2F", "/")
//                .replace("%3A", ":")
//                .replace("%3B", ";")
//                .replace("%3D", "=")
//                .replace("%3F", "?")
//                .replace("%40", "@")
//                .replace("%5B", "[")
//                .replace("%5D", "]");
//
//        // SHA256 加密並轉大寫
//        MessageDigest md = MessageDigest.getInstance("SHA-256");
//        byte[] digest = md.digest(encodedString.getBytes(StandardCharsets.UTF_8));
//        StringBuilder hexString = new StringBuilder();
//        for (byte b : digest) {
//            String hex = Integer.toHexString(0xff & b);
//            if (hex.length() == 1) hexString.append('0');
//            hexString.append(hex);
//        }
//        return hexString.toString().toUpperCase();
//    }
}

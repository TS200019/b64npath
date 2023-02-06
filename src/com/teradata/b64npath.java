package com.teradata;

import java.util.Base64;
import java.sql.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class b64npath {
	/*
	 * Base64 Encoder 
	 */
	public static String enc( String p1 ) throws SQLException{
		Charset charset = StandardCharsets.UTF_16;
		String s = Base64.getEncoder().encodeToString(p1.getBytes(charset));
		String ret = s;
		return ret;
	} 	
	/*
	 * Base64 Decoder 
	 */
	public static String dec(String p1) throws SQLException{
		Charset charset = StandardCharsets.UTF_16;
		byte[] s = Base64.getDecoder().decode(p1.getBytes());
		String ret = new String(s,charset);
		return ret;
	} 
	/*
	 * Base64 Decoder nPath
	 */
	public static String decary(String p1) throws SQLException{
		Charset charset = StandardCharsets.UTF_16;
		String[] ss = p1.replace("[","").replace("]", "").split(", ");
		String sep = "";
		String ret = "[";
		for (int i = 0; i < ss.length; i++) {
			byte[] s = Base64.getDecoder().decode(ss[i].trim().getBytes());
			ret = ret + sep + new String(s,charset);
			sep = ",　";
		}
		ret += "]";
		return ret;
	} 
}

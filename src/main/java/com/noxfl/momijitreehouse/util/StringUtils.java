/**
 * 
 */
package com.noxfl.momijitreehouse.util;

/**
 * @author Fernando Nathanael
 *
 */
public class StringUtils {

	public static String slugify(String str) {
		return String.join("-", str.toLowerCase().split("[ &,]"));
	}

}

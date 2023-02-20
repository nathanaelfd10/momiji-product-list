/**
 * 
 */
package com.noxfl.momijitreehouse.util;

/**
 * @author Fernando Nathanael
 *
 */
public class StringUtils {

	/**
	 * Slugify with custom separator
	 * 
	 * @param string
	 * @param separator
	 * @return slugified string
	 */
	public static String slugify(String string, String separator, String[] splitter) {
		String splitArg = String.format("[%s]", String.join("", splitter));
		return String.join(separator, string.toLowerCase().split(splitArg));
	}

	/**
	 * Slugify with default separator "-"
	 * 
	 * @param string
	 * @return slugified string
	 */
	public static String slugify(String string) {
		String[] defaultSplitter = { " ", "&", "," };
		String defaultSeparator = "-";
		return slugify(string, defaultSeparator, defaultSplitter);
	}

}

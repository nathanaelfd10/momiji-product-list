/**
 * 
 */
package com.noxfl.momijitreehouse.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	
	/**
	 * Generates a random letter
	 * 
	 * @return random letter
	 */
    public static char generateRandomLetter() {
        Random random = new Random();
        char c = (char)(random.nextInt(26) + 'a');
        return c;
    }
    
    /**
     * Generates a string of random letters
     * 
     * @param length
     * @return a stringn of random letters
     */
    public static String generateRandomLetters(int length) {
        
        StringBuilder sb = new StringBuilder();
        
        for(int i=0;i < length; i++) {
            char randomLetter = generateRandomLetter();
            sb.append(randomLetter);
        }
        
        String output = sb.toString();
        
        return output;
        
    }
    
    /**
     * Generates strings of 3 random letters
     * 
     * @param count
     * @return strings of 3 random letters (example: abc-def-ghi)
     */
    public static String generateRandom3Letters(int count) {
        
        List<String> stringOfRandomLetters = new ArrayList<>();
        
        for(int i=0; i< count; i++) {
            stringOfRandomLetters.add(generateRandomLetters(3));
        }
        
        String output = String.join("-", stringOfRandomLetters);
        
        return output;
    }

}
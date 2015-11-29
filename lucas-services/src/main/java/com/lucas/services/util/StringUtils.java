package com.lucas.services.util;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	   /**
     * This method returns a truncated string to the length specified with trailing elipsis.
     * If the string is less than the truncateLength then it returns the string unmodified.
     * @param inputString
     * @param number
     * @return
     */
    public static String truncate(String inputString, int truncateLength){
    	String q = "";
    	if (inputString != null){
    		if (inputString.length() > truncateLength + 1){
    			q = inputString.substring(0, truncateLength) + "...";
    		} else {
    			q = inputString;
    		}
    	}
    	return q;
    }
    
    
    /**
     * This method returns a truncated string to the length specified with trailing elipsis.
     * If the byte array length is less than the truncateLength then it returns the string representation of he byte array.
     * @param ba
     * @param number
     * @return
     */
    public static String truncate(byte[] ba, int number){
    	String s = new String(ba);
    	return StringUtils.truncate(s, number);
    }

	/**
	 * Method to parse the numeric filter values into array list of operations and numeric values
	 *
	 * @author DiepLe
	 * @param searchText - the text to split out to list
	 * @return
	 */
	public static List<String> processNumericSearchValue(String searchText){
        /*
        Input (searchText)      Output array list
        =====                   ===============
        >-100                   [>, -100]
        <=-100.00               [<=, -100.00]
        =12                     [=, 12]
        !=100                   [!=, 100]
        <>100                   [<>, 100]
        <100                    [<, 100]
        */

		List<String> opsNumericList = new LinkedList<String>();
		final String VALID_PATTERN = "^(<|>|=|<=|>=|<>|!=)(-?[0-9]+(\\.[0-9]+)?)$";

		Pattern pattern = Pattern.compile(VALID_PATTERN);
		Matcher matcher = pattern.matcher(searchText);

		if (matcher.find()) {
			opsNumericList.add(matcher.group(1)); // operations
			opsNumericList.add(matcher.group(2)); // numeric value
		} else {
			System.out.println(searchText + " is not in legal format.");
		}

		return opsNumericList;
	}
}

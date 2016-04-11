package com.eugenefe.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * convert string to camelCaseString
 */
public enum ENamingConvention {

	SNAKE_CASE  ( '_' )
	{ 
		public String convertToCamelCase(String input) {
				return convertToCamelCase(input.toLowerCase(), '_');
		}

	}
	, CAMEL_CASE(' ')
	{
		public String convertToCamelCase(String input) {
			 return input;
		}
	}
	, PASCAL_CASE(' ')
	{
		public String convertToCamelCase(String input) {
			 Character.isLowerCase(input.charAt(0)) ;
			 return input;
		}
	}
	, KEBAB_CASE('-')
	{
		public String convertToCamelCase(String input) {
			return convertToCamelCase(input, '-');
		}
	}
	;

	
	private Character delimeter;
	
	
	private ENamingConvention(Character delimeter) {
		this.delimeter = delimeter;
	}
	
	
	public Character getDelimeter() {
		return delimeter;
	}


	public String convertToCamelCase(String input) {
		return input;
	}
	
	public static String convertToCamelCase(String input, Character delimeter) {
		if (input.indexOf(delimeter) < 0 && Character.isLowerCase(input.charAt(0))) {
			return input;
		}
		StringBuilder result = new StringBuilder();
		boolean nextUpper = false;
		int len = input.length();

		for (int i = 0; i < len; i++) {
			char currentChar = input.charAt(i);
			if (currentChar == delimeter) {
				nextUpper = true;
			} else {
				if (nextUpper) {
					result.append(Character.toUpperCase(currentChar));
					nextUpper = false;
				} else {
					result.append(Character.toLowerCase(currentChar));
				}
			}
		}
		
		return result.toString();
	}
}

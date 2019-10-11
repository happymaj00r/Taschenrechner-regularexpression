package main;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
	
	public static void main(String args[]){
		Scanner scanner = new Scanner(System.in);
		String input = scanner.next();
		if(areParanthesisClosed(input) == true){
			input = removeUselessParanthesis(input);
			calculate(input);
		} else {
			System.out.println("Klammern nicht geschlossen!");
		}
	}
	
	public static void calculate(String input) {
		while(input.contains("(")) {
			String substring = getDeepestParanthesis(input);
			String ergebnis = calculateSubstring(substring);
			input = input.replace("(" + substring + ")", ergebnis);
		}
		input = calculateSubstring(input);
		System.out.println("ergebnis: " + input);
	}
	
	public static String calculateSubstring(String input) {
	      String newLine = input;	      
	      String[] currentPattern = new String[7];
	      int currentIndex = 0;
	      currentPattern[0] = "(\\p{Digit}+)([w])"; //wurzel
	      currentPattern[1] = "[(]((?<!\\p{Digit})[-]?\\p{Digit}+)([*+/-])?([-]?\\p{Digit}+)?[)]"; //klammer
	      currentPattern[2] = "((?<!\\p{Digit})[-]?\\p{Digit}+)([*])([-]?\\p{Digit}+)"; // multi
	      currentPattern[3] = "((?<!\\p{Digit})[-]?\\p{Digit}+)([/])([-]?\\p{Digit}+)"; // div
	      currentPattern[5] = "((?<!\\p{Digit})[-]?\\p{Digit}+)([+])([-]?\\p{Digit}+)"; // plus
	      currentPattern[4] = "((?<!\\p{Digit})[-]?\\p{Digit}+)([-])([-]?\\p{Digit}+)"; // min
	      currentPattern[6] = "^[-]?\\p{Digit}+$"; // final	      

		  while(currentIndex < 7) {		  
			  Pattern r = Pattern.compile(currentPattern[currentIndex]);
			  Matcher m = r.matcher(newLine);	
			  
			  if(Pattern.matches(currentPattern[6], newLine)) {
				  return newLine;
			  } else if (currentIndex >= 6) {
				  currentIndex = 0;
			  }
			  
			  while(m.find()) {
				  	System.out.println("CURRENT INDEX" + currentIndex);
					String fullExpression = m.group(0);			
					String num1 = m.group(1); 
					String operator = m.group(2);
					String num2 = m.group(3);				
					
					if(operator.equalsIgnoreCase("w")) {
						 String ergebnis = "" + sqrtString(num1);	
						 newLine = newLine.replace(fullExpression, ergebnis);	
					}
					if(operator.equalsIgnoreCase("*")) {		
						 String ergebnis = "" + multiplicateString(num1,num2);	
						 newLine = newLine.replace(fullExpression, ergebnis);					 
					}
					if(operator.equalsIgnoreCase("-")) {
						 String ergebnis = "" + subtractString(num1,num2);	
						 newLine = newLine.replace(fullExpression, ergebnis);
					}
					if(operator.equalsIgnoreCase("+")) {
						 String ergebnis = "" + sumString(num1,num2);	
						 newLine = newLine.replace(fullExpression, ergebnis);
					}
					if(operator.equalsIgnoreCase("/")) {
						 String ergebnis = "" + divString(num1,num2);	
						 newLine = newLine.replace(fullExpression, ergebnis);
					}				
					newLine = removeUselessParanthesis(newLine);	
					System.out.println(fullExpression + "######" + newLine);
					m.reset(newLine);
		      }		  
			  currentIndex++;
		  }
		  
		return newLine;      
	}
	
	public static String getDeepestParanthesis(String equation) {
		if(equation.contains("(")) {
			int openingIndex = equation.lastIndexOf("(");
			int closingIndex = equation.indexOf(")", openingIndex);
			
			String newEquation = equation.substring(openingIndex + 1, closingIndex);
			System.out.println(newEquation);
			return newEquation;
		}
		return "";
	}
	
	public static String removeUselessParanthesis(String equation) {
		Pattern pattern = Pattern.compile("[(]([-]?\\p{Digit}+)[)]");
		Matcher matcher = pattern.matcher(equation);
		if(matcher.find()) {
			equation = equation.replaceAll("[(][-]?\\p{Digit}+[)]", matcher.group(1));
		}
		return equation;
	}
	
	public static boolean areParanthesisClosed(String input){
		Pattern p = Pattern.compile("[(]");
		Matcher m = p.matcher(input);
		int openedCount = 0;
		int closedCount = 0;
		while(m.find()){
			openedCount++;
		}
		p = Pattern.compile("[)]");
		m = p.matcher(input);
		while(m.find()){
			closedCount++;
		}
		if(openedCount - closedCount == 0) return true;
		return false;
	}
	
	public static int sqrtString(String num1){
		return (int) Math.sqrt(Integer.parseInt(num1));
	}
	public static int sumString(String num1,String num2){
		return Integer.parseInt(num1) + Integer.parseInt(num2);
	}
	public static int subtractString(String num1,String num2){
		return Integer.parseInt(num1) - Integer.parseInt(num2);
	}
	public static int divString(String num1,String num2){
		return Integer.parseInt(num1) / Integer.parseInt(num2);
	}
	public static int multiplicateString(String num1,String num2){
		return Integer.parseInt(num1) * Integer.parseInt(num2);
	}
}

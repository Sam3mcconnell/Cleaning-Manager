package edu.ncsu.csc316.cleaning.ui;

import java.io.FileNotFoundException; 
import java.time.format.DateTimeFormatter;

import edu.ncsu.csc316.cleaning.manager.ReportManager;


/**
 * This is the UI for the program. It prompts the user to enter commands and gives the user the outputs from 
 * ReportManager. 
 * @author Samuel McConnell
 *
 */
public class CleaningManagerUI {
	
	/** The formatter for the date and time */
	public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
	
	/**
	 * This is the main method of the program that takes user inputs and gives specific values.
	 * @param args as the command line arguments.
	 * @throws FileNotFoundException if a file is not found
	 */
	public static void main(String args[]) throws FileNotFoundException {
		
		System.out.println("Running....");
		
		String roomFile = "input/rooms_20.csv";
		String eventFile = "input/cleaningEvents_20.csv";
		
		ReportManager rm = new ReportManager(roomFile, eventFile);
		System.out.println("Files have been read");
		long start = System.currentTimeMillis();
		rm.getVacuumBagReport("01/01/2014 00:00:00");
		long end = System.currentTimeMillis();
		long elapsedTime = end - start;
		System.out.println(elapsedTime);
		
	
//		Scanner scanner = new Scanner(System.in);
//		
//		String userInput;
//		String roomFile;
//		String eventFile;
//		
//		while (true) {
//			
//			while (true) {
//				System.out.print("Enter the room file: ");
//				roomFile = scanner.nextLine();
//				if (fileExists(roomFile)) {
//					break;
//				}
//				System.out.println("Please enter a correct file");
//			}
//			
//			while (true) {
//				System.out.print("Enter the event file: ");
//				eventFile = scanner.nextLine();
//				if (fileExists(eventFile)) {
//					break;
//				}
//				System.out.println("Please enter a correct file");
//			}
//			
//			ReportManager rm = new ReportManager(roomFile, eventFile);
//			
//			while (true) {
//				
//				System.out.println("Type quit to quit, R to get the Room Report, F to get the Frequency Report, and V to get Vacume bag life");
//				while (true) {
//					System.out.print("Please enter a command: ");
//					userInput = scanner.nextLine();
//					
//					if (userInput.equals("quit") || userInput.equals("R") || userInput.equals("F") || userInput.equals("V")) {
//						break;
//					}
//				}
//				
//				if (userInput.equals("R")) {
//					System.out.println(rm.getRoomReport());
//				}
//				if (userInput.equals("F")) {
//					while (true) {
//						System.out.print("Please enter how many rooms you would like to see: ");
//						userInput = scanner.nextLine();
//						if (isInteger(userInput)) {
//							break;
//						}
//						System.out.println("Please enter a number");
//					}
//					System.out.println(rm.getFrequencyReport(Integer.parseInt(userInput)));
//					
//				}
//				if (userInput.equals("V")) {
//					while (true) {
//						System.out.print("Please enter a date: ");
//						userInput = scanner.nextLine();
//						if (isValidDate(userInput)) {
//							break;
//						}
//						System.out.println("Please enter a correct date");
//					}
//					
//					System.out.println(rm.getVacuumBagReport(userInput));
//				}
//
//				if (userInput.equalsIgnoreCase("quit")) {
//					break;
//				}
//			}
//			
//			if (userInput.equalsIgnoreCase("quit")) {
//				break;
//			}
//			
//		}
//		
//		scanner.close();
		
	}
	
	/**
	 * Private static method to see if a file exists
	 * @param filename as the file name
	 * @return true if the file exists.
	 */
//	private static boolean fileExists(String filename) {
//		try {
//			InputReader.readRoomFile(filename);
//			return true;
//	    } catch(FileNotFoundException ex) {
//	        return false;
//	    }
//    }
//	
	/**
	 * Private static method to see if a string can be turned into an integer.
	 * @param str as the possible integer.
	 * @return true if the string can be turned into an integer.
	 */
//	private static boolean isInteger(String str) {
//		try {
//			Integer.parseInt(str);
//	        return true; 
//	    } catch (NumberFormatException e) {
//	        return false;
//	    }
//	}
	 
	/**
	 * Private static method to see if a string can be turned into a time stamp.
	 * @param dateStr as the possible time stamp.
	 * @return true if the string can be turned into a time stamp.
	 */
//	 private static boolean isValidDate(String dateStr) {
//		 try {
//			 LocalDateTime.parse(dateStr, DATE_TIME_FORMAT);
//			 return true;
//		 } catch (DateTimeParseException e) {
//			 return false;
//		 }
//	 }
	
}

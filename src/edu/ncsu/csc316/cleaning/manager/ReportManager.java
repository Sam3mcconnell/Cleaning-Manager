package edu.ncsu.csc316.cleaning.manager;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;

import edu.ncsu.csc316.cleaning.data.CleaningLogEntry;
import edu.ncsu.csc316.cleaning.dsa.Algorithm;
import edu.ncsu.csc316.cleaning.dsa.DSAFactory;
import edu.ncsu.csc316.cleaning.dsa.DataStructure;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.map.Map.Entry;


/**
 * The ReportManager class is used to create reports of cleaning events, rooms, and vacuums.
 * @author Samuel McConnell
 *
 */
public class ReportManager {

	/** The formatter for the date and time */
    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
    /** The CleaningManager reference */ 
    private CleaningManager manager;
    
    /**
     * The constructor for ReportManager. Sets all of the ADTs used by ReportManager and also 
     * sets manger as the CleaningManger reference.
     * @param pathToRoomFile as the room file.
     * @param pathToLogFile as the log file. 
     * @param mapType as the map type.
     * @throws FileNotFoundException if the file is not found.
     */
    public ReportManager(String pathToRoomFile, String pathToLogFile, DataStructure mapType) throws FileNotFoundException {
        manager = new CleaningManager(pathToRoomFile, pathToLogFile, mapType);
        DSAFactory.setListType(DataStructure.ARRAYBASEDLIST);
        DSAFactory.setComparisonSorterType(Algorithm.QUICKSORT);
        DSAFactory.setNonComparisonSorterType(Algorithm.RADIX_SORT);
        DSAFactory.setMapType(mapType); 
    }
    
    /**
     * This is the other ReportManager constructor. It constructs the ReportManager when a 
     * map type is not provided. 
     * @param pathToRoomFile as the room file.
     * @param pathToLogFile as the event file.
     * @throws FileNotFoundException if the file is not found.
     */
    public ReportManager(String pathToRoomFile, String pathToLogFile) throws FileNotFoundException {
        this(pathToRoomFile, pathToLogFile, DataStructure.SEARCHTABLE);
    }

    /**
     * Returns a the vacuum bag report as a string. Takes a time of when the vacuum was last
     * replaces.
     * @param timestamp as the date and time the vacuum was last replace.
     * @return the vacuum bag report.
     */
    public String getVacuumBagReport(String timestamp) {
    	LocalDateTime dateTime;
    	try {
    		dateTime = LocalDateTime.parse(timestamp, DATE_TIME_FORMAT);
    	} catch (DateTimeParseException e) {
    		return "Date & time must be in the format: MM/DD/YYYY HH:MM:SS";
    	}
    	
    	int i = 5280 - manager.getCoverageSince(dateTime);
    	
    	String report = ""; 
    	if (i < 0) {
    		report = "Vacuum Bag Report (last replaced " + timestamp + ") [\n   Bag is overdue for replacement!\n]";
    	} else {
    		report = "Vacuum Bag Report (last replaced " + timestamp + ") [\n   Bag is due for replacement in " + i + " SQ FT\n]";
    	}

		return report;
    }

    /**
     * Returns a the frequency report as a string. Takes on the number of rooms 
     * that the user wants to see.
     * @param number as the number of rooms the user wants to see.
     * @return the frequency report.
     */
    public String getFrequencyReport(int number) {
    	
    	if (number <= 0) {
    		return "Number of rooms must be greater than 0.";
    	}
    	
    	Map<String, Integer> cleaningFrequency = DSAFactory.getMap(null);
    	Map<String, List<CleaningLogEntry>> m = manager.getEventsByRoom();
    	for (String key : m) {
    		cleaningFrequency.put(key, m.get(key).size());
    	}
    	
    	String[] sortedRooms = new String[cleaningFrequency.size()];
    	int count = 0;
    	for (Entry<String, Integer> entry : cleaningFrequency.entrySet()) {
    		sortedRooms[count] = entry.getKey();
    		count++;
    	}
    	DSAFactory.getComparisonSorter(new FrequencyComparator(cleaningFrequency)).sort(sortedRooms);
    	
    	String returnValue = "Frequency of Cleanings [\n";
    	if (count > number) {
    		count = number;
    	}
    	
    	for (int i = 0; i < count; i++) {
    		returnValue += "   " + sortedRooms[i] + " has been cleaned " + cleaningFrequency.get(sortedRooms[i]) + " times\n";
    	}
    	returnValue += "]";
    	
    	return returnValue;
    
       
    }

    /**
     * Returns the room report.
     * @return the room report.
     */
    public String getRoomReport() {
    	Map<String, List<CleaningLogEntry>> m = manager.getEventsByRoom();
    	boolean checkEachEvent = false;
    	for (List<CleaningLogEntry> l : m.values()) {
    		if (l.size() > 0) {
    			checkEachEvent = true;
    		}
    	}
    	if (m.size() == 0 || !checkEachEvent) { 
    		return "No rooms have been cleaned.";
    	}
    	
    	String[] sortedRooms = new String[m.size()];
    	int count = 0;
    	for (String s : m) {
    		sortedRooms[count] = s;
    		count++;
    	}
    	DSAFactory.getComparisonSorter(new RoomComparator()).sort(sortedRooms);
    	
    	String returnValue = "Room Report [\n";
    	
    	for (int i = 0; i < sortedRooms.length; i++) {
    		returnValue += "   " + sortedRooms[i] + " was cleaned on [\n";
    		if (m.get(sortedRooms[i]).size() == 0) {
    			returnValue += "      (never cleaned)\n";
    		} else {
    			for (CleaningLogEntry s : m.get(sortedRooms[i])) {
    				returnValue += "      " + s.getTimestamp().format(DATE_TIME_FORMAT) + "\n";
    			}
    		}
    		returnValue += "   ]\n";
    	}
    	
    	returnValue += "]";
    	
    	
		return returnValue;
    }
    
    /**
     * This is a private comparator class used to compare the frequency a room has been cleaned.
     * @author Samuel McConnell
     *
     */
    private class FrequencyComparator implements Comparator<String> {
        private Map<String, Integer> cleaningFrequency;

        public FrequencyComparator(Map<String, Integer> cleaningFrequency) {
            this.cleaningFrequency = cleaningFrequency;
        }

        @Override
        public int compare(String room1, String room2) {
        	
        	int frequency1 = 0;
            if (cleaningFrequency.get(room1) != 0) {
            	frequency1 = cleaningFrequency.get(room1);
            }
            int frequency2 = 0;
            if (cleaningFrequency.get(room2) != 0) {
            	frequency2 = cleaningFrequency.get(room2);
            }
            
            if (frequency2 == frequency1) {
            	 return room1.compareTo(room2);
            }
            
            return Integer.compare(frequency2, frequency1); // Descending order
        }
    }
    
    /**
     * This is a private comparator class used to compare the room names.
     * @author Samuel McConnell
     *
     */
    private class RoomComparator implements Comparator<String> {
    	
		@Override
		public int compare(String room1, String room2) {
			return room1.compareTo(room2);
		}
    	
    }
    
    
 }
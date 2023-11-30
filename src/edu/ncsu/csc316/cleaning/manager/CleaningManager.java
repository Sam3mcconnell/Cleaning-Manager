package edu.ncsu.csc316.cleaning.manager;

import java.io.FileNotFoundException;  
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import edu.ncsu.csc316.cleaning.data.CleaningLogEntry;
import edu.ncsu.csc316.cleaning.data.RoomRecord;
import edu.ncsu.csc316.cleaning.dsa.Algorithm;
import edu.ncsu.csc316.cleaning.dsa.DSAFactory;
import edu.ncsu.csc316.cleaning.dsa.DataStructure;
import edu.ncsu.csc316.cleaning.io.InputReader;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;

/**
 * This class reads the room file and the event file. It makes a room list, event list
 * events by room map, and a room map. This class helps ReportManager gather data so it can
 * work properly.
 * @author Samuel McConnell
 *
 */
public class CleaningManager {
	/** The list of rooms */ 
	List<RoomRecord> rooms;
	/** The list of cleaning events */
	List<CleaningLogEntry> events;
	/** The map of cleaning events by room */
	Map<String, List<CleaningLogEntry>> eventsByRoom;
	/** The map of room records by room */
	Map<String, RoomRecord> roomMap;
	/** The formatter for the date and time */
    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

	/**
	 * This is the constructor for CleaningManager. It creates the ADTs and reads the files.
	 * @param pathToRoomFile as the room file.
	 * @param pathToLogFile as the event file. 
	 * @param mapType as the type of map to be used.
	 */
    public CleaningManager(String pathToRoomFile, String pathToLogFile, DataStructure mapType) {
        DSAFactory.setListType(DataStructure.ARRAYBASEDLIST);
        DSAFactory.setComparisonSorterType(Algorithm.QUICKSORT);
        DSAFactory.setNonComparisonSorterType(Algorithm.RADIX_SORT);
        DSAFactory.setMapType(mapType);
        
        this.events = DSAFactory.getIndexedList();
        this.rooms = DSAFactory.getIndexedList();
        try {
        	rooms = InputReader.readRoomFile(pathToRoomFile);
            events = InputReader.readLogFile(pathToLogFile);
        } catch(FileNotFoundException ex) {
        	System.out.println("Invalid file");
        }
        sortEvents();
        eventsByRoom = DSAFactory.getMap(null);
        roomMap = DSAFactory.getMap(null);
        setEventByRoom(); 
        setRoomMap();
        
        
    }
    
    /**
     * This is the other CleaningManager constructor. It constructs the CleaningManager when a 
     * map type is not provided. 
     * @param pathToRoomFile as the room file.
     * @param pathToLogFile as the event file.
     */
    public CleaningManager(String pathToRoomFile, String pathToLogFile) {
        this(pathToRoomFile, pathToLogFile, DataStructure.SEARCHTABLE);
    }
    
    private void sortEvents() {
    	
    	CleaningLogEntry[] entries = new CleaningLogEntry[events.size()];
    	
    	for (int i = 0; i < events.size(); i++) {
    		entries[i] = events.get(i);
    	}
    	
    	DSAFactory.getComparisonSorter(new TimeComparator()).sort(entries);
    	List<CleaningLogEntry> events2 = DSAFactory.getIndexedList();
    	for (int i = 0; i < events.size(); i++) {
    		events2.addLast(entries[i]);
    	}
    	events = events2; 
    }
    
    /**
     * Private method to help set the eventByRoom map
     */
    private void setEventByRoom() {
    	for (int i = 0; i < rooms.size(); i++) {
    		eventsByRoom.put(rooms.get(i).getRoomID(), DSAFactory.getIndexedList());
    	}

    	for (int i = 0; i < events.size(); i++) {
    		
    		if (eventsByRoom.get(events.get(i).getRoomID()) != null) {
    			eventsByRoom.get(events.get(i).getRoomID()).addLast(events.get(i));
    		}
    	}
    }
    
    
    /**
     * Private helper method to set the map of room records by room.
     */
    private void setRoomMap() {
    	for (int i = 0; i < rooms.size(); i++) {
    		roomMap.put(rooms.get(i).getRoomID(), rooms.get(i));
    	}
    }

    /**
     * This method gets the events by room map
     * @return the event by room map.
     */
    public Map<String, List<CleaningLogEntry>> getEventsByRoom() {
		return eventsByRoom; 
    }

    /**
     * This method gets the use of the vacuum by square feet since the parameter time.
     * @param time as the time the vacuum was last reset.
     * @return the total amount of sq ft the vacuum has been used for.
     */
    public int getCoverageSince(LocalDateTime time) {
    	
    	int total = 0;
    	for (int i = 0; i < events.size(); i++) {
    		if (events.get(i).getTimestamp().compareTo(time) >= 0) {
    			total += roomMap.get(events.get(i).getRoomID()).getLength() * 
    					roomMap.get(events.get(i).getRoomID()).getWidth() * 
    					(events.get(i).getPercentCompleted() / 100.0);
    		}
    	}
		return total;
    }
    
    /**
     * This is a private comparator class used to compare the time log a room was cleaned.
     * @author Samuel McConnell
     *
     */
    private class TimeComparator implements Comparator<CleaningLogEntry> {
    	
    	
		@Override
		public int compare(CleaningLogEntry entry1, CleaningLogEntry entry2) {
			
			String time1 = entry1.getTimestamp().format(DATE_TIME_FORMAT);
			String time2 = entry2.getTimestamp().format(DATE_TIME_FORMAT);
			
			String year1 = time1.substring(6, 10);
			String year2 = time2.substring(6, 10);
			String month1 = time1.substring(0, 2);
			String month2 = time2.substring(0, 2);
			String day1 = time1.substring(3, 5);
			String day2 = time2.substring(3, 5);
			String hour1 = time1.substring(11, 13);
			String hour2 = time2.substring(11, 13);
			String minute1 = time1.substring(14, 16);
			String minute2 = time2.substring(14, 16);
			String second1 = time1.substring(17);
			String second2 = time2.substring(17);
			
			if (!year1.equals(year2)) {
				return year2.compareTo(year1);
			} else if (!month1.equals(month2)) {
				return month2.compareTo(month1);
			} else if (!day1.equals(day2)) {
				return day2.compareTo(day1);
			} else if (!hour1.equals(hour2)) {
				return hour2.compareTo(hour1);
			} else if (!minute1.equals(minute2)) {
				return minute2.compareTo(minute1);
			} else if (!second1.equals(second2)) {
				return second2.compareTo(second1);
			} else {
				return 0;
			}
			
		}
    	
    }

}
package edu.ncsu.csc316.cleaning.manager;



import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc316.cleaning.dsa.DataStructure;

class CleaningManagerTest {
	
	public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
	

	@Test
	void testConstructors() {
		CleaningManager cm1 = new CleaningManager("input/rooms.txt", "input/events.txt");
		assertEquals(1, cm1.getEventsByRoom().get("Office").size());
		
		CleaningManager cm2 = new CleaningManager("input/rooms.txt", "input/events.txt", DataStructure.UNORDEREDLINKEDMAP);
		assertEquals(1, cm2.getEventsByRoom().get("Office").size());
	} 
	
	@Test
	void testGetCoverageSince() {
		CleaningManager cm1 = new CleaningManager("input/rooms.txt", "input/events.txt");
		String dateTimeString = "05/28/2021 14:15:02";
		LocalDateTime parsedDateTime = LocalDateTime.parse(dateTimeString, DATE_TIME_FORMAT);
		assertEquals(1538, cm1.getCoverageSince(parsedDateTime));
	}
	
	@Test
	void testGetEventsByRoom() {
		CleaningManager cm1 = new CleaningManager("input/rooms.txt", "input/events.txt");
		assertEquals(1, cm1.getEventsByRoom().get("Office").size());
		assertEquals(3, cm1.getEventsByRoom().get("Dining Room").size());
		assertEquals(6, cm1.getEventsByRoom().get("Living Room").size());
		assertEquals(2, cm1.getEventsByRoom().get("Guest Bedroom").size());
		assertEquals(2, cm1.getEventsByRoom().get("Guest Bathroom").size());
		assertEquals(1, cm1.getEventsByRoom().get("Foyer").size());
		assertEquals(0, cm1.getEventsByRoom().get("Kitchen").size());
	}

}

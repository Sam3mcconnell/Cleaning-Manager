package edu.ncsu.csc316.cleaning.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc316.cleaning.dsa.DataStructure;

class ReportManagerTest {
	


	@Test
	void testGetVacuumBagReport() throws FileNotFoundException {
		ReportManager cm1 = new ReportManager("input/rooms.txt", "input/events.txt");
		String dateTimeString = "05/01/2021 00:00:00";
		String s1 = "Vacuum Bag Report (last replaced 05/01/2021 00:00:00) [\n"
				+ "   Bag is overdue for replacement!\n"
				+ "]";
		assertEquals(s1, cm1.getVacuumBagReport(dateTimeString));
		
		
		String dateTimeString2 = "05/28/2021 14:15:02";
		String s2 = "Vacuum Bag Report (last replaced 05/28/2021 14:15:02) [\n"
				+ "   Bag is due for replacement in 3742 SQ FT\n"
				+ "]"; 
		assertEquals(s2, cm1.getVacuumBagReport(dateTimeString2));
	}
	
	@Test
	void testGetFrequencyReport() throws FileNotFoundException {
		ReportManager cm1 = new ReportManager("input/rooms.txt", "input/events.txt", DataStructure.UNORDEREDLINKEDMAP);
		String s1 = "Frequency of Cleanings [\n"
				+ "   Living Room has been cleaned 6 times\n"
				+ "   Dining Room has been cleaned 3 times\n"
				+ "   Guest Bathroom has been cleaned 2 times\n"
				+ "   Guest Bedroom has been cleaned 2 times\n" 
				+ "   Foyer has been cleaned 1 times\n"
				+ "   Office has been cleaned 1 times\n"
				+ "   Kitchen has been cleaned 0 times\n"
				+ "]";
		assertEquals(s1, cm1.getFrequencyReport(7));
		
		String s2 = "Frequency of Cleanings [\n"
				+ "   Living Room has been cleaned 6 times\n"
				+ "   Dining Room has been cleaned 3 times\n"
				+ "   Guest Bathroom has been cleaned 2 times\n"
				+ "]";
		assertEquals(s2, cm1.getFrequencyReport(3));
		
		String s3 = "Number of rooms must be greater than 0.";
		assertEquals(s3, cm1.getFrequencyReport(-2));
		
	}
	
	
	@Test
	void testGetRoomReport() throws FileNotFoundException {
		ReportManager cm1 = new ReportManager("input/rooms.txt", "input/events.txt", DataStructure.UNORDEREDLINKEDMAP);
		String s1 = "Room Report [\n"
				+ "   Dining Room was cleaned on [\n"
				+ "      05/31/2021 09:27:45\n"
				+ "      05/23/2021 18:22:11\n"
				+ "      05/21/2021 09:16:33\n"
				+ "   ]\n"
				+ "   Foyer was cleaned on [\n"
				+ "      05/01/2021 10:03:11\n"
				+ "   ]\n"
				+ "   Guest Bathroom was cleaned on [\n"
				+ "      05/17/2021 04:37:31\n"
				+ "      05/08/2021 07:01:51\n"
				+ "   ]\n"
				+ "   Guest Bedroom was cleaned on [\n"
				+ "      05/23/2021 11:51:19\n"
				+ "      05/13/2021 22:20:34\n"
				+ "   ]\n"
				+ "   Kitchen was cleaned on [\n"
				+ "      (never cleaned)\n"
				+ "   ]\n"
				+ "   Living Room was cleaned on [\n"
				+ "      05/30/2021 10:14:41\n"
				+ "      05/28/2021 17:22:52\n"
				+ "      05/12/2021 18:59:12\n"
				+ "      05/11/2021 19:00:12\n"
				+ "      05/09/2021 18:44:23\n"
				+ "      05/03/2021 17:22:52\n"
				+ "   ]\n"
				+ "   Office was cleaned on [\n"
				+ "      06/01/2021 13:39:01\n"
				+ "   ]\n"
				+ "]";
		assertEquals(s1, cm1.getRoomReport());
		
		ReportManager cm2 = new ReportManager("input/rooms2.txt", "input/events2.txt", DataStructure.UNORDEREDLINKEDMAP);
		cm2.getRoomReport();
		
	}

}

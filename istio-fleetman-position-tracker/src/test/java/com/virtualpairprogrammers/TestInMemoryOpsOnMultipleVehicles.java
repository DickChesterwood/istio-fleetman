package com.virtualpairprogrammers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;

import com.virtualpairprogrammers.tracker.data.Data;
import com.virtualpairprogrammers.tracker.data.DataBasicInMemoryImpl;
import com.virtualpairprogrammers.tracker.domain.VehicleBuilder;
import com.virtualpairprogrammers.tracker.domain.VehiclePosition;

public class TestInMemoryOpsOnMultipleVehicles {
	
	private Data testData = new DataBasicInMemoryImpl();
	private VehiclePosition[] allReports;
	private VehiclePosition firstReportVehicle1;
	private VehiclePosition secondReportVehicle1;
	private VehiclePosition thirdReportVehicle1;
	private VehiclePosition firstReportVehicle2;
	private VehiclePosition secondReportVehicle2;
	private VehiclePosition thirdReportVehicle2;
	private VehiclePosition fourthReportVehicle2;
	
	public TestInMemoryOpsOnMultipleVehicles() {
		firstReportVehicle1 = new VehicleBuilder()
				.withName("truck1")
				.withLat("1.0")
				.withLng("1.0")
				.withTimestamp(TestUtils.getDateFrom("Wed Feb 01 10:26:12 BST 2017"))
				.build();
		
		secondReportVehicle1 = new VehicleBuilder()
				.withName("truck1")
				.withLat("1.0")
				.withLng("1.0")
				.withTimestamp(TestUtils.getDateFrom("Mon May 01 10:26:12 BST 2017"))
				.build();

		thirdReportVehicle1 = new VehicleBuilder()
				.withName("truck1")
				.withLat("1.0")
				.withLng("1.0")
				.withTimestamp(TestUtils.getDateFrom("Wed Jul 05 10:26:12 BST 2017"))
				.build();

		firstReportVehicle2 = new VehicleBuilder()
						.withName("truck2")
						.withLat("1.0")
						.withLng("1.0")
						.withTimestamp(TestUtils.getDateFrom("Wed Jul 05 10:26:24 BST 2017"))
						.build();
		
		secondReportVehicle2 = new VehicleBuilder()
				.withName("truck2")
				.withLat("4.0")
				.withLng("2.0")
				.withTimestamp(TestUtils.getDateFrom("Wed Jul 05 10:26:30 BST 2017"))
				.build();
		
		thirdReportVehicle2 = new VehicleBuilder()
				.withName("truck2")
				.withLat("1.0")
				.withLng("1.0")
				.withTimestamp(TestUtils.getDateFrom("Thu Jul 06 10:26:12 BST 2017"))
				.build();
		
		fourthReportVehicle2 = new VehicleBuilder()
				.withName("truck2")
				.withLat("1.0")
				.withLng("1.0")
				.withTimestamp(TestUtils.getDateFrom("Wed May 09 19:55:12 BST 2018"))
				.build();		
		
		allReports = new VehiclePosition[] {firstReportVehicle1, secondReportVehicle1, thirdReportVehicle1, 
				                            firstReportVehicle2, secondReportVehicle2, thirdReportVehicle2, fourthReportVehicle2 };
	}
	
	@Test
	public void testGettingAllReportsButWithAVeryRecentCutOffDateReturnsNothing() {
		testData.addAllReports(allReports);
		
		String timeStamp = "Thu May 10 20:00:00 BST 2018";
		Collection<VehiclePosition> results = testData.getLatestPositionsOfAllVehiclesUpdatedSince(TestUtils.getDateFrom(timeStamp));
		assertEquals(0, results.size());
	}

	@Test
	public void testGettingAllReportsButWithAVeryLongAgoSinceReturnsTheLatestReportForEach() {
		testData.addAllReports(allReports);
		
		String timeStamp = "Mon May 10 20:00:00 BST 2010";
		Collection<VehiclePosition> results = testData.getLatestPositionsOfAllVehiclesUpdatedSince(TestUtils.getDateFrom(timeStamp));
		assertEquals(2, results.size());
		assertTrue(results.contains(thirdReportVehicle1));
		assertTrue(results.contains(fourthReportVehicle2));
	}	
	
	@Test
	public void testGettingAllReportsWithACutOffDateTheMeansNoUpdatesForVehicle1() {
		testData.addAllReports(allReports);

		String timeStamp = "Wed Jul 05 10:26:24 BST 2017";
		Collection<VehiclePosition> results = testData.getLatestPositionsOfAllVehiclesUpdatedSince(TestUtils.getDateFrom(timeStamp));
		assertEquals(1, results.size());
		assertFalse(results.contains(thirdReportVehicle1));
		assertTrue(results.contains(fourthReportVehicle2));
	}
	
	@Test
	public void testGettingAllReportsWithNoSinceReturnsAllLatestReports() {
		testData.addAllReports(allReports);
		
		Date timeStamp = null;
		Collection<VehiclePosition> results = testData.getLatestPositionsOfAllVehiclesUpdatedSince(timeStamp);
		assertEquals(2, results.size());
		assertTrue(results.contains(thirdReportVehicle1));
		assertTrue(results.contains(fourthReportVehicle2));
	}	

}

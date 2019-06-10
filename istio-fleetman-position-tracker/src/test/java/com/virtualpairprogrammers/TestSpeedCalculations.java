package com.virtualpairprogrammers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.virtualpairprogrammers.tracker.data.Data;
import com.virtualpairprogrammers.tracker.data.DataBasicInMemoryImpl;
import com.virtualpairprogrammers.tracker.domain.VehicleBuilder;
import com.virtualpairprogrammers.tracker.domain.VehicleNotFoundException;
import com.virtualpairprogrammers.tracker.domain.VehiclePosition;

/**
 * These tests are based on rough measurements on a real map. Therefore, we only expect
 * results to be correct within 0.1mph. This is good enough for this system anyway.
 */
public class TestSpeedCalculations {

	@Test
	public void testSpeedAsMeasuredBetweenTwoPointsCoveredInFiveSeconds() throws VehicleNotFoundException {
		Data data = new DataBasicInMemoryImpl();
		
		// These data points measured on a map
		// 1: 53.33507, -1.53766
		VehiclePosition report1 = new VehicleBuilder()
										.withName("city_truck")
										.withLat("53.33507")
										.withLng("-1.53766")
										.withTimestamp(TestUtils.getDateFrom("Wed Jul 05 10:26:24 BST 2017"))
										.build();
										
		data.updatePosition(report1);
		
		VehiclePosition pos = data.getLatestPositionFor("city_truck");
		assertNull("Expected speed of vehicle with one report is null", pos.getSpeed());
		
		// Point 2 is measured at 153m apart. 53.33635, -1.53682
		VehiclePosition report2 = new VehicleBuilder()
				.withName("city_truck")
				.withLat("53.33635")
				.withLng("-1.53682")
				.withTimestamp(TestUtils.getDateFrom("Wed Jul 05 10:26:29 BST 2017"))
				.build();
		
		data.updatePosition(report2);
		
		pos = data.getLatestPositionFor("city_truck");
		
		// The two points are 153m apart, covered in 5s which gives 30.6m/s
		// This equates to 68.45025 mph. We're not expecting any of this to be dead accurate!
		// We'll go for within 0.1 mph...
		
		assertEquals(68.45025, pos.getSpeed().doubleValue(), 0.1);
	}

	@Test
	public void testSpeedWhenTravellingExactlyOneKilometerInOneMinute() throws VehicleNotFoundException {
		Data data = new DataBasicInMemoryImpl();
		
		// These two points are on OS grid lines 1km apart, as measured by Memory Map.
		VehiclePosition report1 = new VehicleBuilder()
				.withName("city_truck")
				.withLat("53.33393")
				.withLng("-1.52097")
				.withTimestamp(TestUtils.getDateFrom("Wed Jul 05 10:26:00 BST 2017"))
				.build();
		data.updatePosition(report1);
		
		VehiclePosition pos = data.getLatestPositionFor("city_truck");
		assertNull("Expected speed of vehicle with one report is null", pos.getSpeed());
		
		VehiclePosition report2 = new VehicleBuilder()
				.withName("city_truck")
				.withLat("53.34292")
				.withLng("-1.52083")
				.withTimestamp(TestUtils.getDateFrom("Wed Jul 05 10:27:00 BST 2017"))
				.build();
		data.updatePosition(report2);
		
		pos = data.getLatestPositionFor("city_truck");
		
		// 1km apart, gives a speed of 16.67m/s ie 37.28mph
		assertEquals(37.28, pos.getSpeed().doubleValue(), 0.1);
	}
	
	@Test
	public void testStationaryVehicle() throws VehicleNotFoundException {
		Data data = new DataBasicInMemoryImpl();
		
		VehiclePosition report1 = new VehicleBuilder()
				.withName("city_truck")
				.withLat("53.33393")
				.withLng("-1.52097")
				.withTimestamp(TestUtils.getDateFrom("Wed Jul 05 10:26:00 BST 2017"))
				.build();
		data.updatePosition(report1);
		
		VehiclePosition pos = data.getLatestPositionFor("city_truck");
		assertNull("Expected speed of vehicle with one report is null", pos.getSpeed());
		
		VehiclePosition report2 = new VehicleBuilder()
				.withName("city_truck")
				.withLat("53.33393")
				.withLng("-1.52097")
				.withTimestamp(TestUtils.getDateFrom("Wed Jul 05 10:26:05 BST 2017"))
				.build();
		
		data.updatePosition(report2);
		
		pos = data.getLatestPositionFor("city_truck");
		
		assertEquals(0, pos.getSpeed().doubleValue(), 0);
	}
	
	@Test
	public void testSpeedIsBasedOnlyOnLastReport() throws VehicleNotFoundException {
		Data data = new DataBasicInMemoryImpl();
		
		// These two points are on OS grid lines 1km apart, as measured by Memory Map.
		VehiclePosition report1 = new VehicleBuilder()
				.withName("city_truck")
				.withLat("53.33393")
				.withLng("-1.52097")
				.withTimestamp(TestUtils.getDateFrom("Wed Jul 05 10:26:00 BST 2017"))
				.build();
		data.updatePosition(report1);
			
		VehiclePosition report2 = new VehicleBuilder()
				.withName("city_truck")
				.withLat("53.34292")
				.withLng("-1.52083")
				.withTimestamp(TestUtils.getDateFrom("Wed Jul 05 10:27:00 BST 2017"))
				.build();
		data.updatePosition(report2);
		
		VehiclePosition report3 = new VehicleBuilder()
				.withName("city_truck")
				.withLat("53.33635")
				.withLng("-1.53682")
				.withTimestamp(TestUtils.getDateFrom("Wed Jul 05 10:28:24 BST 2017"))
				.build();		
		data.updatePosition(report3);
		
		VehiclePosition pos = data.getLatestPositionFor("city_truck");
		
		// This last leg is 1.29km, and it took 84 seconds. 34.35mph 
		assertEquals(34.35, pos.getSpeed().doubleValue(), 0.1);
	}	
}

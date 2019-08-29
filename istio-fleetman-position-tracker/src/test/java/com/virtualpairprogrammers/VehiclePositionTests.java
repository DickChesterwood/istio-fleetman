package com.virtualpairprogrammers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Date;

import org.junit.Test;

import com.virtualpairprogrammers.tracker.domain.VehicleBuilder;
import com.virtualpairprogrammers.tracker.domain.VehiclePosition;

public class VehiclePositionTests {

	private static final Date TIMESTAMP = TestUtils.getDateFrom("Wed Feb 01 10:26:12 BST 2017");

	@Test
	public void testEqualityOfVehiclePositions()
	{
		// If two vehicle positions for a named vehicle have the same timestamp, then they're equal
		// This was decided after some agonising: surely we should also discriminate
		// on the lat/longs as well? Well yes but in reality, if we have two vehiclepositions
		// with exactly the same time, then it must be the same VehiclePosition: a vehicle
		// can't be in two places at the same time.
		VehiclePosition one = new VehicleBuilder().withName("truck").withTimestamp(TIMESTAMP).build();
		VehiclePosition two = new VehicleBuilder().withName("truck").withTimestamp(TIMESTAMP).build();
		assertEquals(one, two);
	}
	
	@Test
	public void testNonEquality()
	{
		VehiclePosition one = new VehicleBuilder().withName("truck").withTimestamp(TestUtils.getDateFrom("Wed Feb 01 10:26:11 BST 2017")).build();
		VehiclePosition two = new VehicleBuilder().withName("truck").withTimestamp(TIMESTAMP).build();
		assertNotEquals(one, two);
	}
	
	@Test
	public void testDifferentVehiclesAreNeverEqual()
	{
		VehiclePosition one = new VehicleBuilder().withName("truck").withTimestamp(TIMESTAMP).build();
		VehiclePosition two = new VehicleBuilder().withName("truckdifferent").withTimestamp(TIMESTAMP).build();
		assertNotEquals(one, two);
	}
	
	
	
}

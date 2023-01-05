package com.virtualpairprogrammers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.virtualpairprogrammers.simulator.utils.VehicleNameUtils;

public class PositionsimulatorApplicationTests {

	@Test
	public void testPrettifyOfSimpleVehicleName() {
		String originalName = "truck";
		assertEquals("Truck", VehicleNameUtils.prettifyName(originalName));
	}

	@Test
	public void testUnderscoresRemoved() {
		String originalName = "truck_a";
		assertEquals("Truck A", VehicleNameUtils.prettifyName(originalName));
	}
	
}

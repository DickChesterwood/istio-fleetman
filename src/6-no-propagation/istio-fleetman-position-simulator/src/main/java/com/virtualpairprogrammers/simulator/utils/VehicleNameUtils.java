package com.virtualpairprogrammers.simulator.utils;

import org.apache.commons.text.WordUtils;

public class VehicleNameUtils {

	public static String prettifyName(String originalName) {
		return WordUtils.capitalizeFully(originalName.replaceAll("_", " "));
	}

	public static String uglifyName(String vehicleName) {
		return vehicleName.replaceAll(" ", "_").toLowerCase();
	}
}

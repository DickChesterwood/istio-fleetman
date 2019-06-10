package com.virtualpairprogrammers.simulator.utils;

import org.apache.commons.text.WordUtils;

public class VehicleNameUtils {

	public static String prettifyName(String originalName) {
		return WordUtils.capitalizeFully(originalName.replaceAll("_", " "));
	}

}

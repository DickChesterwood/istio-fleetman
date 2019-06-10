package com.virtualpairprogrammers.tracker.data;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;

import com.virtualpairprogrammers.tracker.domain.VehicleBuilder;
import com.virtualpairprogrammers.tracker.domain.VehicleNotFoundException;
import com.virtualpairprogrammers.tracker.domain.VehiclePosition;

/**
 * This is a very quick and dirty implementation of a Mongo data store. The implementations
 * are certainly sub-optimal. This may be improved in a future release, but the purpose of 
 * this was to demonstrate how mongo or similar persistent stores can be deployed
 * to a K8S cluster.
 */
@Repository
// For now this has been disabled as mongo may exhaust RAM on some user's systems)
public class DataMongoDbImpl implements Data {

	@Autowired
	private PositionRepository mongoDb;
	
	@Override
	public void updatePosition(VehiclePosition position) {
		mongoDb.insert(position);
	}

	@Override
	public VehiclePosition getLatestPositionFor(String vehicleName) throws VehicleNotFoundException {
		// A *very* basic implementation!
		Example<VehiclePosition> example = Example.of(new VehicleBuilder().withName(vehicleName).build());
		List<VehiclePosition> all = mongoDb.findAll(example);
		if (all.size() == 0) throw new VehicleNotFoundException();
		return all.get(all.size() - 1);
	}

	@Override
	public void addAllReports(VehiclePosition[] allReports) {
		for (VehiclePosition next: allReports)
		{
			this.updatePosition(next);
		}
	}

	@Override
	public Collection<VehiclePosition> getLatestPositionsOfAllVehiclesUpdatedSince(Date since) {
		return mongoDb.findByTimestampAfter(since);
	}

	@Override
	public TreeSet<VehiclePosition> getAllReportsForVehicleSince(String name, Date timestamp)
			throws VehicleNotFoundException {
		return new TreeSet<VehiclePosition>(mongoDb.findByNameAndTimestampAfter(name, timestamp));
	}

	@Override
	public Collection<VehiclePosition> getHistoryFor(String vehicleName) throws VehicleNotFoundException {
		VehiclePosition position = new VehicleBuilder().withName(vehicleName).build();
		Example<VehiclePosition> example = Example.of(position);
		return new TreeSet<VehiclePosition>(mongoDb.findAll(example)); // just a hack to sort correctly
	}

}

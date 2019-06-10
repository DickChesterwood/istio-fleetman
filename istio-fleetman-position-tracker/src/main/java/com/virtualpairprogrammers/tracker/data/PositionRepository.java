package com.virtualpairprogrammers.tracker.data;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.virtualpairprogrammers.tracker.domain.VehiclePosition;

public interface PositionRepository extends MongoRepository<VehiclePosition, String> {
	List<VehiclePosition> findByNameAndTimestampAfter(String name, Date timestamp);
	Collection<VehiclePosition> findByTimestampAfter(Date since);
}

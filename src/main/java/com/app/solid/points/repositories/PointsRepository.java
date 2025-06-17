package com.app.solid.points.repositories;

import com.app.solid.points.models.Points;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PointsRepository extends MongoRepository<Points, String> {

}

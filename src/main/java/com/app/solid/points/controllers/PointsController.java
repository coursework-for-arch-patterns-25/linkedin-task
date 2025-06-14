package com.app.solid.points.controllers;

import com.app.solid.points.models.Points;
import com.app.solid.points.repositories.PointsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/points")
public class PointsController {
    private final PointsRepository repository;

    PointsController(PointsRepository repository){
        this.repository = repository;
    }

    @GetMapping("/")
    List<Points> getAll(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    Optional<Points> getPointsByParticipantId(@PathVariable String id){
        return repository.findById(id);
    }

    @PatchMapping("/{id}")
    ResponseEntity<String> modifyPointsByParticipantId(@PathVariable String id, @RequestBody int score){
        var pointsRecord = repository.findByParticipantId(id);
        if (pointsRecord.isPresent()){
            var existingPoints = pointsRecord.get();
            int updatedScore = existingPoints.getScore() + score;
            updatedScore = Math.max(updatedScore, 0);
            existingPoints.setScore(updatedScore);
            repository.save(existingPoints);
            return ResponseEntity.ok("Points modified successfully!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Participant not found!");
    }
}

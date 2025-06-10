package com.app.solid.points.controllers;

import com.app.solid.points.DTO.PointsPatchDTO;
import com.app.solid.points.models.Points;
import com.app.solid.points.repositories.PointsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/points")
public class PointsController {
    private final PointsRepository repository;

    private final HashMap<String, Integer> pointsCache = new HashMap<>();
    private String allPointsCache = "";

    PointsController(PointsRepository repository){
        this.repository = repository;
    }

    @GetMapping("/")
    ResponseEntity<?> getAll(@RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch){
        if (allPointsCache.equals(ifNoneMatch)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
        List<Points> points = repository.findAll();
        allPointsCache = String.valueOf(points.hashCode());
        return ResponseEntity.status(HttpStatus.OK).eTag(allPointsCache).body(points);
    }

    // SECURED: Ensures a user can only get their own points.
    @GetMapping("/{id}")
    ResponseEntity<?> getPointsByParticipantId(
        @AuthenticationPrincipal OAuth2User principal,
        @RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch,
        @PathVariable String id
    ){
        // SECURITY CHECK: The path variable 'id' must match the authenticated user's ID.
        if (!principal.getName().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to view this resource.");
        }

        if (pointsCache.containsKey(ifNoneMatch)){
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        Optional<Points> pointsRecord =  repository.findByParticipantId(id);

        if (pointsRecord.isPresent()){
            var existingPoints = pointsRecord.get();
            int hashedRecord = existingPoints.hashCode();
            pointsCache.put(String.valueOf(hashedRecord), 1);
            return ResponseEntity.status(HttpStatus.OK).eTag(String.valueOf(hashedRecord)).body(pointsRecord.get());
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // SECURED: Ensures a user can only modify their own points.
    @PutMapping("/{id}")
    ResponseEntity<?> modifyPointsById(
        @AuthenticationPrincipal OAuth2User principal,
        @RequestHeader(value = "If-Match", required = false) String ifMatch,
        @PathVariable String id,
        @RequestBody Points points
    ){
        // SECURITY CHECK
        if (!principal.getName().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to modify this resource.");
        }

        var pointsRecord = repository.findByParticipantId(id);

        if (pointsRecord.isPresent()){
            var existingPoints = pointsRecord.get();
            int hashedRecord = existingPoints.hashCode();
            if (String.valueOf(hashedRecord).equals(ifMatch)){
                pointsCache.remove(String.valueOf(hashedRecord));
                allPointsCache = "";

                existingPoints.setScore(points.getScore());
                // Security improvement: Don't let the user change their participantId.
                // existingPoints.setParticipantId(points.getParticipantId());
                repository.save(existingPoints);
                return ResponseEntity.ok("Points modified successfully!");
            }
            else{
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Participant not found!");
    }

    // SECURED: Ensures a user can only patch their own points.
    @PatchMapping("/{id}")
    ResponseEntity<String> modifyPointsByParticipantId(
        @AuthenticationPrincipal OAuth2User principal,
        @PathVariable String id,
        @RequestBody PointsPatchDTO patch
    ){
        if (!principal.getName().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to modify this resource.");
        }

        var pointsRecord = repository.findByParticipantId(id);

        if (pointsRecord.isPresent()){
            var existingPoints = pointsRecord.get();
            int score = existingPoints.getScore();
            int hashedRecord = existingPoints.hashCode();
            pointsCache.remove(String.valueOf(hashedRecord));
            allPointsCache = "";
            existingPoints.setScore(score + patch.getScore());
            repository.save(existingPoints);
            return ResponseEntity.ok("Points modified successfully!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Participant not found!");
    }
}

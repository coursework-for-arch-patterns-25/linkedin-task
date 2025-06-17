package com.app.solid.points.controllers;

import com.app.solid.points.models.Points;
import com.app.solid.points.repositories.PointsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    Optional<Points> getPointsById(@PathVariable String id){
        return repository.findById(id);
    }
}

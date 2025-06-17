package com.app.solid.points.models;

import org.springframework.data.annotation.Id;

public record Points(@Id String id, int score) {
    public Points(){
        this("", -1);
    }
}

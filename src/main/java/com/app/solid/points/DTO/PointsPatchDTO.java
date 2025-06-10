package com.app.solid.points.DTO;

public class PointsPatchDTO {
    private final int score;

    PointsPatchDTO(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}

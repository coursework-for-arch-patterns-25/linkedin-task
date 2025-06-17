package com.app.solid.points.models;

import org.springframework.data.annotation.Id;

public class Points {
    @Id
    String id;
    String participantId;
    int score;

    public Points(String id, String participantId, int score){
        this.id = id;
        this.participantId = participantId;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int updatedScore) {
        this.score = updatedScore;
    }
}

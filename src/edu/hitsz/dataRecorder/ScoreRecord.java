package edu.hitsz.dataRecorder;


import java.util.Date;

public class ScoreRecord {
    private String playerName;
    private final int score;
    private final Date date;

    public ScoreRecord(String playerName, int score, Date date) {
        this.playerName = playerName;
        this.score = score;
        this.date = date;
    }

    public String getPlayerName() {

        return playerName;
    }
    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }
    public int getScore() {

        return score;
    }

    public Date getDate() {

        return date;
    }
}


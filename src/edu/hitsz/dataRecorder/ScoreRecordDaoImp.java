package edu.hitsz.dataRecorder;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ScoreRecordDaoImp implements ScoreRecordDao {
    private final String filePath = "src\\edu\\hitsz\\dataRecorder\\ScoreRecorder.txt";

    //    BufferedWriter bufferedWriter;
    @Override
    public List<ScoreRecord> getAllScoreRecord() {
        List<ScoreRecord> scoreRecords = new ArrayList<>();
        String playerNameString;
        String scoreString;
        String dateString;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            while ((playerNameString = bufferedReader.readLine()) != null) {
                scoreString = bufferedReader.readLine();
                dateString = bufferedReader.readLine();
                scoreRecords.add(new ScoreRecord(
                        playerNameString,
                        Integer.parseInt(scoreString),
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString)));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return scoreRecords;
    }

    @Override
    public void addScoreRecord(ScoreRecord scoreRecord) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true));
            bufferedWriter.write(scoreRecord.getPlayerName() + "\r\n");
            bufferedWriter.write(scoreRecord.getScore() + "\r\n");
            bufferedWriter.write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\r\n");
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAllScoreRecord() {

    }

    @Override
    public List<ScoreRecord> getAllSortedScoreRecord() {
        List<ScoreRecord> scoreRecords = getAllScoreRecord();
        scoreRecords.sort(new Comparator<ScoreRecord>() {
            @Override
            public int compare(ScoreRecord o1, ScoreRecord o2) {
                return o2.getScore() - o1.getScore();
            }
        });
        return scoreRecords;
    }

    @Override
    public void printScoreRank() {
        List<ScoreRecord> scoreRecords = getAllSortedScoreRecord();
        int i = 1;
//        List<ScoreRecord> scoreRecords = getAllScoreRecord();
//        scoreRecords.sort(new Comparator<ScoreRecord>() {
//            @Override
//            public int compare(ScoreRecord o1, ScoreRecord o2) {
//                return o2.getScore() - o1.getScore();
//            }
//        });
        for (ScoreRecord scoreRecord : scoreRecords) {
            System.out.print("第" + i + "名：");
            System.out.println(scoreRecord.getPlayerName() + " " +
                    scoreRecord.getScore() + " " +
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(scoreRecord.getDate()));
            i++;
        }
    }

    @Override
    public void refreshScoreRecord(List<ScoreRecord> scoreRecords) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, false));
            for(ScoreRecord scoreRecord : scoreRecords) {
                bufferedWriter.write(scoreRecord.getPlayerName());
                bufferedWriter.newLine();
                bufferedWriter.write(String.valueOf(scoreRecord.getScore()));
                bufferedWriter.newLine();
                bufferedWriter.write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
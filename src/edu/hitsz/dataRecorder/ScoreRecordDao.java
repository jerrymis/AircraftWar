package edu.hitsz.dataRecorder;

import java.util.List;

public interface ScoreRecordDao {
    List<ScoreRecord> getAllScoreRecord();


    /**
     * 添加数据记录
     *  分数记录
     */
    void addScoreRecord(ScoreRecord scoreRecord);
    /**
     * 删除数据记录
     */
    void deleteAllScoreRecord();

    List<ScoreRecord> getAllSortedScoreRecord();

    /**
     * 将保存的所有分数记录按降序排序后打印到控制台
     */
    void printScoreRank();

    void refreshScoreRecord(List<ScoreRecord> scoreRecords);
}

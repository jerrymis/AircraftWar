package edu.hitsz.application;

import edu.hitsz.dataRecorder.ScoreRecord;
import edu.hitsz.dataRecorder.ScoreRecordDao;
import edu.hitsz.dataRecorder.ScoreRecordDaoImp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class RankList {
    private JPanel rankTablePanel;
    private JButton deleteButton;
    private JTable rankTable;
    private JScrollPane rankScrollPane;
    private JPanel deletePanel;

    private String[] columnName;
    private String[][] tableData;
    private final ScoreRecordDao scoreRecordDao;

    public RankList() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = rankTable.getSelectedRow();
                System.out.println(row);
                if (row != -1) {
                    List<ScoreRecord> scoreRecords = scoreRecordDao.getAllSortedScoreRecord();
                    scoreRecords.remove(row);
                    scoreRecordDao.refreshScoreRecord(scoreRecords);
                    refreshTable();
                }
            }
        });

        scoreRecordDao = new ScoreRecordDaoImp();
        refreshTable();
        rankScrollPane.setViewportView(rankTable);
    }

    private void refreshTable() {
        List<ScoreRecord> scoreRecords = scoreRecordDao.getAllSortedScoreRecord();

        columnName = new String[]{"排名", "玩家", "得分", "游戏时间"};
        tableData = new String[scoreRecords.size()][4];

        int i = 0;
        for (ScoreRecord scoreRecord : scoreRecords) {
            tableData[i][0] = (i + 1) + "";
            tableData[i][1] = scoreRecord.getPlayerName();
            tableData[i][2] = scoreRecord.getScore() + "";
            tableData[i][3] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(scoreRecord.getDate());
            i++;
        }

        rankTable.setModel(new DefaultTableModel(tableData, columnName) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        });
    }

    public JPanel getRankPanel() {
        return rankTablePanel;
    }

}

package edu.hitsz.application;

import javax.swing.*;
import java.awt.*;

/**
 * 程序入口
 *
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    public static final Object LOCK = new Object();

    public static void main(String[] args) {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new Menu().getMainPanel();
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
        try {
            synchronized (LOCK) {
                while (mainPanel.isVisible()) {
                    LOCK.wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Game game;
        if (Menu.flag == 1) {
            game = new SimpleGame();
        } else if (Menu.flag == 2) {
            game = new NormalGame();
        } else {
            game = new HardGame();
        }
        frame.setContentPane(game);
        frame.setVisible(true);
        game.action();
        try {
            synchronized (LOCK) {
                while (!(game.getGameOverFlag())) {
                    LOCK.wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JPanel rankPanel = new RankList().getRankPanel();
        frame.setContentPane(rankPanel);
        frame.setVisible(true);
    }
}

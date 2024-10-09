package edu.hitsz.application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    private JPanel mainPanel;
    private JButton simplePattern;
    private JButton hardPattern;
    private JCheckBox sound;
    private JButton normalPattern;
    public static int flag = 0;
    public Menu() {
        simplePattern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag = 1;
                ImageManager.BACKGROUND_IMAGE = ImageManager.SIMPLE_BACKGROUND_IMAGE;
                mainPanel.setVisible(false);
                System.out.println("菜单消失");
                synchronized (Main.LOCK) {
                    Main.LOCK.notify();
                }
            }
        });
        normalPattern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag = 2;
                ImageManager.BACKGROUND_IMAGE = ImageManager.NORMAL_BACKGROUND_IMAGE;
                mainPanel.setVisible(false);
                synchronized (Main.LOCK) {
                    Main.LOCK.notify();
                }
            }
        });
        hardPattern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag = 3;
                ImageManager.BACKGROUND_IMAGE = ImageManager.HARD_BACKGROUND_IMAGE;
                mainPanel.setVisible(false);
                synchronized (Main.LOCK) {
                    Main.LOCK.notify();
                }
            }
        });
        sound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MusicManager.audioAvailable = sound.isSelected();
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
    public JCheckBox getSoundCheckBox() {return sound;}
}

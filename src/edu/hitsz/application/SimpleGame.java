package edu.hitsz.application;

import edu.hitsz.dataRecorder.ScoreRecord;
import edu.hitsz.factory.aircraft_factory.BossEnemyFactory;
import edu.hitsz.factory.aircraft_factory.EliteEnemyFactory;
import edu.hitsz.factory.aircraft_factory.MobEnemyFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SimpleGame extends Game{
    @Override
    public void action() {
        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {
            time += timeInterval;

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                // 新敌机产生
                if (enemyAircrafts.size() < enemyMaxNumber) {
//                    if (score >= j && i == 0 && !isBossEnemyExits) {
//                        musicManager.interruptBackGroundMusic();
//                        musicManager.playBossMusic();
//                        aircraftFactory = new BossEnemyFactory();
//                        enemyAircrafts.add(aircraftFactory.createAircraft(
//                                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.BOSS_ENEMY_IMAGE.getWidth())) ,
//                                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) ,
//                                ((BossEnemyFactory) aircraftFactory).getSpeedX(),
//                                ((BossEnemyFactory) aircraftFactory).getSpeedY(),
//                                ((BossEnemyFactory) aircraftFactory).getHp(),
//                                ((BossEnemyFactory) aircraftFactory).getShootStrategy()
//                        ));
//                        i++;
//                        isBossEnemyExits = true;
//                    } else {
                        if (Math.random() <= 0.25) {
                            aircraftFactory = new EliteEnemyFactory();
                            enemyAircrafts.add(aircraftFactory.createAircraft(
                                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())) ,
                                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) ,
                                    ((EliteEnemyFactory) aircraftFactory).getSpeedX(),
                                    ((EliteEnemyFactory) aircraftFactory).getSpeedY(),
                                    ((EliteEnemyFactory) aircraftFactory).getHp(),
                                    ((EliteEnemyFactory) aircraftFactory).getShootStrategy()
                            ));
                        } else {
                            aircraftFactory = new MobEnemyFactory();
                            enemyAircrafts.add(aircraftFactory.createAircraft(
                                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())) ,
                                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) ,
                                    ((MobEnemyFactory) aircraftFactory).getSpeedX(),
                                    ((MobEnemyFactory) aircraftFactory).getSpeedY(),
                                    ((MobEnemyFactory) aircraftFactory).getHp(),
                                    ((MobEnemyFactory) aircraftFactory).getShootStrategy()
                            ));
                        }

                }
                // 飞机射出子弹
                shootAction();
            }
            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            //道具移动
            itemsMoveAction();



            // 撞击检测
            crashCheckAction();


            getScoresAndProps();
            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();


            // 游戏结束检查
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                executorService.shutdown();
                gameOverFlag = true;
                System.out.println("Game Over!");
                scoreRecordDao.addScoreRecord(new ScoreRecord("player", score, new Date()));
                scoreRecordDao.printScoreRank();
                musicManager.playGameOverAudio();
                musicManager.interruptBossMusic();
                musicManager.interruptBackGroundMusic();
                synchronized (Main.LOCK) {
                    Main.LOCK.notify();
                }
            }
        };
        musicManager.playBackgroundMusic();


        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }
    }


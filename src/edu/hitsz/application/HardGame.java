package edu.hitsz.application;

import edu.hitsz.dataRecorder.ScoreRecord;
import edu.hitsz.factory.aircraft_factory.BossEnemyFactory;
import edu.hitsz.factory.aircraft_factory.EliteEnemyFactory;
import edu.hitsz.factory.aircraft_factory.MobEnemyFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class HardGame extends Game {
    public double p = 0.25;
    public int num = 0;
    public int hpIncrease = 0;
    public int enemyMaxNumber = 8;

    @Override
    public boolean timeCountAndNewCycleJudge() {
        setCycleDuration(400);
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void action() {
        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {
            time += timeInterval;

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                if(cycleTime > 0 && cycleTime % 5000 == 0){
                    p = p + 0.01;
                    hpIncrease = hpIncrease + 1;
                    System.out.println("精英机出现概率提升为" + p + ","+ "生命值总共提升" + hpIncrease);
                }
                // 新敌机产生
                if (enemyAircrafts.size() < enemyMaxNumber) {
                    if (score >= j && i == 0 && !isBossEnemyExits) {
                        musicManager.interruptBackGroundMusic();
                        musicManager.playBossMusic();
                        aircraftFactory = new BossEnemyFactory();
                        ((BossEnemyFactory) aircraftFactory).getShootStrategy().setShootNumEnemy(3);
                        enemyAircrafts.add(aircraftFactory.createAircraft(
                                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.BOSS_ENEMY_IMAGE.getWidth())) ,
                                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) ,
                                ((BossEnemyFactory) aircraftFactory).getSpeedX(),
                                ((BossEnemyFactory) aircraftFactory).getSpeedY(),
                                ((BossEnemyFactory) aircraftFactory).getHp() + num * 10,
                                ((BossEnemyFactory) aircraftFactory).getShootStrategy()
                        ));
                        i++;
                        num++;
                        if(num != 0){
                            System.out.println("Boss机生命值提升" + num*10);
                        }
                        isBossEnemyExits = true;
                    } else {
                        if (Math.random() <= p) {
                            aircraftFactory = new EliteEnemyFactory();
                            enemyAircrafts.add(aircraftFactory.createAircraft(
                                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2),
                                    ((EliteEnemyFactory) aircraftFactory).getSpeedX(),
                                    ((EliteEnemyFactory) aircraftFactory).getSpeedY(),
                                    ((EliteEnemyFactory) aircraftFactory).getHp() + hpIncrease,
                                    ((EliteEnemyFactory) aircraftFactory).getShootStrategy()
                            ));
                        } else {
                            aircraftFactory = new MobEnemyFactory();
                            enemyAircrafts.add(aircraftFactory.createAircraft(
                                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2),
                                    ((MobEnemyFactory) aircraftFactory).getSpeedX(),
                                    ((MobEnemyFactory) aircraftFactory).getSpeedY(),
                                    ((MobEnemyFactory) aircraftFactory).getHp(),
                                    ((MobEnemyFactory) aircraftFactory).getShootStrategy()
                            ));
                        }
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


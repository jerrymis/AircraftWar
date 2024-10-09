package edu.hitsz.application;

import edu.hitsz.ShootStrategyPackage.DirectShoot;
import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.dataRecorder.ScoreRecord;
import edu.hitsz.dataRecorder.ScoreRecordDao;
import edu.hitsz.dataRecorder.ScoreRecordDaoImp;
import edu.hitsz.factory.aircraft_factory.AircraftFactory;
import edu.hitsz.factory.aircraft_factory.BossEnemyFactory;
import edu.hitsz.factory.aircraft_factory.EliteEnemyFactory;
import edu.hitsz.factory.aircraft_factory.MobEnemyFactory;
import edu.hitsz.observerPattern.BombImp;
import edu.hitsz.observerPattern.EnemyAircraft;
import edu.hitsz.observerPattern.EnemyBulletBomb;
import edu.hitsz.observerPattern.Item;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BombProp;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public abstract class Game extends JPanel {
    private int backGroundTop = 0;
    /**
     * Scheduled 线程池，用于任务调度
     */
    public final ScheduledExecutorService executorService;
    /**
     * 时间间隔(ms)，控制刷新频率
     */
    public int timeInterval = 40;

    public final HeroAircraft heroAircraft;
    public final List<AbstractAircraft> enemyAircrafts;
    public final List<AbstractBullet> heroBullets;
    public final List<AbstractBullet> enemyBullets;
    public final List<AbstractProp> heroProps;
    public AircraftFactory aircraftFactory;

    public int enemyMaxNumber = 5;

    public boolean gameOverFlag = false;
    public int score = 0;
    public int time = 0;
    public boolean isBossEnemyExits;
    public final MusicManager musicManager;

    public void setCycleDuration(int cycleDuration) {
        this.cycleDuration = cycleDuration;
    }

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    public int cycleDuration = 600;
    public int cycleTime = 0;
    public int i = 0;
    public int j = 200;

    public final ScoreRecordDao scoreRecordDao;

    public Game() {
        heroAircraft = HeroAircraft.getHeroAircraft(
                Main.WINDOW_WIDTH / 2,
                Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                0, 0, 100,
                new DirectShoot());

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        heroProps = new LinkedList<>();
        scoreRecordDao = new ScoreRecordDaoImp();
        isBossEnemyExits = false;
        musicManager = new MusicManager();
        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {
            time += timeInterval;

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                // 新敌机产生
                if (enemyAircrafts.size() < enemyMaxNumber) {
                    if (score >= j && i == 0 && !isBossEnemyExits) {
                        musicManager.interruptBackGroundMusic();
                        musicManager.playBossMusic();
                        aircraftFactory = new BossEnemyFactory();
                        enemyAircrafts.add(aircraftFactory.createAircraft(
                                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.BOSS_ENEMY_IMAGE.getWidth())) ,
                                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) ,
                                ((BossEnemyFactory) aircraftFactory).getSpeedX(),
                                ((BossEnemyFactory) aircraftFactory).getSpeedY(),
                                ((BossEnemyFactory) aircraftFactory).getHp(),
                                ((BossEnemyFactory) aircraftFactory).getShootStrategy()
                        ));
                        i++;
                        isBossEnemyExits = true;
                    } else {
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

    public boolean getGameOverFlag() {
        return gameOverFlag;
    }

    //***********************
    //      Action 各部分
    //***********************
    public boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    public void shootAction() {
        // TODO 敌机射击
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            if (enemyAircraft instanceof MobEnemy) {
                enemyBullets.addAll(enemyAircraft.shoot(enemyAircraft));
            } else if (enemyAircraft instanceof EliteEnemy) {
                enemyBullets.addAll(enemyAircraft.shoot(enemyAircraft));
            } else {
                enemyBullets.addAll(enemyAircraft.shoot(enemyAircraft));
            }
        }
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot(heroAircraft));
    }

    public void bulletsMoveAction() {
        for (AbstractBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (AbstractBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    public void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    public void itemsMoveAction() {
        for (AbstractProp heroItem : heroProps) {
            heroItem.forward();
        }
    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    public void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for (AbstractBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                musicManager.playBulletHitAudio();
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }
        // 英雄子弹攻击敌机
        for (AbstractBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    musicManager.playBulletHitAudio();
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给
                        if (enemyAircraft instanceof EliteEnemy) {
                        } else if (enemyAircraft instanceof BossEnemy) {
                            i--;
                            j += 400;
                            musicManager.interruptBossMusic();
                            musicManager.playBackgroundMusic();
                            isBossEnemyExits = false;
                        } else {
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for (AbstractProp heroItem : heroProps) {
            if (heroItem.notValid()) {
                continue;
            }
            if(heroItem.crash(heroAircraft)){
                if(heroItem instanceof BombProp) {
                    BombImp bombImp = new BombImp();
                    Item enemyAircraft = new EnemyAircraft();
                    Item enemyBullet = new EnemyBulletBomb();
                    bombImp.addItem(enemyAircraft);
                    bombImp.bombHappen(enemyAircrafts);
                    bombImp.removeItem(enemyAircraft);
                    bombImp.addItem(enemyBullet);
                    bombImp.bombHappen(enemyBullets);
                    musicManager.playBombExplosionAudio();
                }
                musicManager.playGetSupplyAudio();
                heroItem.work(heroAircraft);
                heroItem.vanish();
            }
        }

    }
    public void getScoresAndProps(){
        for (AbstractAircraft enemyAircraft : enemyAircrafts){
            if((enemyAircraft instanceof MobEnemy) && enemyAircraft.getFlag() && (!enemyAircraft.getisValid()) ) {
                score = score + 10;
            }
            else if((enemyAircraft instanceof EliteEnemy) && enemyAircraft.getFlag() && (!enemyAircraft.getisValid()) ){
                score = score + 20;
                if (Math.random() <= 0.7) {
                    heroProps.add(((EliteEnemy) enemyAircraft).dropProp());
                }
            }
            else if((enemyAircraft instanceof BossEnemy) && enemyAircraft.getFlag() && (!enemyAircraft.getisValid()) ){
                score = score + 30;
                heroProps.add(((BossEnemy) enemyAircraft).dropProp());
            }
            else{
                score = score;
            }
            }
        }



    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */

    public void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        //消除无效的道具
        heroProps.removeIf(AbstractFlyingObject::notValid);
    }
    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }
        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, heroProps);
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, enemyAircrafts);
        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);
        //绘制得分和生命值
        paintScoreAndLife(g);
    }

    public void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }
        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    public void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }
}

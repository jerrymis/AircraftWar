package edu.hitsz.application;

public class MusicManager {
    public static boolean audioAvailable = true;
    private MusicLoop backgroundMusicThread;
    private MusicLoop  bossMusicThread;

    public MusicManager() {
        this.backgroundMusicThread = null;
        this.bossMusicThread = null;
    }

    public void playGetSupplyAudio() {
        if(audioAvailable) {
            new MusicThread("src/Music/get_supply.wav").start();
        }
    }

    public void playBombExplosionAudio() {
        if(audioAvailable) {
            new MusicThread("src/Music/bomb_explosion.wav").start();
        }
    }

    public void playBulletShootAudio() {
        if(audioAvailable) {
            new MusicThread("src/Music/bullet.wav").start();
        }
    }

    public void playBulletHitAudio() {
        if(audioAvailable) {
            new MusicThread("src/Music/bullet_hit.wav").start();
        }
    }

    public void playGameOverAudio() {
        if(audioAvailable) {
            new MusicThread("src/Music/game_over.wav").start();
        }
    }

    public void playBackgroundMusic() {
        if(!audioAvailable) {
            return;
        }
        if(backgroundMusicThread == null || backgroundMusicThread.getWaited()) {
            System.out.println("play backgroundMusicThread");
            backgroundMusicThread = new MusicLoop("src/Music/bgm.wav");
            backgroundMusicThread.start();
        } else {
            System.out.println("failed play backgroundMusicThread");
        }
    }

    public void interruptBackGroundMusic() {
        if(!audioAvailable) {
            return;
        }
        if(backgroundMusicThread != null) {
            backgroundMusicThread.setWaited(true);
            System.out.println("backgroundMusicThread interrupted");
        } else {
            System.out.println("failed backgroundMusicThread interrupted");
        }
    }

    public void playBossMusic() {
        if(!audioAvailable) {
            return;
        }
        if(bossMusicThread == null || backgroundMusicThread.getWaited()) {
            System.out.println("play bossMusicThread");
            bossMusicThread = new MusicLoop ("src/Music/bgm_boss.wav");
            bossMusicThread.start();
        } else {
            System.out.println("failed play backgroundMusicThread");
        }
    }

    public void interruptBossMusic() {
        if(!audioAvailable) {
            return;
        }
        if(bossMusicThread != null) {
            bossMusicThread.setWaited(true);
            System.out.println("bossMusicThread interrupted");
        } else {
            System.out.println("failed bossMusicThread interrupted");
        }
    }
}

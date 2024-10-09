package edu.hitsz.aircraft;

import edu.hitsz.ShootStrategyPackage.DirectShoot;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EliteEnemyTest {
    private EliteEnemy  eliteaircraft;
    DirectShoot directshoot = new DirectShoot();

    @BeforeEach
    void setUp() {
        eliteaircraft = new EliteEnemy(100, 100, 2, 8, 60,directshoot);
    }

    @AfterEach
    void tearDown() {
        eliteaircraft = null;
    }

    @DisplayName("Test setLocation method")
    @Test
    void setLocation() {
        eliteaircraft.setLocation(120, 110);
        assertEquals(120,  eliteaircraft.getLocationX());
        assertEquals(110,  eliteaircraft.getLocationY());
        System.out.println("-----位置设定生效-----");
    }

    @DisplayName("Test notValid method")
    @Test
    void notValid() {
        eliteaircraft.vanish();
        assertTrue( eliteaircraft.notValid());
        System.out.println("-----精英敌机不再生效-----");
    }
}
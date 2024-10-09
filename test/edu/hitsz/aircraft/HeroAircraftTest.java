package edu.hitsz.aircraft;

import edu.hitsz.ShootStrategyPackage.DirectShoot;
import edu.hitsz.factory.aircraft_factory.AircraftFactory;
import edu.hitsz.factory.aircraft_factory.MobEnemyFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HeroAircraftTest {
    private HeroAircraft heroAircraft;
    private AircraftFactory aircraftFactory;
    DirectShoot directshoot = new DirectShoot();
    @BeforeEach
    void setUp() {
        heroAircraft = HeroAircraft.getHeroAircraft(100, 100, 0, 0, 100,directshoot);
    }

    @AfterEach
    void tearDown() {
        heroAircraft = null;
    }

    @DisplayName("Test decreaseHp method")
    @Test
    void decreaseHp() {
        heroAircraft.decreaseHp(20);
        assertEquals(80, heroAircraft.hp);
        System.out.println("-----生命值变化正常-----");
    }

    @DisplayName("Test crash method ")
    @Test
    void crash() {
        aircraftFactory = new MobEnemyFactory();
        assertTrue(heroAircraft.crash(aircraftFactory.createAircraft(100, 100, 2, 8, 30,directshoot)));
        System.out.println("-----英雄机与敌机碰撞生效-----");
    }

    @DisplayName("Test vanish method")
    @Test
    void vanish() {
        heroAircraft.vanish();
        assertTrue(heroAircraft.notValid());
        System.out.println("-----英雄机消亡-----");
    }
}
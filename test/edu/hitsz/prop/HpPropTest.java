package edu.hitsz.prop;

import edu.hitsz.ShootStrategyPackage.DirectShoot;
import edu.hitsz.aircraft.HeroAircraft;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class HpPropTest {
    private HpProp hp1;
    private HeroAircraft heroAircraft;
    DirectShoot directshoot = new DirectShoot();





    @BeforeEach
    void setUp() {
        hp1 = new HpProp(100, 800, 2, 6);
    }

    @AfterEach
    void tearDown() {
        hp1 = null;
    }

    @DisplayName("Test forward method of HpProp")
    @Test
    void forward() {
        hp1.forward();
        assumeTrue(hp1.notValid());
        System.out.println("-----道具位置不合理-----");

    }

    @DisplayName("Test work method of HpProp")
    @Test
    void work() {
        heroAircraft = HeroAircraft.getHeroAircraft(100, 100, 0, 0, 70,directshoot);
        hp1.work(heroAircraft);
        assertTrue(heroAircraft.getHp() == 100);
        System.out.println("-----生命值上限确实为100-----");
    }
}
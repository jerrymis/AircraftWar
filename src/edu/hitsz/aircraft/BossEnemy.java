package edu.hitsz.aircraft;

import edu.hitsz.ShootStrategyPackage.ShootStrategy;
import edu.hitsz.application.Main;
import edu.hitsz.factory.Prop_factory.BombPropFactory;
import edu.hitsz.factory.Prop_factory.FirePropFactory;
import edu.hitsz.factory.Prop_factory.HpPropFactory;
import edu.hitsz.factory.Prop_factory.PropFactory;
import edu.hitsz.prop.AbstractProp;

public class BossEnemy extends AbstractAircraft {

    public PropFactory propFactory;


    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp, ShootStrategy shootstrategy) {
        super(locationX, locationY, speedX, speedY, hp, shootstrategy);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
            setFlag(false);
        }
    }

    public AbstractProp dropProp() {
        if (Math.random() < 0.33) {
            propFactory = new HpPropFactory();
        } else if (Math.random() < 0.66) {
            propFactory = new FirePropFactory();
        } else if (Math.random() < 1) {
            propFactory = new BombPropFactory();
        }
        return propFactory.createProp(this.getLocationX(),
                this.getLocationY(),
                0,
                5);
    }

}


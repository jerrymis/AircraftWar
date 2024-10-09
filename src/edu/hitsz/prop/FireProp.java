package edu.hitsz.prop;

import edu.hitsz.ShootStrategyPackage.DirectShoot;
import edu.hitsz.ShootStrategyPackage.ShootStrategy;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.ShootStrategyPackage.ScatteredShoot;


public class  FireProp extends AbstractProp {
    public static Thread fireSupplyThread = null;
    public FireProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void work(AbstractAircraft aircraft){

        aircraft.setShootStrategy(new ScatteredShoot());
        Runnable r = () -> {
            try {

                Thread.sleep(8000);
                aircraft.setShootStrategy(new DirectShoot());
            } catch (InterruptedException e) {
            }
        };
        if(fireSupplyThread != null && fireSupplyThread.isAlive()) {
            fireSupplyThread.interrupt();
        }
        fireSupplyThread = new Thread(r, "fireSupplyThread");
        fireSupplyThread.start();
    }
}

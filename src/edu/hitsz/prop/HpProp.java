package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.application.Main;

public class HpProp extends AbstractProp {
    public HpProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void work(AbstractAircraft aircraft){

        aircraft.decreaseHp(-40);
    }
}

package edu.hitsz.prop;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.observerPattern.BombImp;

public class BombProp extends AbstractProp {
    public BombProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }


    @Override
    public void work(AbstractAircraft aircraft){
        System.out.println("BombSupply active!");
    }
}
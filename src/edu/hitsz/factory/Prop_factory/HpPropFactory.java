package edu.hitsz.factory.Prop_factory;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.HpProp;

public class HpPropFactory implements PropFactory {
    @Override
    public AbstractProp createProp(int locationX, int locationY, int speedX, int speedY){
        return new HpProp(locationX, locationY, speedX, speedY);
    }
}

package edu.hitsz.factory.Prop_factory;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.FireProp;

public class FirePropFactory implements PropFactory {
    @Override
    public AbstractProp createProp(int locationX, int locationY, int speedX, int speedY){
        return new FireProp(locationX, locationY, speedX, speedY);
    }
}

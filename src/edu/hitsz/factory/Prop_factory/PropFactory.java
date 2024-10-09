package edu.hitsz.factory.Prop_factory;

import edu.hitsz.prop.AbstractProp;

public interface PropFactory {
    public abstract AbstractProp createProp(int locationX, int locationY, int speedX, int speedY);
}

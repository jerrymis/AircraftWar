package edu.hitsz.observerPattern;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.basic.AbstractFlyingObject;

import java.util.ArrayList;
import java.util.List;

public class BombImp {
    private List<Item> itemList = new ArrayList<>();
//    public BombImp(List<AbstractAircraft> enemyAircrafts){
//        this.enemyAircrafts = enemyAircrafts;
//    }
    public void addItem(Item item){
        itemList.add(item);
    }
    public void removeItem(Item item){
        itemList.remove(item);
    }

    public void notifyAll(List<?> list){
        for (Item item : itemList) {
            item.update(list);
        }
        }
    public void bombHappen(List<?> list){
        notifyAll(list);
    }

}

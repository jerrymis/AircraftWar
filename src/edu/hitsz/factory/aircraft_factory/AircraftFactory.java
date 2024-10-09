package edu.hitsz.factory.aircraft_factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.ShootStrategyPackage.ShootStrategy;

public interface AircraftFactory {

     AbstractAircraft createAircraft(int locationX, int locationY, int speedX, int speedY, int hp, ShootStrategy shootstrategy);
}


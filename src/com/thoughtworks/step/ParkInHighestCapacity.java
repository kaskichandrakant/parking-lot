package com.thoughtworks.step;

import java.util.ArrayList;

public class ParkInHighestCapacity implements ParkingMethodology {
    @Override
    public ParkingLot getLot(ArrayList<ParkingLot> lots) {
        ParkingLot biggerLot = lots.get(0);
        for (int i = 1; i < lots.size(); i++) {
            ParkingLot firstLot = lots.get(i);
            if(firstLot.isBiggerThan(biggerLot)){
                biggerLot = firstLot;
            }
        }
        return biggerLot;
    }
}

package com.thoughtworks.step;

import java.util.ArrayList;

public class ParkInMostVacant implements ParkingMethodology {

    @Override
    public ParkingLot getLot(ArrayList<ParkingLot> lots) {
        ParkingLot mostVacantLot = lots.get(0);
        for (int i = 1; i < lots.size(); i++) {
            ParkingLot firstLot = lots.get(i);
            if(firstLot.isMoreVacantThan(mostVacantLot)){
                mostVacantLot = firstLot;
            }
        }
        return mostVacantLot;
    }
}

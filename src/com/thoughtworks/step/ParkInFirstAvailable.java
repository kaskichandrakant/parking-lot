package com.thoughtworks.step;

import java.util.ArrayList;

public class ParkInFirstAvailable implements ParkingMethodology {
    public ParkInFirstAvailable() {

    }

    @Override
    public ParkingLot getLot(ArrayList<ParkingLot> parkingLots) {
        return parkingLots.get(0);
    }
}

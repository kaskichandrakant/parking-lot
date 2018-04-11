package com.thoughtworks.step;

public interface ParkStats {
    ParkingLot getMostVacantLot();
    ParkingLot getHighestCapacity();
    ParkingLot getFirstavailable();
}

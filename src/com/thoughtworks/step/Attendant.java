package com.thoughtworks.step;
import java.util.ArrayList;

public class Attendant {
    private ArrayList<ParkingLot> parkingLots;

    public Attendant() {
        this.parkingLots = new ArrayList<>();
    }

    public void add(ParkingLot parkingLot) {
        parkingLots.add(parkingLot);
    }

    public Object park(Vehicle vehicle) throws UnableToParkException {
        if(hasAlreadyParked(vehicle)){
            throw new UnableToParkException("Car Already Parked");
        }
        for (ParkingLot parkingLot : parkingLots) {
            if(!parkingLot.isFull()){
                return parkingLot.park(vehicle);
            }
        }
        throw new UnableToParkException("Parking Lots Are Full");
    }

    private boolean hasAlreadyParked(Vehicle vehicle){
        boolean hasParked=false;
        for (ParkingLot parkingLot : parkingLots) {
            if(parkingLot.isCarAlreadyParked(vehicle)){
                hasParked=true;
            }
        }
        return hasParked;
    }
}

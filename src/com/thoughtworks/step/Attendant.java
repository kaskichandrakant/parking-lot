package com.thoughtworks.step;
import java.util.ArrayList;
import java.util.Collections;

public class Attendant implements Listener {
    private ArrayList<ParkingLot> parkingLots;
    private ArrayList<ParkingLot> sortedLots;

    public Attendant() {
        this.parkingLots = new ArrayList<>();
        this.sortedLots = new ArrayList<>();
    }

    public void add(ParkingLot parkingLot) {
        parkingLots.add(parkingLot);
        Collections.sort(parkingLots);

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

    public Vehicle checkOut(Object token) throws AlreadyCheckedOutException {
        for (ParkingLot parkingLot : parkingLots) {
           if(parkingLot.hasToken(token)){
               return parkingLot.checkOut(token);
           }
        }
        throw new AlreadyCheckedOutException();
    }

    @Override
    public void full() {
        System.out.println("Attendant : Is Full!");
    }

    @Override
    public void notFull() {
        System.out.println("Attendant : Not Full!");
    }
}

package com.thoughtworks.step;
import java.util.ArrayList;

public class Attendant implements Listener {
    private final ArrayList<ParkingLot> availableLots;
    private ArrayList<ParkingLot> parkingLots;
    private ParkingMethodology parkingMethod;

    public Attendant(ParkingMethodology parkingMethod) {
        this.parkingMethod = parkingMethod;
        this.parkingLots = new ArrayList<>();
        this.availableLots=new ArrayList<>();
    }

    public static Attendant createAttendant(ParkingMethodology parkingMethod) {
        return new Attendant(parkingMethod);
    }

    public void add(ParkingLot parkingLot) {
        parkingLots.add(parkingLot);
        availableLots.add(parkingLot);
    }

    public Object park(Vehicle vehicle) throws UnableToParkException {
        if(availableLots.size()==0) throw new UnableToParkException("Parking Lots Are Full");

        if(hasAlreadyParked(vehicle))throw new UnableToParkException("Car Already Parked");

        ParkingLot parkingLot = parkingMethod.getLot(availableLots);

        Object token = parkingLot.park(vehicle);

        if(parkingLot.isFull())availableLots.remove(parkingLot);

        return token;
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
               Vehicle vehicle = parkingLot.checkOut(token);
               availableLots.add(parkingLot);
               return  vehicle;
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

package com.thoughtworks.step;

import java.util.HashMap;

public class ParkingLot  implements  Comparable<ParkingLot>{
    private HashMap<Object, Vehicle> vehicles;
    private int capacity;
    private EventDispatcher eventDispatcher;

    public ParkingLot(int capacity) {
        this.capacity = capacity;
        this.vehicles = new HashMap();
        this.eventDispatcher =new EventDispatcher();
    }

    public Object park(Vehicle vehicle) throws UnableToParkException {

        if (isCarAlreadyParked(vehicle)) {
            throw new UnableToParkException("Vehicle Already Parked");
        }
        if (isFull()) {
            throw new UnableToParkException("Parking Lot Is Full");
        }
        Object token = new Object();
        this.vehicles.putIfAbsent(token, vehicle);
        if (isFull()) {
            eventDispatcher.announceFull();
        }
        return token;
    }

    public Vehicle checkOut(Object token) throws AlreadyCheckedOutException {
        if (!hasToken(token)) {
            throw new AlreadyCheckedOutException();
        }
        if (isFull()) {
            eventDispatcher.announceHasSpace();
        }
        return vehicles.remove(token);
    }

    protected boolean isCarAlreadyParked(Vehicle vehicle) {
        return vehicles.containsValue(vehicle);
    }

    protected boolean hasToken(Object token) {
        return vehicles.containsKey(token);
    }

    public boolean isFull() {
        return vehicles.size() == capacity;
    }

    @Override
    public String toString() {
        return "ParkingLot{" +
                "vehicles=" + vehicles +
                ", capacity=" + capacity +
                '}';
    }
    public void addEventDispatcher(EventDispatcher eventDispatcher){
        this.eventDispatcher=eventDispatcher;
    }

    @Override
    public int compareTo(ParkingLot other) {
        return other.capacity-this.capacity;
    }
}
package com.thoughtworks.step;
import java.util.HashMap;

public class ParkingLot {
    private HashMap<Object, Vehicle> vehicles;
    private int capacity;

    public ParkingLot(int capacity) {
        this.capacity = capacity;
        this.vehicles = new HashMap();
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
        return token;
    }

    public Vehicle checkOut(Object token) throws AlreadyCheckedOutException {
        if (!hasToken(token)) {
            throw new AlreadyCheckedOutException();
        }
        return vehicles.remove(token);
    }

    protected boolean isCarAlreadyParked(Vehicle vehicle) {
        return vehicles.containsValue(vehicle);
    }

    private boolean hasToken(Object token) {
        return vehicles.containsKey(token);
    }

    public boolean isFull() {
        return vehicles.size() == capacity;
    }
}

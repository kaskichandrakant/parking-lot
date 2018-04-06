package com.thoughtworks.step;
import java.util.ArrayList;
import java.util.HashMap;

public class ParkingLot {
    private final ArrayList<Listener> listeners;
    private HashMap<Object, Vehicle> vehicles;
    private int capacity;

    public ParkingLot(int capacity) {
        this.listeners = new ArrayList<>();
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
        if (isFull()) {
            flag();
        }
        return token;
    }

    public Vehicle checkOut(Object token) throws AlreadyCheckedOutException {
        if (!hasToken(token)) {
            throw new AlreadyCheckedOutException();
        }
        if(isFull()){
            unflag();
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

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void flag() {
        for (Listener listener : listeners) {
            listener.full();
        }
    }

    public void unflag() {
        for (Listener listener : listeners) {
            listener.notFull();
        }
    }
}

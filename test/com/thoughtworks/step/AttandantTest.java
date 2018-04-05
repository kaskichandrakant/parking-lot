package com.thoughtworks.step;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertFalse;

public class AttandantTest {

    private Attendant attendant;
    private TestCar testCar;

    class TestCar implements Vehicle{
        public TestCar() {
        }
    }

    @Before
    public void setUp() throws Exception {
        attendant = new Attendant();
        testCar = new TestCar();
    }

    @Test
    public void shouldBeAbleToParkTheCarInParkingLot() throws UnableToParkException {
        attendant.add(new ParkingLot(2));
        Object token = attendant.park(testCar);
        assertNotNull(token);
    }

    @Test
    public void shouldBeAbleToCheckOutTheCar() throws UnableToParkException, AlreadyCheckedOutException {
        attendant.add(new ParkingLot(2));
        attendant.add(new ParkingLot(1));
        Object token = attendant.park(testCar);
        attendant.park(new TestCar());
        attendant.park(new TestCar());
        Vehicle car = attendant.checkOut(token);
        assertEquals(car,testCar);
        assertFalse(attendant.hasAlreadyParked(testCar));
    }

    @Test(expected =UnableToParkException.class)
    public void shouldNotAllowToParkSameCarTwice() throws UnableToParkException {
        attendant.add(new ParkingLot(2));
        attendant.add(new ParkingLot(1));
        attendant.park(testCar);
        attendant.park(testCar);
    }

    @Test(expected =UnableToParkException.class)
    public void shouldNotAllowToParkTheCarIfParkingLotsAreFull() throws UnableToParkException {
        attendant.add(new ParkingLot(2));
        attendant.add(new ParkingLot(1));
        attendant.park(new TestCar());
        attendant.park(testCar);
        attendant.park(new TestCar());
        attendant.park(new TestCar());
    }

}

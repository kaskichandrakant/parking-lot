package com.thoughtworks.step;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AttendantTest {

    private Attendant attendant;
    private TestCar testCar;

    class TestCar implements Vehicle{
        public TestCar() {
        }
    }

    @Before
    public void setUp() throws Exception {
        attendant = Attendant.createAttendant(new ParkInFirstAvailable());
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
        attendant.add(new ParkingLot(1));
        Object token = attendant.park(testCar);
        Vehicle car = attendant.checkOut(token);
        assertEquals(car,testCar);
    }

    @Test(expected =UnableToParkException.class)
    public void shouldNotAllowToParkSameCarTwice() throws UnableToParkException {
        attendant.add(new ParkingLot(2));
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

    @Test
    public void shouldParkCarAfterCheckingOutFromFullParkingLots() throws UnableToParkException, AlreadyCheckedOutException {
        attendant.add(new ParkingLot(1));
        attendant.add(new ParkingLot(1));
        Object token = attendant.park(testCar);
        attendant.park(new TestCar());
        attendant.checkOut(token);
        assertNotNull(attendant.park(new TestCar()));
    }

    @Test
    public void shouldParkInLotWithHigherCapacity() throws UnableToParkException {
        attendant=Attendant.createAttendant(new ParkInHighestCapacity());
        attendant.add(new ParkingLot(1));
        attendant.add(new ParkingLot(3));
        ParkingLot parkingLot = new ParkingLot(5);
        attendant.add(parkingLot);
        Object token = attendant.park(testCar);
        assertTrue(parkingLot.hasToken(token));
    }

    @Test
    public void shouldParkCarInParkingLotWithMostCapacity() throws UnableToParkException {
        attendant=Attendant.createAttendant(new ParkInHighestCapacity());
        attendant.add(new ParkingLot(1));
        attendant.add(new ParkingLot(2));
        ParkingLot parkingLot = new ParkingLot(5);
        attendant.add(parkingLot);
        Object token = attendant.park(testCar);
        assertTrue(parkingLot.hasToken(token));
    }

    @Test
    public void shouldParkInMostVacantLot() throws UnableToParkException {
        attendant=Attendant.createAttendant(new ParkInMostVacant());
        attendant.add(new ParkingLot(3));
        attendant.add(new ParkingLot(1));
        ParkingLot parkingLot = new ParkingLot(4);
        attendant.add(parkingLot);
        attendant.park(new TestCar());
        attendant.park(new TestCar());
        Object token = attendant.park(new TestCar());
        assertTrue(parkingLot.hasToken(token));
    }
}

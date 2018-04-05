package com.thoughtworks.step;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

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

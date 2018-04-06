package com.thoughtworks.step;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ParkingLotTest {

    private TestCar testCar;

    private class TestCar implements Vehicle{
        TestCar() {
        }
    }


    private ParkingLot parkingLot;
    @Before
    public void setUp() {
        parkingLot = new ParkingLot(2);
        testCar = new TestCar();
    }

    @Test
    public void shouldBeAbleToParkTheCarInParkingLot() throws UnableToParkException {
        Object token = parkingLot.park(testCar);
        assertNotNull(token);
    }

    @Test
    public void shouldBeAbleToCheckOutTheCar() throws UnableToParkException, AlreadyCheckedOutException {
        Object token = parkingLot.park(testCar);
        Vehicle checkedOutCar = parkingLot.checkOut(token);
        assertEquals(testCar,checkedOutCar);

    }

    @Test (expected = UnableToParkException.class)
    public void shouldNotBeAbleToParkTheSameCarTwiceWithoutAnyCheckOut() throws UnableToParkException {
        parkingLot.park(testCar);
        parkingLot.park(testCar);
    }

    @Test (expected = AlreadyCheckedOutException.class)
    public void shouldNotBeAbleToCheckoutTheSameTwiceWithoutBeingParkedAgain() throws UnableToParkException,AlreadyCheckedOutException {
        Object token = parkingLot.park(testCar);
        parkingLot.checkOut(token);
        parkingLot.checkOut(token);
    }

    @Test
    public void shouldAssertTrueIfParkingLotIsFull() throws UnableToParkException {
        parkingLot.park(testCar);
        assertFalse(parkingLot.isFull());
        parkingLot.park(new TestCar());
        assertTrue(parkingLot.isFull());
    }

    @Test (expected = UnableToParkException.class)
    public void shouldNotAllowToParkCarIfParkingLotIsFull() throws UnableToParkException {
        parkingLot.park(testCar);
        parkingLot.park(new TestCar());
        parkingLot.park(new TestCar());
    }

    @Test
    public void shouldConveyMsgFull() throws UnableToParkException {

        Listener testListener = mock(Listener.class);
        parkingLot.addListener(testListener);
        parkingLot.park(testCar);
        parkingLot.park(new TestCar());
        verify(testListener, times(1)).full();
    }

    @Test
    public void shouldNotCallListenerWhenCheckingOutFromFullLot() throws UnableToParkException, AlreadyCheckedOutException {
        Listener testListener = mock(Listener.class);
        parkingLot.addListener(testListener);
        Object token = parkingLot.park(testCar);
        parkingLot.park(new TestCar());
        parkingLot.checkOut(token);
        verify(testListener, times(1)).notFull();
        parkingLot.park(new TestCar());
        verify(testListener, times(2)).full();
    }
}
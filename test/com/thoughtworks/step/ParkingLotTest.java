package com.thoughtworks.step;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParkingLotTest {

    private TestCar firstCar;

    private class TestCar implements Vehicle{
        TestCar() {
        }
    }


    private ParkingLot parkingLot;
    @Before
    public void setUp() {
        parkingLot = new ParkingLot(2);
        firstCar = new TestCar();
    }

    @Test
    public void shouldBeAbleToParkTheCarInParkingLot() throws UnableToParkException {
        Object token = parkingLot.park(firstCar);
        assertNotNull(token);
    }

    @Test
    public void shouldBeAbleToCheckOutTheCar() throws UnableToParkException, AlreadyCheckedOutException {
        Object token = parkingLot.park(firstCar);
        Vehicle checkedOutCar = parkingLot.checkOut(token);
        assertEquals(firstCar,checkedOutCar);

    }

    @Test (expected = UnableToParkException.class)
    public void shouldNotBeAbleToParkTheSameCarTwiceWithoutAnyCheckOut() throws UnableToParkException {
        parkingLot.park(firstCar);
        parkingLot.park(firstCar);
    }

    @Test (expected = AlreadyCheckedOutException.class)
    public void shouldNotBeAbleToCheckoutTheSameTwiceWithoutBeingParkedAgain() throws UnableToParkException,AlreadyCheckedOutException {
        Object token = parkingLot.park(firstCar);
        parkingLot.checkOut(token);
        parkingLot.checkOut(token);
    }

    @Test
    public void shouldAssertTrueIfParkingLotIsFull() throws UnableToParkException {
        parkingLot.park(firstCar);
        assertFalse(parkingLot.isFull());
        parkingLot.park(new TestCar());
        assertTrue(parkingLot.isFull());
    }

    @Test (expected = UnableToParkException.class)
    public void shouldNotAllowToParkCarIfParkingLotIsFull() throws UnableToParkException {
        parkingLot.park(firstCar);
        parkingLot.park(new TestCar());
        parkingLot.park(new TestCar());
    }
    //    @Test
//    public void ShouldAssertThatCarIsInParkingLot(){
//        Vehicle swift = new Vehicle();
//        Object token = parkingLot.park(swift);
//        Object token1 =new Object(1);
//        assertEquals(token,token1);
//        assertTrue(token.equals(token1));
//        assertEquals(token.hashCode(),token1.hashCode());
//    }
//
//    @Test
//    public void shouldReturnParkedCarWhileCheckout() {
//        Vehicle swift = new Vehicle();
//        Object token = parkingLot.park(swift);
//        Object mySwift = parkingLot.checkOut(token);
//        assertEquals(swift,mySwift);
//    }
//
//    @Test
//    public void shouldReturnNullForUsedTokenToCheckout() {
//        Vehicle swift = new Vehicle();
//        Object swiftToken = parkingLot.park(swift);
//        Object mySwift = parkingLot.checkOut(swiftToken);
//        assertNull(parkingLot.checkOut(swiftToken));
//    }


}
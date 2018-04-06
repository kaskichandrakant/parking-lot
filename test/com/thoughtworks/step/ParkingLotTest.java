package com.thoughtworks.step;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.*;

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
        ArrayList<String> message = new ArrayList<>();
        Listener Owner = new Listener() {
            @Override
            public void full() {
                message.add("Ooh!");
            }

            @Override
            public void notFull() {
                message.add("Great!");
            }
        };
        parkingLot.addListener(Owner);
        parkingLot.park(testCar);
        parkingLot.park(new TestCar());
        assertThat(message,hasItem("Ooh!"));
        assertEquals(message.size(),1);
    }

    @Test
    public void shouldConveyMsgFullAndNotFull() throws UnableToParkException, AlreadyCheckedOutException {
        ArrayList<String> message = new ArrayList<>();
        Listener CityCouncil = new Listener() {
            @Override
            public void full() {
                message.add("Ack");
            }

            @Override
            public void notFull() {
                message.add("Ack");
            }
        };
        parkingLot.addListener(CityCouncil);
        Object token = parkingLot.park(testCar);
        parkingLot.park(new TestCar());
        parkingLot.checkOut(token);
        assertThat(message,hasItem("Ack"));
        assertEquals(message.size(),2);
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
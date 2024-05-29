package com.tejasCB.restful_service.service;

import com.tejasCB.restful_service.model.Passenger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PassengerServiceTest {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private PassengerService passengerService;

    private Map<Long, Passenger> passengerDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        passengerDetails = new HashMap<>();
        passengerDetails.put(1L, new Passenger("John", "Doe", 30, "Male", 1L, "john.doe@example.com"));
        passengerDetails.put(2L, new Passenger("Jane", "Doe", 28, "Female", 2L, "jane.doe@example.com"));

        when(ticketService.getPassengerDetails()).thenReturn(passengerDetails);
    }

    @Test
    void getPassengerTest() {
        Passenger passenger = passengerService.getPassenger(1L);
        assertNotNull(passenger);
        assertEquals("John", passenger.getFirstName());
        assertEquals(30,passenger.getAge());

        Passenger nonExistentPassenger = passengerService.getPassenger(3L);
        assertNull(nonExistentPassenger);
    }
}

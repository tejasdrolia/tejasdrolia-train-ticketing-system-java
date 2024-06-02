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

/**
 * Test class for PassengerService.
 */
public class PassengerServiceTest {

    @Mock
    private TicketService ticketService; // Inserting instances of TicketService for mocking

    @InjectMocks
    private PassengerService passengerService; // Injecting TicketService into PassengerService Instance

    /**
     * Sets up the test environment before each test method.
     * Initializes mocks and sample passenger data.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Map<Long, Passenger> passengerDetails = new HashMap<>();
        passengerDetails.put(1L, new Passenger("John", "Doe", 30, "Male", 1L, "john.doe@example.com"));
        passengerDetails.put(2L, new Passenger("Jane", "Doe", 28, "Female", 2L, "jane.doe@example.com"));

        when(ticketService.getPassengerDetails()).thenReturn(passengerDetails);
    }

    /**
     * Tests the getPassenger method of PassengerService.
     * Verifies that a passenger is correctly retrieved by their ID.
     */
    @Test
    void testGetPassengerPositive() {
        Passenger passenger = passengerService.getPassenger(1L);
        assertNotNull(passenger);
        assertEquals("John", passenger.getFirstName());
        assertEquals(30, passenger.getAge());
        assertEquals("Doe", passenger.getLastName());
        assertEquals("Male", passenger.getGender());
        assertEquals("john.doe@example.com", passenger.getEmail());
    }

    /**
     * Tests the getPassenger method of PassengerService.
     * Verifies that null is returned for a non-existent passenger.
     */
    @Test
    void testGetPassengerNegative() {
        Passenger nonExistentPassenger = passengerService.getPassenger(3L);
        assertNull(nonExistentPassenger);
    }
}

package com.tejasCB.restful_service.service;

import com.tejasCB.restful_service.model.Passenger;
import com.tejasCB.restful_service.model.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for TicketService.
 */
public class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    /**
     * Sets up the test environment before each test method.
     * Initializes mocks and creates a new instance of TicketService.
     */

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ticketService = new TicketService();
    }

    /**
     * Tests the purchaseTicket method of TicketService.
     * Verifies that a ticket is successfully purchased and associated methods work as expected.
     */

    @Test
    void testPurchaseTicket() {
        Passenger passenger = new Passenger("John", "Doe", 30, "Male", 0,"john.doe@example.com");
        Ticket ticket = ticketService.purchaseTicket(passenger);

        assertNotNull(ticket);
        assertEquals(1, ticketService.getPnrsByPassengerId(1L).size());
        assertTrue(ticketService.checkIfPassengerExists(1L));
        assertTrue(ticketService.checkIfPnrExists(ticket.getPnr()));
    }

    /**
     * Tests the getTicketDetail method of TicketService.
     * Verifies that ticket details can be retrieved correctly.
     */

    @Test
    void testGetTicketDetail() {
        Passenger passenger = new Passenger("Jane", "Doe", 28, "Female", 0, "jane.doe@example.com");
        Ticket purchasedTicket = ticketService.purchaseTicket(passenger);
        Ticket ticket = ticketService.getTicketDetail(purchasedTicket.getPnr());
        assertNotNull(ticket);
        assertEquals(purchasedTicket.getPnr(), ticket.getPnr());
    }

    /**
     * Tests the getTicketDetail method of TicketService.
     * Verifies that an exception is thrown when a non-existent PNR is provided.
     */

    @Test
    void testGetTicketDetailNonExistentPnr() {
        long nonExistentPnr = 999L; // Assuming this PNR does not exist in the repository
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ticketService.getTicketDetail(nonExistentPnr);
        });
        String expectedMessage = "PNR not found: " + nonExistentPnr;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Tests the cancelTicket method of TicketService.
     * Verifies that a ticket can be canceled and the seat becomes available again.
     */

    @Test
    void testCancelTicket() {
        Passenger passenger = new Passenger("John", "Doe", 30, "Male", 0, "john.doe@example.com");
        Ticket ticket = ticketService.purchaseTicket(passenger);

        ticketService.cancelTicket(ticket.getPnr());

        assertFalse(ticketService.checkIfPnrExists(ticket.getPnr()));
        assertTrue(ticketService.getAvailableSeats("A").contains(ticket.getSeat()));
    }

    /**
     * Tests the modifySeat method of TicketService.
     * Verifies that a ticket's seat can be modified successfully.
     */

    @Test
    void testModifySeat() {
        Passenger passenger = new Passenger("Jane", "Doe", 28, "Female", 0, "jane.doe@example.com");
        Ticket ticket = ticketService.purchaseTicket(passenger);
        String newSeat = "B2";
        ticketService.modifySeat(ticket.getPnr(), newSeat);

        Ticket modifiedTicket = ticketService.getTicketDetail(ticket.getPnr());
        assertEquals(newSeat, modifiedTicket.getSeat());
    }

    /**
     * Tests the getTicketDetail method of TicketService.
     * Verifies that an exception is thrown when a non-existent Seat is provided.
     */

    @Test
    void testModifySeatNonExistentSeat() {
        Passenger passenger = new Passenger("Jane", "Doe", 28, "Female", 0, "jane.doe@example.com");
        Ticket ticket = ticketService.purchaseTicket(passenger);
        String nonExistentSeat = "B90"; // Providing a non-existent seat

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ticketService.modifySeat(ticket.getPnr(), nonExistentSeat);
        });
        String expectedMessage = "Seat not available: " + nonExistentSeat;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Tests the getAvailableSeats method of TicketService.
     * Verifies that the available seats are retrieved correctly and updated after ticket purchase.
     */

    @Test
    void testGetAvailableSeats() {
        Set<String> availableSeatsA = ticketService.getAvailableSeats("A");
        assertEquals(50, availableSeatsA.size());

        Passenger passenger = new Passenger("John", "Doe", 30, "Male", 0, "john.doe@example.com");
        ticketService.purchaseTicket(passenger);
        Set<String> newAvailableSeatsA = ticketService.getAvailableSeats("A");
        assertEquals(49, newAvailableSeatsA.size());
    }


    /**
     * Tests the getAvailableSeats method of TicketService.
     * Verifies that an exception is thrown when a non-existent Section is provided.
     */

    @Test
    void testGetAvailableSeatsNonExistentSection() {
        String nonExistentSection = "C"; //Setting non-existent section
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ticketService.getAvailableSeats(nonExistentSection);
        });
        String expectedMessage = "Invalid section: " + nonExistentSection;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    /**
     * Tests the viewSeats method of TicketService.
     * Verifies that passengers in a specific section can be viewed correctly.
     */

    @Test
    void testViewSeats() {
        Passenger passenger = new Passenger("John", "Doe", 30, "Male", 0, "john.doe@example.com");
        ticketService.purchaseTicket(passenger);
        List<Passenger> passengersInA = ticketService.viewSeats("A");

        assertEquals(1, passengersInA.size());
        assertEquals("John", passengersInA.get(0).getFirstName());
    }

    /**
     * Tests the checkIfPassengerExists method of TicketService.
     * Verifies that the existence of a passenger can be checked correctly.
     */

    @Test
    void testCheckIfPassengerExistsPositive() {
        Passenger passenger = new Passenger("John", "Doe", 30, "Male", 0, "john.doe@example.com");
        ticketService.purchaseTicket(passenger);

        assertTrue(ticketService.checkIfPassengerExists(1L));
    }


    /**
     * Tests the checkIfPassengerExists method of TicketService.
     * Verifies that the method correctly identifies a non-existent passenger.
     */

    @Test
    void testCheckIfPassengerExistsNegative() {
        assertFalse(ticketService.checkIfPassengerExists(2L));
    }
}

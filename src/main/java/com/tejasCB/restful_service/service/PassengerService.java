package com.tejasCB.restful_service.service;

import com.tejasCB.restful_service.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing passengers in the train ticketing system.
 */
@Service
public class PassengerService {

    @Autowired
    private TicketService ticketService;

    /**
     * Retrieves the passenger details for the given passenger ID.
     *
     * @param passengerId the unique identifier of the passenger
     * @return the passenger details, or null if no passenger with the given ID is found
     */
    public Passenger getPassenger(long passengerId) {
        return ticketService.getPassengerDetails().get(passengerId);
    }
}

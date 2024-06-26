package com.tejasCB.restful_service.controller;

import com.tejasCB.restful_service.model.Passenger;
import com.tejasCB.restful_service.model.Ticket;
import com.tejasCB.restful_service.service.PassengerService;
import com.tejasCB.restful_service.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;
    @Autowired
    private TicketService ticketService;

    /**
     * API to get all the tickets for a user.
     * @param id the ID of the passenger to remove
     * @return a response entity
     */

    @GetMapping("/{id}/tickets")
    public ResponseEntity<?> getUserTickets(@PathVariable long id) {
        if (!ticketService.checkIfPassengerExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Passenger with ID " + id + " not found.");
        }

        List<Ticket> tickets = ticketService.getAllTickets(id);
        return ResponseEntity.ok(tickets);
    }

    /**
     * API to get details of Passenger.
     * @param id the ID of the passenger to remove
     * @return a response entity
     */

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) {
        if (!ticketService.checkIfPassengerExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Passenger with ID " + id + " not found.");
        }

        Passenger passenger = passengerService.getPassenger(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(passenger);
    }

}

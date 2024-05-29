package com.tejasCB.restful_service.controller;

import com.tejasCB.restful_service.model.Passenger;
import com.tejasCB.restful_service.model.Ticket;
import com.tejasCB.restful_service.service.PassengerService;
import com.tejasCB.restful_service.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private PassengerService passengerService;


    /**
     * API to purchase a ticket.
     * @param passenger the passenger details
     * @return the purchased ticket
     */

   @PostMapping("/purchase")
   public ResponseEntity<Ticket> purchaseTicket(@RequestBody Passenger passenger) {
       System.out.println("passenger ==>"+passenger);
       Ticket ticket = ticketService.purchaseTicket(passenger);
       return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
               .body(ticket);
   }

    /**
     * API to get the details of a ticket by PNR.
     * @param pnr the PNR of the ticket
     * @return the ticket details
     */
    @GetMapping("/{pnr}")
    public ResponseEntity<?> getTicketDetails(@PathVariable long pnr) {
        System.out.println("pnr ==>"+pnr);
        if (!ticketService.checkIfPnrExists(pnr)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PNR not found: " + pnr);
        }
        Ticket ticket = ticketService.getTicketDetail(pnr);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(ticket);
    }

    /**
     * API to view all passengers in a specified section.
     * @param section the section of the train (e.g., "A" or "B")
     * @return a list of passengers in the specified section
     */

   @GetMapping("/view/{section}")
   public ResponseEntity<?> viewSeats(@PathVariable String section) {
       section = section.toUpperCase();
       if (!section.equals("A") &&  !section.equals("B")) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are only 2 sections A & B");
       }
       List<Passenger> passengers = ticketService.viewSeats(section);
       //only getting the passenger details not their seats
       return ResponseEntity.ok(passengers);
   }

    /**
     * API to modify the seat of a ticket.
     * @param pnr the PNR of the ticket
     * @param newSeat the new seat to be assigned
     * @return the modified ticket
     */
    @PutMapping("/modify/{pnr}")
    public ResponseEntity<?> modifySeat(@PathVariable long pnr, @RequestBody String newSeat) {
        System.out.println("pnr , new Seat ==> " + pnr + " " + newSeat);
        if (!ticketService.checkIfPnrExists(pnr)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PNR not found: " + pnr);
        }
        if(!ticketService.isSeatAvailable(newSeat))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Selected seat " + newSeat + " not available.");
        Ticket ticket = ticketService.modifySeat(pnr, newSeat);
        return ResponseEntity.ok(ticket);
    }

    /**
     * API to cancel a ticket by PNR.
     * @param pnr the PNR of the ticket to be cancelled
     * @return a response indicating the result of the cancellation
     */
    @DeleteMapping("/cancel/{pnr}")
    public ResponseEntity<?> cancelTicket(@PathVariable long pnr) {
        if (!ticketService.checkIfPnrExists(pnr)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PNR not found: " + pnr);
        }
        ticketService.cancelTicket(pnr);
        return ResponseEntity.status(HttpStatus.OK).body("Ticket cancelled successfully for PNR: " + pnr);
    }

    /**
     * API to check available seats on the basis of section selected.
     * @param section the section (A or B)
     * @return a response indicating the available seats in a section
     */

    @GetMapping("/available_seats/{section}")
    public ResponseEntity<?> getAvailableSeats(@PathVariable String section)
    {
        section = section.toUpperCase();
        if (!section.equals("A") &&  !section.equals("B")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are only 2 sections A & B");
        }
        Set<String> seats = ticketService.getAvailableSeats(section);
        return ResponseEntity.ok(seats);
    }

}

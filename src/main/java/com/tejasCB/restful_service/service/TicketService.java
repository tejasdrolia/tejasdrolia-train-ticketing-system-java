package com.tejasCB.restful_service.service;

import com.tejasCB.restful_service.model.Passenger;
import com.tejasCB.restful_service.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class TicketService {
    private Map<Long, Ticket> ticketRepository = new HashMap<>();//PNR - Ticket
    private Map<Long, List<Long>> passengerTickets = new HashMap<>();// passenger id - List of Pnrs
    public Map<Long, Passenger> PassengerDetails = new HashMap<>();// passenger id - Passenger info
    private final Set<String> availableSeatsA = new HashSet<>();
    private final Set<String> availableSeatsB = new HashSet<>();
    private long ticketCounter = 1; // for generating unique PNR's
    private long idCounter = 1; // for generating unique idCounter


    public TicketService() {
        // Initialize the available seats for coaches A and B
        for (int i = 1; i <= 50; i++) {
            availableSeatsA.add("A" + i);
            availableSeatsB.add("B" + i);
        }
    }

    /**
     * Purchases a ticket for the given passenger.
     * @param passenger the passenger for whom the ticket is to be purchased
     * @return the purchased ticket
     */

    public Ticket purchaseTicket(Passenger passenger) {
        // Generate a new ID if the passenger does not provide one
        long ID;
        if (passenger.getId() == 0) {
            ID = idCounter++;
            passenger.setId(ID);
            PassengerDetails.put(passenger.getId(),passenger);
        }
        String seat = allocateSeat();
        if (seat == null) {
            throw new RuntimeException("No seats available");
        }

        long pnr = ticketCounter++;
        Ticket ticket = new Ticket(pnr, PassengerDetails.get(passenger.getId()), "London", "France", 20.0, seat);
        ticketRepository.put(pnr, ticket);
        List<Long> temp = getPnrsByPassengerId(passenger.getId());
        temp.add(pnr);
        passengerTickets.put(passenger.getId(), temp);
        return ticket;
    }

    /**
     * Views all passengers in the specified section.
     * @param section the section of the train (e.g., "A" or "B")
     * @return a list of passengers in the specified section
     */

    public List<Passenger> viewSeats(String section) {
        try {
            section = section.toUpperCase();
            List<Passenger> passengersInSection = new ArrayList<>();
            for (Ticket ticket : ticketRepository.values()) {
                if (ticket.getSeat().startsWith(section)) {
                    Passenger passenger = ticket.getPassenger();
                    passengersInSection.add(passenger);
                }
            }
            return passengersInSection;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving seats for section " + section, e);
        }
    }



    /**
     * Retrieves the details of a ticket based on its PNR.
     * @param pnr the PNR of the ticket
     * @return the ticket with the specified PNR
     */

    public Ticket getTicketDetail(long pnr) {
        try {
            Ticket ticket = ticketRepository.get(pnr);
            if (ticket == null) {
                throw new IllegalArgumentException("PNR not found: " + pnr);
            }
            return ticket;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving ticket for PNR " + pnr, e);
        }
    }

    /**
     * Cancels a ticket based on its PNR.
     * DELETE Request
     * @param pnr the PNR of the ticket to be cancelled
     */
    public void cancelTicket(long pnr) {
        try {
            Ticket ticket = ticketRepository.get(pnr);
            if (ticket == null) {
                throw new IllegalArgumentException("PNR not found: " + pnr);
            }
            ticketRepository.remove(pnr);

            List<Long> pnrs = passengerTickets.get(ticket.getPassenger().getId());
            if (pnrs != null) {
                pnrs.remove(pnr);
            }

            freeSeat(ticket.getSeat());
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error cancelling ticket for PNR " + pnr, e);
        }
    }

    /**
     * Modifies the seat of a ticket based on its PNR.
     * @param pnr the PNR of the ticket to be modified
     * @param newSeat the new seat to be assigned
     * @return the modified ticket
     */
    public Ticket modifySeat(long pnr, String newSeat) {
        try {
            Ticket ticket = ticketRepository.get(pnr);
            if (ticket == null) {
                throw new IllegalArgumentException("PNR not found: " + pnr);
            }

            if (!isSeatAvailable(newSeat)) {
                throw new IllegalArgumentException("Seat not available: " + newSeat);
            }

            freeSeat(ticket.getSeat());
            occupySeat(newSeat);
            ticket.setSeat(newSeat);
            //ticket = new Ticket(ticket.getPnr(), ticket.getPassenger(), ticket.getFrom(), ticket.getTo(), ticket.getPrice(), newSeat);
            ticketRepository.put(pnr, ticket);
            return ticket;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error modifying seat for PNR " + pnr, e);
        }
    }

    /**
     * returns a list of all Tickets associated  with a passenger
     * @param passengerId the ID of the passenger
     * @return List of Tickets
     */

    public List<Ticket> getAllTickets(long passengerId) {
        List<Ticket> tickets = new ArrayList<>();
        try {
            List<Long> pnrs = getPnrsByPassengerId(passengerId);
            for (Long pnr : pnrs) {
                tickets.add(getTicketDetail(pnr));
            }
            return tickets;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving tickets for passenger ID " + passengerId, e);
        }
    }

    /**
     * returns a list of all PNR's which are linked with a passenger
     * @param passengerId the ID of the passenger
     * @return List of PNR's
     */
    public List<Long> getPnrsByPassengerId(long passengerId) {
        return passengerTickets.getOrDefault(passengerId, new ArrayList<>());
    }

    /**
     * Checks whether a Pnr exists
     * @param pnr PNR
     * @return True or False based on whether the provided PNR exists
     */

    public boolean checkIfPnrExists(long pnr)
    {
        return ticketRepository.containsKey(pnr);
    }

    /**
     * Checks whether a Passenger exists
     * @param passengerId the ID of the passenger
     * @return True or False based on whether the passenger with provides ID exists
     */

    public boolean checkIfPassengerExists(long passengerId) {
        return PassengerDetails.containsKey(passengerId);
    }

    /**
     * Gets available seats in a specified section.
     * @param section the section of the train (e.g., "A" or "B")
     * @return a set of available seats in the specified section
     */
    public Set<String> getAvailableSeats(String section) {
        if ("A".equals(section)) {
            return new HashSet<>(availableSeatsA);
        } else if ("B".equals(section)) {
            return new HashSet<>(availableSeatsB);
        } else {
            throw new IllegalArgumentException("Invalid section: " + section);
        }
    }

    public Map<Long, Passenger> getPassengerDetails() {
        return PassengerDetails;
    }

    /**
     * Allocates a seat to a passenger based on some logic.
     * @return the allocated seat
     */
    private String allocateSeat() {
        if (!availableSeatsA.isEmpty()) {
            String seat = availableSeatsA.iterator().next();
            availableSeatsA.remove(seat);
            return seat;
        } else if (!availableSeatsB.isEmpty()) {
            String seat = availableSeatsB.iterator().next();
            availableSeatsB.remove(seat);
            return seat;
        } else {
            return null;
        }
    }

    /**
     * Checks if a seat is available.
     * @param seat the seat to check
     * @return true if the seat is available, false otherwise
     */
    public boolean isSeatAvailable(String seat) {
        seat = seat.toUpperCase();
        if (seat.startsWith("A")) {
            return availableSeatsA.contains(seat);
        } else if (seat.startsWith("B")) {
            return availableSeatsB.contains(seat);
        } else {
            return false;
        }
    }

    /**
     * Frees a seat.
     * @param seat the seat to free
     */
    private void freeSeat(String seat) {
        if (seat.startsWith("A")) {
            availableSeatsA.add(seat);
        } else if (seat.startsWith("B")) {
            availableSeatsB.add(seat);
        }
    }

    /**
     * Occupies a seat.
     * @param seat the seat to occupy
     */
    private void occupySeat(String seat) {
        if (seat.startsWith("A")) {
            availableSeatsA.remove(seat);
        } else if (seat.startsWith("B")) {
            availableSeatsB.remove(seat);
        }
    }
}

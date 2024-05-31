package com.tejasCB.restful_service.model;

public class Ticket {
    private final long pnr; // Passenger Name Record
    private final Passenger passenger;
    private final String from;
    private final String to;
    private  double price;
    private  String seat;

    public Ticket(long pnr, Passenger passenger, String from, String to, double price, String seat) {
        this.pnr = pnr;
        this.passenger = passenger;
        this.from = from;
        this.to = to;
        this.price = price;
        this.seat = seat;
    }

    /**
     * @return the PNR (Passenger Name Record)
     */
    public long getPnr() {
        return pnr;
    }

    /**
     * @return the passenger details
     */
    public Passenger getPassenger() {
        return passenger;
    }

    /**
     * @return the departure location
     */
    public String getFrom() {
        return from;
    }

    /**
     * @return the destination location
     */
    public String getTo() {
        return to;
    }

    /**
     * @return the price of the ticket
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return the seat number
     */
    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat)
    {
        this.seat = seat;
    }

}

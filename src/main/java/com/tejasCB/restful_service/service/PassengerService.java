package com.tejasCB.restful_service.service;

import com.tejasCB.restful_service.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PassengerService {


    @Autowired
    private TicketService ticketService;


    public Passenger getPassenger(long passengerId)
    {
        return ticketService.getPassengerDetails().get(passengerId);
    }

}

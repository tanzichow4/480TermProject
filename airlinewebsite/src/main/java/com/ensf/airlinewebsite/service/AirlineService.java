package com.ensf.airlinewebsite.service;

import com.ensf.airlinewebsite.model.Airline;

import java.util.List;

public interface AirlineService {
    public Airline saveAirline(Airline airline);
    public List<Airline> getAllAirlines();
}

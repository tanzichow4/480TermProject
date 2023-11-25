package com.ensf.airlinewebsite.controller;

import com.ensf.airlinewebsite.model.Airline;
import com.ensf.airlinewebsite.service.AirlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airline")
@CrossOrigin
public class AirlineController {
    @Autowired
    private AirlineService airlineService;

    @PostMapping("/add")
    public String add(@RequestBody Airline airline){
        airlineService.saveAirline(airline);
        return "New airline is added";
    }

    @GetMapping("/getAll")
    public List<Airline> list(){
        return airlineService.getAllAirlines();
    }
}
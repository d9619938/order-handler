package com.local.orderhandler.controller;

import com.local.orderhandler.entity.Price;
import com.local.orderhandler.exception.HandlerException;
import com.local.orderhandler.service.PricesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/prices")
public class PricesController {
    private final PricesService pricesService;

    @Autowired
    public PricesController(PricesService priceListsService) {
        this.pricesService = priceListsService;
    }

    @PostMapping
    public ResponseEntity<Void> addPrice (@RequestBody @Valid Price price){
        try {
            String priceUrl = String.valueOf(pricesService.savePrice(price));
            URI location = URI.create("http://localhost:8080/prices" + priceUrl);
            return ResponseEntity.created(location).build();
        } catch (HandlerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @GetMapping("/get/{id}")
    public Price getPrice(@PathVariable("id") int id) {
        try{
            return pricesService.getPrice(id);
        } catch (HandlerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @GetMapping("/get")
    public List<Price> getAllPrices(){
        try {
            return pricesService.getAllPrices();
        } catch (HandlerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

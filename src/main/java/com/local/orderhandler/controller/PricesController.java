package com.local.orderhandler.controller;

import com.local.orderhandler.entity.Price;
import com.local.orderhandler.entity.Product;
import com.local.orderhandler.exception.HandlerException;
import com.local.orderhandler.service.PricesService;
import com.local.orderhandler.service.ProductsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
//@RestController
@RequestMapping("/prices")
public class PricesController {
    private final PricesService pricesService;
    private final ProductsService productsService;

    @Autowired
    public PricesController(PricesService priceListsService, ProductsService productsService) {
        this.pricesService = priceListsService;
        this.productsService = productsService;
    }
    @GetMapping()
    private String getAllPricesHTML(Model model) {
            List<Price> pricesList = pricesService.getAllPrices();
            model.addAttribute("prices", pricesList);
        return "prices";
    }

    @GetMapping("/add")
    public String addPrice (){
        try {
             List<Product> productList = productsService.getAllProducts();
             Price price = new Price(LocalDate.now(), productList);
             try {
                 pricesService.savePrice(price);
             } catch (HandlerException e) {
                 return "redirect:/prices?price_exists";
             }
        } catch (HandlerException e) {
            return "redirect:/prices?no_products";
        }
        return "redirect:/prices";

    }

//    @PostMapping
//    public ResponseEntity<Void> addPrice (@RequestBody @Valid Price price){
//        try {
//            String priceUrl = String.valueOf(pricesService.savePrice(price));
//            URI location = URI.create("http://localhost:8080/prices" + priceUrl);
//            return ResponseEntity.created(location).build();
//        } catch (HandlerException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
//        }
//    }
    @GetMapping("/get/{id}")
    public String getPrice(@PathVariable int id, Model model) {
        try{
            Price price = pricesService.getPrice(id);
            model.addAttribute("price", price);
        } catch (HandlerException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            return "redirect:/prices?price_exists";
        }
        return "price";
    }
    @GetMapping("/del/{id}")
    public String removePrice(@PathVariable int id){
        try {
            pricesService.removePrice(id);
        } catch (HandlerException e) {
           return "redirect:/prices/get/"+id+"?price_exists";
        }
        return "redirect:/prices";
    }



//    @GetMapping("/get")
//    public List<Price> getAllPrices(){
//        try {
//            return pricesService.getAllPrices();
//        } catch (HandlerException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
//        }
//    }
}

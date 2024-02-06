package com.local.orderhandler.controller;

import com.local.orderhandler.entity.Product;
import com.local.orderhandler.exception.HandlerException;
import com.local.orderhandler.service.ProductsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
@Validated
@RestController
@RequestMapping("/products")
public class ProductsController {
    private final ProductsService productService;

    @Autowired
    public ProductsController(ProductsService productService) {
        this.productService = productService;
    }

 @PostMapping()
 public ResponseEntity<Void> addProduct(@RequestBody @Valid Product product) {
        try {
            String productUrl = productService.saveProduct(product);
            URI location = URI.create("http://127.0.0.1:8080/products/" + productUrl);
            return ResponseEntity.created(location).build();
        } catch (HandlerException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
 }
 @GetMapping("/get/{article}")
 public Product getProduct(@PathVariable("article") String article){
        try{
            return productService.getProduct(article);
        } catch (HandlerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
 }
 @GetMapping("/get")
 public List<Product> getAllProducts(){
        try {
            return productService.getAllProducts();
        } catch (HandlerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

 }
 @DeleteMapping("/del/{article}")
 public ResponseEntity<Void> deleteProduct(@PathVariable("article") String article){
        try {
            productService.deleteProduct(article);
            return  ResponseEntity.ok().build();
        } catch (HandlerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
 }
 @PatchMapping("/update/{article}/{width}/{length}/{height}/{weight}")
    public ResponseEntity<Product> updateProductParam (@Size(max = 30) @PathVariable String article,
                                             @PathVariable double width,
                                             @PathVariable double length,
                                             @PathVariable double height,
                                             @PathVariable double weight) {
     Product res;
     try{
         res = productService.updateProductParam(article, width, length, height, weight);
     } catch (HandlerException e) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
     }
        return ResponseEntity.ok(res);
 }
 @PutMapping
    public ResponseEntity<Void> update (@RequestBody @Valid Product product)  {
       try {
           productService.update(product);
           return ResponseEntity.ok().build();
       } catch (HandlerException e){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
       }
 }
}

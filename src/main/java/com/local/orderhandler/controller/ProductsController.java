package com.local.orderhandler.controller;

import com.local.orderhandler.entity.Product;
import com.local.orderhandler.exception.HandlerException;
import com.local.orderhandler.service.ProductsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
 @GetMapping("/getHtml")  // НЕ РАБОТАЕТ
 public String getProduct(@RequestParam() String article, Model model) {
        try{
            Product product = productService.getProductById(article);
            model.addAttribute("productInfo", product);
            return "product";
        } catch (HandlerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
 }

 @GetMapping("/get")  // РАБОТАЕТ
 public Product getProduct(@RequestParam() String article) {
        try{
            return productService.getProductById(article);
        } catch (HandlerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


 @GetMapping("/getAllHtml") // НЕ РАБОТАЕТ
 public String getProductList(Model model){
     try {
         List<Product> productList = productService.getAllProducts();
         model.addAttribute("products", productList);
         return "products";
     } catch (HandlerException e) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
     }
 }

 @GetMapping("/getAll") // РАБОТАЕТ
 public List<Product> getAllProducts(){
        try {
            return productService.getAllProducts();
        } catch (HandlerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

 }
 @DeleteMapping("/del") // РАБОТАЕТ
 public ResponseEntity<Void> deleteProduct(@RequestParam("article") String article){
        try {
            productService.deleteProduct(article);
            return  ResponseEntity.ok().build();
        } catch (HandlerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
 }
 @PatchMapping("/update") // НЕ РАБОТАЕТ
    public ResponseEntity<Product> updateProductParam (@Size(max = 30) @RequestParam String article,
                                             @RequestParam double width,
                                             @RequestParam double length,
                                             @RequestParam double height,
                                             @RequestParam double weight) {
     Product res;
     try{
         res = productService.updateProductParam(article, width, length, height, weight);
     } catch (HandlerException e) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
     }
        return ResponseEntity.ok(res);
 }
 @PutMapping // НЕ РАБОТАЕТ
    public ResponseEntity<Void> update (@RequestBody @Valid Product product)  {
       try {
           productService.update(product);
           return ResponseEntity.ok().build();
       } catch (HandlerException e){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
       }
 }
}

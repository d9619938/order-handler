package com.local.orderhandler.controller;

import com.local.orderhandler.entity.Product;
import com.local.orderhandler.exception.HandlerException;
import com.local.orderhandler.service.ProductsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
@Validated
@Controller
@RequestMapping("/products")
public class ProductsController {
    private final ProductsService productService;

    @Autowired
    public ProductsController(ProductsService productService) {
        this.productService = productService;
    }

  @GetMapping("/add")
  public String getProductAddFormHTML(Product product){
        return "productAddForm";
  }

 @PostMapping("/add")
 public String addProductHTML(@Valid Product product,
                              @ModelAttribute MultipartFile image, BindingResult bindingResult) { // НАДО ТЕСТИРОВАТЬ
        if(bindingResult.hasErrors()) return "productAddForm";
        try {
            productService.saveProduct(product, image);
        } catch (HandlerException e) {
//            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
            return "redirect:/products/add?product_exists";
        }
        return "redirect:/products/getAllHtml";
 }
 @GetMapping("/getHtml/{art}")  // РАБОТАЕТ
 public String getProductHTML(@PathVariable String art, Model model) {
        try{
            Product product = productService.getProductById(art);
            model.addAttribute("productInfo", product);
            return "product";
        } catch (HandlerException e) {
            return "redirect:/products";
        }
 }
 @ResponseBody
 @GetMapping("/get")  // РАБОТАЕТ
 public Product getProductJSON(@RequestParam() String article) {
        try{
            return productService.getProductById(article);
        } catch (HandlerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    @GetMapping("/getAllHtml") // РАБОТАЕТ
 public String getAllProductsHTML(Model model){
//     try {
         List<Product> productList = productService.getProductList();
         model.addAttribute("products", productList);
         return "products";
 }
 @ResponseBody
 @GetMapping("/getAll") // РАБОТАЕТ
 public List<Product> getAllProductsJSON(){
        try {
            return productService.getAllProducts();
        } catch (HandlerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

 }

 @Secured("ROLE_ADMIN")
 @ResponseBody
 @DeleteMapping("/del") // РАБОТАЕТ
 public ResponseEntity<Void> deleteProduct(@RequestParam("article") String article){
        try {
            productService.deleteProduct(article);
            return  ResponseEntity.ok().build();
        } catch (HandlerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
 }

 @Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
 @ResponseBody
 @PatchMapping("/update") // РАБОТАЕТ
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
 @ResponseBody
 @PutMapping("/update") // РАБОТАЕТ (нужно передать в Body(raw) полный json объекта
 // {"article":"1234/02","name":"Джейн","type":"BED","version":"BLACK","width":0.8,"length":1.6,"height":0.15,"weight":40.0,"cost":13440.0,"imagePatch":null})
    public ResponseEntity<Void> updateProduct (@RequestBody @Valid Product product)  {
       try {
           productService.update(product);
           return ResponseEntity.ok().build();
       } catch (HandlerException e){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
       }
 }

 @GetMapping("/bucket/{article}")
    public String addBucket (@PathVariable String article, Principal principal){
        if (principal == null) {
            return "redirect:/products/getAllHtml";
            }
     try {
         productService.addToUserBucket(article, principal.getName());
         return "redirect:/products/getAllHtml";
     } catch (HandlerException e) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
     }
 }
 @GetMapping("/bucket/del/{article}")
 public String removeFromBucket(@PathVariable String article, Principal principal) {
        if (principal == null) {
            return "redirect:/bucket";
        }
        try {
            productService.removeFromUserBucket(article, principal.getName());
            return "redirect:/bucket";
        } catch (HandlerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
     }
 }

}

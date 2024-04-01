package com.local.orderhandler.controller;

import com.local.orderhandler.dto.BucketDto;
import com.local.orderhandler.entity.Bucket;
import com.local.orderhandler.entity.User;
import com.local.orderhandler.exception.HandlerException;
import com.local.orderhandler.service.AccountService;
import com.local.orderhandler.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/bucket")
public class BucketController {

    private final BucketService bucketService;
    private final AccountService accountService;

    @Autowired
    public BucketController(BucketService bucketService, AccountService accountService) {
        this.bucketService = bucketService;
        this.accountService = accountService;
    }

    @GetMapping
    public String about (Model model, Principal principal){
        if (principal == null){
            model.addAttribute("bucket", new BucketDto());
        } else {
            BucketDto bucketDto = bucketService.getBucketByUser(principal.getName());
            model.addAttribute("bucket", bucketDto);
        }
        return "bucket";
    }

    @GetMapping("/delAll")
    public String cleaningTheBucket (Principal principal){
        if (principal == null) {
            return "redirect:/bucket";
        }
        try {
            User user = accountService.getUserByUsername(principal.getName());
            Bucket bucket = user.getBucket();
            if (bucket != null)
            {
                bucketService.deleteAllProduct(bucket);
            }
            return "redirect:/bucket";
        } catch (HandlerException e) {
            throw new RuntimeException(e);
        }


    }


}

package com.local.orderhandler.controller;

import com.local.orderhandler.dto.StatisticDto;
import com.local.orderhandler.entity.Invoice;
import com.local.orderhandler.entity.Role;
import com.local.orderhandler.entity.User;
import com.local.orderhandler.exception.HandlerException;
import com.local.orderhandler.service.AccountService;
import com.local.orderhandler.service.BucketService;
import com.local.orderhandler.service.InvoiceService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final AccountService accountService;
    private final BucketService bucketService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, AccountService accountService, BucketService bucketService) {
        this.invoiceService = invoiceService;
        this.accountService = accountService;
        this.bucketService = bucketService;
    }

    @GetMapping
    public String getAllInvoicesHTML (Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("invoice", new Invoice());
        } else {
            List<Invoice> invoices = invoiceService.getInvoicesByUser(principal.getName());
            model.addAttribute("invoices", invoices);
        }
        return "invoices";
    }
    @GetMapping("/add")
    public String addInvoice(Principal principal) {
        if(principal == null) {
            return "redirect:/bucket";
        }
        User user = new User();
        try {
            user = accountService.getUserByUsername(principal.getName());
        } catch (HandlerException e) {
            return "redirect:/bucket?user_exists";
        }
        invoiceService.createNewInvoice(user);
        bucketService.deleteAllProduct(user.getBucket());
        return "redirect:/bucket?invoice_add";
    }

    @GetMapping("/get/{id}")
    public String getInvoiceHTML(@PathVariable Integer id, Model model) {
        try {
            Invoice invoice = invoiceService.getInvoiceById(id);
            model.addAttribute("invoice", invoice);
        } catch (HandlerException e) {
            return "redirect:/invoices?invoice_problem";
        }
        return "invoice";
    }

        @GetMapping("/getByUser/{id}")
    public String getInvoiceByUserIdHTML(@PathVariable Integer id, Model model){
            List<Invoice> invoices = invoiceService.getInvoicesByUserId(id);
            model.addAttribute("invoices",invoices);
            return "invoices";
    }

    @GetMapping("/getAll")
    public String getAllInvoiceHTML(Model model){
        List<Invoice> invoices = invoiceService.getInvoices();
        model.addAttribute("invoices",invoices);
        return "invoices";

    }

    @GetMapping("/send/{id}")
    public String sendInvoice(@PathVariable Integer id, Principal principal){
        if (principal == null){
            return "redirect:/invoices";
        }
        try {
            Invoice invoice = null;
            try{
                invoice = invoiceService.getInvoiceById(id);
            } catch (HandlerException e){
                return "redirect:/invoices?invoice_problem";
            }
            User user = accountService.getUserByUsername(principal.getName());
            invoiceService.sendEmail(user, invoice);
        } catch (HandlerException e) {
            return "redirect:/invoices?user_exists";
        }
        return "redirect:/invoices/get/"+ id +"?invoice_send";
    }


    @GetMapping("/stat")
    public String getAllStatistic(Model model) {
        if(invoiceService.getInvoices() == null || invoiceService.getInvoices().isEmpty()) {
            return "redirect:/invoices/stat?stat_exists";
        }
        List<StatisticDto> statisticDtoList = invoiceService.getAllStatistic();
        model.addAttribute("statistic", statisticDtoList);
        return "statistic";
    }


}

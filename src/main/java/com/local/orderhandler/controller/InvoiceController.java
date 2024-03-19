package com.local.orderhandler.controller;

import com.local.orderhandler.entity.Invoice;
import com.local.orderhandler.entity.User;
import com.local.orderhandler.exception.HandlerException;
import com.local.orderhandler.service.AccountService;
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

    @Autowired
    public InvoiceController(InvoiceService invoiceService, AccountService accountService) {
        this.invoiceService = invoiceService;
        this.accountService = accountService;
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
        return "redirect:/bucket?invoice_add";
    }

    @GetMapping("/get/{id}")
    public String getInvoiceHTML(@PathVariable Integer id, Model model){
        try {
            Invoice invoice = invoiceService.getInvoiceById(id);
            model.addAttribute("invoice", invoice);
        } catch (HandlerException e) {
            return "redirect:/invoices?invoice_problem";
        }
        return "invoice";

    }


}

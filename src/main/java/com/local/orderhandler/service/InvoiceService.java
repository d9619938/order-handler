package com.local.orderhandler.service;

import com.local.orderhandler.entity.*;
import com.local.orderhandler.exception.HandlerException;
import com.local.orderhandler.repository.AccountRepository;
import com.local.orderhandler.repository.InvoiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, AccountRepository accountRepository) {
        this.invoiceRepository = invoiceRepository;
        this.accountRepository = accountRepository;
    }
    public List<Invoice> getInvoicesByUser(String username) {
    User user = accountRepository.getUserByUsername(username).orElse(null);
    List <Invoice> invoices = invoiceRepository.findAllInvoiceByUser_Id(user.getId()).orElse(null);
    if (invoices == null) return Collections.emptyList();
    return invoices;
    }

    public Invoice getInvoiceById(int invoiceId) throws HandlerException {
        return invoiceRepository.findInvoiceById(invoiceId)
                .orElseThrow(() -> new HeadlessException("Инвойс по id " + invoiceId + " не найден"));
    }

    @Transactional
    public void createNewInvoice(User user){
        Invoice invoice = new Invoice();
        invoice.setCreatedAt(LocalDate.now());
        invoice.setUser(user);
        List<InvoiceDetails> invoiceDetails = new ArrayList<>();
        List<Product> productList = user.getBucket().getProductList();
        Map<Product, Integer> productIntegerMap = new HashMap<>();
         for(Product product : productList) {
             if(productIntegerMap.get(product) == null) {
                 productIntegerMap.put(product, 1);
             } else {
                 productIntegerMap.put(product, productIntegerMap.get(product) + 1);
             }
         }
         productIntegerMap.entrySet().forEach(productIntegerEntry ->
                 invoiceDetails.add(new InvoiceDetails(invoice, productIntegerEntry.getKey(),
                         productIntegerEntry.getValue(), productIntegerEntry.getKey().getCost())));
        invoice.setInvoiceDetails(invoiceDetails);
         double sum = 0;
         for(InvoiceDetails details : invoice.getInvoiceDetails())  {
//             details.setPrice(details.getProduct().getCost());
             sum = sum + (details.getAmount() * details.getProduct().getCost());
         }
         invoice.setSum(sum);
         invoiceRepository.save(invoice);
    }

}

package com.local.orderhandler.service;

import com.local.orderhandler.dto.StatisticDto;
import com.local.orderhandler.entity.*;
import com.local.orderhandler.exception.HandlerException;
import com.local.orderhandler.repository.AccountRepository;
import com.local.orderhandler.repository.InvoiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final AccountRepository accountRepository;
    private JavaMailSender javaMailSender;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository,
                          JavaMailSender javaMailSender,
                          AccountRepository accountRepository) {
        this.invoiceRepository = invoiceRepository;
        this.accountRepository = accountRepository;
        this.javaMailSender = javaMailSender;
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

    public List<Invoice> getInvoices(){
        return (List<Invoice>) invoiceRepository.findAll();
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
   @Async("invoiceExecutor")
    public /*CompletableFuture<String>*/void sendEmail(User user, Invoice invoice) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("mebel-klick@yandex.ru");
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Заказ Мебель Клик №" + invoice.getId() + " от " + invoice.getCreatedAt());
        String text = "Уважаемый(ая) " + user.getUsername()+  "!\n" +
                "\nДля Вас сформирован заказ:\n " + invoice.getInvoiceDetails().toString() + "\n" +
                "Сумма к оплате: " + invoice.getSum() + "руб.\n" +
                "\n" +
                "Для подтверждения заказа необходима оплата!\n"+
                "Реквизиты для оплаты:\n" +
                "ООО \"Мебель Клик \"\n" +
                "p/c 876398764198736549817326\n" +
                "Спасибо за Ваш заказ!";
        mailMessage.setText(text);
        javaMailSender.send(mailMessage);
//        return CompletableFuture.completedFuture("Счет отправлен пользователю");
    }

    public List<Invoice> getInvoicesByUserId(int userId) {
        List <Invoice> invoices = invoiceRepository.findInvoiceByUserId(userId).orElse(null);
        if (invoices == null) return Collections.emptyList();
        return invoices;
    }

    public List<StatisticDto> getAllStatistic() {
        List<StatisticDto> statisticDtoList = new ArrayList<>();
        List<User> userList = (List<User>) accountRepository.findAll();
        for (User user : userList) {
            statisticDtoList.add(new StatisticDto(user.getUsername(),
                    invoiceRepository.findInvoiceByUserId(user.getId()).orElse(Collections.emptyList())));
        }
        for (StatisticDto statByUser : statisticDtoList) {
            double sum = 0.0;
            double volumeSum = 0.0;
            LocalDate lastDate = null;
            for (Invoice invoice : statByUser.getInvoices()) {
                sum += invoice.getSum();
                volumeSum += invoice.getInvoiceDetails()
                        .stream().
                        mapToDouble(x ->(x.getProduct().getLength() *
                                x.getProduct().getWidth() *
                                x.getProduct().getHeight())).sum();
                lastDate = invoice.getCreatedAt();
            }
            statByUser.setSum(sum);
            statByUser.setVolumeSum(volumeSum);
            statByUser.setInvoiceAmount(statByUser.getInvoices().size());
            statByUser.setLastInvoiceDate(lastDate);
        }
        return statisticDtoList;
    }
}

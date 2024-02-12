package com.local.orderhandler.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "tb_invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "invoice.createdAt not null")
    @PastOrPresent(message = "invoice.createdAt not past or present")
    @Column (nullable = false/*,
            columnDefinition = "DATE CHECK (tb_invoices.created_at<= CURRENT_DATE"*/)
    private LocalDate createdAt;
    @NotNull(message = "user not null")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @NotNull(message = "invoice details not null")
    @NotEmpty(message = "invoice details not null")
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<InvoiceDetails> invoiceDetails = new ArrayList<>();
    @NotNull
    @PositiveOrZero
    private double sum;

    public Invoice(LocalDate createdAt, User user, List<InvoiceDetails> invoiceDetails) {
        this.createdAt = createdAt;
        this.user = user;
        this.invoiceDetails = invoiceDetails;
    }

    public Invoice() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<InvoiceDetails> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetails> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}

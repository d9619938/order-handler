package com.local.orderhandler.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "tb_details")
public class InvoiceDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "product_article", nullable = false)
    private Product product;

    @PositiveOrZero
    @Max(1000)
    @Column(nullable = false)
    private int amount;
    @PositiveOrZero
    @DecimalMax(value = "10000000.0")
    @Column(nullable = false)
    private double price;

    public InvoiceDetails(Invoice invoice, Product product, int amount) {
        this.invoice = invoice;
        this.product = product;
        this.amount = amount;
    }

    public InvoiceDetails() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

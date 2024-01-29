package com.local.orderhandler.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_buyers")
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "name not null")
    @NotEmpty (message = "name not empty")
    @Column(nullable = false, unique = true)
    private String name;
    @NotNull(message = "provider not null")
    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;
    @NotNull(message = "product list not null")
    @NotEmpty(message = "product list not null")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "tb_buers_products",
            joinColumns = @JoinColumn(name = "buyer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_article", referencedColumnName = "article"))
    private List<Product> productList = new ArrayList<>();
    @NotNull(message = "invoice list not null")
    @NotEmpty(message = "invoice list not null")
    @ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "tb_buers_invoices",
    joinColumns = @JoinColumn(name = "buyer_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "invoice_id", referencedColumnName = "id"))
    private List<Invoice> invoiceList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }
}

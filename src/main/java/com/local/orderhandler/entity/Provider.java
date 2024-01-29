package com.local.orderhandler.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_providers")
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "product list not null")
    @NotEmpty(message = "product list not empty")
    @OneToMany(mappedBy = "provider")
    private List<Product> productList = new ArrayList<>();

    @NotNull(message = "buyer list not null")
    @NotEmpty(message = "buyer list not empty")
    @OneToMany(mappedBy = "provider")
    private List<Buyer> buyersList = new ArrayList<>();

    @NotNull(message = "invoice not null")
    @NotEmpty(message = "invoice list not empty")
    @OneToMany(mappedBy = "provider")private List<Invoice> invoiceList = new ArrayList<>();
    @NotNull(message = "price list not null")
    @NotEmpty(message = "prise list not empty")
    @OneToMany(mappedBy = "provider")
    private List<Price> priceList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<Buyer> getBuyersList() {
        return buyersList;
    }

    public void setBuyersList(List<Buyer> buyersList) {
        this.buyersList = buyersList;
    }

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoceList) {
        this.invoiceList = invoceList;
    }

    public List<Price> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<Price> priceList) {
        this.priceList = priceList;
    }
}

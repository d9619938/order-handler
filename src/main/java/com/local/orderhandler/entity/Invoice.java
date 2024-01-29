package com.local.orderhandler.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "tb_invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "invoice.createdAt not null")
    @PastOrPresent(message = "invoice.createdAt not past or present")
    @Column (nullable = false,
            columnDefinition = "DATE CHECK (tb_invoice.created_at<= CURRENT_DATE")
    private LocalDate createdAt;
    @NotNull(message = "provider not null")
    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;
    @NotNull(message = "buyer not null")
    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;
    @NotNull(message = "product list not null")
    @NotEmpty(message = "product list not null")
    @ManyToMany
    @JoinTable(name = "tb_invoices_products",
            joinColumns = @JoinColumn(name = "invoice_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_article", referencedColumnName = "article"))
    private List<Product> productList = new ArrayList<>();

/*    public Invoice(LocalDate createdAt, Buyer buyer, List<Product> productList) {
      setCreatedAt(createdAt);
      setBuyer(buyer);
      setProductList(productList);
    }

    public Invoice() {
    }*/

    public int getId() {
        return id;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}

package com.local.orderhandler.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_prices")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "price.createdAt not null")
    @PastOrPresent(message = "price.createdAt not past or presents")
    @Column(nullable = false, updatable = false)
    private LocalDate createdAt;
    @NotNull (message = "product list not null")
    @ManyToMany
    @JoinTable(name = "tb_prices_products",
            joinColumns = @JoinColumn(name = "price_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_article", referencedColumnName = "article"))
    private List<Product> productList = new ArrayList<>();

    public Price(LocalDate createdAt, List<Product> productList) {
        this.createdAt = createdAt;
        this.productList = productList;
    }

    public Price() {
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

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}

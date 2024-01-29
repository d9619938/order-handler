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
    @NotNull(message = "provider not null")
    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;
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
    @PositiveOrZero
    @DecimalMax(value = "100000.0")
    @Column(nullable = false)
    private double cost;

    public int getId() {
        return id;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public double getCost() {
        return cost;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public void setCost(double price) {
        this.cost = price;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}

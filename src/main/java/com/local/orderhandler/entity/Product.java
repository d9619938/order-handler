package com.local.orderhandler.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.context.annotation.EnableMBeanExport;

@Entity
@Table(name = "tb_products")
public class Product {

    @NotNull(message = "article not null")
    @NotEmpty(message = "article not empty")
    @Size(max = 30)
    @Id()
    private String article;
    @NotNull(message = "name not null")
    @NotEmpty(message = "name not empty")
    @Column (nullable = false)
    private String name;
    @NotNull(message = "provider not null")
    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;
    @NotNull(message = "version not null")
    @NotEmpty(message = "version not empty")
    @Column(nullable = false)
    private String version;
    @Positive
    @DecimalMax(value = "3.0", message ="width is not validated")
    @Column (nullable = false)
    private double width;
    @Positive
    @DecimalMax(value = "3.0", message = "length is not validated")
    @Column(nullable = false)
    private double length;
    @Positive
    @DecimalMax(value = "3.0", message = "height is not validated")
    @Column(nullable = false)
    private double height;
    @Positive
    @DecimalMax(value = "200.0", message = "weight is not validated")
    @Column(nullable = false)
    private double weight;

/*    public Product(String article, String name, String version) {
        if (article == null || article.isEmpty()) throw new IllegalArgumentException("article not null");
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("name not null");
        if (version == null || version.isEmpty()) throw new IllegalArgumentException("version not null");
        this.article = article;
        this.name = name;
        this.version = version;
    }

    public Product() {
    }*/


    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}

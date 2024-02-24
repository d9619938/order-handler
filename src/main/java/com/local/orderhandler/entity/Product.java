package com.local.orderhandler.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.context.annotation.EnableMBeanExport;

import java.awt.*;

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
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "version not null")
    @Column(nullable = false)
    private VersionProduct version;
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
    @PositiveOrZero
    @DecimalMax(value = "100000.0")
    @Column(nullable = false)
    private double cost;
    //дата изменения прайса парсится из файла и обновляется в БД

  /*  @NotNull(message = "imagePatch not null")
    @NotEmpty(message = "imagePatch not empty")*/
    @Column(/*nullable = false*/)
    private String imagePatch;

    public Product(String article, String name, VersionProduct version, double cost) {
        this.article = article;
        this.name = name;
        this.version = version;
        this.cost = cost;
    }

    public Product() {
    }

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

    public VersionProduct getVersion() {
        return version;
    }

    public void setVersion(VersionProduct version) {
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getImagePatch() {
        return imagePatch;
    }

    public void setImagePatch(String imagePatch) {
        this.imagePatch = imagePatch;
    }
}

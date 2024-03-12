package com.local.orderhandler.dto;

import com.local.orderhandler.entity.Product;
import com.local.orderhandler.entity.Type;
import com.local.orderhandler.entity.VersionProduct;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class BucketDetailsDto {
    private String title;
    private String article;
    private Type type;
    private VersionProduct versionProduct;
    private Double price;
    private BigDecimal amount;
    private Double sum;

    public BucketDetailsDto(Product product) {
        this.title = product.getName();
        this.article = product.getArticle();
        this.type = product.getType();
        this.versionProduct = product.getVersion();
        this.price = product.getCost();
        this.amount = new BigDecimal(1.0);
        this.sum = product.getCost();
    }

    public BucketDetailsDto() {
    }

    public BucketDetailsDto(String title, String article, Type type, VersionProduct versionProduct, Double price, BigDecimal amount, Double sum) {
        this.title = title;
        this.article = article;
        this.type = type;
        this.versionProduct = versionProduct;
        this.price = price;
        this.amount = amount;
        this.sum = sum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public VersionProduct getVersionProduct() {
        return versionProduct;
    }

    public void setVersionProduct(VersionProduct versionProduct) {
        this.versionProduct = versionProduct;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}

package com.local.orderhandler.dto;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public class BucketDto {

    private int amountProducts;
    private Double sum;
    private List<BucketDetailsDto> bucketDetails = new ArrayList<>();

    public void aggregate() {
        this.amountProducts = bucketDetails.size();
        this.sum = bucketDetails.stream()
                .map(BucketDetailsDto :: getSum)
                .mapToDouble(Double :: doubleValue)
                .sum();

    }

    public BucketDto() {
    }

    public BucketDto(int amountProducts, Double sum, List<BucketDetailsDto> bucketDetails) {
        this.amountProducts = amountProducts;
        this.sum = sum;
        this.bucketDetails = bucketDetails;
    }

    public int getAmountProducts() {
        return amountProducts;
    }

    public void setAmountProducts(int amountProducts) {
        this.amountProducts = amountProducts;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public List<BucketDetailsDto> getBucketDetails() {
        return bucketDetails;
    }

    public void setBucketDetails(List<BucketDetailsDto> bucketDetails) {
        this.bucketDetails = bucketDetails;
    }
}

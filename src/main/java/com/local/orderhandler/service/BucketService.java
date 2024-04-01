package com.local.orderhandler.service;

import com.local.orderhandler.dto.BucketDetailsDto;
import com.local.orderhandler.dto.BucketDto;
import com.local.orderhandler.entity.Bucket;
import com.local.orderhandler.entity.Product;
import com.local.orderhandler.entity.User;
import com.local.orderhandler.repository.AccountRepository;
import com.local.orderhandler.repository.BucketRepository;
import com.local.orderhandler.repository.ProductsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BucketService {

    private final BucketRepository bucketRepository;
    private final ProductsRepository productsRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public BucketService(BucketRepository bucketRepository, ProductsRepository productsRepository, AccountRepository accountRepository) {
        this.bucketRepository = bucketRepository;
        this.productsRepository = productsRepository;
        this.accountRepository = accountRepository;
    }
    @Transactional
    Bucket createBucket(User user, List<String> productsArticles){
      Bucket bucket = new Bucket();
      bucket.setUser(user);
      List<Product> productList = new ArrayList<>();
      for (String art : productsArticles){
          productList.add(productsRepository.getProductsByArticle(art));
      }
      bucket.setProductList(productList);
      return bucketRepository.save(bucket);
    }
    @Transactional
    void addProducts(Bucket bucket, List<String> productsArticles){
        List<Product> products = bucket.getProductList();
        List<Product> newProductList = products;
        if(newProductList == null) newProductList = new ArrayList<>();
        else newProductList = new ArrayList<>(products);
        for (String art : productsArticles){
            newProductList.add(productsRepository.getProductsByArticle(art));
        }
        bucket.setProductList(newProductList);
        bucketRepository.save(bucket);

    }
    @Transactional
    public void deleteProduct(Bucket bucket, List<String> productsArticles) {
        List<Product> products = bucket.getProductList();
        List<Product> newProductList = products;
        if (newProductList == null) newProductList = new ArrayList<>();
        else newProductList = new ArrayList<>(products);
        for (String art : productsArticles){
            newProductList.remove(productsRepository.getProductsByArticle(art));
        }
        bucket.setProductList(newProductList);
        bucketRepository.save(bucket);
    }

    @Transactional
    public void deleteAllProduct(Bucket bucket){
        bucket.setProductList(new ArrayList<>());
        bucketRepository.save(bucket);
    }


    public BucketDto getBucketByUser(String username) {
        User user = accountRepository.getUserByUsername(username).orElse(null);
        if (user == null || user.getBucket() == null) {
            return new BucketDto();
        }
        BucketDto bucketDto = new BucketDto();
        Map<String, BucketDetailsDto> mapByProductArt = new HashMap<>();

        List<Product> products = user.getBucket().getProductList();
        for(Product product : products) {
            BucketDetailsDto detail = mapByProductArt.get(product.getArticle());
            if(detail == null) {
                mapByProductArt.put(product.getArticle(), new BucketDetailsDto(product));
            } else {
                detail.setAmount(detail.getAmount().add(new BigDecimal(1.0)));
                detail.setSum(detail.getSum() + product.getCost());
            }
        }
        bucketDto.setBucketDetails(new ArrayList<>(mapByProductArt.values()));
        bucketDto.aggregate();
        return bucketDto;
    }


}

package com.local.orderhandler.service;

import com.local.orderhandler.entity.Bucket;
import com.local.orderhandler.entity.Product;
import com.local.orderhandler.entity.User;
import com.local.orderhandler.repository.BucketRepository;
import com.local.orderhandler.repository.ProductsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BucketService {

    private final BucketRepository bucketRepository;
    private final ProductsRepository productsRepository;

    @Autowired
    public BucketService(BucketRepository bucketRepository, ProductsRepository productsRepository) {
        this.bucketRepository = bucketRepository;
        this.productsRepository = productsRepository;
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

}

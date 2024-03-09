package com.local.orderhandler.service;

import com.local.orderhandler.entity.Bucket;
import com.local.orderhandler.entity.Product;
import com.local.orderhandler.entity.User;
import com.local.orderhandler.exception.HandlerException;
import com.local.orderhandler.repository.BucketRepository;
import com.local.orderhandler.repository.ProductsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductsService {
    private final ProductsRepository productRepository;
    private final AccountService accountService;
    private final BucketService bucketService;

    @Autowired
    public ProductsService(ProductsRepository productRepository, AccountService accountService, BucketService bucketService) {
        this.productRepository = productRepository;
        this.accountService = accountService;
        this.bucketService = bucketService;
    }

    public String saveProduct(Product product, MultipartFile image) throws HandlerException{
        if (productRepository.existsById(product.getArticle())){
            throw new HandlerException("Товар с таким артикулом уже существует");
        }
//        productRepository.save(product);
        product.setImagePatch(saveFile(image));
        productRepository.save(product);
        return product.getArticle();
    }

    public void deleteProduct(String article) throws HandlerException{
        if (!productRepository.existsById(article)){
            throw new HandlerException("Товар с таким артикулом не найден");
        }
        productRepository.delete(productRepository.getProductsByArticle(article));
    }
    public Product getProductById(String article) throws HandlerException {
        if(!productRepository.existsById(article)){
            throw new HandlerException("Товар с таким артикулом не найден");
        }
       return productRepository.getProductsByArticle(article);
    }
    public List<Product> getAllProducts() throws HandlerException {
        List<Product> productList = (List<Product>) productRepository.findAll();
        if(productList.isEmpty()) throw new HandlerException("Список товаров пуст");
        return productList;
    }
    public List<Product> getProductList(){
        return (List<Product>) productRepository.findAll();
    }
    public Product updateProductParam(String article, double width, double length, double height, double weight)
            throws HandlerException{
        if (!productRepository.existsById(article)){
            throw new HandlerException("Товар с таким артикулом не найден");
        }
        productRepository.updateProductParam(article, width, length, height, weight);
        return productRepository.getProductsByArticle(article);
    }

    public void update (Product product) throws HandlerException {
        if (!productRepository.existsById(product.getArticle())){
            throw new HandlerException("Товар с таким артикулом не найден");
        }
        productRepository.save(product);

    }


    private String saveFile(MultipartFile image) throws HandlerException {
        String check = checkFile(image);
        if(!check.equals("true")) throw new HandlerException("Файл не прошел валидацию." + check);
        String fileName = "src/main/resources/static/images/"
                + UUID.randomUUID() + "-" + image.getOriginalFilename();
        try {
           try(OutputStream os = new FileOutputStream(fileName)) {
               os.write(image.getBytes());
           }
            return parseFileName(fileName);
        } catch (IOException e) {
            throw new HandlerException("Проблема загрузки файла " + e.getMessage());
        }
    }
    private String checkFile(MultipartFile image) {
        if(image.isEmpty()) return "Вам не удалось загрузить" + image.getOriginalFilename() + " потому, что файл пустой.";
        if(image.getSize() >= 10*1024*1024) return "Вам не удалось загрузить " + image.getOriginalFilename() + " потому, что файл" +
                " превышает допустимый размер (10 Мб).";
        if(!Objects.requireNonNull(image.getOriginalFilename()).endsWith(".jpg")) return "Вам не удалось загрузить"
                + image.getOriginalFilename() + " потому, что тип файла не jpeg";
        return "true";
    }

    private String parseFileName (String fileName) {
       String[] strings = fileName.split("/");
       return strings[strings.length-1];
    }
    @Transactional
    public void addToUserBucket(String productArticle, String username) throws HandlerException {
        User user = accountService.getUserByUsername(username);
        Bucket bucket = user.getBucket();
        if (bucket == null){
            Bucket newBucket = bucketService.createBucket(user, Collections.singletonList(productArticle));
            user.setBucket(newBucket);
            accountService.saveUser(user);
        } else {
            bucketService.addProducts(bucket, Collections.singletonList(productArticle));
        }
    }


}

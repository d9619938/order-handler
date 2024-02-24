package com.local.orderhandler.service;

import com.local.orderhandler.entity.Product;
import com.local.orderhandler.exception.HandlerException;
import com.local.orderhandler.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductsService {
    private final ProductsRepository productRepository;

    @Autowired
    public ProductsService(ProductsRepository productRepository) {
        this.productRepository = productRepository;
    }

    public String saveProduct(Product product, MultipartFile image) throws HandlerException{
        if (productRepository.existsById(product.getArticle())){
            throw new HandlerException("Товар с таким артикулом уже существует");
        }
        productRepository.save(product);
        product.setImagePatch(saveFile(image));
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
        String check;
//        if(!(check=checkFile(image)).equals("OK")) throw new HandlerException("Файл не прошел валидацию." + check);
        String fileName = "/Users/dmitrijbogdanov/IdeaProjects/my-training/order-handler/src/main/resources/static/images"
                + image.getOriginalFilename() + UUID.randomUUID();
        try {
            Files.write(Path.of(fileName), image.getBytes());
            return fileName;
        } catch (IOException e) {
            throw new HandlerException("Проблема загрузки файла " + e.getMessage());
        }
    }
    private String checkFile(MultipartFile image) {
        if(image.isEmpty()) return "Вам не удалось загрузить" + image.getName() + " потому, что файл пустой.";
        if(image.getSize() >= 10*1024*1024) return "Вам не удалось загрузить" + image.getName() + " потому, что файл" +
                " превышает допустимый размер (10 Мб).";
        if(!Objects.requireNonNull(image.getOriginalFilename()).endsWith(".jpg")) return "Вам не удалось загрузить"
                + image.getName() + " потому, что тип файла не jpeg";
        return "ОК";
    }


}

package com.local.orderhandler.service;

import com.local.orderhandler.entity.Product;
import com.local.orderhandler.exception.HandlerException;
import com.local.orderhandler.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {
    private final ProductsRepository productRepository;

    @Autowired
    public ProductsService(ProductsRepository productRepository) {
        this.productRepository = productRepository;
    }

    public String saveProduct(Product product) throws HandlerException{
        if (productRepository.existsById(product.getArticle())){
            throw new HandlerException("Товар с таким артикулом уже существует");
        }
        return productRepository.save(product).getArticle();
    }

    public void deleteProduct(String article) throws HandlerException{
        if (!productRepository.existsById(article)){
            throw new HandlerException("Товар с таким артикулом не найден");
        }
        productRepository.delete(productRepository.getProductsByArticle(article));
    }
    public Product getProduct(String article) throws HandlerException {
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


}

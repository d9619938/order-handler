package com.local.orderhandler.repository;

import com.local.orderhandler.entity.Product;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductsRepository extends CrudRepository<Product, String> {

    @Query(nativeQuery = true, value = "SELECT * FROM tb_products WHERE article =:article")
    Product getProductsByArticle(@NotNull(message = "article not null")
                                 @NotEmpty(message = "article not empty")
                                 @Size(max = 30)
                                 @Param("article")
                                 String productArticle);

    @Transactional// поставить на все запросы, кроме SELECT, чтобы сохранялись изменения.
    @Modifying // для Spring data jpa не SELECT запросы
    @Query(nativeQuery = true, value = "UPDATE tb_products " +
            "SET width =:width, length =:length, height =:height, weight =:weight WHERE article =:article")
    default void updateProductParam(@NotNull(message = "article not null") @NotEmpty(message = "article not empty") @Size(max = 30)
                        @Param("article") String article,
                                    @Positive @DecimalMax(value = "3.0", message = "width is not validated")
                        @Param("width") double width,
                                    @Positive @DecimalMax(value = "3.0", message = "length is not validated")
                        @Param("length") double length,
                                    @Positive @DecimalMax(value = "3.0", message = "height is not validated")
                        @Param("height") double height,
                                    @Positive @DecimalMax(value = "200.0", message = "weight is not validated")
                        @Param("weight") double weight) {
    }
}

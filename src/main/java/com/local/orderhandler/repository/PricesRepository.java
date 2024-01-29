package com.local.orderhandler.repository;

import com.local.orderhandler.entity.Price;
import org.springframework.data.repository.CrudRepository;

public interface PricesRepository extends CrudRepository<Price, Integer> {

}

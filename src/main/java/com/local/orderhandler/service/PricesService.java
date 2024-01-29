package com.local.orderhandler.service;

import com.local.orderhandler.entity.Price;
import com.local.orderhandler.exception.HandlerException;
import com.local.orderhandler.repository.PricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Service
public class PricesService {
    private final PricesRepository pricesRepository;

    @Autowired
    public PricesService(PricesRepository priceListsRepository) {
        this.pricesRepository = priceListsRepository;
    }

    public int savePrice(Price price) throws HandlerException{
        if (pricesRepository.existsById(price.getId())){
            throw new HandlerException("Прайс с таким id уже существует");
        }
        return pricesRepository.save(price).getId();
    }
    public Price getPrice(int id) throws HandlerException{
        if(!pricesRepository.existsById(id)){
            throw new HandlerException("Товар с таким артикулом не найден");
        }
        return pricesRepository.findById(id).get();
    }

    public List<Price> getAllPrices() throws HandlerException {
        List<Price> priceList = (List<Price>) pricesRepository.findAll();
        if(priceList.isEmpty()) throw new HandlerException("Прайсы не загружены в систему");
        return priceList;
    }
}

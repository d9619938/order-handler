package com.local.orderhandler.service;

import com.local.orderhandler.entity.Price;
import com.local.orderhandler.exception.HandlerException;
import com.local.orderhandler.repository.PricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Price> getAllPrices(){
        //        if(priceList.isEmpty()) throw new HandlerException("Прайсы не загружены в систему");
        return (List<Price>) pricesRepository.findAll();
    }

    public void removePrice(int id) throws HandlerException {
        if(pricesRepository.existsById(id)){
            pricesRepository.delete(pricesRepository.findById(id).get());
        } else {
            throw new HandlerException("Не удалось удалить прайс с id" + id);
        }

    }
}

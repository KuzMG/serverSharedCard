package com.project.sharedCardServer.model.basket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class BasketDao {
    @Autowired
    private BasketRepository basketRepository;

    public List<Basket> get(UUID purchaseId) {
        return basketRepository.get(purchaseId);
    }

    public void save(Basket basket) {
        if (basketRepository.findById(basket.getId()).isPresent()) {
            basketRepository.deleteById(basket.getId());
        }
        basketRepository.save(
                basket.getId(),
                basket.getIdPurchase(),
                basket.getCount());
    }


    public void delete(UUID basketId) {
        basketRepository.deleteById(basketId);
    }


    public Basket getById(UUID id) {
        return basketRepository.findById(id).get();
    }

    public List<Basket> getAll(UUID personId) {
        return basketRepository.getAll(personId);
    }

    public List<UUID> deleteByPurchaseId(UUID purchaseId) {
        return basketRepository.deleteByPurchaseId(purchaseId).stream().map(Basket::getId).toList();
    }
}
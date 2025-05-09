package com.project.sharedCardServer.model.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class PurchaseDao {
    @Autowired
    private PurchaseRepository purchaseRepository;

    public List<Purchase> getAll(UUID idUser) {
        return purchaseRepository.getAll(idUser);
    }

    public void save(Purchase purchase) {
        if (purchaseRepository.findById(purchase.getId()).isPresent()) {

            purchaseRepository.update(
                    purchase.getId(),
                    purchase.isBought(),
                    purchase.getPurchaseDate());
        } else {
            purchaseRepository.save(
                    purchase.getId(),
                    purchase.getIdProduct(),
                    purchase.getIdCurrency(),
                    purchase.getIdGroup(),
                    purchase.getIdPerson(),
                    purchase.getCount(),
                    purchase.getPrice(),
                    purchase.getDescription(),
                    purchase.getCreationDate(),
                    purchase.isBought(),
                    purchase.getPurchaseDate());
        }
    }


    public void delete(UUID purchaseId) {
        purchaseRepository.deleteById(purchaseId);
    }

    public List<Purchase> get(UUID groupId) {
        return purchaseRepository.get(groupId);
    }

    public Purchase getById(UUID id) {
        return purchaseRepository.findById(id).get();
    }
}
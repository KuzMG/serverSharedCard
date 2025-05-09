package com.project.sharedCardServer.model.history;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class HistoryDao {
    @Autowired
    private HistoryRepository historyRepository;

    public void save(History history) {
        if (historyRepository.findById(history.getIdBasket()).isPresent()) {
            historyRepository.deleteById(history.getIdBasket());
        }
        historyRepository.save(
                history.getIdBasket(),
                history.getIdCurrency(),
                history.getIdShop(),
                history.getIdPerson(),
                history.getPrice(),
                history.getPurchaseDate());
    }


    public void delete(UUID purchaseId) {
        historyRepository.deleteById(purchaseId);
    }



    public History getById(UUID id) {
        return historyRepository.findById(id).get();
    }

    public List<History> getAll(UUID personId) {
        return historyRepository.getAll(personId);
    }
}
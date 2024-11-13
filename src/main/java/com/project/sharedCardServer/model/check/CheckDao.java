package com.project.sharedCardServer.model.check;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class CheckDao {
    @Autowired
    private CheckRepository checkRepository;

    public List<Check> getAll(UUID idUser) {
        return checkRepository.getAll(idUser);
    }

    public void save(Check check) {
        if (checkRepository.findById(check.getId()).isPresent()) {
            checkRepository.deleteById(check.getId());
        }
        checkRepository.save(
                check.getId(),
                check.getIdProduct(),
                check.getIdShop(),
                check.getIdCurrency(),
                check.getIdMetric(),
                check.getIdGroup(),
                check.getId_creator(),
                check.getId_buyer(),
                check.getCount(),
                check.getPrice(),
                check.getDescription(),
                check.getDateFirst(),
                check.getDateLast(),
                check.isStatus());
    }


    public void delete(UUID checkId) {
        checkRepository.deleteById(checkId);
    }

    public List<Check> get(UUID groupId) {
        return checkRepository.get(groupId);
    }

    public Check getById(UUID id) {
        return checkRepository.findById(id).get();
    }
}
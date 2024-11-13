package com.project.sharedCardServer.model.target;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TargetDao  {
@Autowired private TargetRepository repository;

    public List<Target> getAll(UUID idUser) {
        return repository.getAll(idUser);
    }

    public void save(Target target) {
        if (repository.findById(target.getId()).isPresent()) {
            repository.deleteById(target.getId());
        }
        repository.save(target.getId(),
                target.getName(),
                target.getIdGroup(),
                target.getIdCategory(),
                target.getIdShop(),
                target.getPriceFirst(),
                target.getPriceLast(),
                target.getIdCurrencyFirst(),
                target.getIdCurrencyLast(),
                target.getIdCreator(),
                target.getIdBuyer(),
                target.getDateFirst(),
                target.getDateLast(),
                target.isStatus());
    }

    public void delete(UUID targetId) {
        repository.deleteById(targetId);
    }

    public List<Target> get(UUID groupId) {
        return repository.get(groupId);
    }

    public Target getById(UUID id) {
        return repository.findById(id).get();
    }
}

package com.project.sharedCardServer.model.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopDao {
    @Autowired
    private ShopRepository repository;
    public void save(Shop shop){
        repository.save(shop);}

    public List<Shop> getAllShops(){
        List<Shop> shops = new ArrayList<>();
        Streamable.of(repository.findAll())
                .forEach(shops::add);
        return shops;}

    public Long getCountShops(){
        return repository.count();
    }

    public Shop getShopById(Integer id){
        return repository.findById(id).orElse(null);}

    public void delete(Shop shop){
        repository.delete(shop);
    }

    public List<Shop> getAll() {
        return (List<Shop>) repository.findAll();
    }
}

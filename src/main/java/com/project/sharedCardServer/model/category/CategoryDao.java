package com.project.sharedCardServer.model.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryDao {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired

    private CategoryTypeRepository categoryTypeRepository;

    public void addType(){
        categoryTypeRepository.save(new CategoryTypes(1,"продукты"));
        categoryTypeRepository.save(new CategoryTypes(2,"цели"));
        categoryTypeRepository.save(new CategoryTypes(3,"рецепты"));
    }
    public void addCategory(){
        categoryRepository.save(new Category("мясо","meat",new CategoryTypes(1)));
        categoryRepository.save(new Category("авто","car",new CategoryTypes(2)));
        categoryRepository.save(new Category("супы","soup",new CategoryTypes(3)));
    }
    public List<Category> getAll(){
        return (List<Category>) categoryRepository.findAll();
    }

}

package com.project.sharedCardServer.model.category_product;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CategoryProductRepository extends CrudRepository<CategoryProduct, Integer> {
}

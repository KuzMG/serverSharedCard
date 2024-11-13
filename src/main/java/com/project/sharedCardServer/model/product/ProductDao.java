package com.project.sharedCardServer.model.product;

import com.project.sharedCardServer.model.metrics.Metric;
import com.project.sharedCardServer.model.metrics.MetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDao {
    @Autowired
    private ProductRepository repository;

    public List<Product> getAll() {
        return (List<Product>) repository.findAll();
    }
}

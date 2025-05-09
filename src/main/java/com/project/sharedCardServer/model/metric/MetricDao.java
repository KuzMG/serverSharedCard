package com.project.sharedCardServer.model.metric;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MetricDao {
    @Autowired
    private MetricRepository repository;

    public void save(Metric metric) {
        repository.save(metric);
    }
    public void delete(Metric metric){
        repository.delete(metric);}

    public List<Metric> getAll() {
        return (List<Metric>) repository.findAll();
    }
}

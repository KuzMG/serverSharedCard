package com.project.sharedCardServer.model.metrics;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricRepository extends CrudRepository<Metric, Integer> {
}

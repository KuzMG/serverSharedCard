package com.project.sharedCardServer.model.metric;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricRepository extends CrudRepository<Metric, Integer> {
}

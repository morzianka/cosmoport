package com.space.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PredicateBuilder {
    private CriteriaBuilder builder;
    private List<Predicate> predicates = new ArrayList<>();

    public List<Predicate> getPredicates() {
        return predicates;
    }

    public PredicateBuilder(CriteriaBuilder builder) {
        this.builder = builder;
    }

    public void addGTE(Date date, Path<Date> path) {
        if (isNotNull(date)) {
            predicates.add(builder.greaterThanOrEqualTo(path, date));
        }
    }

    public void addLTE(Date date, Path<Date> path) {
        if (isNotNull(date)) {
            predicates.add(builder.lessThanOrEqualTo(path, date));
        }
    }

    public void addGTE(Number num, Path<Double> path) {
        if (isNotNull(num)) {
            predicates.add(builder.greaterThanOrEqualTo(path, num.doubleValue()));
        }
    }

    public void addLTE(Number num, Path<Double> path) {
        if (isNotNull(num)) {
            predicates.add(builder.lessThanOrEqualTo(path, num.doubleValue()));
        }
    }

    public void addE(Object obj, Path<Object> path) {
        if (isNotNull(obj)) {
            predicates.add(builder.equal(path, obj));
        }
    }

    public void addLike(String text, Path<String> path) {
        if (isNotNull(text)) {
            predicates.add(builder.like(path, "%" + text + "%"));
        }
    }

    private boolean isNotNull(Object obj) {
        return obj != null;
    }
}

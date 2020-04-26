package com.space.repository;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

@Repository
public class ShipDAOImpl implements ShipDAO {
    @PersistenceContext
    private final EntityManager entityManager;

    public ShipDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public List<Ship> getWithParams(String name, String planet, ShipType shipType, Date after, Date before,
                                    Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize,
                                    Integer maxCrewSize, Double minRating, Double maxRating, ShipOrder order,
                                    Integer pageNumber, Integer pageSize) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ship> criteria = builder.createQuery(Ship.class);
        Root<Ship> root = criteria.from(Ship.class);
        criteria.select(root);

        PredicateBuilder predBuilder = new PredicateBuilder(builder);
        predBuilder.addLike(name, root.get("name"));
        predBuilder.addLike(planet, root.get("planet"));
        predBuilder.addE(shipType, root.get("shipType"));
        predBuilder.addE(isUsed, root.get("isUsed"));
        predBuilder.addLTE(before, root.get("prodDate"));
        predBuilder.addGTE(after, root.get("prodDate"));
        predBuilder.addLTE(maxSpeed, root.get("speed"));
        predBuilder.addGTE(minSpeed, root.get("speed"));
        predBuilder.addLTE(maxCrewSize, root.get("crewSize"));
        predBuilder.addGTE(minCrewSize, root.get("crewSize"));
        predBuilder.addLTE(maxRating, root.get("rating"));
        predBuilder.addGTE(minRating, root.get("rating"));
        List<Predicate> predicates = predBuilder.getPredicates();

        criteria.where(predicates.toArray(new Predicate[0]));
        criteria.orderBy(builder.asc(root.get(order.getFieldName())));
        TypedQuery<Ship> query = entityManager.createQuery(criteria);
        query.setFirstResult(pageNumber);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    @Override
    @Transactional
    public Ship add(Ship ship) {
        return entityManager.merge(ship);
    }

    @Override
    @Transactional
    public Ship update(Ship ship) {
        return entityManager.merge(ship);
    }

    @Override
    @Transactional
    public Integer count() {
        return null;
    }

    @Override
    @Transactional
    public Ship getById(Long id) {
        return entityManager.find(Ship.class, id);
    }

    @Override
    @Transactional
    public Ship delete(Long id) {
        Ship ship = entityManager.find(Ship.class, id);
        if (ship != null) {
            entityManager.remove(ship);
        }
        return ship;
    }
}

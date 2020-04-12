package com.space.repository;

import com.space.model.Ship;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ShipDAOImpl implements ShipDAO {
    private final EntityManager entityManager;

    public ShipDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Ship> getAll() {
        return entityManager.createQuery("from ship", Ship.class).getResultList();
    }

    @Override
    @Transactional
    public Ship add(Ship ship) {
        return entityManager.merge(ship);
    }

    @Override
    @Transactional
    public void update(Ship ship) {

    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
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

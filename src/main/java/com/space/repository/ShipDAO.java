package com.space.repository;

import com.space.model.Ship;

import java.util.List;

public interface ShipDAO {
    List<Ship> getAll();

    Ship add(Ship ship);

    void update(Ship ship);

    Integer count();

    Ship getById(Long id);

    Ship delete(Long id);
}

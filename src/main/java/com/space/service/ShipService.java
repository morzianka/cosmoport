package com.space.service;

import com.space.model.Ship;

import java.util.List;

public interface ShipService {
    List<Ship> getAll();

    Ship add(Ship ship);

    void update(Ship ship);

    Integer count();

    Ship getById(Long id);

    Ship delete(Long id);
}

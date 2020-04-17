package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;

import java.util.List;

public interface ShipService {
    List<Ship> getWithParams(String name, String planet, ShipType shipType, Long after, Long before,
                             Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize,
                             Integer maxCrewSize, Double minRating, Double maxRating, ShipOrder order,
                             Integer pageNumber, Integer pageSize);

    Ship add(Ship ship);

    Ship update(Long id, Ship ship);

    Integer count();

    Ship getById(Long id);

    Ship delete(Long id);
}

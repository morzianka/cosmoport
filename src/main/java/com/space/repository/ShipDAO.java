package com.space.repository;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;

import java.util.Date;
import java.util.List;

public interface ShipDAO {
    List<Ship> getWithParams(String name, String planet, ShipType shipType, Date after, Date before,
                             Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize,
                             Integer maxCrewSize, Double minRating, Double maxRating, ShipOrder order,
                             Integer pageNumber, Integer pageSize);

    Ship add(Ship ship);

    Ship update(Ship ship);

    Ship getById(Long id);

    Ship delete(Long id);
}

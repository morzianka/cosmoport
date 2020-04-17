package com.space.repository;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface ShipRepository extends PagingAndSortingRepository<Ship, Long> {
    @Transactional
    List<Ship> findAllByNameLikeOrPlanetLikeOrShipTypeOrProdDateBetweenOrIsUsedOrSpeedBetweenOrCrewSizeBetweenOrRatingBetween(@Param("name") String name, @Param("planet") String planet, @Param("shipType") ShipType shipType, @Param("prodDate") Date after, @Param("prodDate") Date before,
                                                                                                                                     @Param("isUsed") Boolean isUsed, @Param("speed") Double minSpeed, @Param("speed") Double maxSpeed, @Param("crewSize") Integer minCrewSize,
                                                                                                                                     @Param("crewSize") Integer maxCrewSize, @Param("rating") Double minRating, @Param("rating") Double maxRating, Pageable pageable);
}

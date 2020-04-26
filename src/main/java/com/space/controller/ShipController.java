package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class ShipController {
    private final ShipService shipService;

    @Autowired
    public ShipController(@Qualifier("shipServiceImpl") ShipService shipService) {
        this.shipService = shipService;
    }

    @GetMapping("/ships")
    public ResponseEntity<List<Ship>> get(String name, String planet, ShipType shipType,
                                          Long after, Long before, Boolean isUsed,
                                          Double minSpeed, Double maxSpeed,
                                          Integer minCrewSize, Integer maxCrewSize,
                                          Double minRating, Double maxRating, ShipOrder order,
                                          @RequestParam(defaultValue = "0") Integer pageNumber,
                                          @RequestParam(defaultValue = "3") Integer pageSize) throws RuntimeException {

        List<Ship> ships = shipService.getWithParams(name, planet, shipType, after, before, isUsed, minSpeed,
                maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating, order, pageNumber, pageSize);
        return new ResponseEntity<>(ships, HttpStatus.OK);
    }

    @PostMapping("/ships")
    public ResponseEntity<Ship> add(@RequestBody Ship ship) throws RuntimeException {
        Ship createdShip = shipService.add(ship);
        return new ResponseEntity<>(createdShip, HttpStatus.OK);
    }

    @GetMapping("/ships/{id}")
    public ResponseEntity<Ship> getById(@PathVariable() Long id) throws RuntimeException {
        Ship ship = shipService.getById(id);
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    @GetMapping("/ships/count")
    public ResponseEntity<Integer> count(String name, String planet, ShipType shipType,
                                         Long after, Long before, Boolean isUsed,
                                         Double minSpeed, Double maxSpeed,
                                         Integer minCrewSize, Integer maxCrewSize,
                                         Double minRating, Double maxRating) throws RuntimeException {

        List<Ship> ships = shipService.getWithParams(name, planet, shipType, after, before, isUsed, minSpeed,
                maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating, ShipOrder.ID, 0, Integer.MAX_VALUE);
        return new ResponseEntity<>(ships.size(), HttpStatus.OK);
    }

    @DeleteMapping("/ships/{id}")
    public ResponseEntity delete(@PathVariable() Long id) throws RuntimeException {
        shipService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/ships/{id}")
    public ResponseEntity<Ship> update(@PathVariable() Long id, @RequestBody Ship ship) {
        Ship updatedShip = shipService.update(id, ship);
        return new ResponseEntity<>(updatedShip, HttpStatus.OK);
    }
}

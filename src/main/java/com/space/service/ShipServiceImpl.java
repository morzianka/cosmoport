package com.space.service;

import com.space.controller.ShipOrder;
import com.space.exception.InvalidParameterException;
import com.space.exception.RequestException;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ShipServiceImpl implements ShipService {
    private static final ShipValidator validator = new ShipValidator();
    private final ShipDAO shipDAO;

    @Autowired
    public ShipServiceImpl(@Qualifier("shipDAOImpl") ShipDAO shipDAO) {
        this.shipDAO = shipDAO;
    }

    public List<Ship> getWithParams(String name, String planet, ShipType shipType, Long after, Long before,
                                    Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize,
                                    Integer maxCrewSize, Double minRating, Double maxRating, ShipOrder order,
                                    Integer pageNumber, Integer pageSize) {

        Date dateAfter = after != null ? new Date(after) : null;
        Date dateBefore = before != null ? new Date(before) : null;

        return shipDAO.getWithParams(name, planet, shipType, dateAfter, dateBefore, isUsed, minSpeed, maxSpeed,
                minCrewSize, maxCrewSize, minRating, maxRating, order, pageNumber, pageSize);
    }

    @Override
    public Ship add(Ship ship) throws InvalidParameterException {
        if (validator.isInvalidShipForAdd(ship)) {
            throw new InvalidParameterException();
        }
        double k = findK(ship);
        calculateAndSetRating(ship, k);
        return shipDAO.add(ship);
    }

    @Override
    public Ship update(Long id, Ship ship) throws InvalidParameterException, RequestException {
        if (id < 1) {
            throw new InvalidParameterException("Id is not valid");
        }
        Ship oldShip = shipDAO.getById(id);
        if (oldShip == null) {
            throw new RequestException("Ship not found");
        }
        validator.fillNullFieldsWithOldValues(oldShip, ship);
        if (validator.isInvalidShipForUpdate(ship)) {
            throw new InvalidParameterException("Param is not valid");
        }
        ship.setId(id);
        double k = findK(ship);
        calculateAndSetRating(ship, k);
        return shipDAO.update(ship);
    }

    private double findK(Ship ship) {
        double k = 0.5;
        if (ship.getUsed() == null || !ship.getUsed()) {
            ship.setUsed(false);
            k = 1;
        }
        return k;
    }

    private void calculateAndSetRating(Ship ship, double k) {
        Calendar calendar = getCalendarByDate(ship.getProdDate());
        Double rating = new BigDecimal(80 * ship.getSpeed() * k / (3019 - calendar.get(Calendar.YEAR) + 1))
                .setScale(2, RoundingMode.HALF_UP).doubleValue();
        ship.setRating(rating);
    }

    private Calendar getCalendarByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    @Override
    public Ship getById(Long id) throws InvalidParameterException, RequestException {
        if (id == 0) {
            throw new InvalidParameterException();
        }
        Ship ship = shipDAO.getById(id);
        if (ship == null) {
            throw new RequestException();
        }
        return ship;
    }

    @Override
    public Ship delete(Long id) throws InvalidParameterException, RequestException {
        if (id == 0) {
            throw new InvalidParameterException();
        }
        Ship ship = shipDAO.delete(id);
        if (ship == null) {
            throw new RequestException();
        }
        return ship;
    }
}

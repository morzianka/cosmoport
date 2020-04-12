package com.space.service;

import com.space.exception.InvalidParameterException;
import com.space.exception.RequestException;
import com.space.model.Ship;
import com.space.repository.ShipDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ShipServiceImpl implements ShipService {
    private final ShipDAO shipDAO;

    @Autowired
    public ShipServiceImpl(@Qualifier("shipDAOImpl") ShipDAO shipDAO) {
        this.shipDAO = shipDAO;
    }


    public List<Ship> getAll() {
        return shipDAO.getAll();
    }

    @Override
    public Ship add(Ship ship) throws InvalidParameterException {
        if (!isValidShip(ship)) {
            throw new InvalidParameterException();
        }
        double k = 0.5;
        if (ship.getUsed() == null || !ship.getUsed()) {
            ship.setUsed(false);
            k = 1;
        }
        Double rating = calculateRating(ship, k);
        ship.setRating(rating);
        return shipDAO.add(ship);
    }

    private Double calculateRating(Ship ship, double k) {
        Calendar calendar = getCalendarByDate(ship.getProdDate());
        return 80 * ship.getSpeed() * k / (3019 - calendar.get(Calendar.YEAR) + 1);
    }

    private boolean isValidShip(Ship ship) {
        if (isNull(ship.getProdDate()) || isNull(ship.getCrewSize()) ||
                isNull(ship.getSpeed()) || isNull(ship.getShipType()) ||
                isParamInvalid(ship.getName()) || isParamInvalid(ship.getPlanet()) ||
                ship.getCrewSize() < 1 || ship.getCrewSize() > 9999 ||
                ship.getSpeed() < 0.01 || ship.getSpeed() > 0.99) {
            return false;
        }
        Calendar calendar = getCalendarByDate(ship.getProdDate());
        int prodYear = calendar.get(Calendar.YEAR);
        return prodYear >= 2018 && prodYear <= 3019;
    }

    private boolean isNull(Object param) {
        return param == null;
    }

    private boolean isParamInvalid(String param) {
        return isNull(param) || param.isEmpty() || param.length() > 50;
    }

    private Calendar getCalendarByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    @Override
    public void update(Ship ship) {
        shipDAO.update(ship);
    }

    @Override
    public Integer count() {
        return shipDAO.count();
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

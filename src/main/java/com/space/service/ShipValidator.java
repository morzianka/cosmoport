package com.space.service;

import com.space.model.Ship;

import java.util.Calendar;
import java.util.Date;

public class ShipValidator {
    public boolean isInvalidShipForAdd(Ship ship) {
        return isNull(ship.getProdDate()) || isNull(ship.getCrewSize()) ||
                isNull(ship.getSpeed()) || isNull(ship.getShipType()) ||
                isStringParamInvalid(ship.getName()) || isStringParamInvalid(ship.getPlanet()) ||
                isInvalidNotNullParams(ship);
    }

    public boolean isInvalidShipForUpdate(Ship ship) {
        return isInvalidNotNullParams(ship) ||
                isParamEmptyOrTooLarge(ship.getName()) || isParamEmptyOrTooLarge(ship.getPlanet());
    }

    public void fillNullFieldsWithOldValues(Ship oldShip, Ship ship) {
        if (isNull(ship.getUsed())) {
            ship.setUsed(oldShip.getUsed());
        }
        if (isNull(ship.getSpeed())) {
            ship.setSpeed(oldShip.getSpeed());
        }
        if (isNull(ship.getCrewSize())) {
            ship.setCrewSize(oldShip.getCrewSize());
        }
        if (isNull(ship.getName())) {
            ship.setName(oldShip.getName());
        }
        if (isNull(ship.getPlanet())) {
            ship.setPlanet(oldShip.getPlanet());
        }
        if (isNull(ship.getShipType())) {
            ship.setShipType(oldShip.getShipType());
        }
        if (isNull(ship.getProdDate())) {
            ship.setProdDate(oldShip.getProdDate());
        }
        if (isNull(ship.getRating())) {
            ship.setRating(oldShip.getRating());
        }
    }

    private boolean isInvalidNotNullParams(Ship ship) {
        return isCrewSizeInvalid(ship.getCrewSize()) ||
                isSpeedInvalid(ship.getSpeed()) || isProdDateInvalid(ship.getProdDate());
    }

    private boolean isCrewSizeInvalid(Integer crewSize) {
        return crewSize < 0 || crewSize > 9999;
    }

    private boolean isSpeedInvalid(Double speed) {
        return speed < 0 || speed > 1.0;
    }

    private boolean isProdDateInvalid(Date prodDate) {
        Calendar calendar = getCalendarByDate(prodDate);
        int prodYear = calendar.get(Calendar.YEAR);
        return prodYear < 2018 || prodYear > 3019;
    }

    private boolean isNull(Object param) {
        return param == null;
    }

    private boolean isParamEmptyOrTooLarge(String param) {
        return param.isEmpty() || param.length() > 50;
    }

    private boolean isStringParamInvalid(String param) {
        return isNull(param) || isParamEmptyOrTooLarge(param);
    }

    private Calendar getCalendarByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}

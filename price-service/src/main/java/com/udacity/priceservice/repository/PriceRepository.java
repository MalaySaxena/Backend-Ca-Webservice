package com.udacity.priceservice.repository;

import com.udacity.priceservice.entity.Price;
import com.udacity.priceservice.service.PriceException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PriceRepository extends CrudRepository<Price, Long> {

    Price getPriceByVehicleId(@Param("vehicleId") Long vehicleId) throws PriceException;

}

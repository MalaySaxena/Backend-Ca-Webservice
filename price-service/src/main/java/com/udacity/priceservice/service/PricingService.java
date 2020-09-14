package com.udacity.priceservice.service;

import com.udacity.priceservice.entity.Price;
import com.udacity.priceservice.repository.PriceRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
public class PricingService implements ApplicationRunner {

    private PriceRepository priceRepository;

    public PricingService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    /**
     * Holds {ID: Price} pairings (current implementation allows for 20 vehicles)
     */
    private static final List<Price> PRICES = LongStream
            .range(1, 20)
            .mapToObj(i -> new Price(i, "USD", randomPrice()))
            .collect(Collectors.toList());

    /**
     * If a valid vehicle ID, gets the price of the vehicle from the stored array.
     * @param vehicleId ID number of the vehicle the price is requested for.
     * @return price of the requested vehicle
     * @throws PriceException vehicleID was not found
     */
    public Price getPrice(Long vehicleId) throws PriceException {

        if (priceRepository.findById(vehicleId).isEmpty()) {
            throw new PriceException("Cannot find price for Vehicle " + vehicleId);
        }

        return priceRepository.findById(vehicleId).get();
    }

    /**
     * Gets a random price to fill in for a given vehicle ID.
     * @return random price for a vehicle
     */
    private static BigDecimal randomPrice() {
        return new BigDecimal(ThreadLocalRandom.current().nextDouble(1, 5))
                .multiply(new BigDecimal(5000d)).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        priceRepository.saveAll(PRICES);
    }
}

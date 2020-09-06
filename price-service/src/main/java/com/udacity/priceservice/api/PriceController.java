package com.udacity.priceservice.api;

import com.udacity.priceservice.entity.Price;
import com.udacity.priceservice.service.PriceException;
import com.udacity.priceservice.service.PricingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class PriceController {

    private PricingService pricingService;

    public PriceController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @GetMapping("/services/price")
    public Price get(@RequestParam Long vehicleId){
        try{
            return pricingService.getPrice(vehicleId).orElseThrow();
        }catch (PriceException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Price Not Found", e);
        }
    }

}

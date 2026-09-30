package com.driveease.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "VEHICLE-SERVICE")
public interface VehicleClient {

    @PutMapping("/api/vehicles/{id}/status")
    void updateVehicleStatus(@PathVariable("id") Long id, @RequestParam("status") String status);
}

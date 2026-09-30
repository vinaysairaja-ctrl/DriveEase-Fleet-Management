package com.driveease.vehicle.service;

import com.driveease.vehicle.model.Vehicle;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    private final Map<Long, Vehicle> vehicleStorage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @PostConstruct
    public void initFleetData() {
        addVehicle("SUV", "Ford Explorer 2024", "AVAILABLE", new BigDecimal("85.00"));
        addVehicle("SEDAN", "Honda Accord 2023", "AVAILABLE", new BigDecimal("55.00"));
        addVehicle("TRUCK", "Volvo FH16 Commercial", "MAINTENANCE", new BigDecimal("180.00"));
    }

    public Vehicle addVehicle(String type, String model, String status, BigDecimal price) {
        Long id = idGenerator.getAndIncrement();
        Vehicle vehicle = new Vehicle(id, type, model, status, price);
        vehicleStorage.put(id, vehicle);
        return vehicle;
    }

    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicleStorage.values());
    }

    public List<Vehicle> getAvailableVehicles() {
        return vehicleStorage.values().stream()
                .filter(v -> "AVAILABLE".equalsIgnoreCase(v.getAvailabilityStatus()))
                .collect(Collectors.toList());
    }

    public Vehicle getVehicleById(Long id) {
        return vehicleStorage.get(id);
    }

    public void updateStatus(Long id, String status) {
        Vehicle vehicle = vehicleStorage.get(id);
        if (vehicle != null) {
            vehicle.setAvailabilityStatus(status.toUpperCase());
        } else {
            throw new IllegalArgumentException("Vehicle not found with ID: " + id);
        }
    }
}

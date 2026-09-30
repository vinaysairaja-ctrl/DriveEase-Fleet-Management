package com.driveease.vehicle.model;

import java.math.BigDecimal;

public class Vehicle {
    private Long vehicleId;
    private String type;
    private String model;
    private String availabilityStatus; // AVAILABLE, BOOKED, MAINTENANCE
    private BigDecimal rentalPrice;

    public Vehicle() {}

    public Vehicle(Long vehicleId, String type, String model, String availabilityStatus, BigDecimal rentalPrice) {
        this.vehicleId = vehicleId;
        this.type = type;
        this.model = model;
        this.availabilityStatus = availabilityStatus;
        this.rentalPrice = rentalPrice;
    }

    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(String availabilityStatus) { this.availabilityStatus = availabilityStatus; }

    public BigDecimal getRentalPrice() { return rentalPrice; }
    public void setRentalPrice(BigDecimal rentalPrice) { this.rentalPrice = rentalPrice; }
}

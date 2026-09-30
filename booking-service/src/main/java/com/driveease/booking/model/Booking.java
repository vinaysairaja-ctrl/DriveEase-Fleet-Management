package com.driveease.booking.model;

import java.time.LocalDateTime;

public class Booking {
    private Long bookingId;
    private Long userId;
    private Long vehicleId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status; // CONFIRMED, RETURNED, CANCELLED

    public Booking() {}

    public Booking(Long bookingId, Long userId, Long vehicleId, LocalDateTime startDate, LocalDateTime endDate, String status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

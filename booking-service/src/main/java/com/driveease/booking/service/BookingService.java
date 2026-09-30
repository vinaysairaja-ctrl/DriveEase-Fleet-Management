package com.driveease.booking.service;

import com.driveease.booking.client.VehicleClient;
import com.driveease.booking.dto.BookingRequest;
import com.driveease.booking.model.Booking;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookingService {

    private final Map<Long, Booking> bookingStorage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final VehicleClient vehicleClient;

    public BookingService(VehicleClient vehicleClient) {
        this.vehicleClient = vehicleClient;
    }

    public synchronized Booking createBooking(BookingRequest request) {
        // Overlap Check Algorithm in Java
        boolean isOverlapping = bookingStorage.values().stream()
                .filter(b -> b.getVehicleId().equals(request.getVehicleId()))
                .filter(b -> "CONFIRMED".equalsIgnoreCase(b.getStatus()))
                .anyMatch(b -> request.getStartDate().isBefore(b.getEndDate()) && 
                               request.getEndDate().isAfter(b.getStartDate()));

        if (isOverlapping) {
            throw new IllegalStateException("Vehicle is already booked for the selected date duration.");
        }

        Long bookingId = idGenerator.getAndIncrement();
        Booking booking = new Booking(
                bookingId,
                request.getUserId(),
                request.getVehicleId(),
                request.getStartDate(),
                request.getEndDate(),
                "CONFIRMED"
        );

        bookingStorage.put(bookingId, booking);

        // Synchronous Inter-Service Call via Feign Client
        try {
            vehicleClient.updateVehicleStatus(request.getVehicleId(), "BOOKED");
        } catch (Exception e) {
            bookingStorage.remove(bookingId);
            throw new RuntimeException("Failed to update Vehicle Service status: " + e.getMessage());
        }

        return booking;
    }

    public Booking processReturn(Long bookingId) {
        Booking booking = bookingStorage.get(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Booking record not found for ID: " + bookingId);
        }

        booking.setStatus("RETURNED");

        // Synchronous Inter-Service Call via Feign Client to free vehicle
        vehicleClient.updateVehicleStatus(booking.getVehicleId(), "AVAILABLE");

        return booking;
    }

    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookingStorage.values());
    }

    public Booking getBookingById(Long id) {
        return bookingStorage.get(id);
    }
}

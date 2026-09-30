# DriveEase Fleet Operations Platform (In-Memory Microservices)

This is a complete, lightweight, database-free Spring Boot SOA microservices project for Review 1. All data is stored in-memory using thread-safe Java `ConcurrentHashMap` data structures.

## System Components
1. **Eureka Server** (Port 8761) - Service Discovery Registry
2. **API Gateway** (Port 8080) - Routing and Central Security
3. **User Service** (Port 8081) - In-Memory User Auth & JWT Token Provider
4. **Vehicle Service** (Port 8082) - In-Memory Fleet Inventory & Status Management
5. **Booking Service** (Port 8083) - In-Memory Booking Overlap Guard & OpenFeign Client

## Execution Order
1. Build all modules: `mvn clean package` (or run each via IDE / `mvn spring-boot:run`)
2. Start **Eureka Server** (Port 8761) -> Open http://localhost:8761
3. Start **User Service** (Port 8081)
4. Start **Vehicle Service** (Port 8082)
5. Start **Booking Service** (Port 8083)
6. Start **API Gateway** (Port 8080)

## Sample Pre-loaded Data
- **Vehicles:**
  - ID 1: SUV - Ford Explorer 2024 (AVAILABLE, $85.00/day)
  - ID 2: SEDAN - Honda Accord 2023 (AVAILABLE, $55.00/day)
  - ID 3: TRUCK - Volvo FH16 Commercial (MAINTENANCE, $180.00/day)
- **Users:**
  - `john_doe` / `password123`
  - `admin` / `admin123`

## Quick API Test Workflow
1. **Login & Get JWT Token:**
   - POST `http://localhost:8080/api/users/login`
   - Body: `{"username": "john_doe", "password": "password123"}`
2. **View Fleet:**
   - GET `http://localhost:8080/api/vehicles/available`
3. **Create Booking:**
   - POST `http://localhost:8080/api/bookings`
   - Body:
     ```json
     {
       "userId": 1,
       "vehicleId": 1,
       "startDate": "2026-10-01T10:00:00",
       "endDate": "2026-10-05T10:00:00"
     }
     ```
4. **Test Overlap Prevention (Fails):**
   - Post another booking for vehicleId 1 covering `2026-10-03T10:00:00` to `2026-10-07T10:00:00`.
5. **Return Vehicle:**
   - PUT `http://localhost:8080/api/bookings/1/return`

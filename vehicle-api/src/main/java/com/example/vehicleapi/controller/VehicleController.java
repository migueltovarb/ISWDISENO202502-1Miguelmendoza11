package com.example.vehicleapi.controller;

import com.example.vehicleapi.model.Vehicle;
import com.example.vehicleapi.service.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para la gestión de vehículos
 * Proporciona endpoints para operaciones CRUD y búsquedas avanzadas
 * 
 * @author Miguel
 */
@RestController
@RequestMapping("/api/vehicles")
@Validated
@CrossOrigin(origins = "*")
public class VehicleController {
    
    @Autowired
    private VehicleService vehicleService;
    
    /**
     * Obtener todos los vehículos
     * GET /api/vehicles
     */
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }
    
    /**
     * Obtener un vehículo por ID
     * GET /api/vehicles/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable String id) {
        Vehicle vehicle = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(vehicle);
    }
    
    /**
     * Obtener vehículo por placa
     * GET /api/vehicles/placa/{placa}
     */
    @GetMapping("/placa/{placa}")
    public ResponseEntity<Vehicle> getVehicleByPlaca(@PathVariable @NotBlank String placa) {
        Vehicle vehicle = vehicleService.getVehicleByPlaca(placa);
        return ResponseEntity.ok(vehicle);
    }
    
    /**
     * Crear un nuevo vehículo
     * POST /api/vehicles
     */
    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@Valid @RequestBody Vehicle vehicle) {
        Vehicle newVehicle = vehicleService.createVehicle(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body(newVehicle);
    }
    
    /**
     * Actualizar un vehículo existente completamente
     * PUT /api/vehicles/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(
            @PathVariable String id,
            @Valid @RequestBody Vehicle vehicleDetails) {
        Vehicle updatedVehicle = vehicleService.updateVehicle(id, vehicleDetails);
        return ResponseEntity.ok(updatedVehicle);
    }
    
    /**
     * Actualizar parcialmente un vehículo
     * PATCH /api/vehicles/{id}
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Vehicle> partialUpdateVehicle(
            @PathVariable String id,
            @RequestBody Vehicle vehicleDetails) {
        Vehicle updatedVehicle = vehicleService.updateVehicle(id, vehicleDetails);
        return ResponseEntity.ok(updatedVehicle);
    }
    
    /**
     * Eliminar un vehículo
     * DELETE /api/vehicles/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteVehicle(@PathVariable String id) {
        vehicleService.deleteVehicle(id);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Vehículo eliminado exitosamente");
        response.put("id", id);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Buscar vehículos por marca
     * GET /api/vehicles/marca/{marca}
     */
    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<Vehicle>> getVehiclesByMarca(@PathVariable String marca) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByMarca(marca);
        return ResponseEntity.ok(vehicles);
    }
    
    /**
     * Buscar vehículos por marca y modelo
     * GET /api/vehicles/marca/{marca}/modelo/{modelo}
     */
    @GetMapping("/marca/{marca}/modelo/{modelo}")
    public ResponseEntity<List<Vehicle>> getVehiclesByMarcaAndModelo(
            @PathVariable String marca,
            @PathVariable String modelo) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByMarcaAndModelo(marca, modelo);
        return ResponseEntity.ok(vehicles);
    }
    
    /**
     * Buscar vehículos por rango de años
     * GET /api/vehicles/years?startYear=2020&endYear=2023
     */
    @GetMapping("/years")
    public ResponseEntity<List<Vehicle>> getVehiclesByYearRange(
            @RequestParam Integer startYear,
            @RequestParam Integer endYear) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByYearRange(startYear, endYear);
        return ResponseEntity.ok(vehicles);
    }
    
    /**
     * Buscar vehículos por tipo de combustible
     * GET /api/vehicles/combustible/{tipo}
     */
    @GetMapping("/combustible/{tipo}")
    public ResponseEntity<List<Vehicle>> getVehiclesByTipoCombustible(@PathVariable String tipo) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByTipoCombustible(tipo);
        return ResponseEntity.ok(vehicles);
    }
    
    /**
     * Buscar vehículos por color
     * GET /api/vehicles/color/{color}
     */
    @GetMapping("/color/{color}")
    public ResponseEntity<List<Vehicle>> getVehiclesByColor(@PathVariable String color) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByColor(color);
        return ResponseEntity.ok(vehicles);
    }
    
    /**
     * Buscar vehículos por disponibilidad
     * GET /api/vehicles/disponibilidad/{disponible}
     */
    @GetMapping("/disponibilidad/{disponible}")
    public ResponseEntity<List<Vehicle>> getVehiclesByDisponibilidad(@PathVariable Boolean disponible) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByDisponibilidad(disponible);
        return ResponseEntity.ok(vehicles);
    }
    
    /**
     * Buscar vehículos por rango de precio
     * GET /api/vehicles/precio?min=50000000&max=100000000
     */
    @GetMapping("/precio")
    public ResponseEntity<List<Vehicle>> getVehiclesByPriceRange(
            @RequestParam Double min,
            @RequestParam Double max) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByPriceRange(min, max);
        return ResponseEntity.ok(vehicles);
    }
    
    /**
     * Búsqueda avanzada con múltiples criterios
     * GET /api/vehicles/search?marca=toyota&modelo=corolla&año=2023
     */
    @GetMapping("/search")
    public ResponseEntity<List<Vehicle>> searchVehicles(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) Integer año,
            @RequestParam(required = false) String tipoCombustible) {
        List<Vehicle> vehicles = vehicleService.searchVehicles(marca, modelo, año, tipoCombustible);
        return ResponseEntity.ok(vehicles);
    }
    
    /**
     * Obtener estadísticas de vehículos por marca
     * GET /api/vehicles/stats/marca/{marca}
     */
    @GetMapping("/stats/marca/{marca}")
    public ResponseEntity<Map<String, Object>> getStatsByMarca(@PathVariable String marca) {
        long count = vehicleService.countVehiclesByMarca(marca);
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("marca", marca);
        stats.put("total", count);
        
        return ResponseEntity.ok(stats);
    }
    
    /**
     * Verificar disponibilidad de placa
     * GET /api/vehicles/check-placa/{placa}
     */
    @GetMapping("/check-placa/{placa}")
    public ResponseEntity<Map<String, Object>> checkPlacaAvailability(@PathVariable String placa) {
        boolean exists = vehicleService.existsByPlaca(placa);
        
        Map<String, Object> response = new HashMap<>();
        response.put("placa", placa.toUpperCase());
        response.put("disponible", !exists);
        response.put("mensaje", exists ? "Placa ya está en uso" : "Placa disponible");
        
        return ResponseEntity.ok(response);
    }
}
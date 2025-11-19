package com.example.vehicleapi.service;

import com.example.vehicleapi.model.Vehicle;
import com.example.vehicleapi.repository.VehicleRepository;
import com.example.vehicleapi.exception.VehicleNotFoundException;
import com.example.vehicleapi.exception.DuplicatePlacaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio para la gestión de vehículos
 * Contiene la lógica de negocio para las operaciones CRUD
 * 
 * @author Miguel
 */
@Service
public class VehicleService {
    
    @Autowired
    private VehicleRepository vehicleRepository;
    
    /**
     * Obtener todos los vehículos
     * @return Lista de todos los vehículos
     */
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
    
    /**
     * Obtener vehículo por ID
     * @param id ID del vehículo
     * @return Vehicle encontrado
     * @throws VehicleNotFoundException si no se encuentra el vehículo
     */
    public Vehicle getVehicleById(String id) {
        return vehicleRepository.findById(id)
            .orElseThrow(() -> new VehicleNotFoundException("Vehículo no encontrado con ID: " + id));
    }
    
    /**
     * Obtener vehículo por placa
     * @param placa Placa del vehículo
     * @return Vehicle encontrado
     * @throws VehicleNotFoundException si no se encuentra el vehículo
     */
    public Vehicle getVehicleByPlaca(String placa) {
        return vehicleRepository.findByPlaca(placa.toUpperCase())
            .orElseThrow(() -> new VehicleNotFoundException("Vehículo no encontrado con placa: " + placa));
    }
    
    /**
     * Crear un nuevo vehículo
     * @param vehicle Datos del vehículo
     * @return Vehicle creado
     * @throws DuplicatePlacaException si ya existe un vehículo con esa placa
     */
    public Vehicle createVehicle(Vehicle vehicle) {
        // Validar que la placa no esté duplicada
        if (vehicle.getPlaca() != null && vehicleRepository.existsByPlaca(vehicle.getPlaca().toUpperCase())) {
            throw new DuplicatePlacaException("Ya existe un vehículo con la placa: " + vehicle.getPlaca());
        }
        
        // Normalizar datos
        if (vehicle.getPlaca() != null) vehicle.setPlaca(vehicle.getPlaca().toUpperCase());
        if (vehicle.getMarca() != null) vehicle.setMarca(vehicle.getMarca().toUpperCase());
        if (vehicle.getModelo() != null) vehicle.setModelo(vehicle.getModelo().toUpperCase());
        if (vehicle.getColor() != null) vehicle.setColor(vehicle.getColor().toUpperCase());
        if (vehicle.getTipoCombustible() != null) vehicle.setTipoCombustible(vehicle.getTipoCombustible().toUpperCase());
        
        // Establecer fechas
        vehicle.setFechaCreacion(LocalDateTime.now());
        vehicle.setFechaActualizacion(LocalDateTime.now());
        
        return vehicleRepository.save(vehicle);
    }
    
    /**
     * Actualizar un vehículo existente
     * @param id ID del vehículo
     * @param vehicleDetails Nuevos datos del vehículo
     * @return Vehicle actualizado
     * @throws VehicleNotFoundException si no se encuentra el vehículo
     * @throws DuplicatePlacaException si la nueva placa ya existe en otro vehículo
     */
    public Vehicle updateVehicle(String id, Vehicle vehicleDetails) {
        Vehicle vehicle = getVehicleById(id);
        
        // Validar placa duplicada si se está cambiando
        if (vehicleDetails.getPlaca() != null && 
            !vehicleDetails.getPlaca().equalsIgnoreCase(vehicle.getPlaca())) {
            if (vehicleRepository.existsByPlaca(vehicleDetails.getPlaca().toUpperCase())) {
                throw new DuplicatePlacaException("Ya existe un vehículo con la placa: " + vehicleDetails.getPlaca());
            }
        }
        
        // Actualizar campos solo si vienen en la petición
        if (vehicleDetails.getMarca() != null) {
            vehicle.setMarca(vehicleDetails.getMarca().toUpperCase());
        }
        if (vehicleDetails.getModelo() != null) {
            vehicle.setModelo(vehicleDetails.getModelo().toUpperCase());
        }
        if (vehicleDetails.getAño() != null) {
            vehicle.setAño(vehicleDetails.getAño());
        }
        if (vehicleDetails.getColor() != null) {
            vehicle.setColor(vehicleDetails.getColor().toUpperCase());
        }
        if (vehicleDetails.getPlaca() != null) {
            vehicle.setPlaca(vehicleDetails.getPlaca().toUpperCase());
        }
        if (vehicleDetails.getTipoCombustible() != null) {
            vehicle.setTipoCombustible(vehicleDetails.getTipoCombustible().toUpperCase());
        }
        if (vehicleDetails.getKilometraje() != null) {
            vehicle.setKilometraje(vehicleDetails.getKilometraje());
        }
        if (vehicleDetails.getPrecio() != null) {
            vehicle.setPrecio(vehicleDetails.getPrecio());
        }
        if (vehicleDetails.getDescripcion() != null) {
            vehicle.setDescripcion(vehicleDetails.getDescripcion());
        }
        if (vehicleDetails.getDisponible() != null) {
            vehicle.setDisponible(vehicleDetails.getDisponible());
        }
        
        vehicle.setFechaActualizacion(LocalDateTime.now());
        return vehicleRepository.save(vehicle);
    }
    
    /**
     * Eliminar un vehículo
     * @param id ID del vehículo a eliminar
     * @throws VehicleNotFoundException si no se encuentra el vehículo
     */
    public void deleteVehicle(String id) {
        Vehicle vehicle = getVehicleById(id);
        vehicleRepository.delete(vehicle);
    }
    
    /**
     * Buscar vehículos por marca
     * @param marca Marca del vehículo
     * @return Lista de vehículos
     */
    public List<Vehicle> getVehiclesByMarca(String marca) {
        return vehicleRepository.findByMarcaIgnoreCase(marca);
    }
    
    /**
     * Buscar vehículos por marca y modelo
     * @param marca Marca del vehículo
     * @param modelo Modelo del vehículo
     * @return Lista de vehículos
     */
    public List<Vehicle> getVehiclesByMarcaAndModelo(String marca, String modelo) {
        return vehicleRepository.findByMarcaIgnoreCaseAndModeloIgnoreCase(marca, modelo);
    }
    
    /**
     * Buscar vehículos por rango de años
     * @param startYear Año inicial
     * @param endYear Año final
     * @return Lista de vehículos
     */
    public List<Vehicle> getVehiclesByYearRange(Integer startYear, Integer endYear) {
        return vehicleRepository.findByAñoBetween(startYear, endYear);
    }
    
    /**
     * Buscar vehículos por tipo de combustible
     * @param tipoCombustible Tipo de combustible
     * @return Lista de vehículos
     */
    public List<Vehicle> getVehiclesByTipoCombustible(String tipoCombustible) {
        return vehicleRepository.findByTipoCombustibleIgnoreCase(tipoCombustible);
    }
    
    /**
     * Buscar vehículos por color
     * @param color Color del vehículo
     * @return Lista de vehículos
     */
    public List<Vehicle> getVehiclesByColor(String color) {
        return vehicleRepository.findByColorIgnoreCase(color);
    }
    
    /**
     * Buscar vehículos por disponibilidad
     * @param disponible Estado de disponibilidad
     * @return Lista de vehículos
     */
    public List<Vehicle> getVehiclesByDisponibilidad(Boolean disponible) {
        return vehicleRepository.findByDisponible(disponible);
    }
    
    /**
     * Buscar vehículos por rango de precio
     * @param precioMin Precio mínimo
     * @param precioMax Precio máximo
     * @return Lista de vehículos
     */
    public List<Vehicle> getVehiclesByPriceRange(Double precioMin, Double precioMax) {
        return vehicleRepository.findByPrecioBetween(precioMin, precioMax);
    }
    
    /**
     * Buscar vehículos con kilometraje máximo
     * @param maxKilometraje Kilometraje máximo
     * @return Lista de vehículos
     */
    public List<Vehicle> getVehiclesByMaxKilometraje(Double maxKilometraje) {
        return vehicleRepository.findByKilometrajeLessThanEqual(maxKilometraje);
    }
    
    /**
     * Buscar vehículos por múltiples criterios
     * @param marca Marca (opcional)
     * @param modelo Modelo (opcional)
     * @param año Año (opcional)
     * @param tipoCombustible Tipo de combustible (opcional)
     * @return Lista de vehículos
     */
    public List<Vehicle> searchVehicles(String marca, String modelo, Integer año, String tipoCombustible) {
        return vehicleRepository.findByMultipleCriteria(marca, modelo, año, tipoCombustible);
    }
    
    /**
     * Obtener estadísticas de vehículos por marca
     * @param marca Marca del vehículo
     * @return Número de vehículos de esa marca
     */
    public long countVehiclesByMarca(String marca) {
        return vehicleRepository.countByMarcaIgnoreCase(marca);
    }
    
    /**
     * Verificar si existe un vehículo con una placa específica
     * @param placa Placa del vehículo
     * @return true si existe, false en caso contrario
     */
    public boolean existsByPlaca(String placa) {
        return vehicleRepository.existsByPlaca(placa.toUpperCase());
    }
}
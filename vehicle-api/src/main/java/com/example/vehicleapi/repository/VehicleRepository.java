package com.example.vehicleapi.repository;

import com.example.vehicleapi.model.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para operaciones CRUD de vehículos
 * Extiende MongoRepository para operaciones básicas y define consultas personalizadas
 * 
 * @author Miguel
 */
@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, String> {
    
    /**
     * Buscar vehículo por placa
     */
    Optional<Vehicle> findByPlaca(String placa);
    
    /**
     * Buscar vehículos por marca (ignorando mayúsculas/minúsculas)
     */
    List<Vehicle> findByMarcaIgnoreCase(String marca);
    
    /**
     * Buscar vehículos por marca y modelo
     */
    List<Vehicle> findByMarcaIgnoreCaseAndModeloIgnoreCase(String marca, String modelo);
    
    /**
     * Buscar vehículos por rango de años
     */
    List<Vehicle> findByAñoBetween(Integer yearStart, Integer yearEnd);
    
    /**
     * Buscar vehículos por tipo de combustible
     */
    List<Vehicle> findByTipoCombustibleIgnoreCase(String tipoCombustible);
    
    /**
     * Buscar vehículos por color
     */
    List<Vehicle> findByColorIgnoreCase(String color);
    
    /**
     * Buscar vehículos disponibles o no disponibles
     */
    List<Vehicle> findByDisponible(Boolean disponible);
    
    /**
     * Buscar vehículos por rango de precio
     */
    List<Vehicle> findByPrecioBetween(Double precioMin, Double precioMax);
    
    /**
     * Buscar vehículos con kilometraje menor o igual al especificado
     */
    List<Vehicle> findByKilometrajeLessThanEqual(Double maxKilometraje);
    
    /**
     * Buscar vehículos por múltiples criterios usando consulta personalizada
     */
    @Query("{ $and: [" +
           "{ $or: [ { 'marca': { $regex: ?0, $options: 'i' } }, { ?0: null } ] }," +
           "{ $or: [ { 'modelo': { $regex: ?1, $options: 'i' } }, { ?1: null } ] }," +
           "{ $or: [ { 'año': ?2 }, { ?2: null } ] }," +
           "{ $or: [ { 'tipoCombustible': { $regex: ?3, $options: 'i' } }, { ?3: null } ] }" +
           "] }")
    List<Vehicle> findByMultipleCriteria(String marca, String modelo, Integer year, String tipoCombustible);
    
    /**
     * Contar vehículos por marca
     */
    long countByMarcaIgnoreCase(String marca);
    
    /**
     * Verificar si existe un vehículo con la placa específica
     */
    boolean existsByPlaca(String placa);
}
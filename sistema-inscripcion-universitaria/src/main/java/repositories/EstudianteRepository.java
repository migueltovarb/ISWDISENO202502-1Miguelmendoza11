package repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import domain.entities.Estudiante;
import java.util.List;

@Repository
public interface EstudianteRepository extends MongoRepository<Estudiante, String> {
    
    Estudiante findByDocumento(String documento);
    Estudiante findByEmail(String email);
    List<Estudiante> findByCarreraContainingIgnoreCase(String carrera);
    List<Estudiante> findByNombreContainingIgnoreCase(String nombre);
    List<Estudiante> findByApellidosContainingIgnoreCase(String apellidos);
    List<Estudiante> findBySemestre(Integer semestre);
    List<Estudiante> findByActivoTrue();
    List<Estudiante> findByActivoFalse();
    long countByActivoTrue();
    boolean existsByDocumento(String documento);
    boolean existsByEmail(String email);
    List<Estudiante> findByCreditosLessThan(Integer creditos);
    List<Estudiante> findByCreditosBetween(Integer min, Integer max);
}
package repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import domain.entities.Curso;
import java.util.List;
import java.util.Optional;

@Repository
public interface CursoRepository extends MongoRepository<Curso, String> {
    
    Optional<Curso> findByCodigo(String codigo);
    List<Curso> findByNombreContainingAndActivoTrue(String nombre);
    List<Curso> findByDepartamentoAndActivoTrue(String departamento);
    List<Curso> findByModalidadAndActivoTrue(String modalidad);
    List<Curso> findByActivoTrue();
    boolean existsByCodigo(String codigo);
}
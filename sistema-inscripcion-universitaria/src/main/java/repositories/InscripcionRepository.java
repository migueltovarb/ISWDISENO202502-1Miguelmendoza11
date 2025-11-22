package repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import domain.entities.Inscripcion;
import domain.entities.Estudiante;
import domain.entities.Curso;
import java.util.List;
import java.util.Optional;

@Repository
public interface InscripcionRepository extends MongoRepository<Inscripcion, String> {
    
    List<Inscripcion> findByEstudiante(Estudiante estudiante);
    List<Inscripcion> findByCurso(Curso curso);
    List<Inscripcion> findByEstado(String estado);
    Optional<Inscripcion> findByNumeroConfirmacion(String numeroConfirmacion);
    long countByCurso(Curso curso);
    List<Inscripcion> findByEstudianteAndEstado(Estudiante estudiante, String estado);
    boolean existsByEstudianteAndCurso(Estudiante estudiante, Curso curso);
}
package repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import domain.entities.Horario;
import domain.entities.Curso;
import java.util.List;

@Repository
public interface HorarioRepository extends MongoRepository<Horario, String> {
    
    List<Horario> findByCurso(Curso curso);
    List<Horario> findByDiaSemana(String diaSemana);
    List<Horario> findByAula(String aula);
    List<Horario> findByEdificio(String edificio);
    List<Horario> findByDiaSemanaAndAula(String diaSemana, String aula);
}
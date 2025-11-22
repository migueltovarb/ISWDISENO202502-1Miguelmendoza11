package repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import domain.entities.Pago;
import domain.entities.Inscripcion;
import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends MongoRepository<Pago, String> {
    
    Optional<Pago> findByInscripcion(Inscripcion inscripcion);
    List<Pago> findByEstadoPago(String estadoPago);
    Optional<Pago> findByNumeroTransaccion(String numeroTransaccion);
    List<Pago> findByMetodoPago(String metodoPago);
    boolean existsByInscripcion(Inscripcion inscripcion);
}
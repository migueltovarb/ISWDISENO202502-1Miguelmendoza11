package services;

import domain.entities.Estudiante;
import repositories.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {
    
    @Autowired
    private EstudianteRepository estudianteRepository;
    
    /**
     * Crear un nuevo estudiante
     */
    public Estudiante crearEstudiante(Estudiante estudiante) {
        // Validar que no exista un estudiante con el mismo documento
        if (estudianteRepository.findByDocumento(estudiante.getDocumento()) != null) {
            throw new RuntimeException("Ya existe un estudiante con el documento: " + estudiante.getDocumento());
        }
        
        // Validar que no exista un estudiante con el mismo email
        if (estudianteRepository.findByEmail(estudiante.getEmail()) != null) {
            throw new RuntimeException("Ya existe un estudiante con el email: " + estudiante.getEmail());
        }
        
        // Validar documento usando el método del modelo
        if (!estudiante.validarDocumento()) {
            throw new RuntimeException("El documento no tiene un formato válido");
        }
        
        // Validar credenciales usando el método del modelo
        if (!estudiante.validarCredenciales()) {
            throw new RuntimeException("Las credenciales no son válidas");
        }
        
        // Establecer valores por defecto
        estudiante.setActivo(true);
        
        return estudianteRepository.save(estudiante);
    }
    
    /**
     * Buscar todos los estudiantes
     */
    public List<Estudiante> buscarTodos() {
        return estudianteRepository.findAll();
    }
    
    /**
     * Buscar estudiante por ID
     */
    public Estudiante buscarPorId(String id) {
        Optional<Estudiante> estudiante = estudianteRepository.findById(id);
        return estudiante.orElse(null);
    }
    
    /**
     * Buscar estudiante por documento
     */
    public Estudiante buscarPorDocumento(String documento) {
        return estudianteRepository.findByDocumento(documento);
    }
    
    /**
     * Buscar estudiante por email
     */
    public Estudiante buscarPorEmail(String email) {
        return estudianteRepository.findByEmail(email);
    }
    
    /**
     * Buscar estudiantes por carrera
     */
    public List<Estudiante> buscarPorCarrera(String carrera) {
        return estudianteRepository.findByCarreraContainingIgnoreCase(carrera);
    }
    
    /**
     * Buscar estudiantes por semestre
     */
    public List<Estudiante> buscarPorSemestre(Integer semestre) {
        return estudianteRepository.findBySemestre(semestre);
    }
    
    /**
     * Buscar estudiantes activos
     */
    public List<Estudiante> buscarActivos() {
        return estudianteRepository.findByActivoTrue();
    }
    
    /**
     * Actualizar estudiante
     */
    public Estudiante actualizarEstudiante(String id, Estudiante estudianteActualizado) {
        Optional<Estudiante> estudianteExistente = estudianteRepository.findById(id);
        
        if (estudianteExistente.isPresent()) {
            Estudiante estudiante = estudianteExistente.get();
            
            // Actualizar campos
            if (estudianteActualizado.getNombre() != null) {
                estudiante.setNombre(estudianteActualizado.getNombre());
            }
            if (estudianteActualizado.getApellidos() != null) {
                estudiante.setApellidos(estudianteActualizado.getApellidos());
            }
            if (estudianteActualizado.getEmail() != null) {
                // Validar que el nuevo email no esté en uso por otro estudiante
                Estudiante estudianteConEmail = estudianteRepository.findByEmail(estudianteActualizado.getEmail());
                if (estudianteConEmail != null && !estudianteConEmail.getId().equals(id)) {
                    throw new RuntimeException("El email ya está en uso por otro estudiante");
                }
                estudiante.setEmail(estudianteActualizado.getEmail());
            }
            if (estudianteActualizado.getTelefono() != null) {
                estudiante.setTelefono(estudianteActualizado.getTelefono());
            }
            if (estudianteActualizado.getCarrera() != null) {
                estudiante.setCarrera(estudianteActualizado.getCarrera());
            }
            if (estudianteActualizado.getSemestre() != null) {
                estudiante.setSemestre(estudianteActualizado.getSemestre());
            }
            if (estudianteActualizado.getCreditos() != null) {
                estudiante.setCreditos(estudianteActualizado.getCreditos());
            }
            if (estudianteActualizado.getActivo() != null) {
                estudiante.setActivo(estudianteActualizado.getActivo());
            }
            
            return estudianteRepository.save(estudiante);
        }
        
        return null;
    }
    
    /**
     * Eliminar estudiante (soft delete)
     */
    public boolean eliminarEstudiante(String id) {
        Optional<Estudiante> estudiante = estudianteRepository.findById(id);
        
        if (estudiante.isPresent()) {
            Estudiante est = estudiante.get();
            est.setActivo(false);
            estudianteRepository.save(est);
            return true;
        }
        
        return false;
    }
    
    /**
     * Buscar estudiantes con parámetros opcionales
     */
    public List<Estudiante> buscarConParametros(String nombre, String carrera, Integer semestre) {
        if (nombre != null) {
            return estudianteRepository.findByNombreContainingIgnoreCase(nombre);
        } else if (carrera != null) {
            return estudianteRepository.findByCarreraContainingIgnoreCase(carrera);
        } else if (semestre != null) {
            return estudianteRepository.findBySemestre(semestre);
        } else {
            return estudianteRepository.findAll();
        }
    }
    
    /**
     * Verificar si el estudiante puede inscribirse en un curso
     */
    public boolean puedeInscribirseCurso(String estudianteId, int creditosCurso) {
        Estudiante estudiante = buscarPorId(estudianteId);
        if (estudiante != null) {
            return estudiante.puedeInscribirseCurso(creditosCurso);
        }
        return false;
    }
    
    /**
     * Agregar créditos a un estudiante
     */
    public boolean agregarCreditos(String estudianteId, int creditos) {
        Estudiante estudiante = buscarPorId(estudianteId);
        if (estudiante != null) {
            estudiante.agregarCreditos(creditos);
            estudianteRepository.save(estudiante);
            return true;
        }
        return false;
    }
    
    /**
     * Contar total de estudiantes
     */
    public long contarEstudiantes() {
        return estudianteRepository.count();
    }
    
    /**
     * Contar estudiantes activos
     */
    public long contarEstudiantesActivos() {
        return estudianteRepository.countByActivoTrue();
    }
}
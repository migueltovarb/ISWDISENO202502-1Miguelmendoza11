package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.InscripcionRepository;
import repositories.EstudianteRepository;
import repositories.CursoRepository;
import domain.entities.Inscripcion;
import domain.entities.Estudiante;
import domain.entities.Curso;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InscripcionService {
    
    @Autowired
    private InscripcionRepository inscripcionRepository;
    
    @Autowired
    private EstudianteRepository estudianteRepository;
    
    @Autowired
    private CursoRepository cursoRepository;
    
    /**
     * Crear nueva inscripción
     */
    public Inscripcion crearInscripcion(String estudianteId, String cursoId, String observaciones) {
        // Validar que el estudiante existe
        Optional<Estudiante> estudiante = estudianteRepository.findById(estudianteId);
        if (!estudiante.isPresent()) {
            throw new RuntimeException("Estudiante no encontrado con ID: " + estudianteId);
        }
        
        // Validar que el curso existe
        Optional<Curso> curso = cursoRepository.findById(cursoId);
        if (!curso.isPresent()) {
            throw new RuntimeException("Curso no encontrado con ID: " + cursoId);
        }
        
        // Validar que el estudiante no esté ya inscrito
        if (inscripcionRepository.existsByEstudianteAndCurso(estudiante.get(), curso.get())) {
            throw new RuntimeException("El estudiante ya está inscrito en este curso");
        }
        
        // Validar créditos del estudiante (manejo seguro de null)
        Estudiante est = estudiante.get();
        int creditosActuales = (est.getCreditos() != null) ? est.getCreditos() : 0;
        int creditosNuevos = curso.get().getCreditos();
        int creditosTotales = creditosActuales + creditosNuevos;
        
        System.out.println("📊 VALIDACIÓN DE CRÉDITOS:");
        System.out.println("Estudiante: " + est.getNombre() + " " + est.getApellidos());
        System.out.println("Créditos actuales: " + creditosActuales);
        System.out.println("Créditos del curso: " + creditosNuevos);
        System.out.println("Total después: " + creditosTotales);
        
        // Validar límite de créditos (máximo 20)
        if (creditosTotales > 20) {
            throw new RuntimeException("El estudiante no puede inscribirse: excedería el límite de 20 créditos. " +
                    "Actual: " + creditosActuales + ", Curso: " + creditosNuevos + ", Total sería: " + creditosTotales);
        }
        
        // Validar cupos disponibles
        if (curso.get().getCuposDisponibles() <= 0) {
            throw new RuntimeException("No hay cupos disponibles para este curso");
        }
        
        // Crear la inscripción
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setEstudiante(estudiante.get());
        inscripcion.setCurso(curso.get());
        inscripcion.setFechaInscripcion(LocalDateTime.now());
        inscripcion.setEstado("ACTIVA");
        inscripcion.setObservaciones(observaciones);
        inscripcion.setRequierePago(curso.get().getEsExtension());
        
        // Generar número de confirmación
        inscripcion.setNumeroConfirmacion("INS-" + System.currentTimeMillis());
        
        // Actualizar créditos del estudiante (manejo seguro)
        est.setCreditos(creditosTotales);
        estudianteRepository.save(est);
        
        // Reducir cupos disponibles del curso
        Curso cursoActualizado = curso.get();
        cursoActualizado.setCuposOcupados(cursoActualizado.getCuposOcupados() + 1);
        cursoRepository.save(cursoActualizado);
        
        // Guardar inscripción
        Inscripcion inscripcionGuardada = inscripcionRepository.save(inscripcion);
        
        System.out.println("✅ INSCRIPCIÓN EXITOSA:");
        System.out.println("ID: " + inscripcionGuardada.getId());
        System.out.println("Número: " + inscripcionGuardada.getNumeroConfirmacion());
        System.out.println("Requiere pago: " + inscripcionGuardada.getRequierePago());
        
        return inscripcionGuardada;
    }
    
    /**
     * Buscar todas las inscripciones
     */
    public List<Inscripcion> buscarTodas() {
        return inscripcionRepository.findAll();
    }
    
    /**
     * Buscar inscripción por ID
     */
    public Inscripcion buscarPorId(String id) {
        Optional<Inscripcion> inscripcion = inscripcionRepository.findById(id);
        return inscripcion.orElse(null);
    }
    
    /**
     * Buscar inscripciones por estudiante
     */
    public List<Inscripcion> buscarPorEstudiante(String estudianteId) {
        Optional<Estudiante> estudiante = estudianteRepository.findById(estudianteId);
        if (estudiante.isPresent()) {
            return inscripcionRepository.findByEstudiante(estudiante.get());
        }
        throw new RuntimeException("Estudiante no encontrado");
    }
    
    /**
     * Buscar inscripciones por curso
     */
    public List<Inscripcion> buscarPorCurso(String cursoId) {
        Optional<Curso> curso = cursoRepository.findById(cursoId);
        if (curso.isPresent()) {
            return inscripcionRepository.findByCurso(curso.get());
        }
        throw new RuntimeException("Curso no encontrado");
    }
    
    /**
     * Cancelar inscripción
     */
    public boolean cancelarInscripcion(String inscripcionId) {
        Optional<Inscripcion> inscripcionOpt = inscripcionRepository.findById(inscripcionId);
        if (!inscripcionOpt.isPresent()) {
            throw new RuntimeException("Inscripción no encontrada");
        }
        
        Inscripcion inscripcion = inscripcionOpt.get();
        
        if (!"ACTIVA".equals(inscripcion.getEstado())) {
            throw new RuntimeException("Solo se pueden cancelar inscripciones activas");
        }
        
        // Cambiar estado
        inscripcion.setEstado("CANCELADA");
        
        // Devolver créditos al estudiante (manejo seguro)
        Estudiante estudiante = inscripcion.getEstudiante();
        int creditosActuales = (estudiante.getCreditos() != null) ? estudiante.getCreditos() : 0;
        int creditosACancelar = inscripcion.getCurso().getCreditos();
        estudiante.setCreditos(Math.max(0, creditosActuales - creditosACancelar));
        estudianteRepository.save(estudiante);
        
        // Devolver cupo al curso
        Curso curso = inscripcion.getCurso();
        curso.setCuposOcupados(Math.max(0, curso.getCuposOcupados() - 1));
        cursoRepository.save(curso);
        
        // Guardar inscripción cancelada
        inscripcionRepository.save(inscripcion);
        
        return true;
    }
    
    /**
     * Contar inscripciones
     */
    public long contarInscripciones() {
        return inscripcionRepository.count();
    }
    
    /**
     * Buscar por estado
     */
    public List<Inscripcion> buscarPorEstado(String estado) {
        return inscripcionRepository.findByEstado(estado);
    }
}
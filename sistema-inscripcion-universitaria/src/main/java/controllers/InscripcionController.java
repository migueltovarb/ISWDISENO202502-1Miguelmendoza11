package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.InscripcionService;
import domain.entities.Inscripcion;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@CrossOrigin(origins = "*")
public class InscripcionController {
    
    @Autowired
    private InscripcionService inscripcionService;
    
    /**
     * Crear inscripción usando el service
     * POST /api/inscripciones
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> crearInscripcion(@RequestBody Map<String, String> request) {
        try {
            String estudianteId = request.get("estudianteId");
            String cursoId = request.get("cursoId");
            String observaciones = request.get("observaciones");
            
            System.out.println("=== CREANDO INSCRIPCIÓN ===");
            System.out.println("EstudianteId: " + estudianteId);
            System.out.println("CursoId: " + cursoId);
            System.out.println("Observaciones: " + observaciones);
            
            // USAR EL SERVICE en lugar de lógica propia
            Inscripcion inscripcion = inscripcionService.crearInscripcion(estudianteId, cursoId, observaciones);
            
            // Crear respuesta con datos reales
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("id", inscripcion.getId());
            respuesta.put("estudianteId", inscripcion.getEstudiante().getId());
            respuesta.put("cursoId", inscripcion.getCurso().getId());
            respuesta.put("numeroConfirmacion", inscripcion.getNumeroConfirmacion());
            respuesta.put("fechaInscripcion", inscripcion.getFechaInscripcion().toString());
            respuesta.put("estado", inscripcion.getEstado());
            respuesta.put("observaciones", inscripcion.getObservaciones());
            respuesta.put("requierePago", inscripcion.getRequierePago());
            
            // Datos adicionales útiles
            respuesta.put("nombreEstudiante", inscripcion.getEstudiante().getNombre());
            respuesta.put("nombreCurso", inscripcion.getCurso().getNombre());
            respuesta.put("codigoCurso", inscripcion.getCurso().getCodigo());
            respuesta.put("creditosCurso", inscripcion.getCurso().getCreditos());
            
            System.out.println("✅ Inscripción creada: " + inscripcion.getNumeroConfirmacion());
            
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
            
        } catch (RuntimeException e) {
            System.out.println("❌ Error de validación: " + e.getMessage());
            
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("codigo", "VALIDATION_ERROR");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            
        } catch (Exception e) {
            System.out.println("💥 Error interno: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error interno del sistema");
            error.put("mensaje", "Contacte al administrador");
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Obtener todas las inscripciones
     * GET /api/inscripciones
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> buscarTodasLasInscripciones() {
        try {
            List<Inscripcion> inscripciones = inscripcionService.buscarTodas();
            long total = inscripcionService.contarInscripciones();
            
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("inscripciones", inscripciones);
            respuesta.put("total", total);
            respuesta.put("mensaje", "Inscripciones obtenidas exitosamente");
            
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
            
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error obteniendo inscripciones: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Buscar inscripción por ID
     * GET /api/inscripciones/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> buscarPorId(@PathVariable String id) {
        try {
            Inscripcion inscripcion = inscripcionService.buscarPorId(id);
            
            if (inscripcion == null) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Inscripción no encontrada");
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
            
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("inscripcion", inscripcion);
            
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
            
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Buscar inscripciones por estudiante
     * GET /api/inscripciones/estudiante/{estudianteId}
     */
    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<Map<String, Object>> buscarPorEstudiante(@PathVariable String estudianteId) {
        try {
            List<Inscripcion> inscripciones = inscripcionService.buscarPorEstudiante(estudianteId);
            
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("inscripciones", inscripciones);
            respuesta.put("total", inscripciones.size());
            
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
            
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Buscar inscripciones por curso
     * GET /api/inscripciones/curso/{cursoId}
     */
    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<Map<String, Object>> buscarPorCurso(@PathVariable String cursoId) {
        try {
            List<Inscripcion> inscripciones = inscripcionService.buscarPorCurso(cursoId);
            
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("inscripciones", inscripciones);
            respuesta.put("total", inscripciones.size());
            
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
            
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Cancelar inscripción
     * PUT /api/inscripciones/{id}/cancelar
     */
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Map<String, Object>> cancelarInscripcion(@PathVariable String id) {
        try {
            boolean cancelada = inscripcionService.cancelarInscripcion(id);
            
            Map<String, Object> respuesta = new HashMap<>();
            if (cancelada) {
                respuesta.put("mensaje", "Inscripción cancelada exitosamente");
                respuesta.put("inscripcionId", id);
                return new ResponseEntity<>(respuesta, HttpStatus.OK);
            } else {
                respuesta.put("error", "No se pudo cancelar la inscripción");
                return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
            }
            
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Test endpoint
     * GET /api/inscripciones/test
     */
    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> test() {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("status", "✅ Sistema de inscripciones ACTIVO");
        respuesta.put("version", "1.0.0");
        respuesta.put("timestamp", LocalDateTime.now().toString());
        respuesta.put("endpoints", new String[]{
            "POST /api/inscripciones",
            "GET /api/inscripciones", 
            "GET /api/inscripciones/{id}",
            "GET /api/inscripciones/estudiante/{estudianteId}",
            "GET /api/inscripciones/curso/{cursoId}",
            "PUT /api/inscripciones/{id}/cancelar"
        });
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
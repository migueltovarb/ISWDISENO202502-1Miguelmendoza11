package controllers;

import domain.entities.Estudiante;
import services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "*")
public class EstudianteController {
    
    @Autowired
    private EstudianteService estudianteService;
    
    /**
     * Crear un nuevo estudiante
     * POST /api/estudiantes
     */
    @PostMapping
    public ResponseEntity<Estudiante> crearEstudiante(@RequestBody Estudiante estudiante) {
        try {
            Estudiante nuevoEstudiante = estudianteService.crearEstudiante(estudiante);
            return new ResponseEntity<>(nuevoEstudiante, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * Buscar todos los estudiantes
     * GET /api/estudiantes
     */
    @GetMapping
    public ResponseEntity<List<Estudiante>> buscarTodosLosEstudiantes() {
        try {
            List<Estudiante> estudiantes = estudianteService.buscarTodos();
            return new ResponseEntity<>(estudiantes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Buscar estudiante por ID
     * GET /api/estudiantes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> buscarEstudiantePorId(@PathVariable String id) {
        try {
            Estudiante estudiante = estudianteService.buscarPorId(id);
            if (estudiante != null) {
                return new ResponseEntity<>(estudiante, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Buscar estudiante por documento
     * GET /api/estudiantes/documento/{documento}
     */
    @GetMapping("/documento/{documento}")
    public ResponseEntity<Estudiante> buscarEstudiantePorDocumento(@PathVariable String documento) {
        try {
            Estudiante estudiante = estudianteService.buscarPorDocumento(documento);
            if (estudiante != null) {
                return new ResponseEntity<>(estudiante, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Buscar estudiantes por carrera
     * GET /api/estudiantes/carrera/{carrera}
     */
    @GetMapping("/carrera/{carrera}")
    public ResponseEntity<List<Estudiante>> buscarEstudiantesPorCarrera(@PathVariable String carrera) {
        try {
            List<Estudiante> estudiantes = estudianteService.buscarPorCarrera(carrera);
            return new ResponseEntity<>(estudiantes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Actualizar estudiante
     * PUT /api/estudiantes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> actualizarEstudiante(@PathVariable String id, @RequestBody Estudiante estudiante) {
        try {
            Estudiante estudianteActualizado = estudianteService.actualizarEstudiante(id, estudiante);
            if (estudianteActualizado != null) {
                return new ResponseEntity<>(estudianteActualizado, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Buscar estudiantes con parámetros opcionales
     * GET /api/estudiantes/buscar
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Estudiante>> buscarEstudiantesConParametros(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String carrera,
            @RequestParam(required = false) Integer semestre) {
        try {
            List<Estudiante> estudiantes = estudianteService.buscarConParametros(nombre, carrera, semestre);
            return new ResponseEntity<>(estudiantes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Verificar si estudiante puede inscribirse en un curso
     * GET /api/estudiantes/{id}/puede-inscribirse/{creditos}
     */
    @GetMapping("/{id}/puede-inscribirse/{creditos}")
    public ResponseEntity<Boolean> puedeInscribirseCurso(@PathVariable String id, @PathVariable int creditos) {
        try {
            boolean puede = estudianteService.puedeInscribirseCurso(id, creditos);
            return new ResponseEntity<>(puede, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Agregar créditos a un estudiante
     * PUT /api/estudiantes/{id}/agregar-creditos/{creditos}
     */
    @PutMapping("/{id}/agregar-creditos/{creditos}")
    public ResponseEntity<Boolean> agregarCreditos(@PathVariable String id, @PathVariable int creditos) {
        try {
            boolean agregado = estudianteService.agregarCreditos(id, creditos);
            return new ResponseEntity<>(agregado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
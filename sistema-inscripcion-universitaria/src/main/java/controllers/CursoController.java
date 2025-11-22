package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.CursoService;
import data.dto.CursoDTO;
import data.dto.HorarioDTO;
import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*")
public class CursoController {
    
    @Autowired
    private CursoService cursoService;
    
    // NUEVO: Obtener todos los cursos disponibles
    @GetMapping
    public ResponseEntity<List<CursoDTO>> getAllCursos() {
        List<CursoDTO> cursos = cursoService.obtenerTodosCursos();
        return ResponseEntity.ok(cursos);
    }
    
    // Obtener curso específico por ID
    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> getCursoById(@PathVariable String id) {
        CursoDTO curso = cursoService.obtenerCursoPorId(id);
        if (curso != null) {
            return ResponseEntity.ok(curso);
        }
        return ResponseEntity.notFound().build();
    }
    
    // HU-2: Consultar horarios y cupos disponibles
    @GetMapping("/buscar")
    public ResponseEntity<List<CursoDTO>> buscarCursos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String departamento,
            @RequestParam(required = false) String modalidad) {
        
        List<CursoDTO> cursos = cursoService.buscarCursos(nombre, departamento, modalidad);
        return ResponseEntity.ok(cursos);
    }
    
    @GetMapping("/{id}/cupos")
    public ResponseEntity<Integer> verificarCupos(@PathVariable String id) {
        Integer cupos = cursoService.verificarCuposDisponibles(id);
        return ResponseEntity.ok(cupos);
    }
    
    @GetMapping("/{id}/horarios")
    public ResponseEntity<List<HorarioDTO>> consultarHorarios(@PathVariable String id) {
        List<HorarioDTO> horarios = cursoService.consultarHorarios(id);
        return ResponseEntity.ok(horarios);
    }
}
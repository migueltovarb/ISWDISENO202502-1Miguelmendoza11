package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import services.CursoService;
import data.dto.CursoDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    
    @Autowired
    private CursoService cursoService;
    
    // HU-3: Gestionar cursos (Administrador)
    @PostMapping("/cursos")
    public ResponseEntity<CursoDTO> crearCurso(@Valid @RequestBody CursoDTO cursoDTO) {
        try {
            CursoDTO resultado = cursoService.crearCurso(cursoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    @PutMapping("/cursos/{id}")
    public ResponseEntity<CursoDTO> modificarCurso(@PathVariable String id, @Valid @RequestBody CursoDTO cursoDTO) {
        try {
            CursoDTO resultado = cursoService.modificarCurso(id, cursoDTO);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    @PutMapping("/cursos/{id}/activar")
    public ResponseEntity<String> activarCurso(@PathVariable String id) {
        try {
            boolean resultado = cursoService.activarCurso(id);
            if (resultado) {
                return ResponseEntity.ok("Curso activado exitosamente");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo activar el curso");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PutMapping("/cursos/{id}/desactivar")
    public ResponseEntity<String> desactivarCurso(@PathVariable String id) {
        try {
            boolean resultado = cursoService.desactivarCurso(id);
            if (resultado) {
                return ResponseEntity.ok("Curso desactivado exitosamente");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo desactivar el curso");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
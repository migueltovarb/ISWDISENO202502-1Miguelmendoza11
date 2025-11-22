package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.CursoRepository;
import repositories.HorarioRepository;
import domain.entities.Curso;
import domain.entities.Horario;
import data.dto.CursoDTO;
import data.dto.HorarioDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoService {
    
    @Autowired
    private CursoRepository cursoRepository;
    
    @Autowired
    private HorarioRepository horarioRepository;
    
    // NUEVO: Obtener todos los cursos activos
    public List<CursoDTO> obtenerTodosCursos() {
        List<Curso> cursos = cursoRepository.findByActivoTrue();
        return cursos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // NUEVO: Obtener curso por ID
    public CursoDTO obtenerCursoPorId(String id) {
        return cursoRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }
    
    // HU-2: Consultar horarios y cupos disponibles
    public List<CursoDTO> buscarCursos(String nombre, String departamento, String modalidad) {
        List<Curso> cursos;
        
        if (nombre != null && !nombre.isEmpty()) {
            cursos = cursoRepository.findByNombreContainingAndActivoTrue(nombre);
        } else if (departamento != null && !departamento.isEmpty()) {
            cursos = cursoRepository.findByDepartamentoAndActivoTrue(departamento);
        } else if (modalidad != null && !modalidad.isEmpty()) {
            cursos = cursoRepository.findByModalidadAndActivoTrue(modalidad);
        } else {
            cursos = cursoRepository.findByActivoTrue();
        }
        
        return cursos.stream()
                     .map(this::convertToDTO)
                     .collect(Collectors.toList());
    }
    
    public Integer verificarCuposDisponibles(String cursoId) {
        Optional<Curso> curso = cursoRepository.findById(cursoId);
        if (curso.isPresent()) {
            return curso.get().getCuposDisponibles();
        }
        throw new RuntimeException("Curso no encontrado con ID: " + cursoId);
    }
    
    public List<HorarioDTO> consultarHorarios(String cursoId) {
        Optional<Curso> curso = cursoRepository.findById(cursoId);
        if (!curso.isPresent()) {
            throw new RuntimeException("Curso no encontrado con ID: " + cursoId);
        }
        
        return horarioRepository.findByCurso(curso.get()).stream()
                               .map(this::convertHorarioToDTO)
                               .collect(Collectors.toList());
    }
    
    // HU-3: Gestionar cursos (Admin)
    public CursoDTO crearCurso(CursoDTO cursoDTO) {
        if (cursoRepository.existsByCodigo(cursoDTO.getCodigo())) {
            throw new RuntimeException("Ya existe un curso con el código: " + cursoDTO.getCodigo());
        }
        
        Curso curso = convertToEntity(cursoDTO);
        curso.setActivo(true);
        
        Curso cursoGuardado = cursoRepository.save(curso);
        return convertToDTO(cursoGuardado);
    }
    
    public CursoDTO modificarCurso(String cursoId, CursoDTO cursoDTO) {
        Optional<Curso> cursoExistente = cursoRepository.findById(cursoId);
        if (!cursoExistente.isPresent()) {
            throw new RuntimeException("Curso no encontrado con ID: " + cursoId);
        }
        
        Curso curso = cursoExistente.get();
        actualizarCursoDesdeDTO(curso, cursoDTO);
        
        Curso cursoGuardado = cursoRepository.save(curso);
        return convertToDTO(cursoGuardado);
    }
    
    public boolean activarCurso(String cursoId) {
        return cambiarEstadoCurso(cursoId, true);
    }
    
    public boolean desactivarCurso(String cursoId) {
        return cambiarEstadoCurso(cursoId, false);
    }
    
    // Métodos auxiliares
    private boolean cambiarEstadoCurso(String cursoId, boolean activo) {
        Optional<Curso> curso = cursoRepository.findById(cursoId);
        if (curso.isPresent()) {
            curso.get().setActivo(activo);
            cursoRepository.save(curso.get());
            return true;
        }
        throw new RuntimeException("Curso no encontrado con ID: " + cursoId);
    }
    
    private CursoDTO convertToDTO(Curso curso) {
        CursoDTO dto = new CursoDTO();
        dto.setId(curso.getId());
        dto.setCodigo(curso.getCodigo());
        dto.setNombre(curso.getNombre());
        dto.setDescripcion(curso.getDescripcion());
        dto.setCreditos(curso.getCreditos());
        dto.setCuposMaximos(curso.getCuposMaximos());
        dto.setCuposOcupados(curso.getCuposOcupados());
        dto.setModalidad(curso.getModalidad());
        dto.setDepartamento(curso.getDepartamento());
        dto.setActivo(curso.getActivo());
        dto.setEsExtension(curso.getEsExtension());
        dto.setPrecio(curso.getPrecio());
        return dto;
    }
    
    private Curso convertToEntity(CursoDTO dto) {
        Curso curso = new Curso();
        curso.setCodigo(dto.getCodigo());
        curso.setNombre(dto.getNombre());
        curso.setDescripcion(dto.getDescripcion());
        curso.setCreditos(dto.getCreditos());
        curso.setCuposMaximos(dto.getCuposMaximos());
        curso.setModalidad(dto.getModalidad());
        curso.setDepartamento(dto.getDepartamento());
        curso.setEsExtension(dto.getEsExtension());
        curso.setPrecio(dto.getPrecio());
        return curso;
    }
    
    private void actualizarCursoDesdeDTO(Curso curso, CursoDTO dto) {
        curso.setNombre(dto.getNombre());
        curso.setDescripcion(dto.getDescripcion());
        curso.setCreditos(dto.getCreditos());
        curso.setCuposMaximos(dto.getCuposMaximos());
        curso.setModalidad(dto.getModalidad());
        curso.setDepartamento(dto.getDepartamento());
        curso.setEsExtension(dto.getEsExtension());
        curso.setPrecio(dto.getPrecio());
    }
    
    private HorarioDTO convertHorarioToDTO(Horario horario) {
        HorarioDTO dto = new HorarioDTO();
        dto.setId(horario.getId());
        dto.setDiaSemana(horario.getDiaSemana());
        dto.setHoraInicio(horario.getHoraInicio().toString());
        dto.setHoraFin(horario.getHoraFin().toString());
        dto.setAula(horario.getAula());
        dto.setEdificio(horario.getEdificio());
        if (horario.getCurso() != null) {
            dto.setCursoId(horario.getCurso().getId());
            dto.setCodigoCurso(horario.getCurso().getCodigo());
            dto.setNombreCurso(horario.getCurso().getNombre());
        }
        return dto;
    }
}
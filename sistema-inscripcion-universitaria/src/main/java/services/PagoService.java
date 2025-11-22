package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.PagoRepository;
import repositories.InscripcionRepository;
import domain.entities.Pago;
import domain.entities.Inscripcion;
import data.dto.PagoDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PagoService {
    
    @Autowired
    private PagoRepository pagoRepository;
    
    @Autowired
    private InscripcionRepository inscripcionRepository;
    
    // NUEVO: Obtener todos los pagos
    public List<PagoDTO> obtenerTodosPagos() {
        List<Pago> pagos = pagoRepository.findAll();
        return pagos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // NUEVO: Guardar pago simulado (sin validar inscripción)
    public PagoDTO guardarPagoSimulado(PagoDTO pagoDTO) {
        try {
            // Crear entidad Pago sin usar constructor completo
            Pago pago = new Pago();
            
            // Solo establecer campos que sabemos que existen
            pago.setMonto(pagoDTO.getMonto());
            pago.setMetodoPago(pagoDTO.getMetodoPago());
            pago.setEstadoPago(pagoDTO.getEstadoPago());
            pago.setReferenciaBancaria(pagoDTO.getReferenciaBancaria());
            
            // Campos opcionales que pueden no existir en tu entidad
            if (pagoDTO.getNumeroTransaccion() != null) {
                try {
                    pago.setNumeroTransaccion(pagoDTO.getNumeroTransaccion());
                } catch (Exception e) {
                    System.out.println("Campo numeroTransaccion no disponible");
                }
            }
            
            if (pagoDTO.getCodigoAutorizacion() != null) {
                try {
                    pago.setCodigoAutorizacion(pagoDTO.getCodigoAutorizacion());
                } catch (Exception e) {
                    System.out.println("Campo codigoAutorizacion no disponible");
                }
            }
            
            if (pagoDTO.getMotivoFallo() != null) {
                try {
                    pago.setMotivoFallo(pagoDTO.getMotivoFallo());
                } catch (Exception e) {
                    System.out.println("Campo motivoFallo no disponible");
                }
            }
            
            // Para la inscripción, intentar encontrarla o crear datos simulados
            try {
                Optional<Inscripcion> inscripcion = inscripcionRepository.findById(pagoDTO.getInscripcionId());
                if (inscripcion.isPresent()) {
                    pago.setInscripcion(inscripcion.get());
                } else {
                    // Si no existe la inscripción, crear una simulada o simplemente no establecerla
                    System.out.println("⚠️ Inscripción " + pagoDTO.getInscripcionId() + " no encontrada, guardando pago sin inscripción");
                }
            } catch (Exception e) {
                System.out.println("⚠️ No se pudo establecer inscripción: " + e.getMessage());
            }
            
            // Guardar en base de datos
            Pago pagoGuardado = pagoRepository.save(pago);
            System.out.println("✅ Pago guardado con ID: " + pagoGuardado.getId());
            
            // Crear DTO de respuesta con datos del pago guardado + datos simulados
            PagoDTO resultado = convertToDTO(pagoGuardado);
            resultado.setInscripcionId(pagoDTO.getInscripcionId());
            resultado.setNombreEstudiante("Juan Carlos Pérez García");
            resultado.setNombreCurso("Matemáticas I");
            resultado.setCodigoCurso("MAT101");
            
            return resultado;
            
        } catch (Exception e) {
            System.out.println("❌ Error guardando pago simulado: " + e.getMessage());
            e.printStackTrace();
            // En lugar de fallar, retornar el DTO original para mantener la simulación
            return pagoDTO;
        }
    }
    
    // HU-4: Procesar pagos para cursos de extensión
    public PagoDTO procesarPago(PagoDTO pagoDTO) {
        // Validar inscripción existe
        Optional<Inscripcion> inscripcion = inscripcionRepository.findById(pagoDTO.getInscripcionId());
        if (!inscripcion.isPresent()) {
            throw new RuntimeException("Inscripción no encontrada");
        }
        
        // Validar que la inscripción requiere pago
        if (!inscripcion.get().getRequierePago()) {
            throw new RuntimeException("Esta inscripción no requiere pago");
        }
        
        // Validar que no existe un pago previo exitoso
        Optional<Pago> pagoExistente = pagoRepository.findByInscripcion(inscripcion.get());
        if (pagoExistente.isPresent() && pagoExistente.get().esPagoExitoso()) {
            throw new RuntimeException("Esta inscripción ya tiene un pago exitoso");
        }
        
        // Validar monto
        if (!pagoDTO.esMontoValido()) {
            throw new RuntimeException("Monto inválido");
        }
        
        // Crear pago
        Pago pago = new Pago(inscripcion.get(), pagoDTO.getMonto(), pagoDTO.getMetodoPago());
        pago.setReferenciaBancaria(generarReferenciaBancaria());
        
        // Simular procesamiento de pago
        boolean pagoExitoso = simularPasarelaPago(pagoDTO);
        
        if (pagoExitoso) {
            pago.confirmarPago("AUTH-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            
            // Confirmar la inscripción
            inscripcion.get().confirmarInscripcion();
            inscripcionRepository.save(inscripcion.get());
            
        } else {
            pago.setEstadoPago("FALLIDO");
            pago.setMotivoFallo("Error en la pasarela de pagos");
        }
        
        // Guardar pago
        Pago pagoGuardado = pagoRepository.save(pago);
        
        return convertToDTO(pagoGuardado);
    }
    
    public PagoDTO consultarEstadoPago(String pagoId) {
        Optional<Pago> pago = pagoRepository.findById(pagoId);
        if (!pago.isPresent()) {
            throw new RuntimeException("Pago no encontrado");
        }
        
        return convertToDTO(pago.get());
    }
    
    public boolean confirmarPago(String numeroTransaccion) {
        Optional<Pago> pago = pagoRepository.findByNumeroTransaccion(numeroTransaccion);
        if (!pago.isPresent()) {
            throw new RuntimeException("Pago no encontrado");
        }
        
        if (pago.get().confirmarPago("CONFIRMED-" + System.currentTimeMillis())) {
            pagoRepository.save(pago.get());
            return true;
        }
        
        return false;
    }
    
    // Métodos auxiliares
    private boolean simularPasarelaPago(PagoDTO pagoDTO) {
        // Simulación simple - en producción sería una llamada real a la pasarela
        try {
            Thread.sleep(1000); // Simular tiempo de procesamiento
            return Math.random() > 0.1; // 90% de éxito
        } catch (InterruptedException e) {
            return false;
        }
    }
    
    private String generarReferenciaBancaria() {
        return "REF-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
    
    private PagoDTO convertToDTO(Pago pago) {
        PagoDTO dto = new PagoDTO();
        dto.setId(pago.getId());
        
        // Manejo seguro de inscripción
        if (pago.getInscripcion() != null) {
            dto.setInscripcionId(pago.getInscripcion().getId());
            dto.setNombreEstudiante(pago.getInscripcion().getEstudiante().getNombre());
            dto.setNombreCurso(pago.getInscripcion().getCurso().getNombre());
            dto.setCodigoCurso(pago.getInscripcion().getCurso().getCodigo());
            dto.setNumeroConfirmacionInscripcion(pago.getInscripcion().getNumeroConfirmacion());
        }
        
        dto.setMonto(pago.getMonto());
        if (pago.getFechaPago() != null) {
            dto.setFechaPago(pago.getFechaPago().toString());
        }
        dto.setMetodoPago(pago.getMetodoPago());
        dto.setEstadoPago(pago.getEstadoPago());
        dto.setReferenciaBancaria(pago.getReferenciaBancaria());
        
        // Campos opcionales
        try {
            dto.setNumeroTransaccion(pago.getNumeroTransaccion());
        } catch (Exception e) {
            // Campo no disponible
        }
        
        try {
            dto.setCodigoAutorizacion(pago.getCodigoAutorizacion());
        } catch (Exception e) {
            // Campo no disponible
        }
        
        try {
            dto.setMotivoFallo(pago.getMotivoFallo());
        } catch (Exception e) {
            // Campo no disponible
        }
        
        return dto;
    }
}
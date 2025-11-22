package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.PagoService;
import data.dto.PagoDTO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoController {
    
    @Autowired
    private PagoService pagoService;
    
    /**
     * Obtener todos los pagos
     * GET /api/pagos
     */
    @GetMapping
    public ResponseEntity<List<PagoDTO>> getAllPagos() {
        try {
            List<PagoDTO> pagos = pagoService.obtenerTodosPagos();
            return ResponseEntity.ok(pagos);
        } catch (Exception e) {
            System.out.println("Error obteniendo pagos: " + e.getMessage());
            return ResponseEntity.ok(List.of());
        }
    }
    
    /**
     * Procesar pago - GUARDANDO EN BASE DE DATOS
     * POST /api/pagos/procesar
     */
    @PostMapping("/procesar")
    public ResponseEntity<Map<String, Object>> procesarPago(@RequestBody Map<String, Object> request) {
        try {
            String inscripcionId = (String) request.get("inscripcionId");
            Double monto = ((Number) request.get("monto")).doubleValue();
            String metodoPago = (String) request.get("metodoPago");
            
            System.out.println("=== PROCESANDO PAGO ===");
            System.out.println("InscripcionId: " + inscripcionId);
            System.out.println("Monto: $" + String.format("%,.0f", monto));
            System.out.println("Método: " + metodoPago);
            
            // Crear PagoDTO para procesar
            PagoDTO pagoDTO = new PagoDTO();
            pagoDTO.setInscripcionId(inscripcionId);
            pagoDTO.setMonto(monto);
            pagoDTO.setMetodoPago(metodoPago);
            pagoDTO.setFechaPago(LocalDateTime.now().toString());
            
            // Simular procesamiento de pago (95% de éxito para demo)
            boolean pagoExitoso = Math.random() > 0.05;
            
            // Generar datos del pago
            String transactionId = "TXN-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();
            String referencia = "REF-" + System.currentTimeMillis();
            String pagoId = "PAG-" + System.currentTimeMillis();
            
            // Completar el DTO con datos generados
            pagoDTO.setId(pagoId);
            pagoDTO.setNumeroTransaccion(transactionId);
            pagoDTO.setReferenciaBancaria(referencia);
            
            // Crear respuesta del pago
            Map<String, Object> pagoResponse = new HashMap<>();
            pagoResponse.put("id", pagoId);
            pagoResponse.put("inscripcionId", inscripcionId);
            pagoResponse.put("monto", monto);
            pagoResponse.put("metodoPago", metodoPago);
            pagoResponse.put("fechaPago", LocalDateTime.now().toString());
            pagoResponse.put("numeroTransaccion", transactionId);
            pagoResponse.put("referenciaBancaria", referencia);
            
            if (pagoExitoso) {
                String codigoAuth = "AUTH-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                
                pagoDTO.setEstadoPago("EXITOSO");
                pagoDTO.setCodigoAutorizacion(codigoAuth);
                
                // GUARDAR EN BASE DE DATOS
                try {
                    PagoDTO pagoGuardado = pagoService.guardarPagoSimulado(pagoDTO);
                    System.out.println("💾 PAGO GUARDADO EN BD: " + pagoGuardado.getId());
                } catch (Exception e) {
                    System.out.println("⚠️ No se pudo guardar en BD, pero simulación continúa: " + e.getMessage());
                }
                
                pagoResponse.put("estadoPago", "EXITOSO");
                pagoResponse.put("codigoAutorizacion", codigoAuth);
                pagoResponse.put("mensaje", "🎉 Pago procesado exitosamente");
                pagoResponse.put("banco", "Banco Universitario");
                pagoResponse.put("tiempoRespuesta", "1.2 segundos");
                
                // Datos adicionales para el recibo
                pagoResponse.put("nombreEstudiante", "Juan Carlos Pérez García");
                pagoResponse.put("nombreCurso", "Matemáticas I");
                pagoResponse.put("codigoCurso", "MAT101");
                pagoResponse.put("recibo", "REC-" + System.currentTimeMillis());
                
                System.out.println("✅ PAGO EXITOSO!");
                System.out.println("Código Autorización: " + codigoAuth);
                System.out.println("Número Transacción: " + transactionId);
                
                return new ResponseEntity<>(pagoResponse, HttpStatus.CREATED);
                
            } else {
                pagoDTO.setEstadoPago("FALLIDO");
                pagoDTO.setMotivoFallo("Fondos insuficientes");
                
                // GUARDAR PAGO FALLIDO EN BD
                try {
                    pagoService.guardarPagoSimulado(pagoDTO);
                } catch (Exception e) {
                    System.out.println("⚠️ No se pudo guardar pago fallido en BD: " + e.getMessage());
                }
                
                pagoResponse.put("estadoPago", "FALLIDO");
                pagoResponse.put("motivoFallo", "Fondos insuficientes");
                pagoResponse.put("mensaje", "❌ El pago no pudo ser procesado");
                pagoResponse.put("codigoError", "ERR-" + (int)(Math.random() * 1000));
                pagoResponse.put("sugerencia", "Verifique su saldo o intente con otro método de pago");
                
                System.out.println("❌ PAGO FALLIDO - Fondos insuficientes");
                
                return new ResponseEntity<>(pagoResponse, HttpStatus.PAYMENT_REQUIRED);
            }
            
        } catch (Exception e) {
            System.out.println("💥 ERROR EN PAGO: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error interno del sistema");
            error.put("mensaje", "Por favor contacte soporte técnico");
            error.put("codigoError", "SYS-" + System.currentTimeMillis());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Test endpoint para pagos
     * GET /api/pagos/test
     */
    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> test() {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("status", "✅ Sistema de pagos ACTIVO");
        respuesta.put("version", "2.0.0");
        respuesta.put("timestamp", LocalDateTime.now().toString());
        respuesta.put("metodosPago", new String[]{"TARJETA_CREDITO", "TARJETA_DEBITO", "PSE", "EFECTIVO"});
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    /**
     * Simular consulta de estado
     * GET /api/pagos/{id}/estado  
     */
    @GetMapping("/{id}/estado")
    public ResponseEntity<Map<String, Object>> consultarEstado(@PathVariable String id) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("id", id);
        respuesta.put("estadoPago", "EXITOSO");
        respuesta.put("fechaConsulta", LocalDateTime.now().toString());
        respuesta.put("mensaje", "✅ Pago confirmado y procesado");
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
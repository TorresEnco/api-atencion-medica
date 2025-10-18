package com.atencion.medica.web;

import com.atencion.medica.dtos.HistorialMedicoDTO;
import com.atencion.medica.dtos.MedicoDTO;
import com.atencion.medica.dtos.CitaDTO;
import com.atencion.medica.servicios.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pacientes")
public class PacienteController {
    @Autowired
    private HospitalService medicoService;

    @GetMapping("/{historialId}/historial")
    public ResponseEntity<HistorialMedicoDTO> getHistorialMedico(
            @PathVariable String historialId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        HistorialMedicoDTO historial = medicoService.obtenerHistorialMedico(historialId, page, size);
        return ResponseEntity.ok(historial);
    }

    @GetMapping("/{pacienteId}/citas")
    public ResponseEntity<List<CitaDTO>> obtenerCitasPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(medicoService.obtenerCitasPorPaciente(pacienteId));
    }

    @GetMapping("/citas/{citaId}")
    public ResponseEntity<CitaDTO> obtenerCitaPorId(@PathVariable Long citaId) {
        return ResponseEntity.ok(medicoService.obtenerCitaPorId(citaId));
    }

    @PostMapping("/citas")
    public ResponseEntity<CitaDTO> crearCita(@RequestBody CitaDTO citaDTO) {
        CitaDTO nuevaCita = medicoService.crearCita(citaDTO);
        return new ResponseEntity<>(nuevaCita, HttpStatus.CREATED);
    }

    @GetMapping("/citas")
    public ResponseEntity<List<CitaDTO>> obtenerCitasPorFecha(
            @RequestParam String fecha,
            @RequestParam(required = false) Long medicoId,
            @RequestParam(required = false) Long pacienteId) {
        
        if (medicoId != null) {
            return ResponseEntity.ok(medicoService.obtenerCitasPorMedico(medicoId));
        } else if (pacienteId != null) {
            return ResponseEntity.ok(medicoService.obtenerCitasPorPaciente(pacienteId));
        } else {
            return ResponseEntity.ok(medicoService.obtenerCitasPorFecha(fecha));
        }
    }

}

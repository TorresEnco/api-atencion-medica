package com.atencion.medica.web;

import com.atencion.medica.dtos.MedicoDTO;
import com.atencion.medica.dtos.CitaDTO;
import com.atencion.medica.servicios.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MedicoController {
    @Autowired
    private HospitalService medicoService;
    @PostMapping
    public ResponseEntity<MedicoDTO> crearMedico(@RequestBody MedicoDTO medicoDTO) {
        return new ResponseEntity<>(medicoService.crearMedico(medicoDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MedicoDTO>> listarMedicos() {
        return ResponseEntity.ok(medicoService.obtenerTodosLosMedicos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoDTO> obtenerMedicoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(medicoService.obtenerMedicoPorId(id));
    }

    @GetMapping("/citas/medico/{medicoId}")
    public ResponseEntity<List<CitaDTO>> obtenerCitasPorMedico(@PathVariable Long medicoId) {
        return ResponseEntity.ok(medicoService.obtenerCitasPorMedico(medicoId));
    }

    @GetMapping("/citas/{citaId}")
    public ResponseEntity<CitaDTO> obtenerCitaPorId(@PathVariable Long citaId) {
        return ResponseEntity.ok(medicoService.obtenerCitaPorId(citaId));
    }

    @PutMapping("/citas/{citaId}")
    public ResponseEntity<CitaDTO> actualizarCita(@PathVariable Long citaId, @RequestBody CitaDTO citaDTO) {
        return ResponseEntity.ok(medicoService.actualizarCita(citaId, citaDTO));
    }

    @DeleteMapping("/citas/{citaId}")
    public ResponseEntity<Void> cancelarCita(@PathVariable Long citaId) {
        medicoService.cancelarCita(citaId);
        return ResponseEntity.noContent().build();
    }
}

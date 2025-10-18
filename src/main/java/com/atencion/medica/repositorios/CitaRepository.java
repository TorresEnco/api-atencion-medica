package com.atencion.medica.repositorios;

import com.atencion.medica.entidades.Cita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    
    // Buscar citas por ID del paciente
    List<Cita> findByPacienteId(Long pacienteId);
    
    // Buscar citas por ID del médico
    List<Cita> findByMedicoId(Long medicoId);
    
    // Buscar citas por fecha
    List<Cita> findByFechaCita(Date fechaCita);
    
    // Buscar citas por estado
    List<Cita> findByEstadoCita(String estadoCita);
    
    // Buscar citas por paciente con paginación
    Page<Cita> findByPacienteId(Long pacienteId, Pageable pageable);
    
    // Buscar citas por médico con paginación
    Page<Cita> findByMedicoId(Long medicoId, Pageable pageable);
    
    // Buscar citas por rango de fechas
    List<Cita> findByFechaCitaBetween(Date fechaInicio, Date fechaFin);
    
    // Buscar cita por ID con relaciones cargadas
    Optional<Cita> findById(Long id);
}
package com.atencion.medica.servicios;

import com.atencion.medica.dtos.HistorialMedicoDTO;
import com.atencion.medica.dtos.MedicoDTO;
import com.atencion.medica.dtos.PacienteDTO;
import com.atencion.medica.dtos.CitaDTO;

import java.util.List;

public interface HospitalService {
    //Todo lo que le pertenece al medico
    MedicoDTO crearMedico(MedicoDTO medicoDTO);
    List<MedicoDTO> obtenerTodosLosMedicos();
    MedicoDTO obtenerMedicoPorId(Long id);

    //Todo lo que le pertenece al paciente
    HistorialMedicoDTO obtenerHistorialMedico(String historialId, int page, int size);
    PacienteDTO crearPaciente(PacienteDTO pacienteDTO);

    //Todo lo que le pertenece a la cita
    CitaDTO crearCita(CitaDTO citaDTO);
    CitaDTO obtenerCitaPorId(Long id);
    List<CitaDTO> obtenerCitasPorMedico(Long medicoId);
    List<CitaDTO> obtenerCitasPorPaciente(Long pacienteId);
    List<CitaDTO> obtenerCitasPorFecha(String fecha);
    CitaDTO actualizarCita(Long id, CitaDTO citaDTO);
    void cancelarCita(Long id);
}

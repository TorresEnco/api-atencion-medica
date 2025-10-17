package com.atencion.medica.mappers;

import com.atencion.medica.dtos.MedicoDTO;
import com.atencion.medica.dtos.PacienteDTO;
import com.atencion.medica.dtos.CitaDTO;
import com.atencion.medica.entidades.Medico;
import com.atencion.medica.entidades.Paciente;
import com.atencion.medica.entidades.Cita;

import java.util.List;

public interface AtencionMedicaMapper {
    MedicoDTO medicoToMedicoDTO(Medico medico);
    Medico medicoDTOToMedico(MedicoDTO medicoDTO);

    PacienteDTO pacienteToPacienteDTO(Paciente paciente);
    Paciente pacienteDTOToPaciente(PacienteDTO pacienteDTO);

    CitaDTO citaToCitaDTO(Cita cita);
    Cita citaDTOToCita(CitaDTO citaDTO);

    List<CitaDTO> citasToCitasDTO(List<Cita> citas);
}

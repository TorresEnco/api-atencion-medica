package com.atencion.medica.mappers.Impl;

import com.atencion.medica.dtos.MedicoDTO;
import com.atencion.medica.dtos.PacienteDTO;
import com.atencion.medica.dtos.CitaDTO;
import com.atencion.medica.entidades.Medico;
import com.atencion.medica.entidades.Paciente;
import com.atencion.medica.entidades.Cita;
import com.atencion.medica.mappers.AtencionMedicaMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AtencionMedicaMapperImpl implements AtencionMedicaMapper {

    @Override
    public MedicoDTO medicoToMedicoDTO(Medico medico) {
        if (medico == null) return null;
        MedicoDTO dto = new MedicoDTO();
        dto.setId(medico.getId());
        dto.setNombreCompleto(medico.getNombre() + " " + medico.getApellido());
        dto.setCedulaProfesional(medico.getCedulaProfesional());
        dto.setEspecialidad(medico.getEspecialidad());
        return dto;
    }

    @Override
    public Medico medicoDTOToMedico(MedicoDTO medicoDTO) {
        if (medicoDTO == null) return null;
        Medico medico = new Medico();
        medico.setId(medicoDTO.getId());
        // Se asume que el nombre completo se divide en nombre y apellido en la lógica de servicio si es necesario
        medico.setCedulaProfesional(medicoDTO.getCedulaProfesional());
        medico.setEspecialidad(medicoDTO.getEspecialidad());
        return medico;
    }

    @Override
    public PacienteDTO pacienteToPacienteDTO(Paciente paciente) {
        if (paciente == null) return null;
        PacienteDTO dto = new PacienteDTO();
        dto.setId(paciente.getId());
        dto.setNombre(paciente.getNombre());
        dto.setApellido(paciente.getApellido());
        dto.setFechaNacimiento(paciente.getFechaNacimiento());
        dto.setTelefono(paciente.getTelefono());
        dto.setHistorialClinicoId(paciente.getHistorialClinicoId());
        return dto;
    }

    @Override
    public Paciente pacienteDTOToPaciente(PacienteDTO pacienteDTO) {
        if (pacienteDTO == null) return null;
        Paciente paciente = new Paciente();
        paciente.setId(pacienteDTO.getId());
        paciente.setNombre(pacienteDTO.getNombre());
        paciente.setApellido(pacienteDTO.getApellido());
        paciente.setFechaNacimiento(pacienteDTO.getFechaNacimiento());
        paciente.setTelefono(pacienteDTO.getTelefono());
        paciente.setHistorialClinicoId(pacienteDTO.getHistorialClinicoId());
        return paciente;
    }

    @Override
    public CitaDTO citaToCitaDTO(Cita cita) {
        if (cita == null) return null;
        CitaDTO dto = new CitaDTO();
        dto.setId(cita.getId());
        dto.setFechaCita(cita.getFechaCita());
        dto.setHoraCita(cita.getHoraCita());
        dto.setMotivoConsulta(cita.getMotivoConsulta());
        dto.setDiagnostico(cita.getDiagnostico());
        dto.setNotasMedicas(cita.getNotasMedicas());
        dto.setEstadoCita(cita.getEstadoCita());
        dto.setMedicoDTO(medicoToMedicoDTO(cita.getMedico()));
        dto.setPacienteDTO(pacienteToPacienteDTO(cita.getPaciente()));
        return dto;
    }

    @Override
    public Cita citaDTOToCita(CitaDTO citaDTO) {
        if (citaDTO == null) return null;
        Cita cita = new Cita();
        cita.setId(citaDTO.getId());
        cita.setFechaCita(citaDTO.getFechaCita());
        cita.setHoraCita(citaDTO.getHoraCita());
        cita.setMotivoConsulta(citaDTO.getMotivoConsulta());
        cita.setDiagnostico(citaDTO.getDiagnostico());
        cita.setNotasMedicas(citaDTO.getNotasMedicas());
        cita.setEstadoCita(citaDTO.getEstadoCita());
        // El médico y el paciente se asignarán en la capa de servicio
        return cita;
    }

    @Override
    public List<CitaDTO> citasToCitasDTO(List<Cita> citas) {
        return citas.stream().map(this::citaToCitaDTO).collect(Collectors.toList());
    }
}

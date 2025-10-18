package com.atencion.medica.servicios.Impl;

import com.atencion.medica.dtos.CitaDTO;
import com.atencion.medica.dtos.HistorialMedicoDTO;
import com.atencion.medica.dtos.MedicoDTO;
import com.atencion.medica.dtos.PacienteDTO;
import com.atencion.medica.entidades.Medico;
import com.atencion.medica.entidades.Paciente;
import com.atencion.medica.entidades.Cita;
import com.atencion.medica.excepciones.MedicoNotFoundException;
import com.atencion.medica.excepciones.PacienteNotFoundException;
import com.atencion.medica.excepciones.CitaNotFoundException;
import com.atencion.medica.mappers.AtencionMedicaMapper;
import com.atencion.medica.repositorios.MedicoRepository;
import com.atencion.medica.repositorios.PacienteRepository;
import com.atencion.medica.repositorios.CitaRepository;
import com.atencion.medica.servicios.HospitalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service //spring
@Transactional //spring
@Slf4j //lombok
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private AtencionMedicaMapper mapper;

    @Override
    public MedicoDTO crearMedico(MedicoDTO medicoDTO) {
        // Lógica simple para dividir nombre completo
        String[] nombres = medicoDTO.getNombreCompleto().split(" ", 2);
        Medico medico = new Medico();
        medico.setNombre(nombres[0]);
        medico.setApellido(nombres.length > 1 ? nombres[1] : "");
        medico.setCedulaProfesional(medicoDTO.getCedulaProfesional());
        medico.setEspecialidad(medicoDTO.getEspecialidad());

        Medico medicoGuardado = medicoRepository.save(medico);
        return mapper.medicoToMedicoDTO(medicoGuardado);
    }

    @Override
    public List<MedicoDTO> obtenerTodosLosMedicos() {
        return medicoRepository.findAll()
                .stream()
                .map(mapper::medicoToMedicoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MedicoDTO obtenerMedicoPorId(Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new MedicoNotFoundException("Médico no encontrado con ID: " + id));
        return mapper.medicoToMedicoDTO(medico);
    }

    @Override
    public HistorialMedicoDTO obtenerHistorialMedico(String historialId, int page, int size) {
        // Primero, validamos que el paciente exista
        Optional<Paciente> pacienteOpt = pacienteRepository.findByHistorialClinicoId(historialId);
        if (pacienteOpt.isEmpty()) {
            throw new PacienteNotFoundException("No se encontró paciente con el ID de historial: " + historialId);
        }
        Paciente paciente = pacienteOpt.get();

        Pageable pageable = PageRequest.of(page, size);
        Page<Cita> citasPaginadas = citaRepository.findByPacienteId(paciente.getId(), pageable);

        List<CitaDTO> citasDTO = mapper.citasToCitasDTO(citasPaginadas.getContent());

        HistorialMedicoDTO historialDTO = new HistorialMedicoDTO();
        historialDTO.setHistorialClinicoId(historialId);
        historialDTO.setNombrePaciente(paciente.getNombre() + " " + paciente.getApellido());
        historialDTO.setCurrentPage(citasPaginadas.getNumber());
        historialDTO.setPageSize(citasPaginadas.getSize());
        historialDTO.setTotalPages(citasPaginadas.getTotalPages());
        historialDTO.setCitasDTO(citasDTO);

        return historialDTO;
    }

    @Override
    public PacienteDTO crearPaciente(PacienteDTO pacienteDTO) {
        Paciente paciente = mapper.pacienteDTOToPaciente(pacienteDTO);
        Paciente pacienteGuardado = pacienteRepository.save(paciente);
        return mapper.pacienteToPacienteDTO(pacienteGuardado);
    }

    @Override
    public CitaDTO crearCita(CitaDTO citaDTO) {
        Long medicoId = citaDTO.getMedicoDTO().getId();
        Long pacienteId = citaDTO.getPacienteDTO().getId();

        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new MedicoNotFoundException("Médico no encontrado con ID: " + medicoId));

        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new PacienteNotFoundException("Paciente no encontrado con ID: " + pacienteId));

        Cita cita = mapper.citaDTOToCita(citaDTO);
        cita.setMedico(medico);
        cita.setPaciente(paciente);

        Cita citaGuardada = citaRepository.save(cita);
        return mapper.citaToCitaDTO(citaGuardada);
    }

    @Override
    public CitaDTO obtenerCitaPorId(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new CitaNotFoundException("Cita no encontrada con ID: " + id));
        return mapper.citaToCitaDTO(cita);
    }

    @Override
    public List<CitaDTO> obtenerCitasPorMedico(Long medicoId) {
        return mapper.citasToCitasDTO(citaRepository.findByMedicoId(medicoId));
    }

    @Override
    public List<CitaDTO> obtenerCitasPorPaciente(Long pacienteId) {
        return mapper.citasToCitasDTO(citaRepository.findByPacienteId(pacienteId));
    }

    @Override
    public List<CitaDTO> obtenerCitasPorFecha(String fecha) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaDate = dateFormat.parse(fecha);
            return mapper.citasToCitasDTO(citaRepository.findByFechaCita(fechaDate));
        } catch (Exception e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use yyyy-MM-dd", e);
        }
    }

    @Override
    public CitaDTO actualizarCita(Long id, CitaDTO citaDTO) {
        Cita citaExistente = citaRepository.findById(id)
                .orElseThrow(() -> new CitaNotFoundException("Cita no encontrada con ID: " + id));

        citaExistente.setFechaCita(citaDTO.getFechaCita());
        citaExistente.setHoraCita(citaDTO.getHoraCita());
        citaExistente.setMotivoConsulta(citaDTO.getMotivoConsulta());
        citaExistente.setDiagnostico(citaDTO.getDiagnostico());
        citaExistente.setNotasMedicas(citaDTO.getNotasMedicas());
        citaExistente.setEstadoCita(citaDTO.getEstadoCita());

        Cita citaActualizada = citaRepository.save(citaExistente);
        return mapper.citaToCitaDTO(citaActualizada);
    }

    @Override
    public void cancelarCita(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new CitaNotFoundException("Cita no encontrada con ID: " + id));
        cita.setEstadoCita(com.atencion.medica.enums.EstadoCita.CANCELADA);
        citaRepository.save(cita);
    }
}

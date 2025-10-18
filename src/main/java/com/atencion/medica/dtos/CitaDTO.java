package com.atencion.medica.dtos;

import com.atencion.medica.enums.EstadoCita;
import lombok.Data;

import java.util.Date;

@Data
public class CitaDTO {
    private Long id;
    private Date fechaCita;
    private String horaCita;
    private String motivoConsulta;
    private String diagnostico;
    private String notasMedicas;
    private EstadoCita estadoCita;
    private MedicoDTO medicoDTO; 
    private PacienteDTO pacienteDTO;
}
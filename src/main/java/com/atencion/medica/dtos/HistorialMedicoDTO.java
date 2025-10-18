package com.atencion.medica.dtos;

import lombok.Data;

import java.util.List;
@Data
public class HistorialMedicoDTO {
    private String historialClinicoId;
    private String nombrePaciente;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<CitaDTO> citasDTO; // Lista de citas del paciente
}

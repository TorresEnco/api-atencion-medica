package com.atencion.medica.entidades;

import com.atencion.medica.enums.EstadoCita;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Date fechaCita;
    private String horaCita;
    private String motivoConsulta;
    private String diagnostico;
    private String notasMedicas;
    
    @Enumerated(EnumType.STRING)
    private EstadoCita estadoCita;

    // Relación con Médico (Muchos a Uno)
    @ManyToOne
    private Medico medico;

    // Relación con Paciente (Muchos a Uno)
    @ManyToOne
    private Paciente paciente;
    
    // Una cita puede tener muchas recetas
    @OneToMany(mappedBy = "cita", fetch = FetchType.LAZY)
    private List<Receta> recetas;
}
package com.atencion.medica.entidades;

import com.atencion.medica.enums.EspecialidadMedico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String cedulaProfesional;

    @Enumerated(EnumType.STRING)
    private EspecialidadMedico especialidad;

    // Un médico puede tener muchas citas
    @OneToMany(mappedBy = "medico", fetch = FetchType.LAZY)
    private List<Cita> citas;
}

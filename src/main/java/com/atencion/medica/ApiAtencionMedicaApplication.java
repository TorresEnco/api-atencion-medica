package com.atencion.medica;

import com.atencion.medica.entidades.Medico;
import com.atencion.medica.entidades.Paciente;
import com.atencion.medica.entidades.Receta;
import com.atencion.medica.enums.EspecialidadMedico;
import com.atencion.medica.enums.EstadoReceta;
import com.atencion.medica.repositorios.MedicoRepository;
import com.atencion.medica.repositorios.PacienteRepository;
import com.atencion.medica.repositorios.RecetaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class ApiAtencionMedicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiAtencionMedicaApplication.class, args);
	}

    @Bean
    CommandLineRunner start(MedicoRepository medicoRepository,
                            PacienteRepository pacienteRepository,
                            RecetaRepository recetaRepository) {
        return args -> {
            // 1. Crear Médicos (análogo a Clientes)
            Stream.of("Dr. Alan Brito", "Dra. Elisa Lam", "Dr. Hugo Pardo")
                    .forEach(nombreCompleto -> {
                        Medico medico = new Medico();
                        String[] partes = nombreCompleto.split(" ");
                        medico.setNombre(partes[1]);
                        medico.setApellido(partes[2]);
                        medico.setCedulaProfesional(UUID.randomUUID().toString().substring(0, 8).toUpperCase());

                        // Asignamos una especialidad diferente a cada uno
                        if (nombreCompleto.contains("Alan")) {
                            medico.setEspecialidad(EspecialidadMedico.CARDIOLOGIA);
                        } else if (nombreCompleto.contains("Elisa")) {
                            medico.setEspecialidad(EspecialidadMedico.PEDIATRIA);
                        } else {
                            medico.setEspecialidad(EspecialidadMedico.MEDICINA_GENERAL);
                        }
                        medicoRepository.save(medico);
                    });

            List<Medico> medicos = medicoRepository.findAll();

            // 2. Crear Pacientes (análogo a Clientes)
            Stream.of("Paco Ramos", "Ana Torres", "Luis Garcia")
                    .forEach(nombreCompleto -> {
                        Paciente paciente = new Paciente();
                        String[] partes = nombreCompleto.split(" ");
                        paciente.setNombre(partes[0]);
                        paciente.setApellido(partes[1]);
                        paciente.setFechaNacimiento(new Date()); // Fecha actual solo como ejemplo
                        paciente.setTelefono("555-" + (int) (Math.random() * 1000));
                        paciente.setHistorialClinicoId(UUID.randomUUID().toString().substring(0, 10).toUpperCase()); // ID único
                        pacienteRepository.save(paciente);
                    });

            List<Paciente> pacientes = pacienteRepository.findAll();

            // 3. Crear Recetas (análogo a OperacionCuenta)
            // Iteramos sobre todos los pacientes y les asignamos múltiples recetas
            pacientes.forEach(paciente -> {
                for (int i = 0; i < 3; i++) { // Creamos 3 recetas por paciente
                    Receta receta = new Receta();
                    receta.setFechaEmision(new Date());
                    receta.setDosis("1 pastilla c/8 horas");

                    // Asignar medicamento y estado
                    if (i == 0) {
                        receta.setMedicamento("Amoxicilina " + (i + 500) + "mg");
                        receta.setEstadoReceta(EstadoReceta.ACTIVA);
                    } else if (i == 1) {
                        receta.setMedicamento("Paracetamol " + (i + 500) + "mg");
                        receta.setEstadoReceta(EstadoReceta.SURTIDA);
                    } else {
                        receta.setMedicamento("Ibuprofeno " + (i + 500) + "mg");
                        receta.setEstadoReceta(EstadoReceta.CANCELADA);
                    }

                    // Asignar Paciente y un Médico al azar
                    receta.setPaciente(paciente);
                    receta.setMedico(medicos.get((int) (Math.random() * medicos.size())));

                    recetaRepository.save(receta);
                }
            });
        };
    }

}

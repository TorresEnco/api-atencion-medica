package com.atencion.medica;

import com.atencion.medica.entidades.Medico;
import com.atencion.medica.entidades.Paciente;
import com.atencion.medica.entidades.Cita;
import com.atencion.medica.enums.EspecialidadMedico;
import com.atencion.medica.enums.EstadoCita;
import com.atencion.medica.repositorios.MedicoRepository;
import com.atencion.medica.repositorios.PacienteRepository;
import com.atencion.medica.repositorios.CitaRepository;
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
                            CitaRepository citaRepository) {
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

            // 3. Crear Citas (Appointments)
            // Crear citas para cada paciente con diferentes médicos
            pacientes.forEach(paciente -> {
                for (int i = 0; i < 2; i++) { // Creamos 2 citas por paciente
                    Cita cita = new Cita();
                    cita.setFechaHora(new Date(System.currentTimeMillis() + (i * 24 * 60 * 60 * 1000))); // Diferentes días
                    cita.setMotivo("Consulta general " + (i + 1));
                    cita.setEstado(EstadoCita.PROGRAMADA);
                    cita.setMedico(medicos.get((int) (Math.random() * medicos.size())));
                    cita.setPaciente(paciente);
                    citaRepository.save(cita);
                }
            });
        };
    }

}

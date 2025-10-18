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

    /*@Bean
    CommandLineRunner start(MedicoRepository medicoRepository,
                            PacienteRepository pacienteRepository,
                            CitaRepository citaRepository) {
        return args -> {
            // Verificar si ya existen datos para evitar duplicados
            if (medicoRepository.count() > 0) {
                System.out.println("Los datos de prueba ya existen. Omitiendo inicialización.");
                return;
            }

            System.out.println("Inicializando datos de prueba para el sistema médico...");

            // 1. Crear Médicos
            Stream.of("Dr. Alan Brito", "Dra. Elisa Lam", "Dr. Hugo Pardo")
                    .forEach(nombreCompleto -> {
                        try {
                            Medico medico = new Medico();
                            String[] partes = nombreCompleto.split(" ");
                            
                            if (partes.length >= 3) {
                                medico.setNombre(partes[1]);
                                medico.setApellido(partes[2]);
                                medico.setCedulaProfesional(UUID.randomUUID().toString().substring(0, 8).toUpperCase());

                                // Asignar especialidad según el nombre
                                if (nombreCompleto.contains("Alan")) {
                                    medico.setEspecialidad(EspecialidadMedico.CARDIOLOGIA);
                                } else if (nombreCompleto.contains("Elisa")) {
                                    medico.setEspecialidad(EspecialidadMedico.PEDIATRIA);
                                } else {
                                    medico.setEspecialidad(EspecialidadMedico.MEDICINA_GENERAL);
                                }
                                
                                medicoRepository.save(medico);
                                System.out.println("Médico creado: " + medico.getNombre() + " " + medico.getApellido() + 
                                                 " - " + medico.getEspecialidad());
                            }
                        } catch (Exception e) {
                            System.err.println("Error al crear médico: " + nombreCompleto + " - " + e.getMessage());
                        }
                    });

            List<Medico> medicos = medicoRepository.findAll();
            System.out.println("Total de médicos creados: " + medicos.size());

            // 2. Crear Pacientes
            Stream.of("Paco Ramos", "Ana Torres", "Luis Garcia")
                    .forEach(nombreCompleto -> {
                        try {
                            Paciente paciente = new Paciente();
                            String[] partes = nombreCompleto.split(" ");
                            
                            if (partes.length >= 2) {
                                paciente.setNombre(partes[0]);
                                paciente.setApellido(partes[1]);
                                paciente.setFechaNacimiento(new Date(System.currentTimeMillis() - 
                                    (long) (Math.random() * 30 * 365 * 24 * 60 * 60 * 1000L))); // Edad aleatoria hasta 30 años
                                paciente.setTelefono("555-" + String.format("%04d", (int) (Math.random() * 10000)));
                                paciente.setHistorialClinicoId(UUID.randomUUID().toString().substring(0, 10).toUpperCase());
                                
                                pacienteRepository.save(paciente);
                                System.out.println("Paciente creado: " + paciente.getNombre() + " " + paciente.getApellido() + 
                                                 " - Historial: " + paciente.getHistorialClinicoId());
                            }
                        } catch (Exception e) {
                            System.err.println("Error al crear paciente: " + nombreCompleto + " - " + e.getMessage());
                        }
                    });

            List<Paciente> pacientes = pacienteRepository.findAll();
            System.out.println("Total de pacientes creados: " + pacientes.size());

            // 3. Crear Citas
            if (!medicos.isEmpty() && !pacientes.isEmpty()) {
                pacientes.forEach(paciente -> {
                    try {
                        for (int i = 0; i < 2; i++) {
                            Cita cita = new Cita();
                            // Programar citas en días futuros
                            long tiempoFuturo = System.currentTimeMillis() + ((i + 1) * 24 * 60 * 60 * 1000L);
                            Date fechaCita = new Date(tiempoFuturo);
                            cita.setFechaCita(fechaCita);
                            cita.setHoraCita(String.format("%02d:00", 9 + i)); // Horas 09:00, 10:00, etc.
                            cita.setMotivoConsulta("Consulta general " + (i + 1));
                            cita.setEstadoCita(EstadoCita.PROGRAMADA);
                            
                            // Asignar médico aleatorio
                            Medico medicoAsignado = medicos.get((int) (Math.random() * medicos.size()));
                            cita.setMedico(medicoAsignado);
                            cita.setPaciente(paciente);
                            
                            citaRepository.save(cita);
                            System.out.println("Cita creada: " + paciente.getNombre() + " con Dr. " + 
                                             medicoAsignado.getApellido() + " - " + cita.getMotivoConsulta());
                        }
                    } catch (Exception e) {
                        System.err.println("Error al crear citas para paciente: " + paciente.getNombre() + 
                                         " - " + e.getMessage());
                    }
                });
                
                long totalCitas = citaRepository.count();
                System.out.println("Total de citas creadas: " + totalCitas);
            } else {
                System.err.println("No se pueden crear citas: faltan médicos o pacientes.");
            }

            System.out.println("Inicialización de datos completada exitosamente.");
        };
    }*/

}

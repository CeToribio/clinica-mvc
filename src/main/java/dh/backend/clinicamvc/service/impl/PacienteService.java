package dh.backend.clinicamvc.service.impl;

import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import dh.backend.clinicamvc.repository.IPacienteRepository;
import dh.backend.clinicamvc.service.IPacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);
    private IPacienteRepository pacienteRepository;

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente registrarPaciente(Paciente paciente){
        LOGGER.info("Paciente registrado: " + paciente);
        return pacienteRepository.save(paciente);
    }

    public Optional<Paciente> buscarPorId(Integer id){
        LOGGER.info("Paciente encontrado: " + id);
        return pacienteRepository.findById(id);
    }

    public List<Paciente> buscarTodos(){
        LOGGER.info("listado de pacientes");
        return pacienteRepository.findAll();
    }

    @Override
    public void actualizarPaciente(Paciente paciente) {
        LOGGER.info("Paciente actualizado: " + paciente);
        pacienteRepository.save(paciente);
    }

    @Override
    public void eliminarPaciente(Integer id) throws ResourceNotFoundException {
        LOGGER.info("Paciente eliminado: " + id);
        Optional<Paciente> pacienteOptional = buscarPorId(id);
        if (pacienteOptional.isPresent())
            pacienteRepository.deleteById(id);
        else
            throw new ResourceNotFoundException("{\"message\": \"paciente no encontrado\"}");
    }

    @Override
    public List<Paciente> buscarPorApellido(String apellido) {
        return pacienteRepository.buscarPorApellido(apellido);
    }

    @Override
    public List<Paciente> buscarPorDni(String dni) {
        return pacienteRepository.buscarPorDni(dni);
    }

}

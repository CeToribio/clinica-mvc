package dh.backend.clinicamvc.service.impl;

import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import dh.backend.clinicamvc.repository.IOdontologoRepository;
import dh.backend.clinicamvc.service.IOdontologoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OdontologoService.class);
    private IOdontologoRepository odontologoRepository;

    public OdontologoService(IOdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }


    public Odontologo registrarOdontologo(Odontologo odontologo) throws BadRequestException {
        if(odontologo.getApellido() == null || odontologo.getNombre() == null || odontologo.getMatricula() == null){
            throw new BadRequestException("{\"message\": \"faltan datos obligatorios\"}");
        } else {
            LOGGER.info("Odontologo registrado: " + odontologo);
            return odontologoRepository.save(odontologo);
        }
    }

    public Optional <Odontologo> buscarUnOdontologo(Integer id){
        LOGGER.info("Odontologo encontrado: " + id);
        return odontologoRepository.findById(id);
    }

    public List<Odontologo> buscarTodos(){
        LOGGER.info("listado de odontologos");
        return odontologoRepository.findAll();
    }

    @Override
    public void actualizarOdontologo(Odontologo odontologo) {
        LOGGER.info("Odontologo actualizado: " + odontologo);
        odontologoRepository.save(odontologo);
    }

    @Override
    public void eliminarOdontologo(Integer id) throws ResourceNotFoundException {
        LOGGER.info("Odontologo eliminado: " + id);
        Optional<Odontologo> odontologoOptional = buscarUnOdontologo(id);
        if (odontologoOptional.isPresent())
            odontologoRepository.deleteById(id);
        else throw new ResourceNotFoundException("{\"message\": \"odontologo no encontrado\"}");
    }

    @Override
    public List<Odontologo> buscarPorApellido(String apellido) {
        return odontologoRepository.buscarPorApellido(apellido);
    }

    @Override
    public List<Odontologo> buscarPorNombre(String nombre) {
        return odontologoRepository.findByNombreLike(nombre);
    }
}



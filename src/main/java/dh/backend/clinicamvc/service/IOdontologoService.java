package dh.backend.clinicamvc.service;


import dh.backend.clinicamvc.model.Odontologo;

import java.util.List;

public interface IOdontologoService {
    Odontologo registrarOdontologo(Odontologo odontologo);

    Odontologo buscarUnOdontologo(int id);
    List<Odontologo> buscarTodos();

    void actualizarOdontologo(Odontologo odontologo);
    void eliminarOdontologo(int id);
}

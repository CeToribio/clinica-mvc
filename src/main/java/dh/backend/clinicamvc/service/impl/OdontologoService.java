package dh.backend.clinicamvc.service.impl;

import dh.backend.clinicamvc.dao.IDao;
import dh.backend.clinicamvc.model.Odontologo;
import dh.backend.clinicamvc.service.IOdontologoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService implements IOdontologoService {
    private IDao<Odontologo> odontologoIDao;

    public OdontologoService(IDao<Odontologo> odontologoIDao) {

        this.odontologoIDao = odontologoIDao;
    }

    public IDao<Odontologo> getOdontologoIDao() {
        return odontologoIDao;
    }

    public void setOdontologoIDao(IDao<Odontologo> odontologoIDao) {
        this.odontologoIDao = odontologoIDao;
    }

    public Odontologo registrarOdontologo(Odontologo odontologo){

        return odontologoIDao.registrar(odontologo);
    }

    public Odontologo buscarUnOdontologo(int id){
        return odontologoIDao.buscarPorId(id);
    }

    public List<Odontologo> buscarTodos(){

        return odontologoIDao.buscarTodos();
    }

    @Override
    public void actualizarOdontologo(Odontologo odontologo) {
        odontologoIDao.actualizar(odontologo);

    }

    @Override
    public void eliminarOdontologo(int id) {
        odontologoIDao.eliminar(id);

    }
}
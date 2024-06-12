package dh.backend.clinicamvc.repository;

import dh.backend.clinicamvc.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IOdontologoRepository extends JpaRepository<Odontologo, Integer> {
    //Buscar odontologo por apellido
    @Query("select o from Odontologo o where o.apellido = ?1")
    List<Odontologo> buscarPorApellido(String apellido);
}

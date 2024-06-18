package dh.backend.clinicamvc.repository;

import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPacienteRepository extends JpaRepository<Paciente, Integer> {
    // Buscar paciente por apellido
    @Query("Select p from Paciente p where LOWER(p.apellido) = LOWER(:apellido)")
    List<Paciente> buscarPorApellido(String apellido);

    // Buscar Paciente por dni
    @Query("Select p from Paciente p where p.dni = ?1")
    List<Paciente> buscarPorDni(String dni);
}

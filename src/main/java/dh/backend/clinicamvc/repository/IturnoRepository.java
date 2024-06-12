package dh.backend.clinicamvc.repository;

import dh.backend.clinicamvc.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IturnoRepository extends JpaRepository<Turno, Integer> {
}

package dh.backend.clinicamvc.service;

import dh.backend.clinicamvc.Dto.request.TurnoRequestDto;
import dh.backend.clinicamvc.Dto.response.TurnoResponseDto;
import dh.backend.clinicamvc.entity.Domicilio;
import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.entity.Paciente;

import dh.backend.clinicamvc.service.impl.TurnoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TurnoServiceTest {
    private static Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    //inyección de dependencia
    @Autowired
    private ITurnoService turnoService;
    @Autowired
    private IPacienteService pacienteService;
    @Autowired
    private IOdontologoService odontologoService;
    private TurnoRequestDto turno;
    private Paciente paciente;
    private Odontologo odontologo;

    @BeforeEach
    void setUp(){
        paciente = new Paciente();
        paciente.setId(1);
        paciente.setNombre("Menganito");
        paciente.setApellido("Cosme");
        paciente.setDni("464646");
        paciente.setFechaIngreso(LocalDate.of(2024,01,12));
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Calle falsa");
        domicilio.setNumero(123);
        domicilio.setLocalidad("San Pedro");
        domicilio.setProvincia("Jujuy");
        paciente.setDomicilio(domicilio);
        pacienteService.registrarPaciente(paciente);
        odontologo = new Odontologo();
        odontologo.setId(1);
        odontologo.setMatricula("1111");
        odontologo.setNombre("Juan");
        odontologo.setApellido("Tolentino");
        odontologoService.registrarOdontologo(odontologo);
        turno = new TurnoRequestDto();
        turno.setPaciente_id(1);
        turno.setOdontologo_id(1);
        turno.setFecha("2021-12-12");
    }


    @Test
    @Transactional
    @DisplayName("Testear que un turno fue guardado")
    void testTurnoGuardado(){
        TurnoResponseDto turnoDesdeLaBD = turnoService.registrar(turno);
        assertNotNull(turnoDesdeLaBD);
    }


    @Test
    @DisplayName("Testear busqueda todos los pacientes")
    void testBusquedaTodos() {
        List<TurnoResponseDto> turnos = turnoService.buscarTodos();
        assertTrue(turnos.size()!=0);
    }

}
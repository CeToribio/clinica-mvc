package dh.backend.clinicamvc.service;
import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.service.impl.OdontologoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OdontologoServiceTest {
    private static Logger LOGGER = LoggerFactory.getLogger(OdontologoServiceTest.class);
    //inyección de dependencia
    @Autowired
    private OdontologoService odontologoService;
    private Odontologo odontologo;

    @BeforeEach
    void setUp(){
        odontologo = new Odontologo();
        odontologo.setMatricula("1111");
        odontologo.setNombre("Juan");
        odontologo.setApellido("Tolentino");
    }


    @Test
    @DisplayName("Testear que un odontologo fue guardado")
    void testOdontologoGuardado() throws BadRequestException {
        Odontologo odontologoDesdeLaBD = odontologoService.registrarOdontologo(odontologo);
        assertNotNull(odontologoDesdeLaBD);
    }

    @Test
    @DisplayName("Testear busqueda odontologo por id")
    void testOdontoloPorId(){
        Integer id = 1;
        Optional<Odontologo> odontologoEncontrado = odontologoService.buscarUnOdontologo(id);
        Odontologo odontologo1 = odontologoEncontrado.get();

        assertEquals(id, odontologo1.getId());
    }

    @Test
    @DisplayName("Testear busqueda todos los odontologos")
    void testBusquedaTodos() {
        List<Odontologo> odontologos = odontologoService.buscarTodos();
        assertTrue(odontologos.size()!=0);
    }

}
package dh.backend.clinicamvc.service;

import dh.backend.clinicamvc.dao.impl.OdontologoDaoH2;
import dh.backend.clinicamvc.model.Odontologo;
import dh.backend.clinicamvc.service.impl.OdontologoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OdontologoServiceTest {
    private static Logger LOGGER = LoggerFactory.getLogger(OdontologoServiceTest.class);
    private static OdontologoService odontologoService = new OdontologoService(new OdontologoDaoH2());
    @BeforeAll
    static void crearTablas(){
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/clinicaMVC2805;INIT=RUNSCRIPT FROM 'create.sql'", "sa", "sa");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
    @Test
    @DisplayName("Testear el listar de todos los odontologos")
    void testBusquedaTodos() {
        Odontologo odontologo = new Odontologo("r-554","ANA","DIAZ");
        odontologoService.registrarOdontologo(odontologo);

        List<Odontologo> odontologos = odontologoService.buscarTodos();

        assertEquals(2, odontologos.size());

    }

}
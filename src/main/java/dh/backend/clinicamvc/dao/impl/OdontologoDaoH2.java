package dh.backend.clinicamvc.dao.impl;

import dh.backend.clinicamvc.dao.IDao;
import dh.backend.clinicamvc.db.H2Connection;
import dh.backend.clinicamvc.model.Odontologo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OdontologoDaoH2 implements IDao<Odontologo> {

    public static Logger LOGGER = LoggerFactory.getLogger(OdontologoDaoH2.class);
    public static String SQL_INSERT = "INSERT INTO ODONTOLOGOS VALUES (DEFAULT,?,?,?)";
    public static final String BUSCAR_ID = "SELECT * FROM ODONTOLOGOS WHERE ID = ?";
    public static String SQL_SELECT_ALL = "SELECT * FROM ODONTOLOGOS";


    @Override
    public Odontologo registrar(Odontologo odontologo) {
        Connection connection = null;
        OdontologoDaoH2 odontologoDaoH2 = new OdontologoDaoH2();
        Odontologo odontologoARetornar = null;
        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, odontologo.getMatricula());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.setString(3, odontologo.getApellido());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()){
                Integer id= resultSet.getInt(1);
                odontologoARetornar = new Odontologo(id, odontologo.getMatricula(), odontologo.getNombre(),
                        odontologo.getApellido());
            }
            LOGGER.info("odontologo guardado: "+ odontologoARetornar);

            connection.commit();
            connection.setAutoCommit(true);
        }catch (Exception e){
            if(connection!=null){
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    LOGGER.info(ex.getMessage());
                    ex.printStackTrace();
                }
            }
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.info(e.getMessage());
                e.printStackTrace();
            }
        }

        return odontologoARetornar;
    }

    @Override
    public List<Odontologo> buscarTodos() {
        List<Odontologo> odontologos = new ArrayList<>();
        Connection connection = null;
        Odontologo odontologo = null;
        try{
            connection = H2Connection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()){
                Integer idDevuelto = resultSet.getInt(1);
                String matricula = resultSet.getString(2);
                String nombre = resultSet.getString(3);
                String apellido = resultSet.getString(4);
                odontologo  = new Odontologo(idDevuelto, matricula, nombre, apellido);

                LOGGER.info("odontologo listado: "+ odontologo);
                odontologos.add(odontologo);
            }

        }catch (Exception e){
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.info(e.getMessage());
                e.printStackTrace();
            }
        }
        return odontologos;
    }

    @Override
    public Odontologo buscarPorId(Integer id) {
        Connection connection = null;
        Odontologo odontologoEncontrado = null;
        try{
            connection = H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(BUSCAR_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                odontologoEncontrado = crearOdontologo(resultSet);
            }
            LOGGER.info("Odontologo encontrado: "+odontologoEncontrado);

        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return odontologoEncontrado;
    }

    @Override
    public void actualizar(Odontologo odontologo) {

    }

    @Override
    public void eliminar(Integer id) {

    }

    private Odontologo crearOdontologo(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String matricula = resultSet.getString(2);
        String nombre = resultSet.getString(3);
        String apellido = resultSet.getString(4);
        Odontologo odontologo = new Odontologo(id, matricula,nombre,apellido);
        return odontologo;
    }
}



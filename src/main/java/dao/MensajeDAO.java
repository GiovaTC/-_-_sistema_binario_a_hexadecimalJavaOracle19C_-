package dao;

import config.ConexionOracle;
import model.Mensaje;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MensajeDAO {

    public void guardar(Mensaje mensaje) throws SQLException
    {
        String sql = "{CALL PR_GUARDAR_MENSAJE_HEX(?,?,?)}";

        try (Connection conexion = ConexionOracle.conectar()) {
            conexion.setAutoCommit(false);

            try (CallableStatement cs = conexion.prepareCall(sql)) {
                cs.setString(1, mensaje.getMensaje());
                cs.setString(2, mensaje.getBinario());
                cs.setString(3, mensaje.getHexadecimal());

                cs.execute();

                conexion.commit();

                System.out.println(
                        "Registro guardado correctamente en Oracle!"
                );

            } catch (SQLException e) {

                conexion.rollback();
                throw e;
            } finally {
                conexion.setAutoCommit(true);
            }
        }
    }   
}

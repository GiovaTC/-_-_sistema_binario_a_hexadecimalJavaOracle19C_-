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

    public void consultar() throws SQLException
    {
        String sql = """
                SELECT ID, MENSAJE, BINARIO, HEXADECIMAL, FECHA_REGISTRO
                FROM MENSAJES_HEXADECIMALES ORDER BY ID
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println(
                    "\nREGISTROS ALMACENADOS EN ORACLE"
            );

            while (rs.next()) {

                System.out.println("----------------------------");

                System.out.println("ID: " +
                        rs.getInt("ID"));

                System.out.println("Mensaje: " +
                        rs.getString("MENSAJE"));

                System.out.println("Binario: " +
                        rs.getString("BINARIO"));

                System.out.println("Hexadecimal: " +
                        rs.getString("HEXADECIMAL"));

                System.out.println("Fecha: " +
                        rs.getTimestamp("FECHA_REGISTRO"));
            }
        }
    }   
}

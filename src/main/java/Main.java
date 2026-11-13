import dao.MensajeDAO;
import model.Mensaje;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

public class Main
{
    // CONVERSION BINARIA A HEXADECIMAL!
    public static String convertirHexadecimal(String binario)
    {
        String limpio = binario.replaceAll("\\s+", "");

        if (limpio.isEmpty() || limpio.length() % 8 != 0)
        {
            throw  new IllegalArgumentException("EL binario debe contener grupos completos " +
                    "de 8 bits");
        }

        StringBuilder hexadecimal = new StringBuilder();

        for (int i = 0; i < limpio.length(); i += 8)
        {
            String byteBinario = limpio.substring(i, i + 8);
            int decimal = Integer.parseInt(byteBinario, 2);

            hexadecimal.append(String.format("%02X", decimal));
        }

        return hexadecimal.toString();
    }

    // CONVERSION BINARIO A TEXTO!
    // CONVERSION BINARIA A TEXTO

    public static String convertirTexto(String binario) {

        String limpio = binario.replaceAll("\\s+", "");

        byte[] datos = new byte[limpio.length() / 8];

        for (int i = 0; i < limpio.length(); i += 8) {

            String grupo = limpio.substring(i, i + 8);

            datos[i / 8] = (byte)
                    Integer.parseInt(grupo, 2);
        }

        return new String(datos, StandardCharsets.UTF_8);
    }

    // METODO PRINCIPAL
    public static void main(String[] args) {

        MensajeDAO dao = new MensajeDAO();

        List<String> ejemplos = List.of(

                "01001000 01101111 01101100 01100001",

                "01001010 01100001 01110110 01100001",

                "01001111 01110010 01100001 01100011 01101100 01100101"
        );

        System.out.println("======================================");
        System.out.println(" SISTEMA BINARIO A HEXADECIMAL");
        System.out.println(" JAVA + ORACLE DATABASE 19C");
        System.out.println("======================================");

        int contador = 1;

        for (String binario : ejemplos) {

            try {

                String hexadecimal =
                        convertirHexadecimal(binario);

                String mensaje =
                        convertirTexto(binario);

                System.out.println("\nEJEMPLO " + contador);

                System.out.println("------------------------------");

                System.out.println("Mensaje: " + mensaje);

                System.out.println("Binario: " + binario);

                System.out.println("Hexadecimal: " + hexadecimal);

                // GUARDAR EN ORACLE

                Mensaje registro = new Mensaje(
                        mensaje,
                        binario,
                        hexadecimal
                );

                dao.guardar(registro);

                contador++;

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Error de conversion: " + e.getMessage()
                );

            } catch (SQLException e) {

                System.out.println(
                        "Error Oracle: " + e.getMessage()
                );

                System.out.println(
                        "Se interrumpio el procesamiento."
                );

                return;
            }
        }

        // CONSULTAR REGISTROS

        try {

            dao.consultar();

        } catch (SQLException e) {

            System.out.println(
                    "Error consultando Oracle: " + e.getMessage()
            );
        }

        System.out.println("\n======================================");
        System.out.println(" PROCESAMIENTO FINALIZADO");
        System.out.println("======================================");
    }   
}
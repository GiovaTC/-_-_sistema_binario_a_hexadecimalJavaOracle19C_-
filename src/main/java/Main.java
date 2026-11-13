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
}
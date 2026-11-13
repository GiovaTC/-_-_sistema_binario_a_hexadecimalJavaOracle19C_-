package model;

public class Mensaje {

    private final String mensaje;
    private final String binario;
    private final String hexadecimal;

    public Mensaje(String mensaje, String binario, String hexadecimal)
    {
        this.mensaje = mensaje;
        this.binario = binario;
        this.hexadecimal = hexadecimal;
    }

    public String getMensaje()
    {
        return mensaje;
    }

    public String getBinario()
    {
        return binario;
    }

    public String getHexadecimal()
    {
        return hexadecimal;
    }
}

# -_-_sistema_binario_a_hexadecimalJavaOracle19C_- :.
# 💻 Sistema Binario a Hexadecimal con Java y Oracle 19c:

<img width="1254" height="1254" alt="image" src="https://github.com/user-attachments/assets/1db85522-d8ea-4365-9162-1df123194397" />  

## 1. Descripción del proyecto

Este proyecto permite desarrollar una aplicación de consola utilizando Java 21 e IntelliJ IDEA, encargada 
de convertir mensajes representados en formato binario a formato hexadecimal .

La información procesada se almacena en una base de datos Oracle Database 19c mediante JDBC y un procedimiento almacenado PL/SQL.

### Objetivos

- Mostrar mensajes binarios en la consola.
- Convertir números binarios a hexadecimal.
- Interpretar los valores binarios como caracteres de texto.
- Procesar tres ejemplos diferentes.
- Guardar la información en Oracle 19c.
- Consultar los registros almacenados.
- Implementar conexión JDBC con Java.

## 2. Tecnologías utilizadas

| Tecnología | Versión |
|---|---|
| Java JDK | 21 |
| IntelliJ IDEA | 2026 |
| Oracle Database | 19c |
| JDBC | ojdbc11 |
| Maven | 3.x |
| SQL Developer | Compatible con Oracle 19c |

## 3. Estructura del proyecto

```text
JavaBinarioHexadecimalOracle19C
│
├── pom.xml
│
├── sql
│   └── script_oracle.sql
│
└── src
    └── main
        └── java
            │
            ├── config
            │   └── ConexionOracle.java
            │
            ├── model
            │   └── Mensaje.java
            │
            ├── dao
            │   └── MensajeDAO.java
            │
            └── Main.java
```

## 4. Ejemplos de conversión

El programa procesa tres mensajes.

### Ejemplo 1: HOLA

Binario:

```text
01001000 01101111 01101100 01100001
```

Hexadecimal:

```text
486F6C61
```

Resultado:

```text
HOLA
```

### Ejemplo 2: JAVA

Binario:

```text
01001010 01100001 01110110 01100001
```

Hexadecimal:

```text
4A617661
```

Resultado:

```text
JAVA
```

### Ejemplo 3: ORACLE

Binario:

```text
01001111 01110010 01100001 01100011 01101100 01100101
```

Hexadecimal:

```text
4F7261636C65
```

Resultado:

```text
ORACLE
```

## 5. Configuración Maven

Archivo: `pom.xml`

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.giova</groupId>
    <artifactId>JavaBinarioHexadecimalOracle19C</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>com.oracle.database.jdbc</groupId>
            <artifactId>ojdbc11</artifactId>
            <version>23.6.0.24.10</version>
        </dependency>
    </dependencies>

</project>
```

## 6. Script de base de datos Oracle 19c

Archivo: `sql/script_oracle.sql`

```sql
-- ==========================================
-- PROYECTO BINARIO A HEXADECIMAL
-- ORACLE DATABASE 19C
-- ==========================================

-- CREACION DE TABLA

CREATE TABLE MENSAJES_HEXADECIMALES (
    ID NUMBER GENERATED ALWAYS AS IDENTITY
       PRIMARY KEY,
    MENSAJE VARCHAR2(100),
    BINARIO VARCHAR2(4000),
    HEXADECIMAL VARCHAR2(4000),
    FECHA_REGISTRO DATE DEFAULT SYSDATE
);

-- ==========================================
-- PROCEDIMIENTO ALMACENADO
-- ==========================================

CREATE OR REPLACE PROCEDURE
PR_GUARDAR_MENSAJE_HEX (
    P_MENSAJE IN VARCHAR2,
    P_BINARIO IN VARCHAR2,
    P_HEXADECIMAL IN VARCHAR2
)
AS
BEGIN

    INSERT INTO MENSAJES_HEXADECIMALES (
        MENSAJE,
        BINARIO,
        HEXADECIMAL,
        FECHA_REGISTRO
    )
    VALUES (
        P_MENSAJE,
        P_BINARIO,
        P_HEXADECIMAL,
        SYSDATE
    );

END;
/

-- ==========================================
-- CONSULTA DE REGISTROS
-- ==========================================

SELECT
    ID,
    MENSAJE,
    BINARIO,
    HEXADECIMAL,
    FECHA_REGISTRO
FROM MENSAJES_HEXADECIMALES
ORDER BY ID;
```

## 7. Conexión Oracle JDBC

Archivo: `ConexionOracle.java`

```java
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle {

    private static final String URL =
            "jdbc:oracle:thin:@//localhost:1521/orcl";

    private static final String USUARIO = "system";

    private static final String PASSWORD = "TU_PASSWORD";

    public static Connection conectar() throws SQLException {

        return DriverManager.getConnection(
                URL,
                USUARIO,
                PASSWORD
        );
    }
}
```

**Importante:** reemplazar `TU_PASSWORD` por la contraseña correspondiente al usuario de Oracle.

Si utilizas otro servicio de Oracle, modificar el nombre `orcl` en la URL JDBC.

## 8. Clase modelo Mensaje

Archivo: `Mensaje.java`

```java
package model;

public class Mensaje {

    private final String mensaje;
    private final String binario;
    private final String hexadecimal;

    public Mensaje(
            String mensaje,
            String binario,
            String hexadecimal) {

        this.mensaje = mensaje;
        this.binario = binario;
        this.hexadecimal = hexadecimal;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getBinario() {
        return binario;
    }

    public String getHexadecimal() {
        return hexadecimal;
    }
}
```

## 9. DAO: almacenamiento y consulta

Archivo: `MensajeDAO.java`

```java
package dao;

import config.ConexionOracle;
import model.Mensaje;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MensajeDAO {

    public void guardar(Mensaje mensaje)
            throws SQLException {

        String sql = "{CALL PR_GUARDAR_MENSAJE_HEX(?,?,?)}";

        try (Connection conexion = ConexionOracle.conectar()) {

            conexion.setAutoCommit(false);

            try (CallableStatement cs =
                         conexion.prepareCall(sql)) {

                cs.setString(1, mensaje.getMensaje());
                cs.setString(2, mensaje.getBinario());
                cs.setString(3, mensaje.getHexadecimal());

                cs.execute();

                conexion.commit();

                System.out.println(
                        "Registro guardado correctamente en Oracle."
                );

            } catch (SQLException e) {

                conexion.rollback();
                throw e;

            } finally {

                conexion.setAutoCommit(true);
            }
        }
    }

    public void consultar() throws SQLException {

        String sql = """
                SELECT ID, MENSAJE, BINARIO,
                       HEXADECIMAL, FECHA_REGISTRO
                FROM MENSAJES_HEXADECIMALES
                ORDER BY ID
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
```

## 10. Programa principal Main.java

Este archivo contiene el procesamiento de los tres ejemplos, la conversión binaria a hexadecimal y la conexión con Oracle 19c.

```java
import dao.MensajeDAO;
import model.Mensaje;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

public class Main {

    // CONVERSION BINARIA A HEXADECIMAL

    public static String convertirHexadecimal(String binario) {

        String limpio = binario.replaceAll("\\s+", "");

        if (limpio.isEmpty() ||
                !limpio.matches("[01]+") ||
                limpio.length() % 8 != 0) {

            throw new IllegalArgumentException(
                    "El binario debe contener grupos completos de 8 bits."
            );
        }

        StringBuilder hexadecimal = new StringBuilder();

        for (int i = 0; i < limpio.length(); i += 8) {

            String byteBinario = limpio.substring(i, i + 8);

            int decimal = Integer.parseInt(byteBinario, 2);

            hexadecimal.append(
                    String.format("%02X", decimal)
            );
        }

        return hexadecimal.toString();
    }

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
```

## 11. Ejecución del proyecto

### Paso 1. Crear el proyecto

1. Abrir IntelliJ IDEA.
2. Seleccionar New Project.
3. Seleccionar Java.
4. Configurar JDK 21.
5. Seleccionar Maven.
6. Asignar el nombre `JavaBinarioHexadecimalOracle19C`.

### Paso 2. Configurar Oracle

1. Abrir Oracle SQL Developer.
2. Conectarse a Oracle Database 19c.
3. Ejecutar `script_oracle.sql`.
4. Verificar la creación de la tabla.
5. Verificar la compilación del procedimiento almacenado.

### Paso 3. Configurar la conexión

Abrir `ConexionOracle.java`.

Configurar:

```java
private static final String URL =
        "jdbc:oracle:thin:@//localhost:1521/orcl";

private static final String USUARIO = "system";

private static final String PASSWORD = "TU_PASSWORD";
```

### Paso 4. Ejecutar el programa

1. Abrir `Main.java`.
2. Seleccionar Run Main.
3. Observar las conversiones en consola.
4. Verificar los registros almacenados en Oracle.

## 12. Resultado esperado en consola

```text
======================================
 SISTEMA BINARIO A HEXADECIMAL
 JAVA + ORACLE DATABASE 19C
======================================

EJEMPLO 1
------------------------------
Mensaje: Hola
Binario: 01001000 01101111 01101100 01100001
Hexadecimal: 486F6C61
Registro guardado correctamente en Oracle.

EJEMPLO 2
------------------------------
Mensaje: Java
Binario: 01001010 01100001 01110110 01100001
Hexadecimal: 4A617661
Registro guardado correctamente en Oracle.

EJEMPLO 3
------------------------------
Mensaje: Oracle
Binario: 01001111 01110010 01100001 01100011 01101100 01100101
Hexadecimal: 4F7261636C65
Registro guardado correctamente en Oracle.

REGISTROS ALMACENADOS EN ORACLE

----------------------------
ID: 1
Mensaje: Hola
Hexadecimal: 486F6C61

----------------------------
ID: 2
Mensaje: Java
Hexadecimal: 4A617661

----------------------------
ID: 3
Mensaje: Oracle
Hexadecimal: 4F7261636C65

======================================
 PROCESAMIENTO FINALIZADO
======================================
```

## 13. Verificación de datos en Oracle 19c

Ejecutar:

```sql
SELECT
    ID,
    MENSAJE,
    BINARIO,
    HEXADECIMAL,
    FECHA_REGISTRO
FROM MENSAJES_HEXADECIMALES
ORDER BY ID;
```

Resultado esperado:

| ID | MENSAJE | HEXADECIMAL |
|---|---|---|
| 1 | Hola | 486F6C61 |
| 2 | Java | 4A617661 |
| 3 | Oracle | 4F7261636C65 |

## 14. Arquitectura del proyecto

```text
              JAVA INTELLIJ IDEA
                      |
                      |
                 MAIN.JAVA
                      |
              CONVERSION BINARIA
                      |
                HEXADECIMAL
                      |
                 MENSAJE DAO
                      |
                  JDBC
                      |
              ORACLE DATABASE 19C
                      |
             PROCEDIMIENTO PL/SQL
                      |
          MENSAJES_HEXADECIMALES
                      |
               CONSULTA SQL
```

## 15. Consideraciones técnicas

- El programa utiliza Java 21.
- La conexión se realiza mediante JDBC.
- Se utiliza `ojdbc11` como controlador Oracle.
- El procedimiento almacenado recibe tres parámetros.
- Cada registro se confirma mediante `COMMIT` desde Java.
- En caso de error durante el almacenamiento, se ejecuta `ROLLBACK`.
- Los ejemplos utilizan caracteres ASCII representados en bytes de 8 bits.
- El hexadecimal se representa utilizando letras mayúsculas.
- La tabla almacena las representaciones como cadenas de texto.

**Nota:** para utilizar mensajes con caracteres especiales, como tildes o caracteres Unicode, se deben representar 
los bytes UTF-8 completos y no asumir que cada carácter ocupa exactamente ocho bits .

## 16. Conclusión

El proyecto permite comprender la relación entre los sistemas numéricos binario, decimal y hexadecimal, utilizando 
Java como lenguaje de programación y Oracle Database 19c como motor de persistencia .

También permite practicar conceptos fundamentales de desarrollo de software:

- Programación orientada a objetos.
- Conversión entre sistemas numéricos.
- Manejo de cadenas y arreglos de bytes.
- Conexiones JDBC.
- Procedimientos almacenados PL/SQL.
- Persistencia y consulta de información.

**Proyecto:** JavaBinarioHexadecimalOracle19C

**Autor:** Giovanny Alejandro Tapiero Cataño

**Tecnologías:** Java 21 – IntelliJ IDEA – Oracle Database 19c.
:. . / .  

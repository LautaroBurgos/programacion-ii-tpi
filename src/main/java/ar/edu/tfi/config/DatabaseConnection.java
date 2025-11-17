package ar.edu.tfi.config;

import java.sql.*;
import java.util.Properties;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DatabaseConnection {
    private static final String PROPS = "/db.properties";
    private static boolean initialized = false;

    public static Connection getConnection() throws SQLException {
        try {
            Properties p = new Properties();
            try (InputStream is = DatabaseConnection.class.getResourceAsStream(PROPS)) {
                if (is == null) throw new SQLException("No se encontró db.properties");
                p.load(is);
            }

            Connection conn = DriverManager.getConnection(
                    p.getProperty("db.url"),
                    p.getProperty("db.user"),
                    p.getProperty("db.pass")
            );

            if (!initialized) {
                runSchema(conn);
                initialized = true;
            }

            return conn;

        } catch (Exception e) {
            throw new SQLException("Error al conectar a la BD", e);
        }
    }

    private static void runSchema(Connection conn) {
        try (InputStream is = DatabaseConnection.class.getResourceAsStream("/schema.sql")) {
            if (is == null) return;

            StringBuilder sb = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            }

            String[] statements = sb.toString().split(";");
            for (String st : statements) {
                if (!st.trim().isEmpty()) {
                    try (Statement s = conn.createStatement()) {
                        s.execute(st);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

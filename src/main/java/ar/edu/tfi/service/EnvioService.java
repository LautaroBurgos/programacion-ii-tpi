package ar.edu.tfi.service;

import ar.edu.tfi.config.DatabaseConnection;
import ar.edu.tfi.dao.EnvioDaoJdbc;
import ar.edu.tfi.entities.Envio;

import java.sql.Connection;
import java.util.List;

public class EnvioService {
    private EnvioDaoJdbc envioDao = new EnvioDaoJdbc();

    public List<Envio> listarEnvios() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return envioDao.leerTodos(conn);
        }
    }

    public Envio getById(long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return envioDao.leer(id, conn);
        }
    }
}

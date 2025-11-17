package ar.edu.tfi.service;

import ar.edu.tfi.config.DatabaseConnection;
import ar.edu.tfi.dao.*;
import ar.edu.tfi.entities.*;

import java.sql.Connection;
import java.util.List;

public class PedidoService {
    private PedidoDaoJdbc pedidoDao = new PedidoDaoJdbc();
    private EnvioDaoJdbc envioDao = new EnvioDaoJdbc();

    public void crearPedidoConEnvio(Pedido pedido) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            try {
                conn.setAutoCommit(false);
                // crear envio si necesario
                Envio envio = pedido.getEnvio();
                if (envio != null && envio.getId() == null) {
                    long envioId = envioDao.crear(envio, conn);
                    envio.setId(envioId);
                }
                // crear pedido
                long pedidoId = pedidoDao.crear(pedido, conn);
                pedido.setId(pedidoId);
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    public List<Pedido> listarPedidos() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return pedidoDao.leerTodos(conn);
        }
    }

    public Pedido getById(long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return pedidoDao.leer(id, conn);
        }
    }

    public void eliminarPedidoLogico(long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            try {
                conn.setAutoCommit(false);
                pedidoDao.eliminar(id, conn);
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    // actualizar que puede incluir crear/actualizar envio asociado
    public void actualizarPedidoConEnvio(Pedido pedido) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            try {
                conn.setAutoCommit(false);
                Envio envio = pedido.getEnvio();
                if (envio != null) {
                    if (envio.getId() == null) {
                        long idEnv = envioDao.crear(envio, conn);
                        envio.setId(idEnv);
                    } else {
                        envioDao.actualizar(envio, conn);
                    }
                }
                pedidoDao.actualizar(pedido, conn);
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
}

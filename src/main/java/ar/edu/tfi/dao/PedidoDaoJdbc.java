package ar.edu.tfi.dao;

import ar.edu.tfi.entities.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.math.BigDecimal;

public class PedidoDaoJdbc implements GenericDao<Pedido> {

    private Envio mapEnvioPartial(ResultSet rs) throws SQLException {
        Long envioId = rs.getLong("envio_id");
        if (rs.wasNull()) return null;
        Envio e = new Envio();
        e.setId(envioId);
        e.setTracking(rs.getString("tracking"));
        String emp = rs.getString("empresa"); if (emp!=null) e.setEmpresa(EmpresaEnvio.valueOf(emp));
        String tipo = rs.getString("tipo"); if (tipo!=null) e.setTipo(TipoEnvio.valueOf(tipo));
        BigDecimal costo = rs.getBigDecimal("costo"); e.setCosto(costo);
        Date fd = rs.getDate("fecha_despacho"); if (fd!=null) e.setFechaDespacho(fd.toLocalDate());
        Date fe = rs.getDate("fecha_estimada"); if (fe!=null) e.setFechaEstimada(fe.toLocalDate());
        String est = rs.getString("estado_envio"); if (est!=null) e.setEstado(EstadoEnvio.valueOf(est));
        return e;
    }

    private Pedido map(ResultSet rs) throws SQLException {
        Pedido p = new Pedido();
        p.setId(rs.getLong("id"));
        p.setEliminado(rs.getBoolean("eliminado"));
        p.setNumero(rs.getString("numero"));
        Date d = rs.getDate("fecha"); if (d!=null) p.setFecha(d.toLocalDate());
        p.setClienteNombre(rs.getString("cliente_nombre"));
        p.setTotal(rs.getBigDecimal("total"));
        String est = rs.getString("estado"); if (est!=null) p.setEstado(EstadoPedido.valueOf(est));
        if (rs.getObject("envio_id") != null) p.setEnvio(mapEnvioPartial(rs));
        return p;
    }

    @Override
    public long crear(Pedido p, Connection conn) throws Exception {
        String sql = "INSERT INTO pedido (eliminado, numero, fecha, cliente_nombre, total, estado, envio_id) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setBoolean(1, p.getEliminado());
            ps.setString(2, p.getNumero());
            ps.setDate(3, Date.valueOf(p.getFecha()));
            ps.setString(4, p.getClienteNombre());
            ps.setBigDecimal(5, p.getTotal());
            ps.setString(6, p.getEstado()!=null? p.getEstado().name():null);
            if (p.getEnvio()!=null && p.getEnvio().getId()!=null) ps.setLong(7, p.getEnvio().getId()); else ps.setNull(7, Types.BIGINT);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) return rs.getLong(1); }
        }
        throw new SQLException("No se generó ID de pedido");
    }

    @Override
    public Pedido leer(long id, Connection conn) throws Exception {
        String sql = "SELECT p.*, e.tracking, e.empresa, e.tipo, e.costo, e.fecha_despacho, e.fecha_estimada, e.estado as estado_envio, p.envio_id FROM pedido p LEFT JOIN envio e ON p.envio_id = e.id WHERE p.id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return map(rs); }
        }
        return null;
    }

    @Override
    public List<Pedido> leerTodos(Connection conn) throws Exception {
        String sql = "SELECT p.*, e.tracking, e.empresa, e.tipo, e.costo, e.fecha_despacho, e.fecha_estimada, e.estado as estado_envio, p.envio_id FROM pedido p LEFT JOIN envio e ON p.envio_id = e.id WHERE p.eliminado = FALSE";
        List<Pedido> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        }
        return lista;
    }

    @Override
    public void actualizar(Pedido p, Connection conn) throws Exception {
        String sql = "UPDATE pedido SET eliminado=?, numero=?, fecha=?, cliente_nombre=?, total=?, estado=?, envio_id=? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, p.getEliminado());
            ps.setString(2, p.getNumero());
            ps.setDate(3, Date.valueOf(p.getFecha()));
            ps.setString(4, p.getClienteNombre());
            ps.setBigDecimal(5, p.getTotal());
            ps.setString(6, p.getEstado()!=null? p.getEstado().name():null);
            if (p.getEnvio()!=null && p.getEnvio().getId()!=null) ps.setLong(7, p.getEnvio().getId()); else ps.setNull(7, Types.BIGINT);
            ps.setLong(8, p.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(long id, Connection conn) throws Exception {
        String sql = "UPDATE pedido SET eliminado = TRUE WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id); ps.executeUpdate();
        }
    }
}

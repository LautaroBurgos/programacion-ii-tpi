package ar.edu.tfi.dao;

import ar.edu.tfi.entities.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EnvioDaoJdbc implements GenericDao<Envio> {

    private Envio map(ResultSet rs) throws SQLException {
        Envio e = new Envio();
        e.setId(rs.getLong("id"));
        e.setEliminado(rs.getBoolean("eliminado"));
        e.setTracking(rs.getString("tracking"));
        String emp = rs.getString("empresa"); if (emp!=null) e.setEmpresa(EmpresaEnvio.valueOf(emp));
        String tipo = rs.getString("tipo"); if (tipo!=null) e.setTipo(TipoEnvio.valueOf(tipo));
        BigDecimal costo = rs.getBigDecimal("costo"); e.setCosto(costo);
        Date fd = rs.getDate("fecha_despacho"); if (fd!=null) e.setFechaDespacho(fd.toLocalDate());
        Date fe = rs.getDate("fecha_estimada"); if (fe!=null) e.setFechaEstimada(fe.toLocalDate());
        String est = rs.getString("estado"); if (est!=null) e.setEstado(EstadoEnvio.valueOf(est));
        return e;
    }

    @Override
    public long crear(Envio e, Connection conn) throws Exception {
        String sql = "INSERT INTO envio (eliminado, tracking, empresa, tipo, costo, fecha_despacho, fecha_estimada, estado) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setBoolean(1, e.getEliminado());
            ps.setString(2, e.getTracking());
            ps.setString(3, e.getEmpresa()!=null?e.getEmpresa().name():null);
            ps.setString(4, e.getTipo()!=null?e.getTipo().name():null);
            ps.setBigDecimal(5, e.getCosto());
            if (e.getFechaDespacho()!=null) ps.setDate(6, Date.valueOf(e.getFechaDespacho())); else ps.setNull(6, Types.DATE);
            if (e.getFechaEstimada()!=null) ps.setDate(7, Date.valueOf(e.getFechaEstimada())); else ps.setNull(7, Types.DATE);
            ps.setString(8, e.getEstado()!=null?e.getEstado().name():null);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) return rs.getLong(1); }
        }
        throw new SQLException("No se generó ID de envio");
    }

    @Override
    public Envio leer(long id, Connection conn) throws Exception {
        String sql = "SELECT * FROM envio WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return map(rs); }
        }
        return null;
    }

    @Override
    public List<Envio> leerTodos(Connection conn) throws Exception {
        String sql = "SELECT * FROM envio WHERE eliminado = FALSE";
        List<Envio> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        }
        return lista;
    }

    @Override
    public void actualizar(Envio e, Connection conn) throws Exception {
        String sql = "UPDATE envio SET eliminado=?, tracking=?, empresa=?, tipo=?, costo=?, fecha_despacho=?, fecha_estimada=?, estado=? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, e.getEliminado());
            ps.setString(2, e.getTracking());
            ps.setString(3, e.getEmpresa()!=null?e.getEmpresa().name():null);
            ps.setString(4, e.getTipo()!=null?e.getTipo().name():null);
            ps.setBigDecimal(5, e.getCosto());
            if (e.getFechaDespacho()!=null) ps.setDate(6, Date.valueOf(e.getFechaDespacho())); else ps.setNull(6, Types.DATE);
            if (e.getFechaEstimada()!=null) ps.setDate(7, Date.valueOf(e.getFechaEstimada())); else ps.setNull(7, Types.DATE);
            ps.setString(8, e.getEstado()!=null?e.getEstado().name():null);
            ps.setLong(9, e.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(long id, Connection conn) throws Exception {
        String sql = "UPDATE envio SET eliminado = TRUE WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id); ps.executeUpdate();
        }
    }
}

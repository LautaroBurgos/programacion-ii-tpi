package ar.edu.tfi.dao;
import java.sql.Connection;
import java.util.List;

public interface GenericDao<T> {
    long crear(T t, Connection conn) throws Exception;
    T leer(long id, Connection conn) throws Exception;
    List<T> leerTodos(Connection conn) throws Exception;
    void actualizar(T t, Connection conn) throws Exception;
    void eliminar(long id, Connection conn) throws Exception;
}

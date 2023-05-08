/*
 * Interface que usa el patrón DAO sobre la tabla Persona
 */

package daw.fer.modelo;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author J. Carlos F. Vico <jcarlosvico@maralboran.es>
 */

public interface IFactura {
    
    // Método para obtener todos los registros de la tabla
    List<Factura> getAll() throws SQLException;
    
    // Méodo para obtener un registro a partir de la PK
    Factura findByPk(String pk) throws SQLException;
    
    // Método para insertar un registro
    int insertFactura (Factura factura) throws SQLException;
    
    // Método para insertar varios registros
    int insertFactura (List<Factura> lista) throws SQLException;
    
    // Método para borrar una persona
    int deleteFactura (Factura f) throws SQLException;
    
    // Método para borrar toda la tabla
    int deleteFactura() throws SQLException;
    
    // Método para modificar una persona. Se modifica a la persona que tenga esa 'pk'
    // con los nuevos datos que traiga la persona 'nuevosDatos'
    int updateFactura (String pk, Factura nuevosDatos) throws SQLException;
    
}

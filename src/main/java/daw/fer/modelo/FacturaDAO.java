/*
 * Clase que implementa la interface IPersona
 */
package daw.fer.modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fer
 */
public class FacturaDAO implements IFactura {

    private Connection con = null;

    public FacturaDAO() {
        con = Conexion.getInstance();
    }

    @Override
    public List<Factura> getAll() throws SQLException {
        List<Factura> lista = new ArrayList<>();

        // Preparamos la consulta de datos mediante un objeto Statement
        // ya que no necesitamos parametrizar la sentencia SQL
        try ( Statement st = con.createStatement()) {
            // Ejecutamos la sentencia y obtenemos las filas en el objeto ResultSet
            ResultSet res = st.executeQuery("select * from factura");
            // Ahora construimos la lista, recorriendo el ResultSet y mapeando los datos
            while (res.next()) {
                Factura f = new Factura();
                // Recogemos los datos de la persona, guardamos en un objeto
                f.setpk(res.getString("pk"));
                f.setFechaEmision(res.getDate("fecha_emision").toLocalDate());
                f.setDescripcion(res.getString("descripcion"));
                f.setTotalImporteFactura(res.getDouble("totalImporteFactura"));

                //Añadimos el objeto a la lista
                lista.add(f);
            }
        }

        return lista;
    }

    @Override
    public Factura findByPk(String pk) throws SQLException {

        ResultSet res = null;
        Factura factura = new Factura();

        String sql = "select * from factura where pk=?";

        try ( PreparedStatement prest = con.prepareStatement(sql)) {
            // Preparamos la sentencia parametrizada
            prest.setString(1, pk);

            // Ejecutamos la sentencia y obtenemos las filas en el objeto ResultSet
            res = prest.executeQuery();

            // Nos posicionamos en el primer registro del Resultset. Sólo debe haber una fila
            // si existe esa pk
            if (res.next()) {
                // Recogemos los datos de la persona, guardamos en un objeto

                factura.setpk(res.getString("pk"));
                factura.setFechaEmision(res.getDate("fecha_emision").toLocalDate());
                factura.setDescripcion(res.getString("descripcion"));
                factura.setTotalImporteFactura(res.getDouble("totalImporteFactura"));
                return factura;
            }

            return null;
        }
    }

    @Override
    public int insertFactura(Factura factura) throws SQLException {

        int numFilas = 0;
        String sql = "insert into factura values (?,?,?,?)";

        if (findByPk(factura.getpk()) != null) {
            // Existe un registro con esa pk
            // No se hace la inserción
            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try ( PreparedStatement prest = con.prepareStatement(sql)) {

                // Establecemos los parámetros de la sentencia
                prest.setString(1, factura.getpk());
                prest.setDate(2, Date.valueOf(factura.getFechaEmision()));
                prest.setString(3, factura.getDescripcion());
                prest.setDouble(4, factura.getTotalImporteFactura());

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }

    }

    @Override
    public int insertFactura(List<Factura> lista) throws SQLException {
        int numFilas = 0;

        for (Factura tmp : lista) {
            numFilas += insertFactura(tmp);
        }

        return numFilas;
    }

    @Override
    public int deleteFactura() throws SQLException {

        String sql = "delete from factura";

        int nfilas = 0;

        // Preparamos el borrado de datos  mediante un Statement
        // No hay parámetros en la sentencia SQL
        try ( Statement st = con.createStatement()) {
            // Ejecución de la sentencia
            nfilas = st.executeUpdate(sql);
        }

        // El borrado se realizó con éxito, devolvemos filas afectadas
        return nfilas;

    }

    @Override
    public int deleteFactura(Factura factura) throws SQLException {
        int numFilas = 0;

        String sql = "delete from factura where pk = ?";

        // Sentencia parametrizada
        try ( PreparedStatement prest = con.prepareStatement(sql)) {

            // Establecemos los parámetros de la sentencia
            prest.setString(1, factura.getpk());
            // Ejecutamos la sentencia
            numFilas = prest.executeUpdate();
        }
        return numFilas;
    }

    @Override
    public int updateFactura(String pk, Factura nuevosDatos) throws SQLException {

        int numFilas = 0;
        String sql = "update factura set fecha_emision = ?, descripcion = ?, totalImporteFactura = ? where pk=?";

        if (findByPk(pk) == null) {
            // La persona a actualizar no existe
            return numFilas;
        } else {
            // Instanciamos el objeto PreparedStatement para inserción
            // de datos. Sentencia parametrizada
            try ( PreparedStatement prest = con.prepareStatement(sql)) {

                // Establecemos los parámetros de la sentencia
                prest.setDate(1, Date.valueOf(nuevosDatos.getFechaEmision()));
                prest.setString(2, nuevosDatos.getDescripcion());
                prest.setDouble(3, nuevosDatos.getTotalImporteFactura());
                prest.setString(4, pk);

                numFilas = prest.executeUpdate();
            }
            return numFilas;
        }
    }
}


package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ProveedorDao {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean RegistrarProveedor(Proveedor proV){
        boolean exito = false;
        String sql = "INSERT INTO proveedor(ruc, nombre, telefono, direccion) VALUES (?,?,?,?)";
        try {
            if (!EvaluarDuplicidad(proV) ){
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, proV.getRuc());
            ps.setString(2, proV.getNombre());
            ps.setString(3, proV.getTelefono());
            ps.setString(4, proV.getDireccion());
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Proveedor registrado correctamente");
                    exito = true;
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo registrar el Proveedor");
                }
            } else {
                // El nuevo código de barras ya existe en la base de datos
                JOptionPane.showMessageDialog(null, "No se puede registrar el Proveedor. El código ya existe en la base de datos.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return exito;
    }
    
    public boolean EvaluarDuplicidad(Proveedor proV) {
        boolean exist = false;
        try {
            String sql = "SELECT COUNT(*) FROM proveedor WHERE ruc = ? AND id != ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, proV.getRuc());
            statement.setInt(2, proV.getId());
            ResultSet result = statement.executeQuery();
            if (result.next() && result.getInt(1) > 0) {
                exist = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return exist;
    }
    
    public boolean ModificarProveedor(Proveedor pr){
        String sql = "UPDATE proveedor SET ruc=?, nombre=?, telefono=?, direccion=? WHERE id=?";
       boolean exito = false;
       try {
           if (!EvaluarDuplicidad(pr) ){
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, pr.getRuc());
                statement.setString(2, pr.getNombre());
                statement.setString(3, pr.getTelefono());
                statement.setString(4, pr.getDireccion());
                statement.setInt(5, pr.getId());
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Proveedor actualizado correctamente");
                    exito = true;
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo actualizar el Proveedor");
                }
            } else {
                // El nuevo código de barras ya existe en la base de datos
                JOptionPane.showMessageDialog(null, "No se puede actualizar el Proveedor. El RFC ya existe en la base de datos.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return exito;
    }
    
    public List ListarProveedor(){
        List<Proveedor> Listapr = new ArrayList();
        String sql = "SELECT * FROM proveedor";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {                
                Proveedor pr = new Proveedor();
                pr.setId(rs.getInt("id"));
                pr.setRuc(rs.getString("ruc"));
                pr.setNombre(rs.getString("nombre"));
                pr.setTelefono(rs.getString("telefono"));
                pr.setDireccion(rs.getString("direccion"));
                Listapr.add(pr);
            }
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Listapr;
    }
    
    public boolean EliminarProveedor(int id){
        String sql = "DELETE FROM proveedor WHERE id = ? ";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
}

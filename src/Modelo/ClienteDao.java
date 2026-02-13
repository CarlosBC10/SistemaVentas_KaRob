/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos Blanco
 */
public class ClienteDao {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean RegistrarCliente(Cliente cl){
        boolean exito = false;
        String sql = "INSERT INTO clientes (dni, nombre, telefono, direccion) VALUES (?,?,?,?)";
        try {
            if (!EvaluarDuplicidad(cl) ){
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cl.getDni());
            ps.setString(2, cl.getNombre());
            ps.setString(3, cl.getTelefono());
            ps.setString(4, cl.getDireccion());
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente registrado correctamente");
                    exito = true;
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo registrar el Cliente");
                }
            } else {
                // El nuevo código de barras ya existe en la base de datos
                JOptionPane.showMessageDialog(null, "No se puede registrar el Cliente. El código ya existe en la base de datos.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return exito;
    }
    
     public boolean ModificarCliente(Cliente cl) {
       String sql = "UPDATE clientes SET dni=?, nombre=?, telefono=?, direccion=? WHERE id=?";
       boolean exito = false;
       //Productos pro2 = new Productos();
       //String sql = "UPDATE productos SET codigo=?, nombre=?, proveedor=?, stock=?, precio=? WHERE id=?";
       try {
           if (!EvaluarDuplicidad(cl) ){
                // Actualizar el registro del producto en la base de datos
                //String sql = "UPDATE productos SET codbarras = ?, nombre = ?, stock = ?, proveedor = ? WHERE id = ?";
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, cl.getDni());
                statement.setString(2, cl.getNombre());
                statement.setString(3, cl.getTelefono());
                statement.setString(4, cl.getDireccion());
                statement.setInt(5, cl.getId());
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente actualizado correctamente");
                    exito = true;
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo actualizar el Cliente");
                }
            } else {
                // El nuevo código de barras ya existe en la base de datos
                JOptionPane.showMessageDialog(null, "No se puede actualizar el Cliente. El código ya existe en la base de datos.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return exito;
        }
    
    public boolean EvaluarDuplicidad(Cliente cl) {
        boolean exist = false;
        try {
            String sql = "SELECT COUNT(*) FROM clientes WHERE dni = ? AND id != ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, cl.getDni());
            statement.setInt(2, cl.getId());
            ResultSet result = statement.executeQuery();
            if (result.next() && result.getInt(1) > 0) {
                exist = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return exist;
        }
    
   public List ListarCliente(){
       List<Cliente> ListaCl = new ArrayList();
       String sql = "SELECT * FROM clientes";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           while (rs.next()) {               
               Cliente cl = new Cliente();
               cl.setId(rs.getInt("id"));
               cl.setDni(rs.getString("dni"));
               cl.setNombre(rs.getString("nombre"));
               cl.setTelefono(rs.getString("telefono"));
               cl.setDireccion(rs.getString("direccion"));
               ListaCl.add(cl);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return ListaCl;
   }
   
   public boolean EliminarCliente(int id){
       String sql = "DELETE FROM clientes WHERE id = ?";
       try {
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
           } catch (SQLException ex) {
               System.out.println(ex.toString());
           }
       }
   }
   
   public Cliente Buscarcliente(int dni){
       Cliente cl = new Cliente();
       String sql = "SELECT * FROM clientes WHERE dni = ?";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           ps.setInt(1, dni);
           rs = ps.executeQuery();
           if (rs.next()) {
               cl.setId(rs.getInt("id"));
               cl.setNombre(rs.getString("nombre"));
               cl.setTelefono(rs.getString("telefono"));
               cl.setDireccion(rs.getString("direccion"));
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return cl;
   }
   
}

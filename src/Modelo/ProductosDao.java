
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ProductosDao {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean RegistrarProductos(Productos pro){
        String sql = "INSERT INTO productos (codigo, nombre, proveedor, stock, precio) VALUES (?,?,?,?,?)";
        boolean exito = false;
        //String sql = "INSERT INTO clientes (dni, nombre, telefono, direccion) VALUES (?,?,?,?)";
        try {
            if (!EvaluarDuplicidad(pro) ){
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, pro.getCodigo());
            ps.setString(2, pro.getNombre());
            ps.setInt(3, pro.getProveedor());
            ps.setDouble(4, pro.getStock());
            ps.setDouble(5, pro.getPrecio());
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
    
    public boolean EvaluarDuplicidad(Productos pro) {
        boolean exist = false;
        try {
            String sql = "SELECT COUNT(*) FROM productos WHERE codigo = ? AND id != ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, pro.getCodigo());
            statement.setInt(2, pro.getId());
            ResultSet result = statement.executeQuery();
            if (result.next() && result.getInt(1) > 0) {
                exist = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return exist;
    }

    public boolean ModificarProductos(Productos pro){
       boolean exito = false;
       //Productos pro2 = new Productos();
       String sql = "UPDATE productos SET codigo=?, nombre=?, proveedor=?, stock=?, precio=? WHERE id=?";
       try {
           if (!EvaluarDuplicidad(pro) ){
                // Actualizar el registro del producto en la base de datos
                //String sql = "UPDATE productos SET codbarras = ?, nombre = ?, stock = ?, proveedor = ? WHERE id = ?";
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, pro.getCodigo());
                statement.setString(2, pro.getNombre());
                statement.setInt(3, pro.getProveedor());
                statement.setDouble(4, pro.getStock());
                statement.setDouble(5, pro.getPrecio());
                statement.setInt(6, pro.getId());
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Producto actualizado correctamente");
                    exito = true;
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo actualizar el producto");
                }
            } else {
                // El nuevo código de barras ya existe en la base de datos
                JOptionPane.showMessageDialog(null, "No se puede actualizar el producto. El código de barras ya existe en la base de datos.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return exito;
   }
    
    public List ListarProductos(){
       List<Productos> Listapro = new ArrayList();
       String sql = "SELECT pr.id AS id_proveedor, pr.nombre AS nombre_proveedor, p.* FROM proveedor pr INNER JOIN productos p ON pr.id = p.proveedor ORDER BY p.id DESC";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           while (rs.next()) {               
               Productos pro = new Productos();
               pro.setId(rs.getInt("id"));
               pro.setCodigo(rs.getString("codigo"));
               pro.setNombre(rs.getString("nombre"));
               pro.setProveedor(rs.getInt("id_proveedor"));
               pro.setProveedorPro(rs.getString("nombre_proveedor"));
               pro.setStock(rs.getDouble("stock"));
               pro.setPrecio(rs.getDouble("precio"));
               Listapro.add(pro);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return Listapro;
   }
    
    public boolean EliminarProductos(int id){
       String sql = "DELETE FROM productos WHERE id = ?";
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
    
    public Productos BuscarPro(String cod){
        Productos producto = new Productos();
        String sql = "SELECT * FROM productos WHERE codigo = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cod);
            rs = ps.executeQuery();
            if (rs.next()) {
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getDouble("stock"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return producto;
    }
    public Productos BuscarId(int id){
        Productos pro = new Productos();
        String sql = "SELECT pr.id AS id_proveedor, pr.nombre AS nombre_proveedor, p.* FROM proveedor pr INNER JOIN productos p ON p.proveedor = pr.id WHERE p.id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                pro.setId(rs.getInt("id"));
                pro.setCodigo(rs.getString("codigo"));
                pro.setNombre(rs.getString("nombre"));
                pro.setProveedor(rs.getInt("proveedor"));
                pro.setProveedorPro(rs.getString("nombre_proveedor"));
                pro.setStock(rs.getDouble("stock"));
                pro.setPrecio(rs.getDouble("precio"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return pro;
    }
    public Proveedor BuscarProveedor(String nombre){
        Proveedor pr = new Proveedor();
        String sql = "SELECT * FROM proveedor WHERE nombre = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            if (rs.next()) {
                pr.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return pr;
    }
    
    
    public Config BuscarDatos(){
        Config conf = new Config();
        String sql = "SELECT * FROM config";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                conf.setId(rs.getInt("id"));
                conf.setRuc(rs.getString("ruc"));
                conf.setNombre(rs.getString("nombre"));
                conf.setTelefono(rs.getString("telefono"));
                conf.setDireccion(rs.getString("direccion"));
                conf.setMensaje(rs.getString("mensaje"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return conf;
    }
    
    public boolean ModificarDatos(Config conf){
       String sql = "UPDATE config SET ruc=?, nombre=?, telefono=?, direccion=?, mensaje=? WHERE id=?";
       try {
           ps = con.prepareStatement(sql);
           ps.setString(1, conf.getRuc());
           ps.setString(2, conf.getNombre());
           ps.setString(3, conf.getTelefono());
           ps.setString(4, conf.getDireccion());
           ps.setString(5, conf.getMensaje());
           ps.setInt(6, conf.getId());
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

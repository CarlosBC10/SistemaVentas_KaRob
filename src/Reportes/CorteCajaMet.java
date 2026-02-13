package Reportes;
import Modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author Carlos Blanco
 */
import Modelo.Venta;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
public class CorteCajaMet {
    CorteCaja RC = new CorteCaja();
    //Conexión
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    int r;
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    //int indice = InicioCaja();
    //Login L = new Login();
    
    
    
    public int RegistrarCorteCaja(CorteCaja v){
        String sql = "INSERT INTO cortecaja (vendedor, total, fecha) VALUES (?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            //ps.setString(1, v.getVendedor());
            ps.setString(1, v.getVendedor());
            ps.setDouble(2, v.getTotal());
            ps.setString(3, v.getFecha());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return r;
        
    }
    
    public void ActualizarCorte(){
        String sql = "UPDATE ventas SET Contabilizador =? WHERE Contabilizador =1;";
        try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           ps.setInt(1, 0);
           ps.execute();
       } catch (SQLException e) {
           System.out.println(e.toString());
           
       }finally{
           try {
               con.close();
           } catch (SQLException e) {
               System.out.println(e.toString());
           }
       }
    }
    
    public List ListarCorteCaja(){
       List<CorteCaja> ListarCorteCaja = new ArrayList();
       String sql = "SELECT * FROM cortecaja";
       //String sql = "SELECT ven.id AS folio_vend, ven.nombre, CC.* FROM usuarios ven INNER JOIN cortecaja CC ON ven.id = vendedor";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           while (rs.next()) {               
               CorteCaja Corte = new CorteCaja();
               Corte.setFolio(rs.getInt("folio"));
               Corte.setVendedor(rs.getString("vendedor"));
               Corte.setTotal(rs.getDouble("total"));
               Corte.setFecha(rs.getString("fecha"));
               ListarCorteCaja.add(Corte);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return ListarCorteCaja;
   }
    
    public CorteCaja BuscarCorteCaja(int id){
        CorteCaja Cc = new CorteCaja();
        String sql = "SELECT * FROM cortecaja WHERE folio = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Cc.setFolio(rs.getInt("folio"));
                Cc.setVendedor(rs.getString("vendedor"));
                Cc.setTotal(rs.getDouble("total"));
                Cc.setFecha(rs.getString("fecha"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Cc;
    }
    
}


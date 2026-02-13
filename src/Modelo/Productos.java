
package Modelo;


public class Productos {
    private int id;
    private String codigo;
    private String nombre;
    private int proveedor;
    private String proveedorPro;
    private double stock;
    private double precio;
    
    public Productos(){
        
    }

    public Productos(int id, String codigo, String nombre, int proveedor, String proveedorPro, double stock, double precio) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.proveedor = proveedor;
        this.proveedorPro = proveedorPro;
        this.stock = stock;
        this.precio = precio;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getProveedor() {
        return proveedor;
    }

    public void setProveedor(int proveedor) {
        this.proveedor = proveedor;
    }

    public String getProveedorPro() {
        return proveedorPro;
    }

    public void setProveedorPro(String proveedorPro) {
        this.proveedorPro = proveedorPro;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

   
}

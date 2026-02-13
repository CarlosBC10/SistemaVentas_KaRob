
package Reportes;

/**
 *
 * @author Carlos Blanco
 */
public class CorteCaja {
    private String vendedor;
    //private int vendedor;
    private double total;
    private String fecha;
    private int indice;
    private int folio;

    public CorteCaja() {
        
    }

    public CorteCaja(String vendedor, double total, String fecha, int indice, int folio) {
        this.vendedor = vendedor;
        this.total = total;
        this.fecha = fecha;
        this.indice = indice;
        this.folio = folio;
    }

    

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }
    
}



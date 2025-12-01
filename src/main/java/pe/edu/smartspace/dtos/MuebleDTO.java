package pe.edu.smartspace.dtos;

public class MuebleDTO {

    private Long idMueble;
    private String nombre;
    private String categoria;
    private String dimension;
    private String estilo;
    private Double precio;
    private Boolean sostenibilidad;
    private String proveedor;

    public Long getIdMueble() {
        return idMueble;
    }

    public void setIdMueble(Long idMueble) {
        this.idMueble = idMueble;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Boolean getSostenibilidad() {
        return sostenibilidad;
    }

    public void setSostenibilidad(Boolean sostenibilidad) {
        this.sostenibilidad = sostenibilidad;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }
}

package pe.edu.smartspace.entities;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "muebles")
@Getter @Setter
public class Mueble {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMueble;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "categoria", length = 50)
    private String categoria;

    @Column(name = "dimension", length = 50)
    private String dimension;

    @Column(name = "estilo", length = 50)
    private String estilo;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "sostenibilidad", nullable = false)
    private Boolean sostenibilidad;

    @Column(name = "proveedor", length = 100)
    private String proveedor;

    public Mueble() {
    }

    public Mueble(Long idMueble, String nombre, String categoria, String dimension, String estilo, Double precio, Boolean sostenibilidad, String proveedor) {
        this.idMueble = idMueble;
        this.nombre = nombre;
        this.categoria = categoria;
        this.dimension = dimension;
        this.estilo = estilo;
        this.precio = precio;
        this.sostenibilidad = sostenibilidad;
        this.proveedor = proveedor;
    }

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

package pe.edu.smartspace.dtos;

import java.util.Date;

public class DisenoDTO {

    private Long idDiseno;
    private String nombre;
    private Date fechaCreacion;
    private String estado;
    private String archivoUrl;

    private Long userId;      // solo ID del dueño
    private String username;  // para mostrar en Angular si lo necesitas

    public Long getIdDiseno() { return idDiseno; }
    public void setIdDiseno(Long idDiseno) { this.idDiseno = idDiseno; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getArchivoUrl() { return archivoUrl; }
    public void setArchivoUrl(String archivoUrl) { this.archivoUrl = archivoUrl; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}

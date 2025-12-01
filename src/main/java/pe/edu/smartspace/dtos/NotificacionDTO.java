package pe.edu.smartspace.dtos;

import pe.edu.smartspace.entities.Usuario;

import java.time.LocalDateTime;

public class NotificacionDTO {

    private Long idNotificacion;
    private String mensaje;
    private Boolean leido;
    private LocalDateTime fechaEnvio;
    private Usuario usuario;

    public Long getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Boolean getLeido() {
        return leido;
    }

    public void setLeido(Boolean leido) {
        this.leido = leido;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

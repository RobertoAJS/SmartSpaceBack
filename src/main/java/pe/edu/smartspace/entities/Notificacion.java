package pe.edu.smartspace.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Getter
@Setter

public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotificacion;

    @Column(name = "mensaje", length = 250, nullable = false)
    private String mensaje;

    @Column(name = "leido", nullable = false)
    private Boolean leido = false;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    // Relación con Usuario
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    public Notificacion() {
    }

    public Notificacion(Long idNotificacion, String mensaje, Boolean leido, LocalDateTime fechaEnvio, Usuario usuario) {
        this.idNotificacion = idNotificacion;
        this.mensaje = mensaje;
        this.leido = leido;
        this.fechaEnvio = fechaEnvio;
        this.usuario = usuario;
    }

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

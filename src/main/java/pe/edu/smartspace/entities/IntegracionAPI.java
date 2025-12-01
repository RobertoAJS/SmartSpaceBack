package pe.edu.smartspace.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "integraciones_api")
@Getter
@Setter
@Builder
public class IntegracionAPI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIntegracion;

    @Column(name = "tipo_servicio", length = 100, nullable = false)
    private String tipoServicio;

    @Column(name = "api_key", length = 260, nullable = false)
    private String apiKey;

    @Column(name = "estado", length = 20)
    private String estado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;


    public IntegracionAPI() {
    }

    public IntegracionAPI(Long idIntegracion, String tipoServicio, String apiKey, String estado, LocalDateTime fechaCreacion, Usuario usuario) {
        this.idIntegracion = idIntegracion;
        this.tipoServicio = tipoServicio;
        this.apiKey = apiKey;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.usuario = usuario;
    }

    public Long getIdIntegracion() {
        return idIntegracion;
    }

    public void setIdIntegracion(Long idIntegracion) {
        this.idIntegracion = idIntegracion;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

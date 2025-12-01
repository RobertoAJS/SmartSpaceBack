package pe.edu.smartspace.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Getter
@Setter

public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    @Column(name = "monto", nullable = false)
    private Double monto;

    @Column(name = "metodo_pago", length = 50, nullable = false)
    private String metodoPago;

    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;

    // Relación con Usuario
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;


    public Pago() {
    }

    public Pago(Long idPago, Double monto, String metodoPago, LocalDateTime fechaPago, Usuario usuario) {
        this.idPago = idPago;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.fechaPago = fechaPago;
        this.usuario = usuario;
    }

    public Long getIdPago() {
        return idPago;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

package pe.edu.smartspace.dtos;

import pe.edu.smartspace.entities.Usuario;

import java.time.LocalDateTime;

public class PagoDTO {

    private Long idPago;
    private Double monto;
    private String metodoPago;
    private LocalDateTime fechaPago;
    private Usuario usuario;

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

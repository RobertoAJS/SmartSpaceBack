package pe.edu.smartspace.servicesinterfaces;

import pe.edu.smartspace.entities.Pago;
import java.util.List;

public interface IPagoService {
    List<Pago> listar();
    void registrar(Pago p);
    Pago buscarPorId(Long id);
    void modificar(Pago p);
    void eliminar(Long id);
}

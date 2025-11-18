package pe.edu.smartspace.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.smartspace.entities.Pago;
import pe.edu.smartspace.repositories.IPagoRepository;
import pe.edu.smartspace.servicesinterfaces.IPagoService;

import java.util.List;

@Service
public class PagoServiceImpl implements IPagoService {

    @Autowired
    private IPagoRepository pR;

    @Override
    public List<Pago> listar() {
        return pR.findAll();
    }

    @Override
    public void registrar(Pago p) {
        pR.save(p);
    }

    @Override
    public Pago buscarPorId(Long id) {
        return pR.findById(id).orElse(null);
    }

    @Override
    public void modificar(Pago p) {
        pR.save(p);
    }

    @Override
    public void eliminar(Long id) {
        pR.deleteById(id);
    }
}

package pe.edu.smartspace.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.smartspace.entities.Mueble;
import pe.edu.smartspace.repositories.IMuebleRepository;
import pe.edu.smartspace.servicesinterfaces.IMuebleService;

import java.util.List;

@Service
public class MuebleServiceImpl implements IMuebleService {


    @Autowired
    private IMuebleRepository mR;

    @Override
    public List<Mueble> listar() {
        return mR.findAll();
    }

    @Override
    public void registrar(Mueble mueble) {
        mR.save(mueble);
    }

    @Override
    public Mueble buscarPorId(Long id) {
        return mR.findById(id).orElse(null);
    }

    @Override
    public void modificar(Mueble mueble) {
        mR.save(mueble);
    }

    @Override
    public void eliminar(Long id) {
        mR.deleteById(id);
    }

    @Override
    public List<Mueble> buscarPorCategoria(String categoria) {
        return mR.findByCategoriaContainingIgnoreCase(categoria);
    }
}

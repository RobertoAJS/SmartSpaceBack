package pe.edu.smartspace.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.smartspace.entities.Favorito;
import pe.edu.smartspace.repositories.IFavoritoRepository;
import pe.edu.smartspace.servicesinterfaces.IFavoritoService;

import java.util.List;

@Service
public class FavoritoServiceImpl implements IFavoritoService {

    @Autowired
    private IFavoritoRepository fR;


    @Override
    public List<Favorito> listar() {
        return fR.findAll();
    }

    @Override
    public void registrar(Favorito f) {
        fR.save(f);
    }

    @Override
    public Favorito buscarPorId(Long id) {
        return fR.findById(id).orElse(null);
    }

    @Override
    public void modificar(Favorito f) {
        fR.save(f);
    }

    @Override
    public void eliminar(Long id) {
        fR.deleteById(id);
    }
}
